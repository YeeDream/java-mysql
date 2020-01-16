package 复习.网络通信.$2;

import java.io.OutputStream;
import java.util.Scanner;
/**
 * @Author DreamYee
 * @Create 2020/01/16  15:20
 */
public class 输出 extends Thread {
    private OutputStream out;
    private String name;
    private Scanner in=new Scanner(System.in);


    public 输出(OutputStream out,String name){
        this.out=out;
        this.name=name;
    }

    @Override
    public void run() {
        while (true){
            String str=name+":"+in.next();
            try{
               out.write(str.getBytes());
               out.flush();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
