
## Self Project Commons Dependency Package

### How to use it

如果你不需要 web 相关工具类的支持，仅需引入 core 包即可。

```xml
<dependency>
    <groupId>com.github.jitwxs</groupId>
    <artifactId>commons-core</artifactId>
    <version>${latest.version}</version>
</dependency>
```

如果你还需要 web 相关工具类的支持，直接引入 web 包即可，会同时自动引入 core 包。

```xml
<dependency>
    <groupId>com.github.jitwxs</groupId>
    <artifactId>commons-web</artifactId>
    <version>${latest.version}</version>
</dependency>
```

### Support Utils

#### Core Package

| Filename        | Features              |
| --------------- | --------------------- |
| DateUtils       | 日期工具类            |
| DateFormatUtils | 日期/字符串转换工具类 |
| TimeUtils       | 时间工具类            |
| LoopUtils       | 通用循环工具类        |
| ThreadUtils     | 线程工具类            |
| ThreadPoolUtils | 线程池工具类          |
| DataUtils       | 集合处理工具类        |
| DoubleUtils     | double类型计算工具类  |
| JacksonUtils    | Jackson工具类         |

#### Web Package

| Filename        | Features             |
| --------------- | -------------------- |
| BeanCopierUtils | 浅拷贝工具类         |
| SpringBeanUtils | SpringBean管理工具类 |


### Developers And Contributor

#### Developers

- Jitwxs


