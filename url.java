package 复习.网络通信;

import java.io.IOException;
import java.net.URL;

/**
 * @Author DreamYee
 * @Create 2020/01/07  21:47
 */
public class url {
    public static void main(String[] args) {
        try{
            //网址后面的“/”后指的是路径(含/)，?后面是请求参数
            URL url=new URL("https://www.baidu.com/aaa?wd=java");
            System.out.println("URL为："+url.toString());
            System.out.println("协议为："+url.getProtocol());
            System.out.println("验证信息为："+url.getAuthority());
            System.out.println("文件及请求参数："+url.getFile());
            System.out.println("主机名："+url.getHost());
            System.out.println("路径："+url.getPath());
            System.out.println("端口："+url.getPort());
            System.out.println("默认端口："+url.getDefaultPort());
            System.out.println("请求参数："+url.getQuery());
            System.out.println("定位位置："+url.getRef());
        }catch (IOException e){
            e.getStackTrace();
        }
    }
}
