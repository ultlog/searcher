# ultlog searcher

## searcher 是什么
searcher 是ultlog系统中收集日志的一个程序。通过对系统中日志文件的监控，实时的将产生的日志发送到ula中。
与[collector](https://github.com/ultlog/collector)不同，searcher不需要在工程中集成，转而在操作系统中集成。
因此一些非基于logback的java项目（非常少）或非java项目也可以通过修改日志的格式而将日志托管给ultlog，享受ultlog带来的便利。

## 使用

searcher使用java编写，因此在应用searcher的操作系统上需要[jdk8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)以上的环境，若系统中没有以上环境则可以使用自带jdk的searcher包进行部署。


### docker linux

### linux

### windows


## 配置项说明
参数说明

|  参数|   说明 |是否必填| 默认值| 
| ------ | ------ | ------ | ------ | 
| -p , --path | 产生日志文件的路径 | √ | 
| --pattern | 日志文件的关键字 | √ | 
| --project  | 项目名称 |√ |  
| -m , --module | 模块名称 |√ | 
| --uuid | 服务唯一属性 | √ |  
| --max | 同时监控的最多文件数 | √ | 10 | 
| --level | 收集日志的最低等级，支持输入[DEBUG,INFO,WARN,ERROR] | √ | INFO | 
| -u , --ula | 服务唯一属性 |
