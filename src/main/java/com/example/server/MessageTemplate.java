package com.example.server;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageTemplate extends VBox {
    SimpleDateFormat formatter = new SimpleDateFormat("MMM dd - HH:mm");
    Label message;
    Label dateLabel;
    public MessageTemplate(String text, Date date) {
        message = new Label();
        message.setWrapText(true);
        dateLabel = new Label(formatter.format(date));
        message.setText(text);
        getChildren().addAll(message,dateLabel);
    }

}
