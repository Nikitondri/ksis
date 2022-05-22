package com.zakharenko.lab04.controller;

import com.zakharenko.lab04.entity.MailMessage;
import com.zakharenko.lab04.service.MailService;
import com.zakharenko.lab04.service.exception.ServiceException;
import com.zakharenko.lab04.service.factory.ServiceFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.Date;

public class Controller {
    @FXML
    public TableView<MailMessage> mailTable;
    @FXML
    private TableColumn<MailMessage, String> sender = new TableColumn<>();
    @FXML
    private TableColumn<MailMessage, String> subject = new TableColumn<>();
    @FXML
    private TableColumn<MailMessage, Date> date = new TableColumn<>();

    private ObservableList<MailMessage> list = FXCollections.observableArrayList();

    @FXML
    public Button sendButton;
    @FXML
    public Button reloadButton;

    private MailService mailService = ServiceFactory.getInstance().getMailService();

    public void initialize(){

        sender.setCellValueFactory(new PropertyValueFactory<MailMessage, String>("sender"));
        subject.setCellValueFactory(new PropertyValueFactory<MailMessage, String>("subject"));
        date.setCellValueFactory(new PropertyValueFactory<MailMessage, Date>("date"));


        reloadButton.setOnAction(actionEvent -> reloadButtonAction());
        sendButton.setOnAction(actionEvent -> sendButtonAction());
    }

    private void reloadButtonAction(){
        try {
            Message[] messages = mailService.loadMail();
            for(Message message : messages){
//                mailTable.getColumns().get(0).setCellValueFactory(message.);
                list.add(new MailMessage(Arrays.toString(message.getFrom()), message.getSubject(), message.getReceivedDate()));
            }
            mailTable.setItems(list);
        } catch (ServiceException | MessagingException e) {
            showErrorMessage("Load mail exception");
        }

    }

    private void sendButtonAction(){

    }

    private void showErrorMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }
}
