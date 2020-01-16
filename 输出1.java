package 复习.网络通信.$1;

import java.io.OutputStream;
import java.util.Scanner;

/**
 * @Author DreamYee
 * @Create 2020/01/16  14:45
 */
public class 输出1 extends Thread{
    private OutputStream out;
    private String name;
    private Scanner in=new Scanner(System.in);

    public 输出1(OutputStream out,String name){
        this.out=out;
        this.name=name;
    }

    @Override
    public void run() {
        while (true){
            try{
                System.out.println(name+"请输出内容：");
                String str=name+":"+in.next();
                out.write(str.getBytes());
                out.flush();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
