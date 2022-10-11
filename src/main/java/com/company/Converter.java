package com.company;

import com.company.controller.AdminController;
import com.company.controller.UserController;
import com.company.db.Database;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Converter extends TelegramLongPollingBot {

    private Message message;
    private User user;

    UserController userController = new UserController();
    AdminController adminController = new AdminController();

    @Override
    public String getBotUsername() {
        return " @valyutaConverterUzBot";
    }

    @Override
    public String getBotToken() {
        return "5287544016:AAHviyjFIc742CtzQhSWBi9EoSTrQZ49OxY";
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()){
            message = update.getMessage();
            user = message.getFrom();

            if (Database.adminList.contains(user.getUserName())){
                if (message.hasText()){
                    adminController.handleText(user,message);
                } else if (message.hasPhoto()) {
                    adminController.handlePhoto(message);
                }else if (message.hasVoice()){
                    adminController.handleVoice(message);

                }else if (message.hasVideo()){
                    adminController.handleVideo(message);
                }
            }else {
                userController.handleText(user,message);
            }

        }else if (update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            user = callbackQuery.getFrom();
            if (Database.adminList.contains(user.getUserName())){
                adminController.handleCallback(callbackQuery);
            }else {
                userController.handleCallback(callbackQuery);
            }
        }
    }

    public void sendMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(EditMessageText message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(DeleteMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(SendDocument sendDocument) {
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(SendVoice sendAudio) {
        try {
            execute(sendAudio);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(SendVideo sendVideo) {
        try {
            execute(sendVideo);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
