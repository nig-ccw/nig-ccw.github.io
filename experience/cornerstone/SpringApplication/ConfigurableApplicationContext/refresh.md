# refresh 

```java
//AbstractApplicationContext#refresh
public void refresh() throws BeansException, IllegalStateException {
 synchronized (this.startupShutdownMonitor) {
  //无异常区
  prepareRefresh();//准备刷新
  ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();//获取新的 Bean 工厂
  prepareBeanFactory(beanFactory);//准备 Bean 工厂
  try {
   //异常捕获区
   postProcessBeanFactory(beanFactory);//后置处理 Bean 工厂
   invokeBeanFactoryPostProcessors(beanFactory);//调用 Bean 工厂后置处理器
   registerBeanPostProcessors(beanFactory);//注册 Bean 后置处理器
   initMessageSource();//初始化消息源
   initApplicationEventMulticaster();//初始化应用事件广播器
   onRefresh();//特殊刷新
   registerListeners();//注册监听器
   finishBeanFactoryInitialization(beanFactory);//完成 Bean 工厂实例化
   finishRefresh();//完成刷新
  } catch (BeansException ex) {
   //异常处理区
   destroyBeans();//销毁 Bean
   cancelRefresh(ex);//取消刷新
   throw ex;
  } finally {
   //最终执行区
   resetCommonCaches();//重置常用缓存
  }
 }
}
```

## 无异常区

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
private final ConfigurablePropertyResolver propertyResolver = new PropertySourcesPropertyResolver(this.propertySources);
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

## 异常捕获区

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

