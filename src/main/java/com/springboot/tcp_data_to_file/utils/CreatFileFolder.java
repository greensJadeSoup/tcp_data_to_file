package com.springboot.tcp_data_to_file.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreatFileFolder {

    public void createTcpAcpetFilefolder(String filePath){

        File file = new File(filePath);

        //如果不存在,创建文件夹
        if(!file.exists()){
            file.mkdirs();
            // 重新实例化
            file = new File(filePath);
        }
    }
}
