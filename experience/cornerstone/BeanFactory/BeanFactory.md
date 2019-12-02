# BeanFactory

##接口

- BeanFactory

```java
public interface BeanFactory {
 String FACTORY_BEAN_PREFIX = "&"; //FactoryBean 实例名称前缀
 Object getBean(String name) throws BeansException;
 <T> T getBean(String name, Class<T> requiredType) throws BeansException;
 Object getBean(String name, Object... args) throws BeansException;
 <T> T getBean(Class<T> requiredType) throws BeansException;
 <T> T getBean(Class<T> requiredType, Object... args) throws BeansException;
 <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType);
 <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType);
 boolean containsBean(String name);
 boolean isSingleton(String name) throws NoSuchBeanDefinitionException;
 boolean isPrototype(String name) throws NoSuchBeanDefinitionException;
 boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException;
 boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException;
 @Nullable Class<?> getType(String name) throws NoSuchBeanDefinitionException;
 @Nullable Class<?> getType(String name, boolean allowFactoryBeanInit) throws NoSuchBeanDefinitionException;
 String[] getAliases(String name);
}
```

- ListableBeanFactory

```java
public interface ListableBeanFactory extends BeanFactory {
 boolean containsBeanDefinition(String beanName);
 int getBeanDefinitionCount();
 String[] getBeanDefinitionNames();
 String[] getBeanNamesForType(ResolvableType type);
 String[] getBeanNamesForType(ResolvableType type, boolean includeNonSingletons, boolean allowEagerInit);
 String[] getBeanNamesForType(@Nullable Class<?> type);
 String[] getBeanNamesForType(@Nullable Class<?> type, boolean includeNonSingletons, boolean allowEagerInit);
 <T> Map<String, T> getBeansOfType(@Nullable Class<T> type) throws BeansException;
 <T> Map<String, T> getBeansOfType(@Nullable Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException;
 String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType);
 Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException;
 @Nullable <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) throws NoSuchBeanDefinitionException;
}
```

- HierarchicalBeanFactory

```java
public interface HierarchicalBeanFactory extends BeanFactory {
 @Nullable BeanFactory getParentBeanFactory();
 boolean containsLocalBean(String name);
}
```

- AutowireCapableBeanFactory

```java
public interface AutowireCapableBeanFactory extends BeanFactory {
 <T> T createBean(Class<T> beanClass) throws BeansException;
 void autowireBean(Object existingBean) throws BeansException;
 Object configureBean(Object existingBean, String beanName) throws BeansException;
 Object createBean(Class<?> beanClass, int autowireMode, boolean dependencyCheck) throws BeansException;
 Object autowire(Class<?> beanClass, int autowireMode, boolean dependencyCheck) throws BeansException;
 void autowireBeanProperties(Object existingBean, int autowireMode, boolean dependencyCheck) throws BeansException;
 void applyBeanPropertyValues(Object existingBean, String beanName) throws BeansException;
 Object initializeBean(Object existingBean, String beanName) throws BeansException;
 Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;
 Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
 void destroyBean(Object existingBean);
 <T> NamedBeanHolder<T> resolveNamedBean(Class<T> requiredType) throws BeansException;
 Object resolveBeanByName(String name, DependencyDescriptor descriptor) throws BeansException;
 @Nullable Object resolveDependency(DependencyDescriptor descriptor, @Nullable String requestingBeanName) throws BeansException;
 @Nullable Object resolveDependency(DependencyDescriptor descriptor, @Nullable String requestingBeanName,@Nullable Set<String> autowiredBeanNames, @Nullable TypeConverter typeConverter) throws BeansException;
}
```

- ConfigurableBeanFactory

