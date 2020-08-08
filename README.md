# ultlog searcher

## Preface
Searcher is a program that collects logs in the ultlog system. By monitoring the log files in the system, the generated logs are sent to ula in real time.
Unlike [collector](https://github.com/ultlog/collector), Searcher does not need to be integrated in the project, but integrated in the operating system instead.
Therefore, some non-logback-based java projects and even non-java projects can also send log to ultlog by modifying the format of the log, and enjoy the convenience brought by ultlog.

## Need

Searcher is written in java, so an environment above [jdk8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) is required on the operating system of the application searcher.


It is recommended to download the execution script to the intranet, mount it through minio, ftp or another http services, and write automatic integration scripts based on this. For details, see [Automatic Integration Practice](#Automatic Integration Practice).

If there are a large number of environments that require integration, it is recommended to check [Automatic Integration Practice](#Auto Integration Practice) to quickly write automatic integration scripts.

## Install
### Project Config
The logback log format of the program that will apply the searcher should be as follows:
````
%d{yyyy-MM-dd HH:mm:ss} [%-5level] [%-5thread] %logger{20} - %msg%n</pattern>
````
If a non-logback project or a non-java project should adjust the log format to this:
````
2020-07-21 09:16:37 [ERROR] [pool-420-thread-7] c.t.g.h.HttpConnectionPoolUtil - test error
java.lang.RuntimeException: test error
at com.example.demo.DemoApplicationTests.contextLoads(DemoApplicationTests.java:21)
....
````

### docker linux
- Use exec and other commands to enter the docker image
- Import the downloaded software package (you can use curl and other commands in the mirror to download)
- unzip files
- Execute sh searcher.sh [{args}](#Searcher Configuration)

### linux
- Pass in the downloaded software package, you can use curl and other commands in the mirror to download
- unzip files
- Execute sh searcher.sh [{args}](#Searcher Configuration)

### windows
Edit {searcher path}/application.yml,fill in the configuration items correctly and run:
````shell
java -jar searcher.jar
````

### Automatic Integration Practice

Automatic integration provides the transformation of docker environment files, 
if you need to integrate on jenkins or other platforms, you can refer to this for transformation.

#### Source DOCKERFILE
````shell
FROM java:8
ADD demo.jar /demo.jar
ENTRYPOINT ["java","jar","/demo.jar"]

````
#### Retrofit
Add a script named start.sh to the same level directory of the program **.jar (you can move to the directory where the jar is located through commands such as cp, the same below), the content is as follows:
````shell
sh startSearcher.sh -f /opt/application.yml
java -jar /demo.jar
````

Add those files to the same level directory:
- startSearcher.sh
- application.yml

Edit DOCKERFILE

````shell
FROM java:8
ADD demo.jar /demo.jar
ADD startSearcher.sh /startSearcher.sh
ADD application.yml /opt/application.yml
ENTRYPOINT ["sh","start.sh"]

````

### Searcher Configuration
#### Using Yml
Using Yml is to modify the configuration file inside the software package, and then store it in an address of the system that needs to integrate the searcher, and specify the path through the parameter

| Parameter | Description | Is it required | Default value | Example|
| ------ | ------ | ------ | ------ | ------ |
| -f, --file | Path of configuration file | √ | | /searcher/


#### Using Param

Start the searcher service directly with the following parameters:

| Parameter | Description | Is it required | Default value | Example |
| ------ | ------ | ------ | ------ | ------ |
| -p, --path | Path to generate log file | √ | | /logs/|
| --pattern | Keyword of the log file | √ | |error.log|
| --project | Project name |√ | | ultlog|
| -m,--module | Module name |√ | | searcher|
| --uuid | Service unique attribute | √ | | 4RFS23Q
| --max | Maximum number of files monitored at the same time | × | 10 | 12|
| --level | The lowest level to collect logs | × | INFO | WARN|
| -u, --ula | ula service address |√ | | http://192.168.2.2:8080/ |

#### Demo Yml
The following is the configuration file used in the test, for reference only:
````yaml
ultlog:
  searcher:
    path: F:\Java\Workspace\ultlog\collector\logs
    pattern: demo
    max: 10
    project: searcherTest
    module: test
    uuid: test
    log:
      level: INFO
  ula:
    url:  http://localhost:8080/
````

