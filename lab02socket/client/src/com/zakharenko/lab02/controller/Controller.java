package com.zakharenko.lab02.controller;

import com.zakharenko.lab02.service.exception.ServiceException;
import com.zakharenko.lab02.service.server.ClientConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    public Button buttonJoin;
    @FXML
    public TextField portEdit;
    @FXML
    public TextField editIp;
    @FXML
    public TextField nameEdit;
    @FXML
    public TextArea textAreaChat;
    @FXML
    public TextArea textAreaMessage;
    @FXML
    public Button sendButton;

    private ClientConnection connection;

    @FXML
    private void initialize() {
        sendButton.setOnAction(event -> {
            try {
                connection.sendMessage(nameEdit.getText(), textAreaMessage.getText());
                textAreaMessage.setText("");
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        });
        buttonJoin.setOnAction(event -> {
            int port = Integer.parseInt(portEdit.getText());
            String ip = editIp.getText();
            try {
                connection = new ClientConnection(ip, port, textAreaChat);
                new Thread(connection).start();
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        });
    }
}
