# BeanFactory

## 接口

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
 protected Object getObjectForBeanInstance(Object beanInstance, String name, String beanName, @Nullable RootBeanDefinition mbd) {
  if (BeanFactoryUtils.isFactoryDereference(name)) {
   if (beanInstance instanceof NullBean) {
    return beanInstance;
   }
   if (!(beanInstance instanceof FactoryBean)) {
    throw new BeanIsNotAFactoryException(beanName, beanInstance.getClass());
   }
   if (mbd != null) {
    mbd.isFactoryBean = true;
   }
   return beanInstance;
  }

  if (!(beanInstance instanceof FactoryBean)) {
   return beanInstance;
  }

  Object object = null;
  if (mbd != null) {
   mbd.isFactoryBean = true;
  }
  else {
   object = getCachedObjectForFactoryBean(beanName);
  }
  //如果获取到的 FactoryBean 为空，说明这个 bean 没有实现 FactoryBean 接口
  if (object == null) {
   FactoryBean<?> factory = (FactoryBean<?>) beanInstance;
   //如果 bean 已经加载过，则获取合并过后的 RootBeanDefinition
   if (mbd == null && containsBeanDefinition(beanName)) {
    mbd = getMergedLocalBeanDefinition(beanName);
   }
   boolean synthetic = (mbd != null && mbd.isSynthetic());
   object = getObjectFromFactoryBean(factory, beanName, !synthetic);
  }
  return object;
 }   
 
 protected <T> T doGetBean(final String name, @Nullable final Class<T> requiredType, @Nullable final Object[] args, boolean typeCheckOnly) throws BeansException {
  final String beanName = transformedBeanName(name);//&beanName 是 FactoryBean的实例，此处需要的是 FactoryBean#getObject() 返回的对象（beanName）
  Object bean;
  //先查单例缓存
  Object sharedInstance = getSingleton(beanName);//DefaultSingletonBeanRegistry#getSingleton
  if (sharedInstance != null && args == null) {
   bean = getObjectForBeanInstance(sharedInstance, name, beanName, null);//从已经加载的 object 中获取 bean 
  }else {
   if (isPrototypeCurrentlyInCreation(beanName)) {//检查要获取的 bean 是不是当前线程正在创建中的 bean
    throw new BeanCurrentlyInCreationException(beanName);
   }
   //检查当前 BeanFactory 的父类 BeanFactory 是不是不为空，并且要获取的 bean 不包含在当前 BeanFactory 中
   BeanFactory parentBeanFactory = getParentBeanFactory();
   if (parentBeanFactory != null && !containsBeanDefinition(beanName)) {
    String nameToLookup = originalBeanName(name);//还原要获取的 bean 的 beanName
    if (parentBeanFactory instanceof AbstractBeanFactory) {
     return ((AbstractBeanFactory) parentBeanFactory).doGetBean(nameToLookup, requiredType, args, typeCheckOnly);//交由父类 BeanFactory 处理
    }else if (args != null) {
     return (T) parentBeanFactory.getBean(nameToLookup, args);//指定参数
	}else if (requiredType != null) {
	 return parentBeanFactory.getBean(nameToLookup, requiredType);//指定类型
    }else {
	 return (T) parentBeanFactory.getBean(nameToLookup);//强制转换
	}
   }
   //typeCheckOnly默认为false
   if (!typeCheckOnly) {
    markBeanAsCreated(beanName);//如果当前 bean 不在正在创建中的 bean 的缓存集合（alreadyCreated）中，就加进去
   }
   try {
    //合并要获取的 bean 的属性并把属性赋值给一个 RootBeanDefinition
	final RootBeanDefinition mbd = getMergedLocalBeanDefinition(beanName);
	//检查合并之后的 RootBeanDefinition 的是不是抽象的，还会检查如果获取 bean 的参数不空的情况下，bean 的类型是 singleton 的就会报错，因为单例情况下 bean 都是一样的，只有 prototype 情况下才能不一致
	checkMergedBeanDefinition(mbd, beanName, args);
    //先处理依赖，获取当前 bean 依赖的 bean 名称的数组
    String[] dependsOn = mbd.getDependsOn();
    if (dependsOn != null) {
     for (String dep : dependsOn) {
	  if (isDependent(beanName, dep)) {
       //循环依赖抛异常
	  }
	  //将要获取的 bean 的 beanName 和所依赖到的 bean 的 beanName 之间的关系缓存起来
      registerDependentBean(dep, beanName);
	  try {
       getBean(dep);
	  }catch (NoSuchBeanDefinitionException ex) {
	  }
     }
    }
    //单例Bean实例化
    if (mbd.isSingleton()) {
     sharedInstance = getSingleton(beanName, () -> {
      try {
        return createBean(beanName, mbd, args);//1
      }catch (BeansException ex) {
        destroySingleton(beanName);
        throw ex;
      }
     });
     bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
    }
    //原型Bean实例化
    else if (mbd.isPrototype()) {
     Object prototypeInstance = null;
     try {
      beforePrototypeCreation(beanName);//将需要创建的 bean 的 beanName 放到 ThreadLocal 中
      prototypeInstance = createBean(beanName, mbd, args);
     }finally {
      afterPrototypeCreation(beanName);//将需要创建的 bean 的 beanName 从 ThreadLocal 中移除
     }
     bean = getObjectForBeanInstance(prototypeInstance, name, beanName, mbd);
    }
    //其他作用域Bean实例化
    else {
     String scopeName = mbd.getScope();
     final Scope scope = this.scopes.get(scopeName);
     if (scope == null) {
      //作用域不存在
     }
     try {
      Object scopedInstance = scope.get(beanName, () -> {
       beforePrototypeCreation(beanName);//将需要创建的 bean 的 beanName 放到 ThreadLocal 中
       try {
	    return createBean(beanName, mbd, args);
       }finally {
		afterPrototypeCreation(beanName);//将需要创建的 bean 的 beanName 从 ThreadLocal 中移除
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

## 具体类

```java
public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {
 private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
 private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);
 private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);
 private final Set<String> singletonsCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap<>(16));
 private final Map<String, Object> disposableBeans = new LinkedHashMap<>();
 private final Map<String, Set<String>> containedBeanMap = new ConcurrentHashMap<>(16);
 private final Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);
 private final Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);
 
 @Override
 @Nullable
 public Object getSingleton(String beanName) {
  return getSingleton(beanName, true);
 }
 
 //具体实现
 protected Object getSingleton(String beanName, boolean allowEarlyReference) {
  //在已经注册了的单例 map 集合（singletonObjects）中获取特定 beanName 的 bean
  Object singletonObject = this.singletonObjects.get(beanName);
  //检查这个 bean 是不是 null，并且这个 bean 不在正在创建中的 bean 的 map 缓存（singletonsCurrentlyInCreation）中
  if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
   synchronized (this.singletonObjects) {
    //从已经缓存了的单例对象集合中获取 beanName 对应的 Bean
    singletonObject = this.earlySingletonObjects.get(beanName);
    //如果不存在，并且允许早期引用当前创建的对象
    if (singletonObject == null && allowEarlyReference) {
     //根据 beanName 获取在可以在调用时返回单例 Object 实例的工厂。
     ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
     //如果返回的工厂不为空就把对应的 beanName 放到 earlySingletonObjects 中，并移除 singletonFactories 中的值
     if (singletonFactory != null) {
      singletonObject = singletonFactory.getObject();
      this.earlySingletonObjects.put(beanName, singletonObject);
      this.singletonFactories.remove(beanName);
     }
    }
   }
  }
  return singletonObject;
 }
}
```
