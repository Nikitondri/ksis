package com.zakharenko.lab02.service.server;

import com.zakharenko.lab02.service.exception.ServiceException;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection implements Runnable {

    private final Socket clientSocket;
    private final PrintWriter writer;
    private final Scanner scanner;
    private final TextArea textAreaChat;

    public ClientConnection(String ip, int port, TextArea textAreaChat) throws ServiceException {
        this.textAreaChat = textAreaChat;
        try {
            clientSocket = new Socket();
            clientSocket.connect(new InetSocketAddress(ip, port));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            scanner = new Scanner(clientSocket.getInputStream());
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void run(){
        while(scanner.hasNextLine() && !clientSocket.isClosed()){
            String message = scanner.nextLine();
            textAreaChat.appendText("\n" + message);
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String name, String message) throws ServiceException {
            writer.println(name + ": " + message);
            writer.flush();
    }
}
