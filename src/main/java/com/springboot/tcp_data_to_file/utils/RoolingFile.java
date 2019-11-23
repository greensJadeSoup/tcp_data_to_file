package com.springboot.tcp_data_to_file.utils;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RoolingFile {
    //文件最大10M
    //private int MaxFile = 1024*1024*10;
    private int MaxFile = 1*1024;
    private RandomAccessFile out_r;     //当前文件
    private String fileName = "/nowWork.log";    //时间+后缀nameDate
    private String filePath; //文件路径
    private String nameDate;    //完全文件名
    private File file;
    private int fileNum=1;   //有几个日志

    public void writeFile(String filePath,String fileData){
        this.filePath = filePath;
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
    }

    //拆分数据循环创建50字节大小的文件
    public void splitFile(String fileData){
        long len = fileData.length();
        /* 如果输入的数据与目标文件的大小和大于x,就创建新文件
         * 若输入的数据与目标文件的大小和小于x，就写入
         */
        try {
            if (len+out_r.length() >=MaxFile) {
                //createFile(this.filePath);  //创建文件方法
                //将nowWork.log改名为新文件，再新建
/*                System.out.println(file);
                System.out.println(new File(newDateFile(filePath)));

                splitFile(fileData);*/

                out_r.close();
                //File file = new File("F:\\workApp\\IdeaProjectsWorkspace\\tcp_data_to_file\\logs\\2019\\11\\23\\192.168.1.123\\nowWork.log");
                //file.renameTo(new File("F:\\workApp\\IdeaProjectsWorkspace\\tcp_data_to_file\\logs\\2019\\11\\23\\192.168.1.123\\17_13_15.log"));
                System.out.println(this.file);
                System.out.println(new File(newDateFile(filePath)));
                this.file.renameTo(new File(newDateFile(filePath)));

                //创建newWork
                try {
                    out_r = new RandomAccessFile(this.file, "rw");
                    out_r.seek(out_r.length()); // 设置从该文件数据末尾开始写入
                } catch (Exception e) {
                    e.printStackTrace();
                }
                splitFile(fileData);
            }else{
                //\r\n换行
                out_r.write((fileData+"\r\n").getBytes());
                return;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String newDateFile(String filePath){
        Date date = new Date(); // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss"); // 定义文件名格式
        String formatDate = sdf.format(date); // 把当前时间以定义的格式 格式化
        String str1 = filePath+"/" ;
        String str2 = str1.concat(formatDate);
        String newDateFileName = str2 + ".log"; // 定义路径
        return newDateFileName;
    }

    // 接收当前路径，创建新文件以当前时间命名，重新赋值输出流
/*    public void createFile(String filePath) throws IOException {
        Date date = new Date(); // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss"); // 定义文件名格式
        String formatDate = sdf.format(date); // 把当前时间以定义的格式 格式化
        String str1 = filePath+"/" ;
        String str2 = str1.concat(formatDate);
        String nameDate = str2 + ".log"; // 定义路径

        //不加就一直查最初的文件
        this.nameDate = nameDate;
        this.fileName = formatDate+".log";

        out_r = new RandomAccessFile(nameDate, "rw"); // 获得写入目标文件
    }*/
}
