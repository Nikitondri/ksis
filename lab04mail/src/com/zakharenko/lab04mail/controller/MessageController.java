package com.zakharenko.lab04mail.controller;

import com.zakharenko.lab04mail.entity.MailMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class MessageController implements Initializable {

    @FXML
    public Label subjectTitle;
    @FXML
    public Label senderTitle;
    @FXML
    public TextArea messageTextArea;
    @FXML
    public Button answerButton;
    @FXML
    public Label dateTitle;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        answerButton.setOnAction(actionEvent -> {
            try {
                answerButtonAction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void fillFields(MailMessage message) {
        subjectTitle.setText(message.getSubject());
        senderTitle.setText(message.getSender());
        dateTitle.setText(message.getDate().toString());
        messageTextArea.setText(message.getText());
        if(message.getContentType().contains("multipart")){
            try {
                loadFile(message.getContent());
            } catch (MessagingException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadFile(Object content) throws MessagingException, IOException {
            Multipart multiPart = (Multipart) content;

            for (int i = 0; i < multiPart.getCount(); i++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    String destFilePath = "D:/university/4_semestr/ksis/lab04mail/" + part.getFileName();

                    FileOutputStream output = new FileOutputStream(destFilePath);

                    InputStream input = part.getInputStream();

                    byte[] buffer = new byte[4096];

                    int byteRead;

                    while ((byteRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, byteRead);
                    }
                    output.close();
                }
            }
    }

    private void answerButtonAction() throws IOException {
        FXMLLoader loader = new FXMLLoader (getClass().getResource("../view/sendMessageWindow.fxml"));
        Scene messageScene = new Scene(loader.load(), 903, 622);
        Stage messageWindow = new Stage();
        messageWindow.setScene(messageScene);
        messageWindow.initModality(Modality.WINDOW_MODAL);

        SendMessageController sendMessageController = loader.getController();
        sendMessageController.answerInit(senderTitle.getText());

        messageWindow.show();
    }



}
