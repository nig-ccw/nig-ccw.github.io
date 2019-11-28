# SpringApplication 

## SpringApplication 实例化

```java
public class SpringApplication {
 public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
  this.resourceLoader = resourceLoader;//可自定义
  this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));//可自定义
  this.webApplicationType = WebApplicationType.deduceFromClasspath();//可先指定
  setInitializers((Collection)getSpringFactoriesInstances(ApplicationContextInitializer.class));//可通过 spring.factories 文件 ApplicationContextInitializer 指定
  setListeners((Collection)getSpringFactoriesInstances(ApplicationListener.class));//可通过 spring.factories 文件 ApplicationListener 指定
  this.mainApplicationClass = deduceMainApplicationClass();//可先指定
 }
}
```

## SpringApplication 启动流程

```java
public class SpringApplication {
 public ConfigurableApplicationContext run(String... args) {
   StopWatch stopWatch = new StopWatch();
   stopWatch.start();
   ConfigurableApplicationContext context = null;//可配置应用上下文
   Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();//Spring Boot 异常报告器
   configureHeadlessProperty();//配置 Headless 属性
   SpringApplicationRunListeners listeners = getRunListeners(args);//Spring 应用运行监听器（实例化）
   listeners.starting();//正在启动
   try {
    ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);//应用参数
    ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);//可配置环境
    configureIgnoreBeanInfo(environment);//配置忽略 Bean 信息
    Banner printedBanner = printBanner(environment);//打印横幅
    context = createApplicationContext();//创建应用上下文
    exceptionReporters = getSpringFactoriesInstances(SpringBootExceptionReporter.class, new Class[] { ConfigurableApplicationContext.class }, context);//实例化 Spring Boot 异常报告器
    prepareContext(context, environment, listeners, applicationArguments, printedBanner);//准备应用上下文
    refreshContext(context);//刷新应用上下文
    afterRefresh(context, applicationArguments);//刷新应用上下文之后的处理
    stopWatch.stop();
    if (this.logStartupInfo) {
     new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
    }
    listeners.started(context);//已启动
    callRunners(context, applicationArguments);//调用应用运行器
   } catch (Throwable ex) {
    handleRunFailure(context, ex, exceptionReporters, listeners);//处理运行失败
    throw new IllegalStateException(ex);
   }
   try {
    listeners.running(context);//正在运行
   } catch (Throwable ex) {
    handleRunFailure(context, ex, exceptionReporters, null);//处理运行失败，没监听器
    throw new IllegalStateException(ex);
   }
   return context;
 }
}
```

## run 方法详解

### SpringApplicationRunListener

- Spring 应用运行监听器 - 在 run 方法被调用
- EventPublishingRunListener 是 SpringApplicationRunListener 的唯一实现类

- 之后调用 SimpleApplicationEventMulticaster#multicastEvent 发布事件

| Spring应用启动监听器       | 发布方式          | Spring应用事件类型         |
| -------------------------------- | --------------------------- | ----------------------------------- |
| starting()            | multicastEvent       | ApplicationStartingEvent      |
| environmentPrepared(environment) | multicastEvent       | ApplicationEnvironmentPreparedEvent |
| contextPrepared(context)     | multicastEvent       | ApplicationContextInitializedEvent |
| contextLoaded(context)      | multicastEvent       | ApplicationPreparedEvent      |
| started(context)         | publishEvent        | ApplicationStartedEvent       |
| running(context)         | publishEvent        | ApplicationReadyEvent        |
| failed(context,Throwable)    | publishEvent multicastEvent | ApplicationFailedEvent       |