```java
//AbstractApplicationContext
protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){
 PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors());
 if (beanFactory.getTempClassLoader() == null && beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)) {
  beanFactory.addBeanPostProcessor(new LoadTimeWeaverAwareProcessor(beanFactory));
  beanFactory.setTempClassLoader(new ContextTypeMatchClassLoader(beanFactory.getBeanClassLoader()));
 }
}
//PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors
public static void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors) {
  // Invoke BeanDefinitionRegistryPostProcessors first, if any.
  Set<String> processedBeans = new HashSet<>();
  if (beanFactory instanceof BeanDefinitionRegistry) {
   BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
   List<BeanFactoryPostProcessor> regularPostProcessors = new ArrayList<>();
   List<BeanDefinitionRegistryPostProcessor> registryProcessors = new ArrayList<>();
  for (BeanFactoryPostProcessor postProcessor : beanFactoryPostProcessors) {
   if (postProcessor instanceof BeanDefinitionRegistryPostProcessor) {
    BeanDefinitionRegistryPostProcessor registryProcessor = (BeanDefinitionRegistryPostProcessor) postProcessor;
    registryProcessor.postProcessBeanDefinitionRegistry(registry);
    registryProcessors.add(registryProcessor);
   }else {
    regularPostProcessors.add(postProcessor);
   }
  }
  List<BeanDefinitionRegistryPostProcessor> currentRegistryProcessors = new ArrayList<>();
  // First, invoke the BeanDefinitionRegistryPostProcessors that implement PriorityOrdered.
  String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
  for (String ppName : postProcessorNames) {
   if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
    currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
    processedBeans.add(ppName);
   }
  }
  sortPostProcessors(currentRegistryProcessors, beanFactory);
  registryProcessors.addAll(currentRegistryProcessors);
  invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
  currentRegistryProcessors.clear();
  // Next, invoke the BeanDefinitionRegistryPostProcessors that implement Ordered.
  postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
  for (String ppName : postProcessorNames) {
   if (!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class)) {
    currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
    processedBeans.add(ppName);
   }
  }
  sortPostProcessors(currentRegistryProcessors, beanFactory);
  registryProcessors.addAll(currentRegistryProcessors);
  invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
  currentRegistryProcessors.clear();
  // Finally, invoke all other BeanDefinitionRegistryPostProcessors until no further ones appear.
  boolean reiterate = true;
  while (reiterate) {
   reiterate = false;
   postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
   for (String ppName : postProcessorNames) {
    if (!processedBeans.contains(ppName)) {
     currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
     processedBeans.add(ppName);
     reiterate = true;
    }
   }
   sortPostProcessors(currentRegistryProcessors, beanFactory);
   registryProcessors.addAll(currentRegistryProcessors);
   invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
   currentRegistryProcessors.clear();
  }
  // Now, invoke the postProcessBeanFactory callback of all processors handled so far.
  invokeBeanFactoryPostProcessors(registryProcessors, beanFactory);
  invokeBeanFactoryPostProcessors(regularPostProcessors, beanFactory);
 }else {
  // Invoke factory processors registered with the context instance.
  invokeBeanFactoryPostProcessors(beanFactoryPostProcessors, beanFactory);
 }
 String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);
 // Separate between BeanFactoryPostProcessors that implement PriorityOrdered, Ordered, and the rest.
 List<BeanFactoryPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();
 List<String> orderedPostProcessorNames = new ArrayList<>();
 List<String> nonOrderedPostProcessorNames = new ArrayList<>();
 for (String ppName : postProcessorNames) {
  if (processedBeans.contains(ppName)) {
  // skip - already processed in first phase above
  } else if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
   priorityOrderedPostProcessors.add(beanFactory.getBean(ppName, BeanFactoryPostProcessor.class));
  } else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
   orderedPostProcessorNames.add(ppName);
  } else {
   nonOrderedPostProcessorNames.add(ppName);
  }
 }
  // First, invoke the BeanFactoryPostProcessors that implement PriorityOrdered.
 sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
 invokeBeanFactoryPostProcessors(priorityOrderedPostProcessors, beanFactory);
  // Next, invoke the BeanFactoryPostProcessors that implement Ordered.
 List<BeanFactoryPostProcessor> orderedPostProcessors = new ArrayList<>(orderedPostProcessorNames.size());
 for (String postProcessorName : orderedPostProcessorNames) {
  orderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
 }
 sortPostProcessors(orderedPostProcessors, beanFactory);
 invokeBeanFactoryPostProcessors(orderedPostProcessors, beanFactory);
  // Finally, invoke all other BeanFactoryPostProcessors.
 List<BeanFactoryPostProcessor> nonOrderedPostProcessors = new ArrayList<>(nonOrderedPostProcessorNames.size());
 for (String postProcessorName : nonOrderedPostProcessorNames) {
  nonOrderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
 }
 invokeBeanFactoryPostProcessors(nonOrderedPostProcessors, beanFactory);
 beanFactory.clearMetadataCache();
}

private static void invokeBeanFactoryPostProcessors(Collection<? extends BeanFactoryPostProcessor> postProcessors, ConfigurableListableBeanFactory beanFactory) {
  for (BeanFactoryPostProcessor postProcessor : postProcessors) {
  postProcessor.postProcessBeanFactory(beanFactory);
  }
}
```

### registerBeanPostProcessors

