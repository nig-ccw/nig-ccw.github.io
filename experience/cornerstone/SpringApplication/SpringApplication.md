# SpringApplication 

## SpringApplication 实例化

```java
public class SpringApplication {
 public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
  this.resourceLoader = resourceLoader;
  this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
  this.webApplicationType = WebApplicationType.deduceFromClasspath();
  setInitializers((Collection)getSpringFactoriesInstances(ApplicationContextInitializer.class));
  setListeners((Collection)getSpringFactoriesInstances(ApplicationListener.class));
  this.mainApplicationClass = deduceMainApplicationClass();
 }
}
```

## SpringApplication 启动流程

```java
public class SpringApplication {
 public ConfigurableApplicationContext run(String... args) {
   StopWatch stopWatch = new StopWatch();
   stopWatch.start();
   ConfigurableApplicationContext context = null;
   Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
   configureHeadlessProperty();
   SpringApplicationRunListeners listeners = getRunListeners(args);
   listeners.starting();
   try {
    ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
    ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
    configureIgnoreBeanInfo(environment);
    Banner printedBanner = printBanner(environment);
    context = createApplicationContext();
    exceptionReporters = getSpringFactoriesInstances(SpringBootExceptionReporter.class, new Class[] { ConfigurableApplicationContext.class }, context);
    prepareContext(context, environment, listeners, applicationArguments, printedBanner);
    refreshContext(context);
    afterRefresh(context, applicationArguments);
    stopWatch.stop();
    if (this.logStartupInfo) {
     new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
    }
    listeners.started(context);
    callRunners(context, applicationArguments);
   } catch (Throwable ex) {
    handleRunFailure(context, ex, exceptionReporters, listeners);
    throw new IllegalStateException(ex);
   }
  
   try {
    listeners.running(context);
   } catch (Throwable ex) {
    handleRunFailure(context, ex, exceptionReporters, null);
    throw new IllegalStateException(ex);
   }
   return context;
 }
}
```

## run 方法详解

### EventPublishingRunListener

- SpringApplicationRunListener 唯一实现类 EventPublishingRunListener
- 调用 SimpleApplicationEventMulticaster#multicastEvent 或 ConfigurableApplicationContext#publishEvent 发布事件

| Spring应用启动监听器       | 发布方式          | Spring应用事件类型         |
| -------------------------------- | --------------------------- | ----------------------------------- |
| starting()            | multicastEvent       | ApplicationStartingEvent      |
| environmentPrepared(environment) | multicastEvent       | ApplicationEnvironmentPreparedEvent |
| contextPrepared(context)     | multicastEvent       | ApplicationContextInitializedEvent |
| contextLoaded(context)      | multicastEvent       | ApplicationPreparedEvent      |
| started(context)         | publishEvent        | ApplicationStartedEvent       |
| running(context)         | publishEvent        | ApplicationReadyEvent        |
| failed(context,Throwable)    | publishEvent multicastEvent | ApplicationFailedEvent       |

### ConfigurableEnvironment

```java
private ConfigurableEnvironment prepareEnvironment(SpringApplicationRunListeners listeners, ApplicationArguments applicationArguments) {
  //Create and configure the environment
  ConfigurableEnvironment environment = getOrCreateEnvironment();
  configureEnvironment(environment, applicationArguments.getSourceArgs());
  ConfigurationPropertySources.attach(environment);
  listeners.environmentPrepared(environment);
  bindToSpringApplication(environment);
  if (!this.isCustomEnvironment) {
   environment = new EnvironmentConverter(getClassLoader()).convertEnvironmentIfNecessary(environment, deduceEnvironmentClass());
  }
  ConfigurationPropertySources.attach(environment);
  return environment;
}
```

### ApplicationContextInitializer

- spring-boot-2.2.1.RELEASE.jar!/META-INF/spring.factories
    - ConfigurationWarningsApplicationContextInitializer - 3
    - ContextIdApplicationContextInitializer - 2
    - DelegatingApplicationContextInitializer - 0
    - RSocketPortInfoApplicationContextInitializer - 4
    - ServerPortInfoApplicationContextInitializer- 5
