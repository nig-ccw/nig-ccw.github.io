# refresh 

```java
//AbstractApplicationContext#refresh
public void refresh() throws BeansException, IllegalStateException {
   synchronized (this.startupShutdownMonitor) {
      //A
      prepareRefresh();//1
      ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();//2
      prepareBeanFactory(beanFactory);//3
      try {
         //B
         postProcessBeanFactory(beanFactory);//1
         invokeBeanFactoryPostProcessors(beanFactory);//2
         registerBeanPostProcessors(beanFactory);//3
         initMessageSource();//4
         initApplicationEventMulticaster();//5
         onRefresh();//6
         registerListeners();//7
         finishBeanFactoryInitialization(beanFactory);//8
         finishRefresh();//9
      } catch (BeansException ex) {
         //C
         destroyBeans();//1
         cancelRefresh(ex);//2
         throw ex;
      } finally {
         //D
         resetCommonCaches();//1
      }
   }
}
```

## A 无异常区

### prepareRefresh

```java
protected void prepareRefresh() {
   // 切换成激活
   this.startupDate = System.currentTimeMillis(); //启动时间设置为当前时间戳
   this.closed.set(false);//closed=false
   this.active.set(true);//active=true
   initPropertySources();//1
   getEnvironment().validateRequiredProperties();//2
   if (this.earlyApplicationListeners == null) {
      //存储 pre-refresh 的 ApplicationListeners
      this.earlyApplicationListeners = new LinkedHashSet<>(this.applicationListeners);//参考1
   } else {
      //重新设置 ApplicationListener 为 pre-refresh 状态
      this.applicationListeners.clear();
      this.applicationListeners.addAll(this.earlyApplicationListeners);
   }
   this.earlyApplicationEvents = new LinkedHashSet<>();
}
```

> 参考1： [SpringApplication](../SpringApplication.md) 中 ApplicationListener 部分

####initPropertySources

```java
//AbstractApplicationContext#initPropertySources
protected void initPropertySources() {
}

//GenericWebApplicationContext#initPropertySources
@Override
protected void initPropertySources() {
  ConfigurableEnvironment env = getEnvironment();
  if (env instanceof ConfigurableWebEnvironment) {
    ((ConfigurableWebEnvironment) env).initPropertySources(this.servletContext, null);
  }
}
//StandardServletEnvironment#initPropertySources
@Override
public void initPropertySources(@Nullable ServletContext servletContext, @Nullable ServletConfig servletConfig) {
  WebApplicationContextUtils.initServletPropertySources(getPropertySources(), servletContext, servletConfig);
}
```

####validateRequiredProperties

```java
//AbstractEnvironment#validateRequiredProperties
private final ConfigurablePropertyResolver propertyResolver =
			new PropertySourcesPropertyResolver(this.propertySources);
@Override
public void validateRequiredProperties() throws MissingRequiredPropertiesException {
   this.propertyResolver.validateRequiredProperties();
}
//PropertySourcesPropertyResolver的父类 AbstractPropertyResolver#validateRequiredProperties
@Override
public void validateRequiredProperties() {
  MissingRequiredPropertiesException ex = new MissingRequiredPropertiesException();
  for (String key : this.requiredProperties) {
    if (this.getProperty(key) == null) {
      ex.addMissingRequiredProperty(key);
    }
  }
  if (!ex.getMissingRequiredProperties().isEmpty()) {
    throw ex;
  }
}
```

### obtainFreshBeanFactory

```java
//AbstractApplicationContext#obtainFreshBeanFactory
protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
   refreshBeanFactory();
   return getBeanFactory();
}
protected abstract void refreshBeanFactory() throws BeansException, IllegalStateException;
@Override
public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

```

#### refreshBeanFactory

```java
//GenericApplicationContext#refreshBeanFactory
@Override
protected final void refreshBeanFactory() throws IllegalStateException {
   if (!this.refreshed.compareAndSet(false, true)) {
      throw new IllegalStateException("只支持调用一次 refresh");
   }
   this.beanFactory.setSerializationId(getId());//DefaultListableBeanFactory
}
```

