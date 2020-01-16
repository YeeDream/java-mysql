package 复习.网络通信.$2;

import java.io.InputStream;

/**
 * @Author DreamYee
 * @Create 2020/01/16  15:20
 */
public class 输入 extends Thread {
    private InputStream in;

    public 输入(InputStream in){
        this.in=in;
    }

    @Override
    public void run() {
        while (true){
            try{
                byte[] bytes=new byte[1000];
                int len=in.read(bytes);
                String str=new String(bytes,0,len);
                System.out.println(str);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
