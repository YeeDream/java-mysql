package 复习.网络通信.TCP;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author DreamYee
 * @Create 2020/01/08  9:43
 */
public class 服务端 {
    public static void main(String[] args) {
        try{
            //创建服务端套接字
            ServerSocket ss=new ServerSocket(9999);
            System.out.println("等待客户端连接……");
            Socket s=ss.accept();//客户端连接，执行
            System.out.println("客户端连接成功！");

            OutputStream outputStream=s.getOutputStream();//获取到输出流

            outputStream.write("Hello,Who are you?".getBytes());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
