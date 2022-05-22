package com.zakharenko.lab04mail.controller;

import com.zakharenko.lab04mail.entity.MailMessage;
import com.zakharenko.lab04mail.service.MailService;
import com.zakharenko.lab04mail.service.exception.ServiceException;
import com.zakharenko.lab04mail.service.factory.ServiceFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    public TableView<MailMessage> mailTable;
    @FXML
    public TableColumn<MailMessage, String> senderColumn;
    @FXML
    public TableColumn<MailMessage, String> subjectColumn;
    @FXML
    public TableColumn<MailMessage, Date> dateColumn;

    private List<MailMessage> list = new ArrayList<>();

    @FXML
    public Button sendButton;
    @FXML
    public Button reloadButton;

    private final MailService mailService = ServiceFactory.getInstance().getMailService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        senderColumn.setCellValueFactory(new PropertyValueFactory<>("sender"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        mailTable.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() == 2){
                try {
                    showMessageWindow(mailTable.getSelectionModel().getFocusedIndex());
                } catch (IOException e) {
                    showErrorMessage("Error showing messageWindow");
                }
            }
        });
//        mailTable.setItems(list);

        reloadButton.setOnAction(actionEvent -> reloadButtonAction());
        sendButton.setOnAction(actionEvent -> sendButtonAction());
    }

    private void doubleClickedMailTableAction(){

    }

    private void reloadButtonAction(){
        try {
//            Message[] messages = mailService.loadMail();
//            for(Message message : messages){
////                mailTable.getColumns().get(0).setCellValueFactory(message.);
//                MailMessage mailMessage = new MailMessage(
//                        Arrays.toString(message.getFrom()),
//                        message.getSubject(),
//                        message.getReceivedDate(),
//                        getTextFromMessage(message)
//                );
//                list.add(mailMessage);
//            }
            list = mailService.loadMail();
            mailTable.setItems(FXCollections.observableArrayList(list));
        } catch (ServiceException e) {
            showErrorMessage("Load mail exception");
        }

    }

    private void sendButtonAction(){
        try {
            showSendMessageWindow();
        } catch (IOException e) {
            showErrorMessage("Exception sendWindow open");
        }
    }

    private void showMessageWindow(int index) throws IOException {
        FXMLLoader loader = new FXMLLoader (getClass().getResource("../view/messageWindow.fxml"));
        Scene messageScene = new Scene(loader.load(), 903, 637);
        Stage messageWindow = new Stage();
        messageWindow.setScene(messageScene);
        messageWindow.initModality(Modality.WINDOW_MODAL);

        MessageController messageController = loader.getController();
        messageController.fillFields(list.get(index));

        messageWindow.show();
    }

    private void showSendMessageWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader (getClass().getResource("../view/sendMessageWindow.fxml"));
        Scene messageScene = new Scene(loader.load(), 903, 622);
        Stage messageWindow = new Stage();
        messageWindow.setScene(messageScene);
        messageWindow.initModality(Modality.WINDOW_MODAL);

        messageWindow.show();
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
