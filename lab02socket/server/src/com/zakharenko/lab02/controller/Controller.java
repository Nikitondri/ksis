package com.zakharenko.lab02.controller;

import com.zakharenko.lab02.service.Server;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.sound.sampled.Line;
import javax.sound.sampled.Port;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {
    @FXML
    public TextField portEdit;
    @FXML
    public Button buttonCreate;
    @FXML
    public Label idLabel;
    @FXML
    public Button buttonExit;
    private Server server;
    private Thread threadServer;

    @FXML
    private void initialize() {
        buttonExit.setDisable(false);
        buttonCreate.setOnAction(event -> {
            int port = Integer.parseInt(portEdit.getText());
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                server = new Server(serverSocket);
                idLabel.setText(InetAddress.getLocalHost().getHostAddress());
                threadServer = new Thread(server);
                threadServer.start();
            } catch (IOException e) {
                showErrorMessage();
            }
        });
        buttonExit.setOnAction(event -> {
            server.stopServer();
            threadServer.interrupt();
            idLabel.setText("");
        });
    }

    public static void showErrorMessage(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error fields");
        alert.setHeaderText("Error fields");
        alert.setContentText("Please? fill correct fields!");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }


}