```java
//AbstractApplicationContext
protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
 PostProcessorRegistrationDelegate.registerBeanPostProcessors(beanFactory, this);
}
//PostProcessorRegistrationDelegate#registerBeanPostProcessors
public static void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory, AbstractApplicationContext applicationContext) {
 String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);
 int beanProcessorTargetCount = beanFactory.getBeanPostProcessorCount() + 1 + postProcessorNames.length;
 beanFactory.addBeanPostProcessor(new BeanPostProcessorChecker(beanFactory, beanProcessorTargetCount));
 // Separate between BeanPostProcessors that implement PriorityOrdered,Ordered, and the rest.
 List<BeanPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();
 List<BeanPostProcessor> internalPostProcessors = new ArrayList<>();
 List<String> orderedPostProcessorNames = new ArrayList<>();
 List<String> nonOrderedPostProcessorNames = new ArrayList<>();
 for (String ppName : postProcessorNames) {
  if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
   BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
   priorityOrderedPostProcessors.add(pp);
   if (pp instanceof MergedBeanDefinitionPostProcessor) {
    internalPostProcessors.add(pp);
   }
  }else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
   orderedPostProcessorNames.add(ppName);
  }else {
   nonOrderedPostProcessorNames.add(ppName);
  }
 }
 // First, register the BeanPostProcessors that implement PriorityOrdered.
 sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
 registerBeanPostProcessors(beanFactory, priorityOrderedPostProcessors);

 // Next, register the BeanPostProcessors that implement Ordered.
 List<BeanPostProcessor> orderedPostProcessors = new ArrayList<>(orderedPostProcessorNames.size());
 for (String ppName : orderedPostProcessorNames) {
  BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
  orderedPostProcessors.add(pp);
  if (pp instanceof MergedBeanDefinitionPostProcessor) {
   internalPostProcessors.add(pp);
  }
 }
 sortPostProcessors(orderedPostProcessors, beanFactory);
 registerBeanPostProcessors(beanFactory, orderedPostProcessors);

 // Now, register all regular BeanPostProcessors.
 List<BeanPostProcessor> nonOrderedPostProcessors = new ArrayList<>(nonOrderedPostProcessorNames.size());
 for (String ppName : nonOrderedPostProcessorNames) {
  BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
  nonOrderedPostProcessors.add(pp);
  if (pp instanceof MergedBeanDefinitionPostProcessor) {
   internalPostProcessors.add(pp);
  }
 }
 registerBeanPostProcessors(beanFactory, nonOrderedPostProcessors);

 // Finally, re-register all internal BeanPostProcessors.
 sortPostProcessors(internalPostProcessors, beanFactory);
 registerBeanPostProcessors(beanFactory, internalPostProcessors);

 beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(applicationContext));
}

private static void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory, List<BeanPostProcessor> postProcessors) {
 for (BeanPostProcessor postProcessor : postProcessors) {
  beanFactory.addBeanPostProcessor(postProcessor);
 }
}
```

### initMessageSource

```java
//AbstractApplicationContext
protected void initMessageSource() {
 ConfigurableListableBeanFactory beanFactory = getBeanFactory();
 if (beanFactory.containsLocalBean(MESSAGE_SOURCE_BEAN_NAME)) {
  this.messageSource = beanFactory.getBean(MESSAGE_SOURCE_BEAN_NAME, MessageSource.class);
  // Make MessageSource aware of parent MessageSource.
  if (this.parent != null && this.messageSource instanceof HierarchicalMessageSource) {
   HierarchicalMessageSource hms = (HierarchicalMessageSource) this.messageSource;
   if (hms.getParentMessageSource() == null) {
    // Only set parent context as parent MessageSource if no parent MessageSource
    // registered already.
    hms.setParentMessageSource(getInternalParentMessageSource());
   }
  }
  if (logger.isTraceEnabled()) {
   logger.trace("Using MessageSource [" + this.messageSource + "]");
  }
 }
 else {
  // Use empty MessageSource to be able to accept getMessage calls.
  DelegatingMessageSource dms = new DelegatingMessageSource();
  dms.setParentMessageSource(getInternalParentMessageSource());
  this.messageSource = dms;
  beanFactory.registerSingleton(MESSAGE_SOURCE_BEAN_NAME, this.messageSource);
  if (logger.isTraceEnabled()) {
   logger.trace("No '" + MESSAGE_SOURCE_BEAN_NAME + "' bean, using [" + this.messageSource + "]");
  }
 }
}
```

### initApplicationEventMulticaster

```java
//AbstractApplicationContext
protected void initApplicationEventMulticaster() {
 ConfigurableListableBeanFactory beanFactory = getBeanFactory();
 if (beanFactory.containsLocalBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME)) {
  this.applicationEventMulticaster = beanFactory.getBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);
 } else {
  this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
  beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, this.applicationEventMulticaster);
 }
}
```

