# ConfigFileApplicationListener

## 定义

```java
public class ConfigFileApplicationListener implements EnvironmentPostProcessor, SmartApplicationListener, Ordered {
}
```

##事件响应

```java
@Override
public void onApplicationEvent(ApplicationEvent event) {
 if (event instanceof ApplicationEnvironmentPreparedEvent) {
  onApplicationEnvironmentPreparedEvent((ApplicationEnvironmentPreparedEvent) event);//a
 }
 if (event instanceof ApplicationPreparedEvent) {
  onApplicationPreparedEvent(event);//b
 }
}
```

### ApplicationEnvironmentPreparedEvent

```java
//ConfigFileApplicationListener
private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
 List<EnvironmentPostProcessor> postProcessors = loadPostProcessors();
 postProcessors.add(this);
 AnnotationAwareOrderComparator.sort(postProcessors);
 for (EnvironmentPostProcessor postProcessor : postProcessors) {
  postProcessor.postProcessEnvironment(event.getEnvironment(), event.getSpringApplication());//调用 EnvironmentPostProcessor#postProcessEnvironment
 }
}

List<EnvironmentPostProcessor> loadPostProcessors() {
  return SpringFactoriesLoader.loadFactories(EnvironmentPostProcessor.class, getClass().getClassLoader());
}

@Override
public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
  addPropertySources(environment, application.getResourceLoader());
}

protected void addPropertySources(ConfigurableEnvironment environment, ResourceLoader resourceLoader) {
  RandomValuePropertySource.addToEnvironment(environment);
  new Loader(environment, resourceLoader).load();//配置文件加载的逻辑部分
}

//ConfigFileApplicationListener.Loader
Loader(ConfigurableEnvironment environment, ResourceLoader resourceLoader) {
  this.environment = environment;
  this.placeholdersResolver = new PropertySourcesPlaceholdersResolver(this.environment);
  this.resourceLoader = (resourceLoader != null) ? resourceLoader : new DefaultResourceLoader();
  this.propertySourceLoaders = SpringFactoriesLoader.loadFactories(PropertySourceLoader.class,getClass().getClassLoader());
}
//load
void load() {
 FilteredPropertySource.apply(this.environment, DEFAULT_PROPERTIES,LOAD_FILTERED_PROPERTY, (defaultProperties) -> {
  this.profiles = new LinkedList<>();
  this.processedProfiles = new LinkedList<>();
  this.activatedProfiles = false;
  this.loaded = new LinkedHashMap<>();
  initializeProfiles();
  while (!this.profiles.isEmpty()) {
   Profile profile = this.profiles.poll();
   if (isDefaultProfile(profile)) {
    addProfileToEnvironment(profile.getName());
   }
   load(profile, this::getPositiveProfileFilter,addToLoaded(MutablePropertySources::addLast, false));
   this.processedProfiles.add(profile);
  }
  load(null, this::getNegativeProfileFilter,addToLoaded(MutablePropertySources::addFirst, true));
  addLoadedPropertySources();
  applyActiveProfiles(defaultProperties);
 });
}

private void load(Profile profile, DocumentFilterFactory filterFactory, DocumentConsumer consumer) {
  getSearchLocations().forEach((location) -> {
   boolean isFolder = location.endsWith("/");
   Set<String> names = isFolder ? getSearchNames() : NO_SEARCH_NAMES;
   names.forEach((name) -> load(location, name, profile, filterFactory, consumer));
  });
}

private void load(String location, String name, Profile profile, DocumentFilterFactory filterFactory,DocumentConsumer consumer) {
  if (!StringUtils.hasText(name)) {
   for (PropertySourceLoader loader : this.propertySourceLoaders) {
    if (canLoadFileExtension(loader, location)) {
     load(loader, location, profile, filterFactory.getDocumentFilter(profile), consumer);
     return;
    }
   }
   //...throw new IllegalStateException
  }
  Set<String> processed = new HashSet<>();
  for (PropertySourceLoader loader : this.propertySourceLoaders) {
   for (String fileExtension : loader.getFileExtensions()) {
    if (processed.add(fileExtension)) {
     loadForFileExtension(loader, location + name, "." + fileExtension, profile, filterFactory,consumer);
    }
   }
  }
}

private void load(PropertySourceLoader loader, String location, Profile profile, DocumentFilter filter, DocumentConsumer consumer) {
  try {
   Resource resource = this.resourceLoader.getResource(location);
   if (resource == null || !resource.exists()) {
    if (this.logger.isTraceEnabled()) {
     StringBuilder description = getDescription("Skipped missing config ", location, resource,profile);
     this.logger.trace(description);
    }
    return;
   }
   if (!StringUtils.hasText(StringUtils.getFilenameExtension(resource.getFilename()))) {
    if (this.logger.isTraceEnabled()) {
     StringBuilder description = getDescription("Skipped empty config extension ", location,resource, profile);
     this.logger.trace(description);
    }
    return;
   }
   String name = "applicationConfig: [" + location + "]";
   List<Document> documents = loadDocuments(loader, name, resource);
   if (CollectionUtils.isEmpty(documents)) {
    if (this.logger.isTraceEnabled()) {
     StringBuilder description = getDescription("Skipped unloaded config ", location, resource,profile);
     this.logger.trace(description);
    }
    return;
   }
   List<Document> loaded = new ArrayList<>();
   for (Document document : documents) {
    if (filter.match(document)) {
     addActiveProfiles(document.getActiveProfiles());
     addIncludedProfiles(document.getIncludeProfiles());
     loaded.add(document);
    }
   }
   Collections.reverse(loaded);
   if (!loaded.isEmpty()) {
    loaded.forEach((document) -> consumer.accept(profile, document));
    if (this.logger.isDebugEnabled()) {
     StringBuilder description = getDescription("Loaded config file ", location, resource, profile);
     this.logger.debug(description);
    }
   }
  }catch (Exception ex) {
   throw new IllegalStateException("Failed to load property source from location '" + location + "'", ex);
  }
}
```

参考[EnvironmentPostProcessor](../ConfigurableEnvironment/EnvironmentPostProcessor/EnvironmentPostProcessor.md)

### ApplicationPreparedEvent

```java
private void onApplicationPreparedEvent(ApplicationEvent event) {
 this.logger.switchTo(ConfigFileApplicationListener.class);
 addPostProcessors(((ApplicationPreparedEvent) event).getApplicationContext());
}
```