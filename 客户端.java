package 复习.网络通信.$2;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Author DreamYee
 * @Create 2020/01/16  15:20
 */
public class 客户端 {
    public static void main(String[] args)throws Exception {
        Socket s=new Socket("127.0.0.1",9999);
        InputStream in=s.getInputStream();
        OutputStream out=s.getOutputStream();
        new 输入(in).start();
        new 输出(out,"客户端").start();
    }
}