### onRefresh

```java
//AbstractApplicationContext
protected void onRefresh() throws BeansException {
}
//ServletWebServerApplicationContext
@Override
protected void onRefresh() {
 super.onRefresh();
 try {
  createWebServer();//参考
 }catch (Throwable ex) {
  throw new ApplicationContextException("Unable to start web server", ex);
 }
}
//GenericWebApplicationContext
@Override
protected void onRefresh() {
 this.themeSource = UiApplicationContextUtils.initThemeSource(this);
}
```

> 参考：[createWebServer]()

### registerListeners

```java
//AbstractApplicationContext
protected void registerListeners() {
 // Register statically specified listeners first.
 for (ApplicationListener<?> listener : getApplicationListeners()) {
 getApplicationEventMulticaster().addApplicationListener(listener);
 }

 // Do not initialize FactoryBeans here: We need to leave all regular beans
 // uninitialized to let post-processors apply to them!
 String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
 for (String listenerBeanName : listenerBeanNames) {
  getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
 }

 // Publish early application events now that we finally have a multicaster...
 Set<ApplicationEvent> earlyEventsToProcess = this.earlyApplicationEvents;
 this.earlyApplicationEvents = null;
 if (earlyEventsToProcess != null) {
  for (ApplicationEvent earlyEvent : earlyEventsToProcess) {
   getApplicationEventMulticaster().multicastEvent(earlyEvent);
  }
 }
}
```

### finishBeanFactoryInitialization

```java
//AbstractApplicationContext
protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory){
 // Initialize conversion service for this context.
 if (beanFactory.containsBean(CONVERSION_SERVICE_BEAN_NAME) && beanFactory.isTypeMatch(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class)) {
  beanFactory.setConversionService(beanFactory.getBean(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class));
 }

 // Register a default embedded value resolver if no bean post-processor
 // (such as a PropertyPlaceholderConfigurer bean) registered any before:
 // at this point, primarily for resolution in annotation attribute values.
 if (!beanFactory.hasEmbeddedValueResolver()) {
  beanFactory.addEmbeddedValueResolver(strVal -> getEnvironment().resolvePlaceholders(strVal));
 }

 // Initialize LoadTimeWeaverAware beans early to allow for registering their transformers early.
 String[] weaverAwareNames = beanFactory.getBeanNamesForType(LoadTimeWeaverAware.class, false, false);
 for (String weaverAwareName : weaverAwareNames) {
  getBean(weaverAwareName);
 }

 // Stop using the temporary ClassLoader for type matching.
 beanFactory.setTempClassLoader(null);

 // Allow for caching all bean definition metadata, not expecting further changes.
 beanFactory.freezeConfiguration();

 // Instantiate all remaining (non-lazy-init) singletons.
 beanFactory.preInstantiateSingletons();
}
```

### finishRefresh

