## 版本详细信息

> 参考 [wiki](<https://en.wikipedia.org/wiki/Java_version_history>)

| 版本       | 时间               | 描述                                                         |
| ---------- | ------------------ | ------------------------------------------------------------ |
| JDK 1.0    | January 23, 1996   | 向标准库添加了大量的类和包。                                 |
| JDK 1.1    | February 19, 1997  | 增加对 AWT 事件模型的广泛重构、添加到语言中的内部类、javabean 和 JDBC。 |
| J2SE 1.2   | December 8, 1998   | *Playground*。增加反射、集合框架、Java IDL，以及将 Swing 图形 API 集成到核心类中。JVM 首次配备了JIT编译器。 |
| J2SE 1.3   | May 8, 2000        | *Kestrel*。HotSpot JVM 的捆绑、JavaSound、JNDI、JPDA。       |
| J2SE 1.4   | February 6, 2002   | *Merlin*。模仿 Perl 的正则表达式、异常链接、集成的 XML 解析器和 XSLT 处理器(JAXP)，以及 Java Web Start。 |
| J2SE 5.0   | September 30, 2004 | *Tiger*。for-each 循环、泛型、注解、枚举、静态导入、JUC、自动装箱和可变参数。 |
| Java SE 6  | December 11, 2006  | *Mustang*。可插入注解(JSR 269)、许多 GUI 改进。              |
| Java SE 7  | July 28, 2011      | *Dolphin*。switch 中的字符串、try-with-resources 自动资源管理和用于泛型实例创建的类型推断；JVM 支持动态语言，类库则支持 join/fork 框架、改进的 New I/O 库以及 SCTP 等新网络协议。 |
| Java SE 8  | March 18, 2014     | lambda 表达式(闭包)和默认方法的语言级支持、集合元素的流式操作、项目 Nashorn JavaScript 运行时、受 Joda Time 启发的新的日期和时间 API，以及删除 PermGen。 |
| Java SE 9  | September 21, 2017 | JDK 模块化、JShell、AOT。                                    |
| Java SE 10 | March 20, 2018     | 本地变量类型推断、G1。                                       |
| Java SE 11 | September, 2018    | 第一个 LTS 版本。两个新的垃圾收集器实现（Epsilon、ZGC），调试深层次问题的 Flight Recorder，一个新的 HTTP 客户端包括对 WebSocket 支持。 |
| Java SE 12 | March 19, 2019     |                                                              |
| Java SE 13 | September 17, 2019 |                                                              |



## 常用包信息

- ### java.lang

| 类或接口                                               | 描述                                                     |
| ------------------------------------------------------ | -------------------------------------------------------- |
| Object                                                 | 所有类的父类                                             |
| Class                                                  | Java 反射系统中的顶级类                                  |
| Throwable                                              | 异常中的顶级类                                           |
| Exception                                              | 可捕捉的异常                                             |
| RuntimeException                                       | 运行时抛出的异常                                         |
| Error                                                  | 无法捕捉的严重问题                                       |
| Threa                                                  | 字符串                                                   |
| String                                                 | 字符串类                                                 |
| Comparable                                             | 通用比较和排序                                           |
| Iterable                                               | 使用增强的 for 循环进行通用迭代                          |
| ClassLoader, Process, Runtime, SecurityManager, System | 类的动态加载、创建外部进程、主机环境查询和安全策略的实施 |

