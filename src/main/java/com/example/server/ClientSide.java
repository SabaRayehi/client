package com.example.server;

public class ClientSide {
    public static ServiceHandler serviceHandler;
    public static void register(SignUpDto signUpDto) {
        serviceHandler.register(signUpDto);
    }
    public static boolean activeUser(String username, int code) {
       return serviceHandler.activeUser(username, code);
    }

    public static UserBaseInfoDto login(String username, String password) throws UserNotFoundException {
        return serviceHandler.login(username, password);
    }
    public static void updateUser(UpdateUserDto updateUserDto) {
        serviceHandler.update(updateUserDto);
    }
}
