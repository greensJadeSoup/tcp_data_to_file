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
    //日期文件路径
    private Date date = new Date();
    public void acceptTcpClientData(Socket socket,byte[] bytes){
        //将byte数据转化为有效字符串
        String data = BytesToString.byteToStr(bytes);

        String information = String.valueOf(socket);
        String informationIP = information.substring(information.indexOf("/")+1,information.indexOf(",p"));
        String filePath=System.getProperty("user.dir")+"/logs"+"/"+new SimpleDateFormat("yyyy/MM/dd/").format(date)+informationIP;
        String fileData = "ip:"+informationIP+",data:"+data;
        //新建文件夹
        CreatFileFolder creatFileFolder = new CreatFileFolder();
        creatFileFolder.createTcpAcpetFilefolder(filePath);
        //写入文件
        RoolingFile roolingFile = new RoolingFile();
        roolingFile.writeFile(filePath,fileData);
    }
}
