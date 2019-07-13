package com.example.server;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

public class ChatRoom extends Stage {
    TextField messageInput;
    private Socket socket;
    public ChatRoom(Chat chat,String senderUsername) {
        VBox vBox = new VBox(8);
        ScrollPane scrollPane = new ScrollPane();
        VBox pane = new VBox();
        Label label = new Label(chat.getUsername());
        label.setTextAlignment(TextAlignment.CENTER);
        label.setStyle("-fx-background-color: aqua");
        pane.setAlignment(Pos.CENTER);
        scrollPane.setContent(pane);
        vBox.setAlignment(Pos.TOP_CENTER);
        messageInput = new TextField();
        Button chooseFile = new Button("File");
        Button emoji = new Button("Emoji");
        HBox hBox = new HBox();
        hBox.getChildren().addAll(chooseFile,emoji);
        vBox.getChildren().addAll(label,scrollPane, messageInput,hBox);
        scrollPane.prefHeightProperty().bind(heightProperty().subtract(60));
        Scene scene = new Scene(vBox, 400,400);
        setScene(scene);
        label.setOnMouseClicked(event -> {
            if(event.getClickCount()==2) {
                Profile profile = new Profile(chat);
                profile.show();
            }
        });

        chooseFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.showOpenDialog(ChatRoom.this);
            }
        });
        startConnection(senderUsername,chat.getId());

        messageInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    String message = messageInput.getText();
                    try {
                        socket.getOutputStream().write(("text:"+message).getBytes());
                        messageInput.clear();
                        pane.getChildren().add(new ReceiverMessageTemplate(message, new Date()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startConnection(String username, int chatid) {
        socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("localhost",12345));
            System.out.println(username+","+chatid);
            socket.getOutputStream().write((username+","+chatid).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
