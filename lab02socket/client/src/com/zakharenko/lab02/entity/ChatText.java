package com.zakharenko.lab02.entity;

public class ChatText {
    private final StringBuilder text;

    public ChatText() {
        this.text = new StringBuilder();
    }

    public void addMessage(String message){
        text.append(message).append("\n\r");
    }

    public String getText(){
        return text.toString();
    }



}
