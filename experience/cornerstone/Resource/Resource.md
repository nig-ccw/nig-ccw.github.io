##Resource

###接口定义

```java
public interface Resource extends InputStreamSource {
 boolean exists();
 default boolean isReadable() {
  return exists();
 }
 default boolean isOpen() {
  return false;
 }
 default boolean isFile() {
  return false;
 }
 URL getURL() throws IOException;
 URI getURI() throws IOException;
 File getFile() throws IOException;
 default ReadableByteChannel readableChannel() throws IOException {
  return Channels.newChannel(getInputStream());
 }
 long contentLength() throws IOException;
 long lastModified() throws IOException;
 Resource createRelative(String relativePath) throws IOException;
 @Nullable String getFilename();
 String getDescription();
}

public interface InputStreamSource {
 InputStream getInputStream() throws IOException;
}
```

### 子接口

- WritableResource

```java
public interface WritableResource extends Resource {
 default boolean isWritable() {
  return true;
 }
 OutputStream getOutputStream() throws IOException;
 default WritableByteChannel writableChannel() throws IOException {
  return Channels.newChannel(getOutputStream());
 }
}
```

- ContextResource

```java
public interface ContextResource extends Resource {
 String getPathWithinContext();
}
```

### 抽象类

- AbstractResource

```java
public abstract class AbstractResource implements Resource {
}
```

- AbstractFileResolvingResource

```java
public abstract class AbstractFileResolvingResource extends AbstractResource {
}
```

### 具体类

- FileSystemResource

```java
public class FileSystemResource extends AbstractResource implements WritableResource {
}
```

- ByteArrayResource

```java
public class ByteArrayResource extends AbstractResource {
}
```

- InputStreamResource

```java
public class InputStreamResource extends AbstractResource {
}
```

- UrlResource

```java
public class UrlResource extends AbstractFileResolvingResource {
}
```

- FileUrlResource

```java
public class FileUrlResource extends UrlResource implements WritableResource {
}
```

- ClassPathResource

```java
public class ClassPathResource extends AbstractFileResolvingResource {
}
```



## ResourceLoader

### 接口定义

```java
public interface ResourceLoader {
 String CLASSPATH_URL_PREFIX = ResourceUtils.CLASSPATH_URL_PREFIX;
 Resource getResource(String location);
 @Nullable ClassLoader getClassLoader();
}
```

### 子接口

```java
public interface ResourcePatternResolver extends ResourceLoader {
 String CLASSPATH_ALL_URL_PREFIX = "classpath*:";
 Resource[] getResources(String locationPattern) throws IOException;
}
```

### 具体类

- DefaultResourceLoader

```java
//ResourceLoader 的默认实现
public class DefaultResourceLoader implements ResourceLoader {
}
```

- FileSystemResourceLoader

```java
public class FileSystemResourceLoader extends DefaultResourceLoader {
 @Override
 protected Resource getResourceByPath(String path) {
  if (path.startsWith("/")) {
	 path = path.substring(1);
  }
	return new FileSystemContextResource(path);
 }
 private static class FileSystemContextResource extends FileSystemResource implements ContextResource {
  public FileSystemContextResource(String path) {
   super(path);
  }
	@Override
  public String getPathWithinContext() {
   return getPath();
  }
 }
}
```

- PathMatchingResourcePatternResolver

```java
public class PathMatchingResourcePatternResolver implements ResourcePatternResolver {
}
```