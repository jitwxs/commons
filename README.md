## Self Project Commons Dependency Package

[![Release](https://img.shields.io/github/v/release/jitwxs/commons.svg)](https://github.com/jitwxs/commons/releases)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![MavenCenter](https://maven-badges.herokuapp.com/maven-central/com.github.jitwxs/commons/badge.svg)](https://oss.sonatype.org)

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
| DoubleUtils     | double 类型计算工具类  |
| BitUtils        | 位操作工具类        |
| ByteUtils       | 字节操作工具类        |
| JacksonUtils    | Jackson 工具类        |

#### Web Package

| Filename        | Features             |
| --------------- | -------------------- |
| BeanCopierUtils | 浅拷贝工具类         |
| SpringBeanUtils | SpringBean 管理工具类 |
| CookieUtils     | Cookie 工具类         |

### Developers And Contributor

#### Developers

- Jitwxs


