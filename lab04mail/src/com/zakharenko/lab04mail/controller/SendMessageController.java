package com.zakharenko.lab04mail.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class SendMessageController implements Initializable {
    @FXML
    public TextField titleField;
    @FXML
    public TextArea messageTextArea;
    @FXML
    public Button sendButton;
    @FXML
    public TextField whereField;
    @FXML
    public Button sendAttach;
    @FXML
    public Label filenameTitle;

    private final FileChooser fileChooser = new FileChooser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendButton.setOnAction(actionEvent -> sendMessage());
        sendAttach.setOnAction(actionEvent -> sendAttachAction());
    }

    public void answerInit(String to){
        whereField.setText(to);
    }

    private void sendMessage(){
        String[] addresses = new String[1];
        addresses[0] = whereField.getText();
        sendFromGMail(
                "nikitazacharenko25071995",
                "lqlyyefaipbyjkya",
                addresses,
                titleField.getText(),
                messageTextArea.getText()
        );
        Stage stage = (Stage) sendButton.getScene().getWindow();
        stage.close();
    }


    private void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            if(!filenameTitle.getText().isEmpty()) {
//                // creates message part
//                MimeBodyPart messageBodyPart = new MimeBodyPart();
//                messageBodyPart.setContent(message, "text/html");

                // creates multi-part
                Multipart multipart = new MimeMultipart();
//                multipart.addBodyPart(messageBodyPart);

                // adds attachments
//                if (attachFiles != null && attachFiles.length > 0) {
//                    for (String filePath : attachFiles) {
                        MimeBodyPart attachPart = new MimeBodyPart();

                        try {
                            attachPart.attachFile(filenameTitle.getText());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        multipart.addBodyPart(attachPart);
//                    }
//                }
                message.setContent(multipart);
            }

            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ae) {
            ae.printStackTrace();
            showErrorMessage(ae.getMessage());
        }
    }

    private void sendAttachAction(){
        filenameTitle.setText(fileChooser.showOpenDialog(new Stage()).getPath());
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
