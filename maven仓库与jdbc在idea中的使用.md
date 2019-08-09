## **一.Maven**
*1.Maven介绍*

 - Maven，一个意义上的意义累积器的意第绪语，开始是为了简化Jakarta Turbine项目中的构建过程。有几个项目，每个项目都有自己的Ant构建文件，这些项目都略有不同。JAR被检入CVS。我们想要一种标准的方法来构建项目，明确定义项目的内容，发布项目信息的简便方法以及在多个项目中共享JAR的方法。
 - 结果是一个工具，现在可用于构建和管理任何基于Java的项目。我们希望我们已经创建了一些东西，使Java开发人员的日常工作变得更加容易，并且通常有助于理解任何基于Java的项目。

*2.Maven的目标*
Maven的主要目标是让开发人员在最短的时间内理解开发工作的完整状态。为了实现这一目标，Maven试图处理以下几个方面的问题：

 - 使构建过程变得简单
 - 提供统一的构建系统
 - 提供优质的项目信息
 - 提供最佳实践开发指南
 - 允许透明迁移到新功能

参照：(http://maven.apache.org/what-is-maven.html)
*3.Maven的主要功能*
Maven是一种构建工具(打包项目)、依赖管理工具(资源依赖管理工具：主要用于集成资源)、项目信息聚合工具

## 二.Maven核心概念

*1.坐标：*定位在中央仓库的位置，在pom文件中有详细定义
*groupId
*artifactId
*version 1.0(2.0, 3.0-SNAPSHOT ...)
*packaging    jar(war...)
*classifier  jdk
组成路径信息，从远程仓库获取资源

上述参照：(https://blog.csdn.net/qq_19704045/article/details/80387075)

------

**==中央仓库：==**
```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
...
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.13</version>
    </dependency>

  </dependencies>
  ...
</project>

```
----

*2.依赖*
scope参数：

依赖范围
maven项目结构：src目录下有main（主代码），test（测试代码），

与src同级的还有pom.xml

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190807222758379.png)

*3.仓库(Repository)*
首先下载maven，如图：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190807223249144.png)

	 a. 默认的，本地所有Maven项目都复用一个本地仓库
	b. 本地仓库从远程仓库(可配置)下载必要的构件
	c. 中央仓库是唯一内置的远程仓库

*4.配置环境变量*

**a**.在path中配置maven的bin目录所在路径(必须依赖jdk)或者将下载好的jdk的环境配置好也是可以的。

**b**.接着在同一目录下建立一个Repository的文件

## 三.在idea中建立一个maven项目
*1.在idea中的File选择new Project:*

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190807224619201.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDY5NDMxNw==,size_16,color_FFFFFF,t_70)

*2.一般①处写的是网址的反向
       ②处就是自己起的名字*

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190807224736379.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDY5NDMxNw==,size_16,color_FFFFFF,t_70)

*3.这一步就是创建maven项目的关键，找到maven下载的位置，找conf文件中的setting.xml文件*

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190807225302283.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDY5NDMxNw==,size_16,color_FFFFFF,t_70)

*4.保存路径，在这一步设置你要将这个项目保存在哪个磁盘中*；*

*5.到此，我们的maven项目就建好了，但是，我们这是要跟数据库连接，所以接下来解决的就是idea中的maven项目怎么去连接数据库？**

## 四.Maven中的jdbc

***1.简要介绍 jdbc***
****※JDBC(Java DataBase Connectivity):Java数据库连接技术****
**※作用：连接Java和相应的数据库**
***※JDBC中所需要的接口和类基本上出自于java.sql.*包**
※JDBC不负责执行SQL命令，SQL命令仍然由数据库执行，也就是说SQL命令Java不负责验  	证,Java只负责传递命令和接收结果**

*2.首先，pom.xml中导入数据库的jar包*
怎么导jar包：百度搜索maven,然后进入搜索mysql就会出现mysql的各个版本，然后根据自己数据库下载的版本去找相应的jar包(我的mysql版本是8.0.13)，下面都是以mysql数据库为例：

==这就是mysql的jar包：==
```
<dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.13</version>
</dependency>
```
***3*.*在main文件下面建立java文件，然后mark一下成为Sources  Root,在java文件下创建4个包：*

	    bean:实体类，封装类(一般模拟数据库中的表)
	biz:业务包
	db:负责打开或关闭数据库通道
	dao:负责增删改查操作
	controller/servlet/action/service:控制器包(后面框架会用到，spring,servlet等)
	util:工具包
4.bean中的封装类在此不多说，主要说明连接数据库
在db包中创建==DBManager的类==，添加如下代码：

```
/**
     *负责获取到数据库的连接
     * 获取到数据库连接为conn
     */
    public static Connection getConnection(){
        //驱动类的完整路径
        String driverClassName="com.mysql.cj.jdbc.Driver";
        //MySQL数据库的URL地址，变的主要有：IP,端口号，数据库名
        String url="jdbc:mysql://localhost:3306/数据库名?characterEncoding=UTF8"+
                "&userSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true";
        String user="root";  //默认都是root
        String password="zmr19980518";  //数据库密码
        Connection conn=null;
        try{
         	//第一步：加载驱动文件
            Class.forName(driverClassName);
          	//第二步：建立连接通道
            conn= DriverManager.getConnection(url,user,password);
			//第三步：产生执行对象
            String sql="select * from student";
        }catch (Exception e){
            System.out.println("数据库连接时出现问题："+e.getMessage());
        }
        return conn;
    }
    //第四步：就是获取到查询的结果集
    ResultSet rs=ps.executeQuery();
    //第五步：
    rs.close();
    ps.close();
    conn.close();
```
5.数据库至此已成功连接上。

以上数据库都是以mysql8.0.13为例
在写的过程中可能有很多不足，希望可以指正错误！！！

