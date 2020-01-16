package 复习.网络通信.$1;


import java.io.InputStream;

/**
 * @Author DreamYee
 * @Create 2020/01/16  14:46
 */
public class 输入1 extends Thread {
    private InputStream in;
    private String name;

    public 输入1(InputStream in,String name){
        this.in=in;
        this.name=name;
    }

    @Override
    public void run() {
        try{
            byte[] bytes=new byte[1000];
            int len=in.read(bytes);
            String str=new String(bytes,0,len);
            if("服务器".equals(name)){
                System.out.println("客户端:"+str);
            }else {
                System.out.println("服务器："+str);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
