# Java发送电子邮件

## 1  理论基础

### 1.1 应用场景

![应用场景](https://user-images.githubusercontent.com/39179120/145777685-b8e5dfea-b0bf-4f5e-b710-180ab05844ce.jpg)

### 1.2电子邮件概述

电子邮件把邮件发送到收件人使用的邮件服务器,并放在其中的收件人邮箱中，收件人可在自己方便时上网到自己的邮件服务器进行读取。电子邮件不仅使用方便，而且还具有传递迅速和费用低廉的优点。

![邮件协议](https://user-images.githubusercontent.com/39179120/145344250-5b22da21-0a34-414a-a4d0-58da89063083.jpg)

一个电子邮件系统应具有图1所示的三个主要组成构件，这就是用户代理、邮件服务器，以及邮件发送协议（如SMTP）和邮件读取协议（如POP3）。邮件发送协议用于用户代理向邮件服务器发送邮件或在邮件服务器之间发送邮件，邮件读取协议用于用户代理从邮件服务器读取邮件。邮件服务器必须能够同时充当客户和服务器。

用户代理功能：

1. 撰写：给用户提供编辑信件的环境。
2. 显示：能方便地在计算机屏幕上显示出来信。
3. 处理：处理包括发送邮件和接收邮件，如：删除、存盘、打印、转发等。
4. 通信：利用邮件发送协议发送到用户所使用的邮件服务器，使用邮件读取协议从本地邮件服务器接收邮件。

![电子邮件组成构件](https://user-images.githubusercontent.com/39179120/145782271-0e871bfa-12eb-438e-bb49-6cf60b511ae6.jpg)

PC之间发送和接收电子邮件的重要步骤：

1.  发件人调用用户代理撰写和编辑要发送的邮件；
1.  发件人点击屏幕上”发送邮件“按钮，把发送邮件的工作全部交给用户代理来完成。用户代理把邮件用SMTP协议发给发送方邮件服务器，用户代理充当SMTP客户，而发送方邮件服务器充当SMTP服务器。
1.  SMTP服务器收到用户代理发来的邮件后，就把邮件临时存放在邮件缓存队列中，等待发送到接收方的邮件服务器（等待时间的长短取决于邮件服务器的处理能力和队列中待发送的信件的数量。但这种等待时间一般都远远大于分组在路由器中等待转发的排队时间）。
1.  发送方邮件服务器的SMTP客户与接收方邮件服务器的SMTP服务器建立TCP连接，然后就把邮件缓存队列中的邮件依次发送出去。如果SMTP客户还有一些邮件要发送到同一个邮件服务器，那么可以在原来已建立的TCP连接上重复发送。
1.  运行在接收方邮件服务器中的SMTP服务器进程收到邮件后，把邮件放入收件人的用户邮箱中，等待收件人进行读取。
1.  收件人在打算收信时，就运行PC中的用户代理，使用POP3（或IMAP）协议读取发送给自己的邮件。

电子邮件地址格式：用户名@邮件服务器地址

### 1.3 邮件协议

#### 1.3.1 简单邮件传输协议SMTP

![SMTP](https://user-images.githubusercontent.com/39179120/145780013-c366aec1-c531-4b1e-942f-3f05ec4ab5b7.jpg)

1. 连接建立

![image](https://user-images.githubusercontent.com/39179120/145344423-59fb47fe-7acf-4a54-9a6e-1f44f37b5a0b.png)

2. 邮件发送

A：MAIL FROM:<1440879349@qq.com>

B：250 OK/451，452，500 （是否已经准备好接收邮件）

A：RCPT TO: <收件人地址>

B：250 OK/550 No such user here（确定是否有这个用户）

A：DATA（开始传输邮件内容）

B：354 Start mail input; end with<CRLF>.<CRLF>（同意传输）

A：DATA（开始传输内容）

B：250OK（接收结束）

3. 连接释放

邮件发送完毕后，SMTP客户应发送QUIT命令。SMTP服务器返回的信息是“221（服务关闭）”，表示SMTP同意释放TCP连接。邮件传送的全部过程即结束。

SMTP不使用中间的邮件服务器。不管发送方和接收方的邮件服务器相隔有多远，不管在邮件的传送过程中要经过多少个路由器，TCP连接总是发送方和接收方这两个邮件服务器之间直接建立。当接收方邮件服务器出故障而不能工作时，发送方邮件服务器只能等待一段时间后再尝试和该邮件服务器建立连接，而不能先找一个中间的邮件服务器建立TCP连接。

#### 1.3.2 MIME协议

SMTP缺点：

1.  发送电子邮件不需要鉴别，方便了垃圾邮件的产生；
1.  SMTP本来就是为传送ASCII码而不是传送二进制数据设计的，虽然后来MIME可以传送二进制数据，但在传送非ASCII码的长报文时，在网络上的传输效率不高；
1.  SMTP传送的邮件是明文，不利于保密。

通用因特网邮件扩充MIME：使电子邮件系统可以支持声音、图像、视频、多种国家语言等等。

![MIME](https://user-images.githubusercontent.com/39179120/145784046-2525d8d7-d14e-4792-987b-9d4f76f78564.jpg)

#### 1.3.3 电子邮件的信息格式

邮件内容首部一些关键字：

1. To：填入一个或多个收件人的电子邮件地址；
2. Subject：邮件主题；
3. Cc：抄送；
4. Bcc：密送；
5. From：发件人的电子邮件；
6. Date：发信日期；
7. Reply-To：对方回信所用的地址。

## 2 实践应用

### 2.1 Hutool发送电子邮件

#### 2.1.1 引入依赖

```xml
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>5.7.15</version>
</dependency>
<dependency>
    <groupId>com.sun.mail</groupId>
    <artifactId>javax.mail</artifactId>
    <version>1.6.2</version>
</dependency>
```

#### 2.1.2 邮件服务器配置

在classpath（Maven项目中为src/main/resource）的config目录下新建mail.setting文件，完整配置文件：

```ymal
# 邮件服务器的SMTP地址
host = smtp.yeah.net
# 邮件服务器的SMTP端口
port =465
# 发件人（必须正确，否则发送失败）
from= hutool@yeah.net
# 用户名（注意：如果使用foxmail邮箱，此处user为qq号）
user = hutool
# 密码（注意，某些邮箱需要为SMTP服务单独设置密码，详情查看相关帮助）
pass= q1w2e3
#使用 STARTTLS安全连接，STARTTLS是对纯文本通信协议的扩展。
startttlsEnable =true
# 使用SSL安全连接
sslEnable =true
# 指定实现javax.net.SocketFactory接口的类的名称,这个类将被用于创建SMTP的套接字
socketFactoryClass = javax.net.ssl.SSLSocketFactory
# 如果设置为true,未能创建一个套接字使用指定的套接字工厂类将导致使用java.net.Socket创建的套接字类, 默认值为true
socketFactoryFallback =true
# 指定的端口连接到在使用指定的套接字工厂。如果没有设置,将使用默认端口456
socketFactoryPort =465
# SMTP超时时长，单位毫秒，缺省值不超时
timeout =0
# Socket连接超时值，单位毫秒，缺省值不超时
connectionTimeout =0
```

#### 2.1.3 邮件HTML模板

项目template目录下的模板文件mail.html

```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>发送邮件</title>
</head>
<body>
<p>发送邮件：${mail}</p>
</body>
</html>
```

#### 2.1.4 发送邮件

1. 发送普通文本邮件，最后一个参数可选添加多个附件：

```java 
MailUtil.send("hutool@foxmail.com","测试","邮件来自Hutool测试",false);
```

2. 发送HTML格式邮件并附带附件，最后一个参数可选是否添加多个附件：

```java
MailUtil.send("hutool@foxmail.com","测试","<h1>邮件来自Hutool测试</h1>",true,FileUtil.file("d:/aaa.xml"));
```

参数说明：

* tos：对方的邮箱地址，可以是单个，也可以是多个
* subject：标题
* content：邮件正文，可以是文本，也可以是HTML内容
* isHtml：是否为HTML，如果是，那参数3识别为HTML内容
* files：可选，附件，可以为多个或没有，将File对象加在最后一个可变参数中即可。

#### 2.1.5 代码

```java
// 项目template目录下的模板文件mail.html
TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.FILE));
Template template = engine.getTemplate("template" + File.separator + "mail.html");
String content = template.render(Dict.create().set("mail", "测试"));

MailAccount account = new MailAccount();
// 邮件服务器的SMTP地址
account.setHost("smtp.qq.com");
// 邮件服务器的SMTP端口
account.setPort("25");
account.setAuth(true);
// 发件人
account.setFrom("xxxxx@qq.com");
// 用户名，默认为发件人邮箱前缀
account.setUser("xxxxx");
//  密码
account.setPass("*****");

MailUtil.send(account, CollUtil.newArrayList("xxxx@qq.com"), "测试", content, true);
```

**参考：**

1. https://www.hutool.cn/docs/#/extra/%E9%82%AE%E4%BB%B6%E5%B7%A5%E5%85%B7-MailUtil
2. 《计算机网络》谢希仁
3. https://www.hutool.cn/docs/#/extra/%E9%82%AE%E4%BB%B6%E5%B7%A5%E5%85%B7-MailUtil
