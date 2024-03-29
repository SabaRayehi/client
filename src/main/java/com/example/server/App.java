package com.example.server;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Stage {
    TextField keyword;
    ScrollPane scrollPane;
    UserBaseInfoDto userBaseInfoDto;
    User user;
    public App(UserBaseInfoDto userBaseInfoDto) {
        user = new User(userBaseInfoDto.getName(), userBaseInfoDto.getLastname(), userBaseInfoDto.getUsername(), userBaseInfoDto.getPicture());
        this.userBaseInfoDto = userBaseInfoDto;
        VBox vBox = new VBox(2);
        vBox.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(vBox, 400 ,400);
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("User");
        MenuItem settingMenuItem = new MenuItem("Setting");
        MenuItem exitMenuItem = new MenuItem("Exit");
        menu.getItems().addAll(settingMenuItem, new SeparatorMenuItem(), exitMenuItem);
        menuBar.getMenus().add(menu);

        keyword = new TextField();
        keyword.setPromptText("Search Users, enter username");
        scrollPane = new ScrollPane();
        VBox pane = new VBox(2);
        for(Chat chat: userBaseInfoDto.getChats()) {
            UserEntry entry = new UserEntry(chat);
            pane.getChildren().add(entry);

            entry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(event.getClickCount()==2) {
                        ChatRoom chatRoom = new ChatRoom(chat, user.getUsername());
                        chatRoom.show();
                    }
                }
            });
        }
        scrollPane.setContent(pane);
        vBox.getChildren().addAll(menuBar, keyword,scrollPane);
        setScene(scene);
        vBox.requestFocus();

        settingMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Setting setting = new Setting(user);
                setting.show();
            }
        });

        exitMenuItem.setOnAction(event -> App.this.close());

    }

}
