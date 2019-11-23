package com.springboot.tcp_data_to_file;

import com.springboot.tcp_data_to_file.servers.TcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class TcpDataToFileApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(TcpDataToFileApplication.class, args);
        TcpServer tcpService = new TcpServer();
        tcpService.getServerDemo();//调用开启服务器
    }

}