```java
public class EventPublishingRunListener implements SpringApplicationRunListener, Ordered {
 private final SpringApplication application;
 private final String[] args;
 private final SimpleApplicationEventMulticaster initialMulticaster;
 public EventPublishingRunListener(SpringApplication application, String[] args) {
  this.application = application;
  this.args = args;
  this.initialMulticaster = new SimpleApplicationEventMulticaster();
  for (ApplicationListener<?> listener : application.getListeners()) {
   this.initialMulticaster.addApplicationListener(listener);
  }
 }

 @Override
 public int getOrder() {
  return 0;
 }

 @Override
 public void starting() {
  this.initialMulticaster.multicastEvent(new ApplicationStartingEvent(this.application, this.args));
 }

 @Override
 public void environmentPrepared(ConfigurableEnvironment environment) {
  this.initialMulticaster.multicastEvent(new ApplicationEnvironmentPreparedEvent(this.application, this.args, environment));
 }

 @Override
 public void contextPrepared(ConfigurableApplicationContext context) {
  this.initialMulticaster.multicastEvent(new ApplicationContextInitializedEvent(this.application, this.args, context));
 }

 @Override
 public void contextLoaded(ConfigurableApplicationContext context) {
  for (ApplicationListener<?> listener : this.application.getListeners()) {
   if (listener instanceof ApplicationContextAware) {
    ((ApplicationContextAware) listener).setApplicationContext(context);
   }
   context.addApplicationListener(listener);
  }
  this.initialMulticaster.multicastEvent(new ApplicationPreparedEvent(this.application, this.args, context));
 }

 @Override
 public void started(ConfigurableApplicationContext context) {
  context.publishEvent(new ApplicationStartedEvent(this.application, this.args, context));
 }

 @Override
 public void running(ConfigurableApplicationContext context) {
  context.publishEvent(new ApplicationReadyEvent(this.application, this.args, context));
 }

 @Override
 public void failed(ConfigurableApplicationContext context, Throwable exception) {
  ApplicationFailedEvent event = new ApplicationFailedEvent(this.application, this.args, context, exception);
  if (context != null && context.isActive()) {
   context.publishEvent(event);
  }else {
   if (context instanceof AbstractApplicationContext) {
    for (ApplicationListener<?> listener : ((AbstractApplicationContext) context).getApplicationListeners()) {
     this.initialMulticaster.addApplicationListener(listener);
    }
   }
   this.initialMulticaster.setErrorHandler(new LoggingErrorHandler());
   this.initialMulticaster.multicastEvent(event);
  }
 }

 private static class LoggingErrorHandler implements ErrorHandler {
  private static final Log logger = LogFactory.getLog(EventPublishingRunListener.class);
  @Override
  public void handleError(Throwable throwable) {
   logger.warn("Error calling ApplicationEventListener", throwable);
  }
 }
}
```

### ConfigurableEnvironment

```java
private ConfigurableEnvironment prepareEnvironment(SpringApplicationRunListeners listeners, ApplicationArguments applicationArguments) {
  //Create and configure the environment
  ConfigurableEnvironment environment = getOrCreateEnvironment();
  configureEnvironment(environment, applicationArguments.getSourceArgs());
  ConfigurationPropertySources.attach(environment);
  listeners.environmentPrepared(environment);//环境已准备
  bindToSpringApplication(environment);
  if (!this.isCustomEnvironment) {
   environment = new EnvironmentConverter(getClassLoader()).convertEnvironmentIfNecessary(environment, deduceEnvironmentClass());
  }
  ConfigurationPropertySources.attach(environment);
  return environment;
}
```

### ApplicationContextInitializer

- DelegatingApplicationContextInitializer
- SharedMetadataReaderFactoryContextInitializer
- ContextIdApplicationContextInitializer
- ConfigurationWarningsApplicationContextInitializer 
- RSocketPortInfoApplicationContextInitializer 
- ServerPortInfoApplicationContextInitializer 
- ConditionEvaluationReportLoggingListener

```java
//SpringApplication
private void prepareContext(ConfigurableApplicationContext context, ConfigurableEnvironment environment,SpringApplicationRunListeners listeners, ApplicationArguments applicationArguments, Banner printedBanner) {
 applyInitializers(context);//在此次被应用
}
//主要是调用所有应用上下文初始化器的 initialize 方法
protected void applyInitializers(ConfigurableApplicationContext context) {
 for (ApplicationContextInitializer initializer : getInitializers()) {
  Class<?> requiredType = GenericTypeResolver.resolveTypeArgument(initializer.getClass(), ApplicationContextInitializer.class);
  initializer.initialize(context);
 }
}
//设置应用上下文初始化器的值
public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
 setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));
}
```

### ApplicationListener

