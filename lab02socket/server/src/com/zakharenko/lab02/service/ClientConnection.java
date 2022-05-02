package com.zakharenko.lab02.service;

import com.zakharenko.lab02.entity.ChatText;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection implements Runnable {
    private final Socket clientSocket;
    private final ChatText chatText;
    private final Server server;
    private final PrintWriter writer;
    private final Scanner scanner;

    public ClientConnection(Socket clientSocket, ChatText chatText, Server server) throws IOException {
        this.clientSocket = clientSocket;
        this.chatText = chatText;
        this.server = server;
        writer = new PrintWriter(clientSocket.getOutputStream(), true);
        scanner = new Scanner(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        executeConnection();
        server.removeConnection(this);
    }

    private void executeConnection() {
        fillChat();
        while (scanner.hasNextLine()){
            String message = scanner.nextLine();
            chatText.addMessage(message);
            sendMessageAll(message);
        }
    }

    private void fillChat(){
        writer.println(chatText.getText());
    }

    private void sendMessageAll(String message){
        for(ClientConnection connection : server.getConnections()){
            connection.sendMessage(message);
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    public void removeConnection(){
        writer.close();
        scanner.close();
    }
}
