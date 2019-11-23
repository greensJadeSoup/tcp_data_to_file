package com.springboot.tcp_data_to_file.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 监控控制台，每当控制台输入一行数据时就往磁盘里面写，磁盘有文件专门存放， 
 * 文件内容长度不能超过50个字符，超过时自动建立新文件并把超过的内容放到新文件中去
 * （要求程序关闭后再启动输入数据不会覆盖原来的数据）。
 * 问题：①数据长度超过50，就自动建立新的文件，将多余的数据写入，
 * 且写入的数据部得超过50，若超过又重新创建，以此循环。
 *
 */
public class CopyOfWriteFile002 {
	private int MaxFile = 1024*1024*10;//10M
	private BufferedReader in_br;
	private RandomAccessFile out_r;
	private File file = new File("F:\\workApp\\IdeaProjectsWorkspace\\tcp_data_to_file\\data\\nowWork.txt");

	{    //初始化 输出流
		in_br = new BufferedReader(new InputStreamReader(System.in));
		try {
			out_r = new RandomAccessFile(file, "rw");
			out_r.seek(out_r.length()); // 设置从该文件末尾开始写入
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    //读取控制台数据
	public void read() {
		System.out.println("请输入数据：");
		String str = null;
		try {
			while ((str = in_br.readLine()) != null) {
				splitFile(str);  
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out_r.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//拆分数据循环创建50字节大小的文件
	public void splitFile(String str) throws IOException{
		byte[] byte2 = str.getBytes(); // 解决乱码问题
		long len = str.length();
		/* 如果输入的数据与目标文件的大小和大于50，且目标文件大小大于50 就创建新文件
		 * 若目标文件大小小于50，就写入50-out_r.length()长度的数据
		 */
		if (len+out_r.length() >=50) { 
			if(out_r.length()>=50){   
			   createFile(file);  //创建文件方法
			   splitFile(str); 
			}else{
			   long buff = 50-out_r.length();
			   out_r.seek(out_r.length());
			   out_r.write(byte2,0,(int)buff);
			   if((len-buff)>0){
				   str = str.substring((int)buff);
				   splitFile(str);  //用递归
			   }
			}
		 }else{
		   out_r.write(byte2);
		   return;
		 }
	}

	// 创建新文件以当前时间命名，重新赋值输出流
	public void createFile(File file) throws IOException {
		Date date = new Date(); // 获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); // 定义文件名格式
		String formatDate = sdf.format(date); // 把当前时间以定义的格式 格式化
		String str1 = file.getParent() + "\\";
		String str2 = str1.concat(formatDate);
		String nameDate = str2 + ".txt"; // 定义路径
		out_r = new RandomAccessFile(nameDate, "rw"); // 获得写入目标文件
	}

	public static void main(String[] args) throws ParseException, IOException {
		CopyOfWriteFile002 wf = new CopyOfWriteFile002();
		wf.read();
	}
}