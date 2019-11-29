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

## 抽象类



##具体类