package com.springboot.tcp_data_to_file.demo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class fileDemo {
    public static void main(String[] args) {
        File file = new File("F:\\workApp\\IdeaProjectsWorkspace\\tcp_data_to_file\\logs\\2019\\11\\23\\192.168.1.123\\nowWork.log");
        file.renameTo(new File("F:\\workApp\\IdeaProjectsWorkspace\\tcp_data_to_file\\logs\\2019\\11\\23\\192.168.1.123\\17_13_15.log"));
    }
}
