# JDK 命令
- javap 反解析工具 [参考](https://www.jianshu.com/p/6a8997560b05)
- javah 生成实现本地方法所需的 C 头文件和源文件 [参考](https://blog.csdn.net/qq_22494029/article/details/80095448)
- javadoc 根据注释 生成 Java 文档 [参考](https://blog.csdn.net/vbirdbest/article/details/80296136)
- wsgen 生成合适的JAX-WS
```
# -cp 指定查找输入类文件的位置
# -r 指定放置资源文件 (例如 WSDL) 的位置
# -d 指定放置生成的输出文件的位置
# -s 指定放置生成的源文件的位置
# -wsdl 生成 WSDL 文件
wsgen -cp ./bin -r ./wsdl -s ./src -d ./bin -wsdl com.vcc.jws.server.Example
```
- wsimport 根据服务端发布的wsdl文件生成客户端存根及框架

```
# -p 指定目标程序包
# -d 指定放置生成的输出文件的位置
# -s 指定放置生成的源文件的位置
# -wsdl 生成 WSDL 文件
wsimport -d ./bin -s ./src -p com.vcc.jws.client.ref http://localhost:8080/hello?wsdl
```
- xjc 将 schema 文件转换成 Java 文件
```
# -p 指定目标程序包
xjc -p com.vcc.bean book.xsd
```

- unpack200 和 pack200 压缩/解压 jar 文件
```
pack200 x.jar x.jar.pack
unpack200 x.jar.pack x.jar
```

- native2ascii 本地编码和 Unicode 互相转换
```
native2ascii -encoding GBK zh.txt u.txt
native2ascii -reverse -encoding GBK u.txt zh.txt
```

- keytool [参考](https://blog.csdn.net/yinhuaiya/article/details/81774314)
- jps 查看 JVM 里面所有进程的具体状态 [参考](https://www.jianshu.com/p/d39b2e208e72)
- jvisualvm [参考](https://my.oschina.net/u/2300159/blog/812984)
- jmap 获得内存的具体匹配情况 [参考](https://www.jianshu.com/p/a4ad53179df3)
- jstack 获取当前进程的线程的信息（线程ID、线程的状态、是否持有锁信息等）[参考](https://www.jianshu.com/p/025cb069cb69)
- jstat 对 Java 应用程序的资源和性能进行实时的命令行的监控 [参考](https://www.jianshu.com/p/213710fb9e40)
- jstatd 启动 jvm 监控服务 [参考](https://www.cnblogs.com/guoximing/articles/6110319.html)
- jshell 交互式 REPL 工具 [参考](https://www.jianshu.com/p/5fb406c6203f)
- jinfo 查看正在运行的 java 应用程序的扩展参数 [参考](https://www.jianshu.com/p/8d8aef212b25) 
- jhat 分析堆文件，以 html 的形式显示出来 [参考](https://www.cnblogs.com/baihuitestsoftware/articles/6406271.html)
- jdeps 显示 Java 类文件的包级或类级依赖关系 [参考](https://blog.csdn.net/zebe1989/article/details/82692244)
- jdb 对正在运行的 Java 进程进行实时调试 [参考](https://www.cnblogs.com/rocedu/p/6371262.html)
- jcontrol 基于 JMX 的实时图形化监测工具 [参考](https://blog.csdn.net/seelye/article/details/50623798)
- jconsole 一个图形化界面，可以观察到 java 进程的 gc，class，内存等信息 [参考](https://blog.csdn.net/qq_31156277/article/details/80035430)
- jcmd 用来导出堆、查看Java进程、导出线程信息、执行GC、还可以进行采样分析 [参考](https://www.jianshu.com/p/388e35d8a09b)
- javapackager 用于常规 Java 独立应用程序的打包 [参考](https://blog.csdn.net/zhaojindong33/article/details/51304521)