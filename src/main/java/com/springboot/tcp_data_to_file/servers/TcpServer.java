package com.springboot.tcp_data_to_file.servers;
//tcp数据接收
import com.springboot.tcp_data_to_file.service.AcceptTcpClientDataService;
import com.springboot.tcp_data_to_file.service.Impl.AcceptTcpClientDataServiceImpl;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class TcpServer {
    public void getServerDemo() throws IOException {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            //建立服务器的Socket，并设定一个监听的端口PORT
            serverSocket = new ServerSocket(8001);
            //由于需要进行循环监听，因此获取消息的操作应放在一个while大循环中
            while(true){
                try {
                    //建立跟客户端的连接
                    socket = serverSocket.accept();
                } catch (Exception e) {
                    System.out.println("建立与客户端的连接出现异常");
                    e.printStackTrace();
                }
                ServerThread thread = new ServerThread(socket);
                thread.start();
            }
        } catch (Exception e) {
            System.out.println("端口被占用");
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }

    class ServerThread extends Thread
    {
        private Socket socket ;
        InputStream inputStream;
        OutputStream outputStream;
        AcceptTcpClientDataService acceptTcpClientDataService = new AcceptTcpClientDataServiceImpl();

        public  ServerThread(Socket socket){
            this.socket=socket;
        }
        public void run(){
            try {
                while (true){
                    /*System.out.println("连接成功");*/
                    //接收客户端的消息并打印
                    //System.out.println(socket);
                    inputStream=socket.getInputStream();
                    outputStream = socket.getOutputStream();
                    byte[] bytes = new byte[1024];
                    inputStream.read(bytes);
                    //System.out.println(string);
                    //调用tcp解析，并存成日志
                    acceptTcpClientDataService.acceptTcpClientData(socket,bytes);

                    //向客户端发送消息
                    //这两句选一句必加，不然触发不了异常，导致继续接收已关闭客户端数据
                    //socket.sendUrgentData(0xFF);
                    outputStream.write(" ".getBytes());
                    System.out.println("OK");

                }
            } catch (Exception e) {
                System.out.println("客户端主动断开连接了");
                //e.printStackTrace();
            }
            //操作结束，关闭socket
            try{
                socket.close();
            }catch(IOException e){
                System.out.println("关闭连接出现异常");
                e.printStackTrace();
            }
        }
    }
}