- spring-boot-autoconfigure-2.2.1.RELEASE.jar!/META-INF/spring.factories
    - SharedMetadataReaderFactoryContextInitializer- 1
    - ConditionEvaluationReportLoggingListener - 6

```java
private void prepareContext(ConfigurableApplicationContext context, ConfigurableEnvironment environment,SpringApplicationRunListeners listeners, ApplicationArguments applicationArguments, Banner printedBanner) {
 applyInitializers(context);
}

protected void applyInitializers(ConfigurableApplicationContext context) {
 for (ApplicationContextInitializer initializer : getInitializers()) {
  Class<?> requiredType = GenericTypeResolver.resolveTypeArgument(initializer.getClass(), ApplicationContextInitializer.class);
  initializer.initialize(context);
 }
}

public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
 setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));
}
```

### ApplicationListener

- spring-boot-2.2.1.RELEASE.jar!/META-INF/spring.factories
  - ClearCachesApplicationListener - 10
    - ContextRefreshedEvent
  - ParentContextCloserApplicationListener - 9
    - ParentContextAvailableEvent
  - FileEncodingApplicationListener - 11
    - ApplicationEnvironmentPreparedEvent
  - AnsiOutputApplicationListener - 4
    - ApplicationEnvironmentPreparedEvent
  - DelegatingApplicationListener - 8
    - ApplicationEnvironmentPreparedEvent
  - ConfigFileApplicationListener - 3
    - ApplicationEnvironmentPreparedEvent
    - ApplicationPreparedEvent
  - ClasspathLoggingApplicationListener - 6
  - LoggingApplicationListener - 5
    - ApplicationStartingEvent
    - ApplicationEnvironmentPreparedEvent
    - ApplicationPreparedEvent
    - ContextClosedEvent
    - ApplicationFailedEvent
  - LiquibaseServiceLocatorApplicationListener - 12
    - ApplicationStartingEvent
- spring-boot-autoconfigure-2.2.1.RELEASE.jar!/META-INF/spring.factories
  - BackgroundPreinitializer - 7
    - ApplicationReadyEvent
    - ApplicationFailedEvent
- ServerPortInfoApplicationContextInitializer 1
  - WebServerInitializedEvent
- ConditionEvaluationReportLoggingListener.ConditionEvaluationReportListener 2
  - ContextRefreshedEvent
  - ApplicationFailedEvent
- RSocketPortInfoApplicationContextInitializer.Listener 0
  - RSocketServerInitializedEvent

```java
protected <T> T getProperty(String key, Class<T> targetValueType, boolean resolveNestedPlaceholders) {
  if (this.propertySources != null) {
   for (PropertySource<?> propertySource : this.propertySources) {
     if (logger.isTraceEnabled()) {
      logger.trace("Searching for key '" + key + "' in PropertySource '" + propertySource.getName() + "'");
     }
     Object value = propertySource.getProperty(key);
     if (value != null) {
      if (resolveNestedPlaceholders && value instanceof String) {
        value = resolveNestedPlaceholders((String) value);
      }
      logKeyFound(key, propertySource, value);
      return convertValueIfNecessary(value, targetValueType);
     }
   }
  }
  if (logger.isTraceEnabled()) {
   logger.trace("Could not find key '" + key + "' in any property source");
  }
  return null;
}
```

```properties
AbstractEnvironment#getProperty 调用 PropertySourcesPropertyResolver#getProperty
```

```java
//EventPublishingRunListener 通过 SimpleApplicationEventMulticaster#multicastEvent 发布事件
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
 if (errorHandler != null) {//null
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
  listener.onApplicationEvent(event);//4
 } catch (ClassCastException ex) {
  //...
 }
}
```

参考 [ConfigFileApplicationListener](ApplicationListener/ConfigFileApplicationListener.md)

### ConfigurableApplicationContext

#### refresh

```
//SpringApplication
private void refreshContext(ConfigurableApplicationContext context) {
  refresh(context);
  if (this.registerShutdownHook) {
   try {
     context.registerShutdownHook();
   } catch (AccessControlException ex) {
     // Not allowed in some environments.
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

