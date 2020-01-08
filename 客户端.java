package 复习.网络通信.TCP;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @Author DreamYee
 * @Create 2020/01/08  9:43
 */
public class 客户端 {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 9999);

            InputStream in = s.getInputStream();
            byte[] bytes = new byte[1024];
            int len = in.read(bytes);
            String str = new String(bytes, 0, len);
            System.out.println(str);
        }catch (UnknownHostException e){
            System.out.println(e.getMessage());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
