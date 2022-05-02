package com.zakharenko.lab02.service;

import com.zakharenko.lab02.controller.Controller;
import com.zakharenko.lab02.entity.ChatText;
import com.zakharenko.lab02.service.exception.ServiceException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread implements Runnable{

    private final ChatText chatText;
    Set<ClientConnection> connections;
//    private final int port;
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        chatText = new ChatText();
        connections = new HashSet<>();
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            startServer();
        } catch (ServiceException e) {
        }
    }

    public void startServer() throws ServiceException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                ClientConnection clientConnection = new ClientConnection(socket, chatText, this);
                connections.add(clientConnection);
                executorService.execute(clientConnection);
            }
        } catch (IOException e) {
            throw new ServiceException(e);
        }
        executorService.shutdown();
    }

    public void stopServer(){
        for(ClientConnection connection : connections){
            removeConnection(connection);
        }
    }

    public void removeConnection(ClientConnection connection){
        connections.remove(connection);
    }

    public Set<ClientConnection> getConnections() {
        return connections;
    }
}
