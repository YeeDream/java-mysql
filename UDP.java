package 复习.网络通信;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @Author DreamYee
 * @Create 2020/01/07  22:37
 */
public class UDP {
    public static void main(String[] args) {
        new UDPServer().start();
        new UDPClient().start();
    }
}

class UDPServer extends Thread{
    @Override
    public void run() {
        try {
            //1.创建服务器端DatagramSocket,指定端口
            DatagramSocket socket=new DatagramSocket(8888);
            //2.创建数据报，用于接收客户端发送的数据
            byte[] data=new byte[1024];//创建字节数组，指定接受的数据包的大小
            DatagramPacket packet=new DatagramPacket(data,data.length);
            //3.接收客户端发送的数据
            System.out.println("****服务器已经启动，等待客户端发送数据");
            socket.receive(packet);//此方法在接收到数据报之前会一直阻塞
            //4.读取数据
            String info=new String(data,0,packet.getLength());//创建字符串对象
            System.out.println("我是服务器，客户端说："+info);//输出提示信息
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

class UDPClient extends Thread{
    @Override
    public void run() {
        try{
            //1.定义服务器的地址，端口号，数据
            InetAddress address=InetAddress.getByName("localhost");
            byte[] data="kkkkkkk".getBytes();
            //2.创建数据报，包含发送的数据信息
            DatagramPacket packet=new DatagramPacket(data,data.length,address,8888);
            //3.创建DatagramSocket对象
            DatagramSocket socket=new DatagramSocket();
            //4.向服务器端发送数据报
            socket.send(packet);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