```java
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
 void setParentBeanFactory(BeanFactory parentBeanFactory) throws IllegalStateException;
 void setBeanClassLoader(@Nullable ClassLoader beanClassLoader);
 @Nullable ClassLoader getBeanClassLoader();
 void setTempClassLoader(@Nullable ClassLoader tempClassLoader);
 @Nullable ClassLoader getTempClassLoader();
 void setCacheBeanMetadata(boolean cacheBeanMetadata);
 boolean isCacheBeanMetadata();
 void setBeanExpressionResolver(@Nullable BeanExpressionResolver resolver);
 @Nullable BeanExpressionResolver getBeanExpressionResolver();
 void setConversionService(@Nullable ConversionService conversionService);
 @Nullable ConversionService getConversionService();
 void addPropertyEditorRegistrar(PropertyEditorRegistrar registrar);
 void registerCustomEditor(Class<?> requiredType, Class<? extends PropertyEditor> propertyEditorClass);
 void copyRegisteredEditorsTo(PropertyEditorRegistry registry);
 void setTypeConverter(TypeConverter typeConverter);
 TypeConverter getTypeConverter();
 void addEmbeddedValueResolver(StringValueResolver valueResolver);
 boolean hasEmbeddedValueResolver();
 @Nullable String resolveEmbeddedValue(String value);
 void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
 int getBeanPostProcessorCount();
 void registerScope(String scopeName, Scope scope);
 String[] getRegisteredScopeNames();
 @Nullable Scope getRegisteredScope(String scopeName);
 AccessControlContext getAccessControlContext();
 void copyConfigurationFrom(ConfigurableBeanFactory otherFactory);
 void registerAlias(String beanName, String alias) throws BeanDefinitionStoreException;
 void resolveAliases(StringValueResolver valueResolver);
 BeanDefinition getMergedBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;
 boolean isFactoryBean(String name) throws NoSuchBeanDefinitionException;
 void setCurrentlyInCreation(String beanName, boolean inCreation);
 boolean isCurrentlyInCreation(String beanName);
 void registerDependentBean(String beanName, String dependentBeanName);
 String[] getDependentBeans(String beanName);
 String[] getDependenciesForBean(String beanName);
 void destroyBean(String beanName, Object beanInstance);
 void destroyScopedBean(String beanName);
 void destroySingletons();
}
```

```java
public interface SingletonBeanRegistry {
 void registerSingleton(String beanName, Object singletonObject);
 @Nullable Object getSingleton(String beanName);
 boolean containsSingleton(String beanName);
 String[] getSingletonNames();
 int getSingletonCount();
 Object getSingletonMutex();
}
```

## 抽象类

- AbstractBeanFactory - doGetBean

```java
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {
 protected <T> T doGetBean(final String name, @Nullable final Class<T> requiredType, @Nullable final Object[] args, boolean typeCheckOnly) throws BeansException {
  final String beanName = transformedBeanName(name);
  Object bean;
  //先查单例缓存
  Object sharedInstance = getSingleton(beanName);
  if (sharedInstance != null && args == null) {
   bean = getObjectForBeanInstance(sharedInstance, name, beanName, null);
  }else {
   if (isPrototypeCurrentlyInCreation(beanName)) {//循环引用抛异常
    throw new BeanCurrentlyInCreationException(beanName);
   }
   //父级BeanFactory存在，当前BeanFactory不包含BeanDefinition，交由父级BeanFactory处理
   BeanFactory parentBeanFactory = getParentBeanFactory();
   if (parentBeanFactory != null && !containsBeanDefinition(beanName)) {
    String nameToLookup = originalBeanName(name);
    if (parentBeanFactory instanceof AbstractBeanFactory) {
     return ((AbstractBeanFactory) parentBeanFactory).doGetBean(nameToLookup, requiredType, args, typeCheckOnly);
    }else if (args != null) {
     return (T) parentBeanFactory.getBean(nameToLookup, args);
	}else if (requiredType != null) {
	 return parentBeanFactory.getBean(nameToLookup, requiredType);
    }else {
	 return (T) parentBeanFactory.getBean(nameToLookup);
	}
   }
   if (!typeCheckOnly) {
    markBeanAsCreated(beanName);
   }
   try {
    //多级存在，将BeanDefinition合并
	final RootBeanDefinition mbd = getMergedLocalBeanDefinition(beanName);
	checkMergedBeanDefinition(mbd, beanName, args);
    //先处理依赖
    String[] dependsOn = mbd.getDependsOn();
    if (dependsOn != null) {
     for (String dep : dependsOn) {
	  if (isDependent(beanName, dep)) {
       //循环依赖抛异常
	  }
      registerDependentBean(dep, beanName);
	  try {
       getBean(dep);
	  }catch (NoSuchBeanDefinitionException ex) {
	  }
     }
    }
    if (mbd.isSingleton()) {//单例Bean实例化
     sharedInstance = getSingleton(beanName, () -> {
      try {
        return createBean(beanName, mbd, args);//1
      }catch (BeansException ex) {
        destroySingleton(beanName);
        throw ex;
      }
     });
     bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
    }else if (mbd.isPrototype()) {//原型Bean实例化
     Object prototypeInstance = null;
     try {
      beforePrototypeCreation(beanName);
      prototypeInstance = createBean(beanName, mbd, args);
     }finally {
      afterPrototypeCreation(beanName);
     }
     bean = getObjectForBeanInstance(prototypeInstance, name, beanName, mbd);
    }else {
     String scopeName = mbd.getScope();
     final Scope scope = this.scopes.get(scopeName);
     if (scope == null) {
      //作用域不存在
     }
     try {
      Object scopedInstance = scope.get(beanName, () -> {
       beforePrototypeCreation(beanName);
       try {
	    return createBean(beanName, mbd, args);
       }finally {
		afterPrototypeCreation(beanName);
       }
      });
      bean = getObjectForBeanInstance(scopedInstance, name, beanName, mbd);
     }catch (IllegalStateException ex) {
      //作用域没激活
     }
    }
   }catch (BeansException ex) {
    cleanupAfterBeanCreationFailure(beanName);
    throw ex;
   }
  }
  //进行类型转换操作
  if (requiredType != null && !requiredType.isInstance(bean)) {
   try {
    T convertedBean = getTypeConverter().convertIfNecessary(bean, requiredType);
    if (convertedBean == null) {
     throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
    }
    return convertedBean;
   }catch (TypeMismatchException ex) {
    if (logger.isTraceEnabled()) {
	 //类型转换失败
    }
    throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
   }
  }
  return (T) bean;
 }
 protected abstract Object createBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args) throws BeanCreationException;
}
```

