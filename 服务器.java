package 复习.网络通信.$1;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author DreamYee
 * @Create 2020/01/16  14:45
 */
public class 服务器 {
    public static void main(String[] args) {
        try{
            ServerSocket ss=new ServerSocket(9999);
            System.out.println("服务器启动成功，等待客户端连接……");
            Socket s=ss.accept();
            System.out.println("有客户端连接成功");

            输入1 in=new 输入1(s.getInputStream(),"服务器");
            in.start();

            输出1 out=new 输出1(s.getOutputStream(),"客户端");
            out.start();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
