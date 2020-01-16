package 复习.网络通信.$1;

import java.net.Socket;

/**
 * @Author DreamYee
 * @Create 2020/01/16  14:45
 */
public class 客户端 {
    public static void main(String[] args) {
        try{
            Socket s=new Socket("localhost",9999);
            输入1 in=new 输入1(s.getInputStream(),"客户端");
            in.start();

            输出1 out=new 输出1(s.getOutputStream(),"服务器");
            out.start();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