- AbstractAutowireCapableBeanFactory - doCreateBean

```java
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
 @Override
 protected Object createBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args) throws BeanCreationException {
  RootBeanDefinition mbdToUse = mbd;
  Class<?> resolvedClass = resolveBeanClass(mbd, beanName);
  if (resolvedClass != null && !mbd.hasBeanClass() && mbd.getBeanClassName() != null) {
   mbdToUse = new RootBeanDefinition(mbd);
   mbdToUse.setBeanClass(resolvedClass);
  }
  try {
   mbdToUse.prepareMethodOverrides();
  }catch (BeanDefinitionValidationException ex) {
   //方法重写验证失败
  }
  try {
   Object bean = resolveBeforeInstantiation(beanName, mbdToUse);//BeanPostProcessor切入点
   if (bean != null) {
    return bean;
   }
  }catch (Throwable ex) {
	//BeanPostProcessor失败
  }
  try {
   Object beanInstance = doCreateBean(beanName, mbdToUse, args);//2
   return beanInstance;
  }catch (BeanCreationException | ImplicitlyAppearedSingletonException ex) {
	 throw ex;
  }catch (Throwable ex) {
  }
 }
 
 protected Object doCreateBean(final String beanName, final RootBeanDefinition mbd, final @Nullable Object[] args) throws BeanCreationException {
  BeanWrapper instanceWrapper = null;
  if (mbd.isSingleton()) {
   instanceWrapper = this.factoryBeanInstanceCache.remove(beanName);
  }
  if (instanceWrapper == null) {
   instanceWrapper = createBeanInstance(beanName, mbd, args);//创建实例
  }
  final Object bean = instanceWrapper.getWrappedInstance();
  Class<?> beanType = instanceWrapper.getWrappedClass();
  if (beanType != NullBean.class) {
   mbd.resolvedTargetType = beanType;
  }
  //允许PostProcessor修改合并的BeanDefinition
  synchronized (mbd.postProcessingLock) {
   if (!mbd.postProcessed) {
    try {
     applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
    }catch (Throwable ex) {
      //操作失败
    }
    mbd.postProcessed = true;
   }
  }
  boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences && isSingletonCurrentlyInCreation(beanName));
  if (earlySingletonExposure) {
   addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));
  }
  Object exposedObject = bean;
  try {
   populateBean(beanName, mbd, instanceWrapper);//属性注入
   exposedObject = initializeBean(beanName, exposedObject, mbd);//初始化
  }catch (Throwable ex) {
	//异常
  }
  if (earlySingletonExposure) {
   Object earlySingletonReference = getSingleton(beanName, false);
   if (earlySingletonReference != null) {
    if (exposedObject == bean) {
     exposedObject = earlySingletonReference;
    }else if (!this.allowRawInjectionDespiteWrapping && hasDependentBean(beanName)) {
     String[] dependentBeans = getDependentBeans(beanName);
     Set<String> actualDependentBeans = new LinkedHashSet<>(dependentBeans.length);
     for (String dependentBean : dependentBeans) {
      if (!removeSingletonIfCreatedForTypeCheckOnly(dependentBean)) {
       actualDependentBeans.add(dependentBean);
      }
     }
     if (!actualDependentBeans.isEmpty()) {
      //异常
     }
    }
   }
  }
  try {
   registerDisposableBeanIfNecessary(beanName, bean, mbd);
  }catch (BeanDefinitionValidationException ex) {
	//异常
  }
  return exposedObject;
 }
}
```
- AbstractAutowireCapableBeanFactory - createBeanInstance

