package 复习.网络通信.$2;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author DreamYee
 * @Create 2020/01/16  15:19
 */
public class 服务器 {
    public static void main(String[] args) throws Exception {
        ServerSocket ss=new ServerSocket(9999);
        Socket s=ss.accept();
        InputStream in=s.getInputStream();
        OutputStream out=s.getOutputStream();
        new 输入(in).start();
        new 输出(out,"服务器").start();
    }
}
