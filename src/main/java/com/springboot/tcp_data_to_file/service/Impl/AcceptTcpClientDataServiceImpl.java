package com.springboot.tcp_data_to_file.service.Impl;
//tcp Socket数据处理

import com.springboot.tcp_data_to_file.service.AcceptTcpClientDataService;
import com.springboot.tcp_data_to_file.utils.BytesToString;
import com.springboot.tcp_data_to_file.utils.CreatFileFolder;
import com.springboot.tcp_data_to_file.utils.RoolingFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AcceptTcpClientDataServiceImpl implements AcceptTcpClientDataService {
    //文件最大10M
    //private int MaxFile = 1024*1024*10;
    private int MaxFile = 10*1024;
    //日期文件路径
    private Date date = new Date();
    public void acceptTcpClientData(Socket socket,byte[] bytes){
        //将byte数据转化为有效字符串
        String data = BytesToString.byteToStr(bytes);

        String information = String.valueOf(socket);
        String informationIP = information.substring(information.indexOf("/")+1,information.indexOf(",p"));
        String filePath=System.getProperty("user.dir")+"/datalogs"+"/"+new SimpleDateFormat("yyyy/MM/dd/").format(date)+informationIP;
        String fileData = "ip:"+informationIP+",data:"+data;
        //新建文件夹
        CreatFileFolder creatFileFolder = new CreatFileFolder();
        creatFileFolder.createTcpAcpetFilefolder(filePath);

        //查看NewWork文件大小并处理
        File file = new File(filePath+"/nowWork.log");
        Date date = new Date(); // 获取当前时间

        SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss"); // 定义文件名格式
        String formatDate = sdf.format(date); // 把当前时间以定义的格式 格式化
        String str = filePath+"/"+formatDate ;
        String newDateFileName = str + ".log"; // 定义路径
        if(file.length()>=MaxFile){
            System.out.println(file);
            System.out.println(file.renameTo(new File(newDateFileName)));
            System.out.println(new File(newDateFileName));
        }

        //写入文件
        RoolingFile roolingFile = new RoolingFile();
        roolingFile.writeFile(filePath,fileData);
    }
}
