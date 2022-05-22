package com.zakharenko.lab04mail.service.impl;

import com.zakharenko.lab04mail.entity.MailMessage;
import com.zakharenko.lab04mail.service.MailService;
import com.zakharenko.lab04mail.service.exception.ServiceException;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailServiceImpl implements MailService {

    @Override
    public List<MailMessage> loadMail() throws ServiceException {
        try {
            Session session = Session.getDefaultInstance(new Properties( ));
            Store store = session.getStore("imaps");
//            store.connect("imap.mail.ru", 993, "nikitondri@mail.ru", "RMLAHnHwpGHsqwUTXmQE");
            store.connect("imap.gmail.com", 993, "nikitazacharenko25071995@gmail.com", "lqlyyefaipbyjkya");
            Folder inbox = store.getFolder( "INBOX" );
            inbox.open( Folder.READ_ONLY );
            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            List<MailMessage> result = new ArrayList<>();
            for(Message message : messages){
                result.add(new MailMessage(
                        findSender(Arrays.toString(message.getFrom())),
                        message.getSubject(),
                        message.getReceivedDate(),
                        getTextFromMessage(message),
                        message.getContent(),
                        message.getContentType()
                ));
            }
            return result;
        } catch (MessagingException | IOException e) {
            throw new ServiceException(e);
        }
    }

    private String findSender(String fullSender){
        Pattern pattern = Pattern.compile("(?<=[<])[^>]+");
        Matcher matcher = pattern.matcher(fullSender);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "";
        }
    }

    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }
}