- RSocketPortInfoApplicationContextInitializer.Listener 监听 RSocketServerInitializedEvent
- ServerPortInfoApplicationContextInitializer 监听 WebServerInitializedEvent
- ConditionEvaluationReportLoggingListener.ConditionEvaluationReportListener 监听 ContextRefreshedEvent、ApplicationFailedEvent
- ConfigFileApplicationListener 监听 ApplicationEnvironmentPreparedEvent、ApplicationPreparedEvent
- AnsiOutputApplicationListener 监听 ApplicationEnvironmentPreparedEvent
- LoggingApplicationListener 监听 ApplicationStartingEvent、ApplicationEnvironmentPreparedEvent、ApplicationPreparedEvent、ContextClosedEvent、ApplicationFailedEvent
- ClasspathLoggingApplicationListener 监听 ApplicationEnvironmentPreparedEvent、ApplicationFailedEvent
- BackgroundPreinitializer 监听 ApplicationReadyEvent、ApplicationFailedEvent
- DelegatingApplicationListener 监听 ApplicationEnvironmentPreparedEvent
- ParentContextCloserApplicationListener 监听 ParentContextAvailableEvent
- ClearCachesApplicationListener 监听 ContextRefreshedEvent
- FileEncodingApplicationListener 监听 ApplicationEnvironmentPreparedEvent
- LiquibaseServiceLocatorApplicationListener 监听 ApplicationStartingEvent

> 相应的应用监听器（ApplicationListener）只关注感兴趣的应用事件（ApplicationEvent）

```java
//已知 EventPublishingRunListener 调用 SimpleApplicationEventMulticaster#multicastEvent
//SimpleApplicationEventMulticaster
@Override
public void multicastEvent(ApplicationEvent event) {
  multicastEvent(event, resolveDefaultEventType(event));//1
}
@Override
public void multicastEvent(final ApplicationEvent event, @Nullable ResolvableType eventType) {
 ResolvableType type = (eventType != null ? eventType : resolveDefaultEventType(event));
 Executor executor = getTaskExecutor();
 for (ApplicationListener<?> listener : getApplicationListeners(event, type)) {
  if (executor != null) {//null
   executor.execute(() -> invokeListener(listener, event));
  } else {
   invokeListener(listener, event);//2
  }
 }
}
protected void invokeListener(ApplicationListener<?> listener, ApplicationEvent event) {
 ErrorHandler errorHandler = getErrorHandler();
 if (errorHandler != null) {
  try {
   doInvokeListener(listener, event);
  } catch (Throwable err) {
   errorHandler.handleError(err);
  }
 } else {
  doInvokeListener(listener, event);//3
 }
}
private void doInvokeListener(ApplicationListener listener, ApplicationEvent event) {
 try {
  listener.onApplicationEvent(event);//4 这里说明了发布事件后会调用 ApplicationListener#onApplicationEvent
 } catch (ClassCastException ex) {
  //...
 }
}
```

参考 [ConfigFileApplicationListener](ApplicationListener/ConfigFileApplicationListener.md)

### ConfigurableApplicationContext

#### refresh

```java
//SpringApplication
private void refreshContext(ConfigurableApplicationContext context) {
  refresh(context);
  if (this.registerShutdownHook) {
   try {
     context.registerShutdownHook();
   } catch (AccessControlException ex) {
   }
  }
}

protected void refresh(ApplicationContext applicationContext) {
 ((AbstractApplicationContext) applicationContext).refresh();
}
//ServletWebServerApplicationContext
@Override
public final void refresh() throws BeansException, IllegalStateException {
 try {
  super.refresh();
 }catch (RuntimeException ex) {
  stopAndReleaseWebServer();
  throw ex;
 }
}
//AbstractApplicationContext
@Override
public void refresh() throws BeansException, IllegalStateException {
 synchronized (this.startupShutdownMonitor) {
  prepareRefresh();
  ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
  prepareBeanFactory(beanFactory);
  try {
   postProcessBeanFactory(beanFactory);
   invokeBeanFactoryPostProcessors(beanFactory);
   registerBeanPostProcessors(beanFactory);
   initMessageSource();
   initApplicationEventMulticaster();
   onRefresh();
   registerListeners();
   finishBeanFactoryInitialization(beanFactory);
   finishRefresh();
  }catch (BeansException ex) {
   destroyBeans();
   cancelRefresh(ex);
   throw ex;
  }finally {
   resetCommonCaches();
  }
 }
}
```

参考 [refresh](ConfigurableApplicationContext/refresh.md) 

