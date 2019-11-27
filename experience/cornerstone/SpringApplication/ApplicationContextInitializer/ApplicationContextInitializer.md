# ApplicationContextInitializer

- DelegatingApplicationContextInitializer

```java

@Override
public void initialize(ConfigurableApplicationContext context) {
   ConfigurableEnvironment environment = context.getEnvironment();
   List<Class<?>> initializerClasses = getInitializerClasses(environment);
   if (!initializerClasses.isEmpty()) {
      applyInitializerClasses(context, initializerClasses);
   }
}

private List<Class<?>> getInitializerClasses(ConfigurableEnvironment env) {
  String classNames = env.getProperty(PROPERTY_NAME);
  List<Class<?>> classes = new ArrayList<>();
  if (StringUtils.hasLength(classNames)) {
    for (String className : StringUtils.tokenizeToStringArray(classNames, ",")) {
      classes.add(getInitializerClass(className));
    }
  }
  return classes;
}

private void applyInitializerClasses(ConfigurableApplicationContext context, List<Class<?>> initializerClasses) {
  Class<?> contextClass = context.getClass();
  List<ApplicationContextInitializer<?>> initializers = new ArrayList<>();
  for (Class<?> initializerClass : initializerClasses) {
    initializers.add(instantiateInitializer(contextClass, initializerClass));
  }
  applyInitializers(context, initializers);
}

private void applyInitializers(ConfigurableApplicationContext context,
			List<ApplicationContextInitializer<?>> initializers) {
  initializers.sort(new AnnotationAwareOrderComparator());
  for (ApplicationContextInitializer initializer : initializers) {
    initializer.initialize(context);
  }
}

private ApplicationContextInitializer<?> instantiateInitializer(Class<?> contextClass, Class<?> initializerClass) {
  	Class<?> requireContextClass = GenericTypeResolver.resolveTypeArgument(initializerClass,
ApplicationContextInitializer.class);
  return (ApplicationContextInitializer<?>) BeanUtils.instantiateClass(initializerClass);
}
```

- SharedMetadataReaderFactoryContextInitializer

```java
@Override
public void initialize(ConfigurableApplicationContext applicationContext) {
  applicationContext.addBeanFactoryPostProcessor(new 	CachingMetadataReaderFactoryPostProcessor());
}
```

- ContextIdApplicationContextInitializer

```java
@Override
public void initialize(ConfigurableApplicationContext applicationContext) {
   ContextId contextId = getContextId(applicationContext);
   applicationContext.setId(contextId.getId());
   applicationContext.getBeanFactory().registerSingleton(ContextId.class.getName(), contextId);
}

private ContextId getContextId(ConfigurableApplicationContext applicationContext) {
  ApplicationContext parent = applicationContext.getParent();
  if (parent != null && parent.containsBean(ContextId.class.getName())) {
    return parent.getBean(ContextId.class).createChildId();
  }
  return new ContextId(getApplicationId(applicationContext.getEnvironment()));
}

private String getApplicationId(ConfigurableEnvironment environment) {
  String name = environment.getProperty("spring.application.name");
  return StringUtils.hasText(name) ? name : "application";
}
```

- ConfigurationWarningsApplicationContextInitializer

```java
@Override
public void initialize(ConfigurableApplicationContext context) {
   context.addBeanFactoryPostProcessor(new ConfigurationWarningsPostProcessor(getChecks()));
}
protected Check[] getChecks() {
  return new Check[] { new ComponentScanPackageCheck() };
}
```

- RSocketPortInfoApplicationContextInitializer

```java
@Override
public void initialize(ConfigurableApplicationContext applicationContext) {
   applicationContext.addApplicationListener(new Listener(applicationContext));
}
```

- ServerPortInfoApplicationContextInitializer

```java
@Override
public void initialize(ConfigurableApplicationContext applicationContext) {
   applicationContext.addApplicationListener(this);
}
```

- ConditionEvaluationReportLoggingListener

```java
@Override
public void initialize(ConfigurableApplicationContext applicationContext) {
   this.applicationContext = applicationContext;
   applicationContext.addApplicationListener(new ConditionEvaluationReportListener());
   if (applicationContext instanceof GenericApplicationContext) {
      // Get the report early in case the context fails to load
      this.report = ConditionEvaluationReport.get(this.applicationContext.getBeanFactory());
   }
}
```

