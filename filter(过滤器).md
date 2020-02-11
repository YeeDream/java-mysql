1.概念
  
  过滤器是处于客户端与服务器资源之间的一道过滤网，在访问资源文件之前，通过一系列的过滤器对请求进行修改、判断等，把不符合规则的请求在中途拦截或修改。也可以对响应进行过滤，拦截或修改响应。

2.执行时间

   使用Filter的完整流程：Filter对用户请求进行预处理，接着将请求交给Servlet进行处理并生成响应，最后Filter再对服务器响应进
   行后处理。
  
  Filter功能：
  
      ①在HttpServletRequest到达Servlet之前，拦截客户的HttpServletRequest。根据需要检查HttpServletRequest，也可以修改HttpServletRequest头和数据。

    ②在HttpServletResponse到达客户端之前，拦截HttpServletResponse。根据检查HttpServletResponse，也可以修改HttpServletResponse头和数据。

3.如何实现

   Filter接口中有一个doFilter方法，当开发人员编写好Filter，并配置对哪个web资源进行拦截后，web服务器每次在调用web资源的service方法之前，都会先调用filter的doFilter方法，因此，在该方法内编写代码可达到如下目的：
  
  ①调用目标资源之前，让一段代码执行
  
  ②倒入是否调用目标资源（即是否让用户访问web资源）
  
  ③Web服务器在调用doFilter方法时，会传递一个filterChain对象时filter接口中最重要的一个对象，它也提供了一个doFilter方法，开发人员可以根据需求决定是否调用此方法，调用该方法，则web服务器就会调用web资源的service方法，即资源就会被访问，否则web资源不会被访问。

4.Filter的生命周期

  (1)初始化：


```
public void init(FilterConfig filterConfig) throws ServletException;
```


和我们编写servlet程序一样，Filter的创建和销毁由web服务器负责。Web应用程序启动时，web服务器将创建Filter的实例对象，并调用其init方法，读取web.xml配置，完成对象的实例化功能，从而为后续的用户请求做好拦截的准备工作（Filter对象只会创建一次，init方法也只会执行一次）。开发人员通过init方法的参数，可获得代表当前filter配置信息的FilterConfig对象。

 (2)拦截请求：
 
 
```
public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws IOException,ServletException;
```

     
这个方法完成实际的过滤操作。当客户请求访问与过滤关联的URL的时候，Servlet过滤器将限制性doFilter方法。FilterChain参数用于访问后过滤过滤器。
  
(3)销毁：


```
public void destroy();  
```

    
Filter对象创建后会驻留在内存，当web应用移除或服务器停止时才销毁。在web容器卸载Filter对象之前被调用。该方法在Filter的生命周期中仅执行一次。在这个方法中，可以释放过滤器使用的资源。