package 复习.网络通信;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author DreamYee
 * @Create 2020/01/07  21:59
 */
public class url2 {
    public static void main(String[] args) {
        try{
            URL url=new URL("http://www.baidu.com");
            URLConnection urlConnection=url.openConnection();
            HttpURLConnection connection=null;
            if(urlConnection instanceof HttpURLConnection){
                connection=(HttpURLConnection) urlConnection;
            }else {
                System.out.println("请输入URL地址：");
                return;
            }
            Reader reader=new InputStreamReader(connection.getInputStream());
        }catch (IOException e){
            e.getStackTrace();
        }

        /**
         * 1.Servlet中获取输入流，输出流
         *   request.getInputStream()    response.getOutputStream()
         * 2.Ajax的输出实现：out.print()
         * 3.使用字符缓冲输出流和字符输出流输出内容，区别：
         *缓冲：避免频繁读取磁盘
         * 刷新：不刷新，不输出
         */
    }
}
