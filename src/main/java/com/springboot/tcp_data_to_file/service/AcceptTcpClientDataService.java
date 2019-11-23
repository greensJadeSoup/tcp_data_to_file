package com.springboot.tcp_data_to_file.service;

import java.net.Socket;

public interface AcceptTcpClientDataService {
    void acceptTcpClientData(Socket socket, byte[] bytes);
}
