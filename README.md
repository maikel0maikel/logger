Android开发者的日志工具。
特点：
支持日志写入文件
支持json格式化输出到控制台
日志可以打印出哪个线程哪个类哪行打印的
使用：
一、在根目录的build.gradle里添加仓库。
```groovy
allprojects {
 repositories {
   maven {
            url "https://raw.githubusercontent.com/maikel0maikel/logger/master"
         }
 }
```
二、在模块的build.gradle中添加依赖。

studio3.0以下
```groovy
dependencies {
     compile 'com.sinohb:logger:1.0.5'
}
```
 studio3.0以上（含3.0）
 ```groovy
 dependencies {
      implementation 'com.sinohb:logger:1.0.5'
 }
```

三、 配置与初始化

建议在你的application的`onCreate()`方法里初始化工具类，当然也可以不用初始化，使用默认的配置。
在使用默认的配置时不继承SystemApplication将无法获取版本等信息，默认E与P级别写入文件
如果需要写入其他日志级别请调用addWriteLevel，这时请用LogTools来打印
以下是一个配置范例：

```java
public class RootApp extends Application {

    private static Logger sLogger;

    @Override
    public void onCreate() {
        super.onCreate();
          sLogger = Logger.Builder.newBuilder(getApplicationContext(), "名字")
                /* 下面的属性都是默认值，你可以根据需求决定是否修改它们. */
                .setDebug(true)//为true 会打印日志到控制台 否则不会
                .setWriteToFile(true)//是否写入文件 为true将写入文件 默认为true
                .setLogDir("HBDvrVideoMonitor")//日志记录目录 如果不设置 默认路径为 sdcard/HBSystem/HBApplication 可以使用应用的名称作为日志目录名
                .setLogPrefix("")//日志前缀 为空时默认为"_" 不设置即可
                .setLogSegment(LogSegment.TWELVE_HOURS)//时间切片 默认24小时
                .addWriteLevel(LogLevel.V)//比如v级别 可以添加多个 默认写E与P级别
                .setZoneOffset(TimeUtils.ZoneOffset.P0800)//时区偏移量 默认东八区
                .setPackageLevel(1)//日志栈层级 0-10
                .setAudoDelete(true)//自动删除日志文件
                .setStoreDays(5)//配置日志存储时间单位天，默认为7天（需同时设置setAudoDelete为true否则无效）
                .build();
    }

    public static Logger getLogger() {
        return sLogger;
    }
}
```
如果'targetSdkVersion'是23+，需要在闪屏页或首页申请'android.permission.WRITE_EXTERNAL_STORAG'权限。


### setLogSegment(LogSegment)

日志按照时间切片写入到不同的文件中，默认是24小时，文件名诸如`2018-04-26.log`，如果设置为`LogSegment.ONE_HOUR`，文件名就会变成诸如`2018-04-26_0203.log`，表示文件里记录的是2:00到3:00的日志。

```java
logger.setLogSegment(LogSegment.ONE_HOUR);
```

### setZoneOffset(ZoneOffset)

我们可以指定文件里日志时间的时区，而不受用户位置的影响，这样会更容易定位问题，默认是`ZoneOffset.P0800`（+0800），表示“北京时间”。

```java
logger.setZoneOffset(ZoneOffset.P0800);
```


