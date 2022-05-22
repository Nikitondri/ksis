package com.zakharenko.lab04.entity;

import java.util.Date;

public class MailMessage {
    private final String sender;
    private final String subject;
    private final Date date;
//    private final String text;

    public MailMessage(String sender, String subject, Date date) {
        this.sender = sender;
        this.subject = subject;
        this.date = date;
//        this.text = text;
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
}
