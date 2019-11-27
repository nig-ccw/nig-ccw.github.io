# EnvironmentPostProcessor
- spring-boot-2.2.1.RELEASE.jar!/META-INF/spring.factories
    - CloudFoundryVcapEnvironmentPostProcessor - 2
    - SpringApplicationJsonEnvironmentPostProcessor - 1
    - SystemEnvironmentPropertySourceEnvironmentPostProcessor - 0
    - DebugAgentEnvironmentPostProcessor - 4
- ConfigFileApplicationListener - 3

## postProcessEnvironment

```java
//SystemEnvironmentPropertySourceEnvironmentPostProcessor
@Override
public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
   String sourceName = StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME;
   PropertySource<?> propertySource = environment.getPropertySources().get(sourceName);
   if (propertySource != null) {
      replacePropertySource(environment, sourceName, propertySource);
   }
}
//SpringApplicationJsonEnvironmentPostProcessor
@Override
public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
  MutablePropertySources propertySources = environment.getPropertySources();
  propertySources.stream().map(JsonPropertyValue::get).filter(Objects::nonNull).findFirst()
    .ifPresent((v) -> processJson(environment, v));
}
//ConfigFileApplicationListener
@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		addPropertySources(environment, application.getResourceLoader());
	}
```