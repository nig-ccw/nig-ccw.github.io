# IoC
- 控制反转(IoC)是面向对象编程中的一种设计原理，目的是实现松耦合
- 最常见的方式叫做依赖注入（Dependency Injection），还有一种方式叫“依赖查找”（Dependency Lookup）
- 哪些方面的控制被反转了？ -> 依赖对象的获得被反转了
- 最基本的Java技术就是“反射”编程

> Inversion of Control（控制反转） , Dependency Inversion Principle（依赖倒置） 是设计原理（design principle）

> Dependency Injection（依赖注入） , Dependency Lookup（依赖查找） 是设计模式（pattern）

> IoC Container 是框架（framework）

## IoC容器
- 管理对象的生命周期、依赖关系
- 知名的IoC容器有：Pico Container、Avalon 、Spring、JBoss、HiveMind、EJB

### DI 生命周期
- Register（注册）
    - 容器必须知道在遇到特定类型时实例化哪个依赖项
- Resolve（解析）
    - 容器必须包含一些方法来解析指定的类型
- Dispose（处置）
    - 大多数 IoC 容器包含不同的生命周期管理器，用于管理对象的生命周期并对其进行处理

## 依赖注入
- 构造函数注入
- 属性注入（也称设置器注入）
- 方法注入