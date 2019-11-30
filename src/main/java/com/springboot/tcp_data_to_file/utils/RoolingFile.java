package com.springboot.tcp_data_to_file.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RoolingFile {
    private RandomAccessFile out_r;     //当前文件
    private String fileName = "/nowWork.log";    //时间+后缀nameDate
    private String nameDate;    //完全文件名
    private File file;

    public void writeFile(String filePath,String fileData){
        this.nameDate = filePath+fileName;
        this.file = new File(nameDate);
        //创建newWork
        try {
            out_r = new RandomAccessFile(this.file, "rw");
            out_r.seek(out_r.length()); // 设置从该文件末尾开始写入
        } catch (Exception e) {
            e.printStackTrace();
        }

        splitFile(fileData);
        try {
            out_r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //拆分数据循环创建50字节大小的文件
    public void splitFile(String fileData){
        long len = fileData.length();
        /* 如果输入的数据与目标文件的大小和大于x,就创建新文件
         * 若输入的数据与目标文件的大小和小于x，就写入
         */
        try {
            out_r.write((fileData+"\r\n").getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
