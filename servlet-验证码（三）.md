验证码（案例）
--
 
  1.没有验证码带来的问题：
 
    ①对特定用户不断登录破解密码
 
    ②对某个网站创建账户
 
    ③对某个网站提交垃圾数据
 
    ④对某个网站刷票

  通过验证码由用户肉眼识别其中的验证码信息，从而区分用户是人还是计算机
  
 2.定义：
    
    验证码：是一种区分用户是人还是计算机的公共全自动程序

    作用：防止恶意破解密码、刷票、论坛灌水、防止黑客暴力破解

 3.使用Servlet实现验证码
     
  (1)实现图片验证码类CodeServlet.java

```
package servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @Author DreamYee
 * @Create 2020/02/03  20:08
 */

@WebServlet(name="codeServlet",urlPatterns = "/code")
public class CodeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int width=200;
        int height=50;
        String str="qwertyuiopasdfghjklzxcvbnm1234567890";
        Random random=new Random();

        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics=image.getGraphics();
        graphics.drawRect(0,0,200,50);
        for(int i=0;i<4;i++){
            int number=random.nextInt(str.length());
            String a=str.substring(number,number+1);
            graphics.drawString(a,25*(i+1),30);
        }

        ImageIO.write(image,"jpg",resp.getOutputStream());
    }
}

```

(2)index.html

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>验证码</title>
</head>
<body>
<image id="verifycode" src="code" onclick="this.src=this.src+'?'+Math.random()" "btn.isDisable=false"></image>
<button id="btn" onclick="document.getElementById('verifycode').click()">刷新验证码</button>
</body>
</html>
```

(3)pom.xml

在pom.xml中能够添加如下代码：

```
<dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>4.0.1</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.13</version>
    </dependency>
```