```java
//AbstractAutowireCapableBeanFactory#createBeanInstance
protected BeanWrapper createBeanInstance(String beanName, RootBeanDefinition mbd, @Nullable Object[] args) {
 Class<?> beanClass = resolveBeanClass(mbd, beanName);
 if (beanClass != null && !Modifier.isPublic(beanClass.getModifiers()) && !mbd.isNonPublicAccessAllowed()) {
  //异常
 }
 Supplier<?> instanceSupplier = mbd.getInstanceSupplier();
 if (instanceSupplier != null) {
  return obtainFromSupplier(instanceSupplier, beanName);
 }
 if (mbd.getFactoryMethodName() != null) {
  return instantiateUsingFactoryMethod(beanName, mbd, args);
 }
 boolean resolved = false;
 boolean autowireNecessary = false;
 if (args == null) {
  synchronized (mbd.constructorArgumentLock) {
   if (mbd.resolvedConstructorOrFactoryMethod != null) {
    resolved = true;
    autowireNecessary = mbd.constructorArgumentsResolved;
   }
  }
 }
 if (resolved) {
  if (autowireNecessary) {
   return autowireConstructor(beanName, mbd, null, null);
  }else {
   return instantiateBean(beanName, mbd);
  }
 }
 Constructor<?>[] ctors = determineConstructorsFromBeanPostProcessors(beanClass, beanName);
 if (ctors != null || mbd.getResolvedAutowireMode() == AUTOWIRE_CONSTRUCTOR ||
         mbd.hasConstructorArgumentValues() || !ObjectUtils.isEmpty(args)) {
  return autowireConstructor(beanName, mbd, ctors, args);
 }
 ctors = mbd.getPreferredConstructors();
 if (ctors != null) {
  return autowireConstructor(beanName, mbd, ctors, null);
 }
 return instantiateBean(beanName, mbd);
}
//AbstractAutowireCapableBeanFactory#instantiateBean
protected BeanWrapper instantiateBean(final String beanName, final RootBeanDefinition mbd) {
 try {
  Object beanInstance;
  final BeanFactory parent = this;
  if (System.getSecurityManager() != null) {
   beanInstance = AccessController.doPrivileged((PrivilegedAction<Object>) () ->
    getInstantiationStrategy().instantiate(mbd, beanName, parent),getAccessControlContext());
  }else {
   //默认 new CglibSubclassingInstantiationStrategy()
   beanInstance = getInstantiationStrategy().instantiate(mbd, beanName, parent);
  }
  BeanWrapper bw = new BeanWrapperImpl(beanInstance);
  initBeanWrapper(bw);
  return bw;
 }catch (Throwable ex) {
  //异常
 }
}
//SimpleInstantiationStrategy#instantiate
@Override
public Object instantiate(RootBeanDefinition bd, @Nullable String beanName, BeanFactory owner) {
 if (!bd.hasMethodOverrides()) {
  Constructor<?> constructorToUse;
  synchronized (bd.constructorArgumentLock) {
   constructorToUse = (Constructor<?>) bd.resolvedConstructorOrFactoryMethod;
   if (constructorToUse == null) {
    final Class<?> clazz = bd.getBeanClass();
    if (clazz.isInterface()) {
     //异常
    }
    try {
     if (System.getSecurityManager() != null) {
      constructorToUse = AccessController.doPrivileged((PrivilegedExceptionAction<Constructor<?>>) clazz::getDeclaredConstructor);
     }else {
      constructorToUse = clazz.getDeclaredConstructor();
     }
     bd.resolvedConstructorOrFactoryMethod = constructorToUse;
    }catch (Throwable ex) {
     //异常
    }
   }
  }
  return BeanUtils.instantiateClass(constructorToUse);
 }else {
  return instantiateWithMethodInjection(bd, beanName, owner);
 }
}
//CglibSubclassingInstantiationStrategy#instantiateWithMethodInjection
@Override
protected Object instantiateWithMethodInjection(RootBeanDefinition bd, @Nullable String beanName, BeanFactory owner) {
 return instantiateWithMethodInjection(bd, beanName, owner, null);
}
@Override
protected Object instantiateWithMethodInjection(RootBeanDefinition bd, @Nullable String beanName, BeanFactory owner, @Nullable Constructor<?> ctor, Object... args) {
 return new CglibSubclassCreator(bd, owner).instantiate(ctor, args);
}
//CglibSubclassingInstantiationStrategy.CglibSubclassCreator#instantiate
public Object instantiate(@Nullable Constructor<?> ctor, Object... args) {
 Class<?> subclass = createEnhancedSubclass(this.beanDefinition);
 Object instance;
 if (ctor == null) {
  instance = BeanUtils.instantiateClass(subclass);
 }else {
  try {
   Constructor<?> enhancedSubclassConstructor = subclass.getConstructor(ctor.getParameterTypes());
   instance = enhancedSubclassConstructor.newInstance(args);
  }catch (Exception ex) {
   //异常
  }
 }
 Factory factory = (Factory) instance;
 factory.setCallbacks(new Callback[] {NoOp.INSTANCE,new LookupOverrideMethodInterceptor(this.beanDefinition, this.owner),new ReplaceOverrideMethodInterceptor(this.beanDefinition, this.owner)});
 return instance;
}
```

