# ultlog searcher

## searcher 是什么
searcher 是ultlog系统中收集日志的一个程序。通过对系统中日志文件的监控，实时的将产生的日志发送到ula中。
与[collector](https://github.com/ultlog/collector)不同，searcher不需要在工程中集成，转而在操作系统中集成。
因此一些非基于logback的java项目（非常少）或非java项目也可以通过修改日志的格式而将日志托管给ultlog，享受ultlog带来的便利。

## 使用

searcher使用java编写，因此在应用searcher的操作系统上需要[jdk8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)以上的环境，若系统中没有以上环境则可以使用自带jdk的searcher包进行部署。


建议将执行脚本下载到内网，通过minio，ftp或http服务挂载，并以此编写自动集成脚本，具体可见[自动集成实践](#实践)。


如果有大量环境需要集成建议查看[自动集成实践](#实践)来快速编写自动集成脚本。

### 选择软件包
|  环境|   软件包 |
| ------ | ------ | 
| \>= jdk 1.8 |[searcher_jdk.tar.gz]() | 
| < jdk 1.8 | [searcher.tar.gz]() | 
| 无jdk | [searcher_jdk.tar.gz]() | 

### 手动/单机集成


#### docker linux
- 进入docker镜像
- 将下载好的软件包传入 可以在镜像内使用curl等命令进行下载
- 解压文件
- 执行 sh searcher.sh [{args}](#配置项说明)

#### linux
sh startSearcher.sh {args}
#### windows
修改{searcher path}/application.yml，正确填写其中配置项
执行语句启动
````shell script
java -jar searcher.jar
````
如果是非jdk8环境
````shell script
{searcher_path}/java -jar searcher.jar
````

#### 配置项说明
参数说明
##### 使用配置文件
|  参数|   说明 |是否必填| 默认值| 实例
| ------ | ------ | ------ | ------ | ------ | 
| -f , --file | 配置文件的路径 | √ | | /searcher/application.yml
##### 直接传参

|  参数|   说明 |是否必填| 默认值| 实例 |
| ------ | ------ | ------ | ------ | ------ | 
| -p , --path | 产生日志文件的路径 | √ | | /logs/|
| --pattern | 日志文件的关键字 | √ | |error.log|
| --project  | 项目名称 |√ |  | ultlog|
| -m , --module | 模块名称 |√ | | searcher|
| --uuid | 服务唯一属性 | √ |  | 4RFS23Q
| --max | 同时监控的最多文件数 |  × | 10 | 12|
| --level | 收集日志的最低等级 | × | INFO | WARN| 
| -u , --ula | ula服务地址 |√ | | http://192.168.2.2:8080/ | 


# 实践