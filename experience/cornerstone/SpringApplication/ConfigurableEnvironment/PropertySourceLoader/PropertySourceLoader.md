# PropertySourceLoader
- spring-boot-2.2.1.RELEASE.jar!/META-INF/spring.factories
    - PropertiesPropertySourceLoader
    - YamlPropertySourceLoader

## PropertiesPropertySourceLoader

```java
//PropertiesPropertySourceLoader
@Override
public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
   Map<String, ?> properties = loadProperties(resource);//1
   if (properties.isEmpty()) {
      return Collections.emptyList();
   }
   return Collections
         .singletonList(new OriginTrackedMapPropertySource(name, Collections.unmodifiableMap(properties), true));
}

private Map<String, ?> loadProperties(Resource resource) throws IOException {
  String filename = resource.getFilename();
  if (filename != null && filename.endsWith(XML_FILE_EXTENSION)) {
    return (Map) PropertiesLoaderUtils.loadProperties(resource);
  }
  return new OriginTrackedPropertiesLoader(resource).load();//2
}
//OriginTrackedPropertiesLoader#load
Map<String, OriginTrackedValue> load() throws IOException {
  return load(true);
}
Map<String, OriginTrackedValue> load(boolean expandLists) throws IOException {
  try (CharacterReader reader = new CharacterReader(this.resource)) {
    Map<String, OriginTrackedValue> result = new LinkedHashMap<>();
    StringBuilder buffer = new StringBuilder();
    while (reader.read()) {
      String key = loadKey(buffer, reader).trim();//3
      if (expandLists && key.endsWith("[]")) {
        key = key.substring(0, key.length() - 2);
        int index = 0;
        do {
          OriginTrackedValue value = loadValue(buffer, reader, true);//4
          put(result, key + "[" + (index++) + "]", value);//5
          if (!reader.isEndOfLine()) {
            reader.read();
          }
        }
        while (!reader.isEndOfLine());
      }
      else {
        OriginTrackedValue value = loadValue(buffer, reader, false);
        put(result, key, value);
      }
    }
    return result;
  }
}
private String loadKey(StringBuilder buffer, CharacterReader reader) throws IOException {
  buffer.setLength(0);
  boolean previousWhitespace = false;
  while (!reader.isEndOfLine()) {
    if (reader.isPropertyDelimiter()) {
      reader.read();
      return buffer.toString();
    }
    if (!reader.isWhiteSpace() && previousWhitespace) {
      return buffer.toString();
    }
    previousWhitespace = reader.isWhiteSpace();
    buffer.append(reader.getCharacter());
    reader.read();
  }
  return buffer.toString();
}

private OriginTrackedValue loadValue(StringBuilder buffer, CharacterReader reader, boolean splitLists) throws IOException {
  buffer.setLength(0);
  while (reader.isWhiteSpace() && !reader.isEndOfLine()) {
    reader.read();
  }
  Location location = reader.getLocation();
  while (!reader.isEndOfLine() && !(splitLists && reader.isListDelimiter())) {
    buffer.append(reader.getCharacter());
    reader.read();
  }
  Origin origin = new TextResourceOrigin(this.resource, location);
  return OriginTrackedValue.of(buffer.toString(), origin);
}

private void put(Map<String, OriginTrackedValue> result, String key, OriginTrackedValue value) {
  if (!key.isEmpty()) {
    result.put(key, value);
  }
}
```