- AbstractAutowireCapableBeanFactory - populateBean

```java
protected void populateBean(String beanName, RootBeanDefinition mbd, @Nullable BeanWrapper bw) {
 if (bw == null) {
  if (mbd.hasPropertyValues()) {
   //异常
  }else {
   return;
  }
 }
 boolean continueWithPropertyPopulation = true;
 if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
  for (BeanPostProcessor bp : getBeanPostProcessors()) {
   if (bp instanceof InstantiationAwareBeanPostProcessor) {
    InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
    if (!ibp.postProcessAfterInstantiation(bw.getWrappedInstance(), beanName)) {
     continueWithPropertyPopulation = false;
     break;
    }  
   }
  }
 }
 if (!continueWithPropertyPopulation) {
  return;
 }
 PropertyValues pvs = (mbd.hasPropertyValues() ? mbd.getPropertyValues() : null);
 int resolvedAutowireMode = mbd.getResolvedAutowireMode();
 if (resolvedAutowireMode == AUTOWIRE_BY_NAME || resolvedAutowireMode == AUTOWIRE_BY_TYPE) {
  MutablePropertyValues newPvs = new MutablePropertyValues(pvs);
  if (resolvedAutowireMode == AUTOWIRE_BY_NAME) {
   autowireByName(beanName, mbd, bw, newPvs);
  }
  if (resolvedAutowireMode == AUTOWIRE_BY_TYPE) {
   autowireByType(beanName, mbd, bw, newPvs);
  }
  pvs = newPvs;
 }
 boolean hasInstAwareBpps = hasInstantiationAwareBeanPostProcessors();
 boolean needsDepCheck = (mbd.getDependencyCheck() != AbstractBeanDefinition.DEPENDENCY_CHECK_NONE);
 PropertyDescriptor[] filteredPds = null;
 if (hasInstAwareBpps) {
  if (pvs == null) {
   pvs = mbd.getPropertyValues();
  }
  for (BeanPostProcessor bp : getBeanPostProcessors()) {
   if (bp instanceof InstantiationAwareBeanPostProcessor) {
    InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
    PropertyValues pvsToUse = ibp.postProcessProperties(pvs, bw.getWrappedInstance(), beanName);
    if (pvsToUse == null) {
     if (filteredPds == null) {
      filteredPds = filterPropertyDescriptorsForDependencyCheck(bw, mbd.allowCaching);
     }
     pvsToUse = ibp.postProcessPropertyValues(pvs, filteredPds, bw.getWrappedInstance(), beanName);
     if (pvsToUse == null) {
      return;
     }
    }
    pvs = pvsToUse;
   }
  }
 }
 if (needsDepCheck) {
  if (filteredPds == null) {
   filteredPds = filterPropertyDescriptorsForDependencyCheck(bw, mbd.allowCaching);
  }
  checkDependencies(beanName, mbd, filteredPds, pvs);
 }
 if (pvs != null) {
  applyPropertyValues(beanName, mbd, bw, pvs);
 }
}
```

