package com.zakharenko.lab04mail.entity;

import java.util.Date;

public class MailMessage {
    private final String sender;
    private final String subject;
    private final Date date;
    private String text;
    private Object content;
    private String contentType;


    public MailMessage(String sender, String subject, Date date) {
        this.sender = sender;
        this.subject = subject;
        this.date = date;
//        this.text = text;
    }

    public MailMessage(String sender, String subject, Date date, String text, Object content, String contentType) {
        this.sender = sender;
        this.subject = subject;
        this.date = date;
        this.text = text;
        this.content = content;
        this.contentType = contentType;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public Object getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }
}