####getBeanFactory

```java
//GenericApplicationContext#getBeanFactory
@Override
public final ConfigurableListableBeanFactory getBeanFactory() {
   return this.beanFactory;
}
```

### prepareBeanFactory

```java
//AbstractApplicationContext#prepareBeanFactory
protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
   beanFactory.setBeanClassLoader(getClassLoader());//
   beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver(beanFactory.getBeanClassLoader()));//
   beanFactory.addPropertyEditorRegistrar(new ResourceEditorRegistrar(this, getEnvironment()));//
   beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));//
  
   beanFactory.ignoreDependencyInterface(EnvironmentAware.class);
   beanFactory.ignoreDependencyInterface(EmbeddedValueResolverAware.class);
   beanFactory.ignoreDependencyInterface(ResourceLoaderAware.class);
   beanFactory.ignoreDependencyInterface(ApplicationEventPublisherAware.class);
   beanFactory.ignoreDependencyInterface(MessageSourceAware.class);
   beanFactory.ignoreDependencyInterface(ApplicationContextAware.class);

   beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
   beanFactory.registerResolvableDependency(ResourceLoader.class, this);
   beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);
   beanFactory.registerResolvableDependency(ApplicationContext.class, this);

   beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(this));

  if (beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)) {
      beanFactory.addBeanPostProcessor(new LoadTimeWeaverAwareProcessor(beanFactory));
      beanFactory.setTempClassLoader(new ContextTypeMatchClassLoader(beanFactory.getBeanClassLoader()));
   }

   if (!beanFactory.containsLocalBean(ENVIRONMENT_BEAN_NAME)) {
      beanFactory.registerSingleton(ENVIRONMENT_BEAN_NAME, getEnvironment());
   }
   if (!beanFactory.containsLocalBean(SYSTEM_PROPERTIES_BEAN_NAME)) {
      beanFactory.registerSingleton(SYSTEM_PROPERTIES_BEAN_NAME, getEnvironment().getSystemProperties());
   }
   if (!beanFactory.containsLocalBean(SYSTEM_ENVIRONMENT_BEAN_NAME)) {
      beanFactory.registerSingleton(SYSTEM_ENVIRONMENT_BEAN_NAME, getEnvironment().getSystemEnvironment());
   }
}
```

> 参考 [DefaultListableBeanFactory](../../BeanFactory/DefaultListableBeanFactory.md) 的相应方法实现

## B 异常捕获区

### postProcessBeanFactory

```java
//AbstractApplicationContext
protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
}

//AnnotationConfigServletWebServerApplicationContext
@Override
protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
  super.postProcessBeanFactory(beanFactory);
  if (this.basePackages != null && this.basePackages.length > 0) {
    this.scanner.scan(this.basePackages);
  }
  if (!this.annotatedClasses.isEmpty()) {
    this.reader.register(ClassUtils.toClassArray(this.annotatedClasses));
  }
}
//ServletWebServerApplicationContext
@Override
protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
  beanFactory.addBeanPostProcessor(new WebApplicationContextServletContextAwareProcessor(this));
  beanFactory.ignoreDependencyInterface(ServletContextAware.class);
  registerWebApplicationScopes();
}
//GenericWebApplicationContext
@Override
protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
  if (this.servletContext != null) {
    beanFactory.addBeanPostProcessor(new ServletContextAwareProcessor(this.servletContext));
    beanFactory.ignoreDependencyInterface(ServletContextAware.class);
  }
  WebApplicationContextUtils.registerWebApplicationScopes(beanFactory, this.servletContext);
  WebApplicationContextUtils.registerEnvironmentBeans(beanFactory, this.servletContext);
}
```

### invokeBeanFactoryPostProcessors



### registerBeanPostProcessors



### initMessageSource



### initApplicationEventMulticaster



### onRefresh



### registerListeners



### finishBeanFactoryInitialization



### finishRefresh



## C 异常处理区

### destroyBeans



### cancelRefresh



## D 最终执行区

###resetCommonCaches