- AbstractAutowireCapableBeanFactory - initializeBean

```java
//AbstractAutowireCapableBeanFactory#initializeBean
protected Object initializeBean(final String beanName, final Object bean, @Nullable RootBeanDefinition mbd) {
 if (System.getSecurityManager() != null) {
  AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
   invokeAwareMethods(beanName, bean);
   return null;
   }, getAccessControlContext());
 }else {
  invokeAwareMethods(beanName, bean);//Aware系列
 }
 Object wrappedBean = bean;
 if (mbd == null || !mbd.isSynthetic()) {
  wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);//Before实例化系列
 }
 try {
  invokeInitMethods(beanName, wrappedBean, mbd);
 }catch (Throwable ex) {
  //异常
 }
 if (mbd == null || !mbd.isSynthetic()) {
  wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);//After实例化系列
 }
 return wrappedBean;
}
//AbstractAutowireCapableBeanFactory#invokeInitMethods
protected void invokeInitMethods(String beanName, final Object bean, @Nullable RootBeanDefinition mbd) throws Throwable {
 boolean isInitializingBean = (bean instanceof InitializingBean);
 if (isInitializingBean && (mbd == null || !mbd.isExternallyManagedInitMethod("afterPropertiesSet"))) {
  if (System.getSecurityManager() != null) {
   try {
    AccessController.doPrivileged((PrivilegedExceptionAction<Object>) () -> {
     ((InitializingBean) bean).afterPropertiesSet();
     return null;
     }, getAccessControlContext());
    }catch (PrivilegedActionException pae) {
     throw pae.getException();
    }
   }else {
    ((InitializingBean) bean).afterPropertiesSet();//InitializingBean#afterPropertiesSet
   }
 }
 if (mbd != null && bean.getClass() != NullBean.class) {
  String initMethodName = mbd.getInitMethodName();
  if (StringUtils.hasLength(initMethodName) &&
        !(isInitializingBean && "afterPropertiesSet".equals(initMethodName)) &&
        !mbd.isExternallyManagedInitMethod(initMethodName)) {
   invokeCustomInitMethod(beanName, bean, mbd);
  }
 }
}
protected void invokeCustomInitMethod(String beanName, final Object bean, RootBeanDefinition mbd) throws Throwable {
 String initMethodName = mbd.getInitMethodName();
 Method initMethod = (mbd.isNonPublicAccessAllowed() ?
                       BeanUtils.findMethod(bean.getClass(), initMethodName) :
                       ClassUtils.getMethodIfAvailable(bean.getClass(), initMethodName));
 if (initMethod == null) {
  if (mbd.isEnforceInitMethod()) {
   // 异常
  }else {
   return;
  }
 }
 Method methodToInvoke = ClassUtils.getInterfaceMethodIfPossible(initMethod);
 if (System.getSecurityManager() != null) {
  AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
   ReflectionUtils.makeAccessible(methodToInvoke);
   return null;
  });
  try {
   AccessController.doPrivileged((PrivilegedExceptionAction<Object>) () ->
                                   methodToInvoke.invoke(bean), getAccessControlContext());
  }catch (PrivilegedActionException pae) {
   InvocationTargetException ex = (InvocationTargetException) pae.getException();
   throw ex.getTargetException();
  }
 }else {
  try {
   ReflectionUtils.makeAccessible(methodToInvoke);
   methodToInvoke.invoke(bean);//调用init方法
  }catch (InvocationTargetException ex) {
   throw ex.getTargetException();
  }
 }
}
```

> resolveBeforeInstantiation -> AOP扩展点

> doCreateBean = 实例化(Cglib) -> 属性注入 -> 初始化(Aware->BeforeInstantiation->afterPropertiesSet->init方法->AfterInitialization)

##具体类