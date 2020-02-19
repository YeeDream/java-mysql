1.什么是拦截器？

Spring MVC 中的拦截器类似于Servlet中的过滤器（Filter），它主要用于拦截用户请求并作相应的处理。

例拦截器可以进行权限验证、记录请求信息的日志、判断用户是否登录等。

要使用拦截器，就需要对拦截器进行定义与配置。通常拦截器可以通过两种方式来定义：


```
1.通过实现HandlerInterceptor接口，或继承HandlerInterceptor接口的实现类来定义。

2.通过实现WebRequestInterceptor接口，或继承WebRequestInterceptor接口的实现类来定义。
```

2.拦截器的配置

开发拦截器就像开发Servlet或者filter一样，都需要在配置文件中进行配置，配置代码如下：


```
<!--配置自定义的拦截器-->
<mvc:interceptors>
<!--拦截器1-->
<mvc:interceptor>
<!--配置拦截器的作用路径-->
<mvc:mapping path= "/user/**"/><!--拦截的路径规则-->
<!--定义在<mvc:interceptor>下面的表示匹配指定路径的请求才进行拦截-->
<bean class="com.mmm.interceptors.MyInterceptor"/>
</mvc:interceptor>
<!--拦截器2-->
<mvc:interceptor>
<mvc:mapping path="/user/**"/><!--拦截的路径规则-->
<bean class="com.mmm.interceptors.MyInterceptor2"/>
</mvc:interceptor>

</mvc:interceptors>
```

3.拦截器的执行流程

①程序先执行preHandle()方法，如果该方法的返回值为true,则程序会继续向下执行处理其中的方法，否则将不再向下执行。

②在业务处理器（即控制器Controller类）处理完请求后，会执行postHandle()方法，然后会通过DispatcherServlet向客户端返回响应。

③在DispatcherServlet处理完请求后，才会执行afterCompletion()方法。


4.拦截器与过滤器的区别与联系

①过滤器和拦截器都是对请求进行过滤拦截的,会在请求服务方法之前进行执行,
响应时也会执行,如果过滤器和拦截器对请求进行拦截,则不能访问服务方法,一般我们使用过滤器和拦截器执行权限校验等操作

②过滤器是Servlet的内容,而拦截器是SpringMVC的内容,过滤器的级别比拦截器级别高

③过滤器能拦截所有请求,拦截器只能拦截spring的请求

④过滤器是在web.xml中配置的,而拦截器是配置在applicationContext.xml中



5.上传下载拦截相关代码：

（1）controller包：
```
package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;

/**
 * @Author DreamYee
 * @Create 2020/02/13  12:17
 */

@Controller
@RequestMapping("/file")
public class FileController {
    @RequestMapping(value = "upload1",method = {RequestMethod.POST})
    public void upload(@RequestParam(name = "file") CommonsMultipartFile file){
        long startTime=System.currentTimeMillis();
        //将要上传的路径
        String fileName=file.getOriginalFilename();
        String type=fileName.substring(fileName.lastIndexOf("."));
        fileName=startTime+type;
        File outFile=new File("E:\\upload",fileName);
        try{
            OutputStream out=new FileOutputStream(outFile);
            InputStream in=file.getInputStream();
            int len;
            while ((len=in.read())!=-1){
                out.write(len);
            }
            out.flush();
            out.close();
            in.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        long endTime=System.currentTimeMillis();
        System.out.println("方法一上传所需毫秒："+(endTime-startTime));
    }

    @RequestMapping("/upload2")
    public void upload2(@RequestParam(name = "file") CommonsMultipartFile file){
        long startTime=System.currentTimeMillis();
        //将要上传的路径
        String fileName=file.getOriginalFilename();
        String type=fileName.substring(fileName.lastIndexOf("."));
        fileName=startTime+type;
        File outFile=new File("E:\\upload",fileName);
        try{
            file.transferTo(outFile);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        long endTime=System.currentTimeMillis();
        System.out.println("方法二上传所需毫秒："+(endTime-startTime));
    }

    @RequestMapping("/upload3")
    public void upload3(HttpServletRequest request)throws IOException{
        long startTime=System.currentTimeMillis();
        //将上下文初识化给CommonsMultipartResolver（多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());

        //检查form中是否有enctype="mutlipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            Iterator iter=multiRequest.getFileNames();//获取multipart中的所有文件名
            while (iter.hasNext()){
                MultipartFile file=multiRequest.getFile(iter.next().toString());//一次遍历所有文件
                if(file!=null){
                    String fileName=file.getOriginalFilename();
                    String type=fileName.substring(fileName.lastIndexOf("."));
                    fileName=startTime+type;
                    String path="E:/upload/"+fileName;
                    file.transferTo(new File(path));//上传
                }
                long endTime=System.currentTimeMillis();
                System.out.println("方法三上传所用毫秒："+(endTime-startTime));
            }

        }
    }

    @RequestMapping("/download")
    public void downupload(String fileName, HttpServletResponse response) throws Exception{
        System.out.println("!!!!!");
        System.out.println(fileName);
        File src=new File("E://upload",fileName);
        System.out.println(src.exists());

        //设置响应头文件内容，文件类型，弹出下载对话框，文件大小
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-disposition","attachment,filename="+new String(fileName.getBytes("utf-8"),"ISO8859-1"));
        response.setHeader("Content-Length", String.valueOf(src.length()));
        File downFile = new File("E://xiazai",fileName);
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(downFile);
        int len;
        while((len=in.read())!=-1){
            out.write(len);
        }
        out.flush();
        out.close();
        in.close();
    }
}

```
（2）interceptor包：


```
package interceptor;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author DreamYee
 * @Create 2020/02/13  12:17
 */
public class FirstInterceptor {
    //拦截方法，在服务方法之前运行，如果返回false则不会去服务方法
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response)throws Exception{
        System.out.println(11111);
        return true;
    }

    //响应时执行的方法，它会在视图解析器之前执行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)throws Exception{
        System.out.println(22222);
    }

    //响应时执行的方法，它会在视图解析器之后执行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,Object handler,Exception ex)throws Exception{
        System.out.println(33333);
    }
}
```


```
package interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author DreamYee
 * @Create 2020/02/13  12:18
 */
public class SecondInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("aaaaa");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("bbbbb");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("ccccc");
    }
}

```
（3）resources包applicationContext.xml中配置：

```
    <mvc:interceptors>
        <bean class="interceptor.FirstInterceptor"/>
        <mvc:interceptor>
            <mvc:mapping path="/user/find*.spring"/>
            <bean class="interceptor.SecondInterceptor"/>
        </mvc:interceptor>

    </mvc:interceptors>

    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/"/>
        <property name="suffix" value=".html"/>
    </bean>


</beans>
```
（4）web.xml中注册核心servlet：

```
<!--注册核心servlet-->
  <servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:</param-value>
    </init-param>
  </servlet>

  <servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>*.spring</url-pattern>
  </servlet-mapping>
```
（5）index.html

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>拦截器</title>
</head>
<body>
第一种上传方式：
 <form action="/file/upload1.spring" method="post" enctype="multipart/form-data">
     <input type="file" name="file"/>
     <button>上传1</button>
 </form>
<hr/>
第二种上传方式：
<form action="/file/upload2.spring" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <button>上传2</button>
</form>
<hr/>
第三种上传方式：
<form action="/file/upload3.spring" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <button>上传3</button>
</form>

<a href="/file/download.spring?fileName=1566860968354.png">文件1</a>
</body>
</html>
```