```java
//AbstractApplicationContext
protected void finishRefresh() {
 clearResourceCaches();
 initLifecycleProcessor();
 getLifecycleProcessor().onRefresh();
 publishEvent(new ContextRefreshedEvent(this));
 LiveBeansView.registerApplicationContext(this);
}
//DefaultResourceLoader
public void clearResourceCaches() {
 this.resourceCaches.clear();
}
//AbstractApplicationContext
protected void initLifecycleProcessor() {
 ConfigurableListableBeanFactory beanFactory = getBeanFactory();
 if (beanFactory.containsLocalBean(LIFECYCLE_PROCESSOR_BEAN_NAME)) {
  this.lifecycleProcessor = beanFactory.getBean(LIFECYCLE_PROCESSOR_BEAN_NAME, LifecycleProcessor.class);
 }else {
  DefaultLifecycleProcessor defaultProcessor = new DefaultLifecycleProcessor();
  defaultProcessor.setBeanFactory(beanFactory);
  this.lifecycleProcessor = defaultProcessor;
  beanFactory.registerSingleton(LIFECYCLE_PROCESSOR_BEAN_NAME, this.lifecycleProcessor);
 }
}
//DefaultLifecycleProcessor
@Override
public void onRefresh() {
 startBeans(true);
 this.running = true;
}
private void startBeans(boolean autoStartupOnly) {
 Map<String, Lifecycle> lifecycleBeans = getLifecycleBeans();
 Map<Integer, LifecycleGroup> phases = new HashMap<>();
 lifecycleBeans.forEach((beanName, bean) -> {
  if (!autoStartupOnly || (bean instanceof SmartLifecycle && ((SmartLifecycle) bean).isAutoStartup())) {
   int phase = getPhase(bean);
   LifecycleGroup group = phases.get(phase);
   if (group == null) {
    group = new LifecycleGroup(phase, this.timeoutPerShutdownPhase, lifecycleBeans, autoStartupOnly);
    phases.put(phase, group);
   }
   group.add(beanName, bean);
  }
 });
 if (!phases.isEmpty()) {
  List<Integer> keys = new ArrayList<>(phases.keySet());
  Collections.sort(keys);
  for (Integer key : keys) {
   phases.get(key).start();
  }
 }
}
//AbstractApplicationContext
protected void publishEvent(Object event, @Nullable ResolvableType eventType) {
 // Decorate event as an ApplicationEvent if necessary
 ApplicationEvent applicationEvent;
 if (event instanceof ApplicationEvent) {
  applicationEvent = (ApplicationEvent) event;
 }else {
  applicationEvent = new PayloadApplicationEvent<>(this, event);
  if (eventType == null) {
   eventType = ((PayloadApplicationEvent<?>) applicationEvent).getResolvableType();
  }
 }

 // Multicast right now if possible - or lazily once the multicaster is initialized
 if (this.earlyApplicationEvents != null) {
  this.earlyApplicationEvents.add(applicationEvent);
 }else {
  getApplicationEventMulticaster().multicastEvent(applicationEvent, eventType);
 }

 // Publish event via parent context as well...
 if (this.parent != null) {
  if (this.parent instanceof AbstractApplicationContext) {
   ((AbstractApplicationContext) this.parent).publishEvent(event, eventType);
  }else {
   this.parent.publishEvent(event);
  }
 }
}
//LiveBeansView
static void registerApplicationContext(ConfigurableApplicationContext applicationContext) {
 String mbeanDomain = applicationContext.getEnvironment().getProperty(MBEAN_DOMAIN_PROPERTY_NAME);
 if (mbeanDomain != null) {
  synchronized (applicationContexts) {
   if (applicationContexts.isEmpty()) {
    try {
     MBeanServer server = ManagementFactory.getPlatformMBeanServer();
     applicationName = applicationContext.getApplicationName();
     server.registerMBean(new LiveBeansView(), new ObjectName(mbeanDomain, MBEAN_APPLICATION_KEY, applicationName));
    }catch (Throwable ex) {
     throw new ApplicationContextException("Failed to register LiveBeansView MBean", ex);
    }
   }
   applicationContexts.add(applicationContext);
  }
 }
}
```

## 异常处理区

### destroyBeans

```java
//AbstractApplicationContext
protected void destroyBeans() {
 getBeanFactory().destroySingletons();
}
```

### cancelRefresh

```java
//AbstractApplicationContext
protected void cancelRefresh(BeansException ex) {
 this.active.set(false);
}
//GenericApplicationContext
@Override
protected void cancelRefresh(BeansException ex) {
 this.beanFactory.setSerializationId(null);
 super.cancelRefresh(ex);
}
```

## 最终执行区

###resetCommonCaches

```java
protected void resetCommonCaches() {
 ReflectionUtils.clearCache();
 AnnotationUtils.clearCache();
 ResolvableType.clearCache();
 CachedIntrospectionResults.clearClassLoader(getClassLoader());
}
```