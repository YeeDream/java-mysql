1.什么是Tomcat
  
  Tomcat服务器是一个免费的开源代码的web应用服务器，属于轻量级应用服务器，在中小型系统和并发访问用户不是很多的场合下被普遍使用，是开发和调试jsp程序的首选。


2.如何安装部署（Windows，在Linux下安装）

  (1)下载网站：https://tomcat.apache.org/
       
   下载tomcat9   zip格式的

  (2)安装部署：
  
    ①直接对压缩包进行解压
    
    ②选择“此电脑”，单击右键，选择属性
    
    ③选择打开属性后，选择单击弹窗左边的“高级系统设置”
    
    ④打开“高级系统设置后”，单击“环境变量”
    
    ⑤对CATALINA_HOME、Path这两个系统变量分别设置
       CATALINA_HOME=安装路径\tomcat文件名
       PathPath=%CATALINA_HOME%\lib
       %CATALINA_HOME%\lib\servlet-api.jar %CATALINA_HOME%\lib\jsp-api.jar
    
    ⑥添加用户，进入tomcat9目录的conf,选择tomcat-users.xml文件，打开后在最后一行代码的前面添加如下代码：
     <role rolename="manager-gui"/> 
     <role rolename="admin-gui"/>  
     <user username="admin" password="admin" roles="admin-gui"/>
     <user username="tomcat" password="admin" roles="manager-gui"/>
    
    ⑦添加完成后，保存退出
    
    ⑧启动tomcat测试，打开tomcat目录下的bin文件夹，再双击startup来启东tomcat，启动成功会显示Start Server startup in ...ms
    
    ⑨打开浏览器，在地址栏输入http://localhost:8080 或 http://127.0.0.1:8080进行打开tomcat的主页
     
3.文件夹的作用
  
  (1)bin目录主要是用来存放tomcat的命令，主要有两大类：一类是.sh结尾的（limux命令），另一类是以.bat结尾的（windows命令）
     很多环境变量的设置都在此处
  
  (2)conf目录主要是用来存放tomcat的一些配置文件
     server.xml可以设置端口号、设置域名或IP、默认加载的项目、请求编码。
     
     web.xml可以设置tomcat支持的文件类型
     
    context.xml可以用来配置数据源之类的
     
    tomcat-users.xml用来配置管理Tomcat用户与权限
     
    在catalina目录下可以设置默认加载的项目
  
  (3)lib目录主要用来存放tomcat运行需要加载的jar包
  
  (4)ogs目录用来存放tomcat在运行过程中产生的日志文件，非常重要的是在控制台输出的日志。（清空不会对tomcat运行带来影响）
  
  (5)temp目录用户存放tomcat在运行过程中产生的临时文件。（清空不会对tomcat运行带来影响）
  
  (6)webapps目录用来存放应用程序，当tomcat启动时会去加载webapps目录下的应用程序。可以在文件夹、war包、jar包的形式发布应用
  
  (7)work目录用来存放tomcat在运行时的编译后文件，例如jsp编译后
  的.java和.class文件。
  
     一个客户端访问后编译后的文件存放在这，下一个客户端再次访问可以直接返回，不用再次编译程序。

    清空work目录，然后重启tomcat，可以达到清除缓存的作用。