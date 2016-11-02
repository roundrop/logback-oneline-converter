# logback-oneline-converter

A custom logback converter which outputs each log with in line forcely.

## What is this for?

Java outputs stacktrace in multiple lines, and it makes difficult to parse the log.
This converter replaces the line feed characters \n with \\n so that log messages will be in one line.

## Installation

### Gradle

```
dependencies {
  compile group: 'com.github.roundrop', name: 'logback-oneline-converter', version: '1.0.+'
}
```

### Maven

```xml
<dependency>
    <groupId>com.github.roundrop</groupId>
    <artifactId>logback-oneline-converter</artifactId>
    <version>[1.0,)</version>
</dependency>
```

## How to use

Declare the new conversion word in your configuration file like as `logback.xml` .

Example - Declare `msg1L`, `ex1L`, `xEx1L` as shown below:

```xml
<configuration>
  <conversionRule conversionWord="msg1L" converterClass="com.github.roundrop.logging.logback.OnelineMessageConverter" />
  <conversionRule conversionWord="ex1L" converterClass="com.github.roundrop.logging.logback.OnelineThrowableProxyConverter" />
  <conversionRule conversionWord="xEx1L" converterClass="com.github.roundrop.logging.logback.OnelineExtendedThrowableProxyConverter" />

  <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <encoder>
      <pattern>%d{yyyy/MM/dd HH:mm:ss:SSS}\t%-5level\t%msg1L\t%xEx1L%n</pattern>
                                                      ^^^^^^  ^^^^^^
    </encoder>
    :
  </appender>
```

### Output

```
2016/11/02 14:44:48:276	INFO 	[ORM] SQL : SELECT\n    t1.*\nFROM\n    tbl1 t1\n ...
```

```
2016/11/02 14:12:40:102	ERROR	error	java.lang.RuntimeException: null\n    at ...
```

## License

See [LICENSE.txt](LICENSE.txt) for details.