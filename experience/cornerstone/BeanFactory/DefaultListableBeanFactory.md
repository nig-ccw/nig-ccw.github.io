# DefaultListableBeanFactory

## 定义

```java
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
      implements ConfigurableListableBeanFactory, BeanDefinitionRegistry, Serializable {
}      
```

## 构造函数

```java
//DefaultListableBeanFactory
public DefaultListableBeanFactory() {
   super();
}
//AbstractAutowireCapableBeanFactory
public AbstractAutowireCapableBeanFactory() {
  super();
  ignoreDependencyInterface(BeanNameAware.class);
  ignoreDependencyInterface(BeanFactoryAware.class);
  ignoreDependencyInterface(BeanClassLoaderAware.class);
}
//AbstractBeanFactory
public AbstractBeanFactory() {
}
```

## 方法

### setBeanClassLoader

```java
//AbstractBeanFactory
@Override
public void setBeanClassLoader(@Nullable ClassLoader beanClassLoader) {
   this.beanClassLoader = (beanClassLoader != null ? beanClassLoader : ClassUtils.getDefaultClassLoader());
}
```

###setBeanExpressionResolver

```java
//AbstractBeanFactory
@Override
public void setBeanExpressionResolver(@Nullable BeanExpressionResolver resolver) {
   this.beanExpressionResolver = resolver;
}
```

###addPropertyEditorRegistrar

```java
//AbstractBeanFactory
@Override
public void addPropertyEditorRegistrar(PropertyEditorRegistrar registrar) {
   this.propertyEditorRegistrars.add(registrar);
}
```

### addBeanPostProcessor

```java
//AbstractBeanFactory
@Override
public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
   this.beanPostProcessors.remove(beanPostProcessor);
   if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
      this.hasInstantiationAwareBeanPostProcessors = true;
   }
   if (beanPostProcessor instanceof DestructionAwareBeanPostProcessor) {
      this.hasDestructionAwareBeanPostProcessors = true;
   }
   this.beanPostProcessors.add(beanPostProcessor);
}
```

###ignoreDependencyInterface

```java
//AbstractAutowireCapableBeanFactory
public void ignoreDependencyInterface(Class<?> ifc) {
   this.ignoredDependencyInterfaces.add(ifc);
}
```

###registerResolvableDependency

```java
//DefaultListableBeanFactory
@Override
public void registerResolvableDependency(Class<?> dependencyType, @Nullable Object autowiredValue) {
   if (autowiredValue != null) {
      if (!(autowiredValue instanceof ObjectFactory || dependencyType.isInstance(autowiredValue))) {
         throw new IllegalArgumentException("未实现指定依赖类型");
      }
      this.resolvableDependencies.put(dependencyType, autowiredValue);
   }
}
```

### setTempClassLoader

```java
//AbstractBeanFactory
@Override
public void setTempClassLoader(@Nullable ClassLoader tempClassLoader) {
   this.tempClassLoader = tempClassLoader;
}
```

###registerSingleton

```java
//DefaultListableBeanFactory
@Override
public void registerSingleton(String beanName, Object singletonObject) throws IllegalStateException {
   super.registerSingleton(beanName, singletonObject);
   updateManualSingletonNames(set -> set.add(beanName), set -> !this.beanDefinitionMap.containsKey(beanName));
   clearByTypeCache();
}
//DefaultSingletonBeanRegistry
@Override
public void registerSingleton(String beanName, Object singletonObject) throws IllegalStateException {
  synchronized (this.singletonObjects) {
    Object oldObject = this.singletonObjects.get(beanName);
    if (oldObject != null) {
      throw new IllegalStateException("已存在");
    }
    addSingleton(beanName, singletonObject);
  }
}
//DefaultSingletonBeanRegistry
protected void addSingleton(String beanName, Object singletonObject) {
  synchronized (this.singletonObjects) {
    this.singletonObjects.put(beanName, singletonObject);
    this.singletonFactories.remove(beanName);
    this.earlySingletonObjects.remove(beanName);
    this.registeredSingletons.add(beanName);
  }
}
```