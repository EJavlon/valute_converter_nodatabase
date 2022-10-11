package com.company.controller;

import com.company.container.ComponentContainer;
import com.company.db.Database;
import com.company.entity.ConverterBotUser;
import com.company.enums.Language;
import com.company.enums.NewsType;
import com.company.enums.UserStatus;
import com.company.service.CurrencyService;
import com.company.service.UserService;
import com.company.utils.DemoUtil;
import com.company.utils.InlineKeyboardButtonUtil;
import com.company.utils.KeyboardButtonUtil;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.nio.file.Files;

public class AdminController {

    private SendMessage sendMessage;
    private DeleteMessage deleteMessage;
    private SendDocument sendDocument;
    private Language adminLanguage;
    private UserStatus adminStatus;
    private NewsType newsType;
    private ConverterBotUser converterBotUser;

    public void handleText(User user, Message message) {

        if (message.getText().equals(DemoUtil.START_COMMAND)) {
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setText(DemoUtil.BOT_LANGUAGE);
            sendMessage.setReplyMarkup(InlineKeyboardButtonUtil.getLanguageMarkup());
            ComponentContainer.convertor.sendMessage(sendMessage);
            converterBotUser = new ConverterBotUser(user.getId(), user.getFirstName(), user.getLastName(),
                    user.getUserName(), String.valueOf(adminLanguage), String.valueOf(UserStatus.LANGUAGE), true, true);

        }else if (message.getText().equals(DemoUtil.USER_LIST_UZ)
                || message.getText().equals(DemoUtil.USER_LIST_RU)
                || message.getText().equals(DemoUtil.USER_LIST_ENG)) {
            UserService.createOrLeplaceUserDocument();
            sendDocument = new SendDocument();
            sendDocument.setChatId(String.valueOf(message.getChatId()));
            sendDocument.setDocument(new InputFile(DemoUtil.USERS_URL));
            sendDocument.setCaption("USER LIST");
            ComponentContainer.convertor.sendMessage(sendDocument);
            adminStatus = UserStatus.USER_LIST;

        }  else if (message.getText().equals(DemoUtil.COURSE_CBU_UZ)
                || message.getText().equals(DemoUtil.COURSE_CBU_RU)
                || message.getText().equals(DemoUtil.COURSE_CBU_ENG)) {

            UserService.createOrLeplaceCourse();
            sendDocument = new SendDocument();
            sendDocument.setChatId(String.valueOf(message.getChatId()));
            sendDocument.setDocument(new InputFile(DemoUtil.COURSE_URL));
            sendDocument.setCaption(String.format("CURRENCY COURCE «%s»", CurrencyService.getCourseDate()));
            ComponentContainer.convertor.sendMessage(sendDocument);
            adminStatus = UserStatus.COURCE_CBU_UZ;
            //Files.delete();

        } else if (message.getText().equals(DemoUtil.SEND_NEWS_UZ)
                || message.getText().equals(DemoUtil.SEND_NEWS_RU)
                || message.getText().equals(DemoUtil.SEND_NEWS_ENG)) {

            sendMessage = new SendMessage();
            sendMessage.setText(message.getText());
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.getBackMenu(adminLanguage));
            ComponentContainer.convertor.sendMessage(sendMessage);

            sendMessage.setChatId(String.valueOf(message.getChatId()));
            if (adminLanguage.equals(Language.UZ)){
                sendMessage.setText(DemoUtil.MESSAGE_TYPE_UZ);
            } else if (adminLanguage.equals(Language.RU)) {
                sendMessage.setText(DemoUtil.MESSAGE_TYPE_RU);
            }else {
                sendMessage.setText(DemoUtil.MESSAGE_TYPE_ENG);
            }
            sendMessage.setReplyMarkup(InlineKeyboardButtonUtil.getInlineNewsButtons());
            ComponentContainer.convertor.sendMessage(sendMessage);
            adminStatus = UserStatus.SEND_NEWS;

        } else if (message.getText().equals(DemoUtil.BACK_UZ)
                || message.getText().equals(DemoUtil.BACK_RU)
                || message.getText().equals(DemoUtil.BACK_ENG)) {
            sendMessage = new SendMessage();
            sendMessage.setText(message.getText());
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.getAdmintMenuMarkup(adminLanguage));
            ComponentContainer.convertor.sendMessage(sendMessage);
            newsType = null;
        } else if (newsType.equals(NewsType.TEXT_MESSAGE)) {
            sendMessage = new SendMessage();
            sendMessage.setText(message.getText());
            UserService.sendMessageToUser(sendMessage);
            sendMessage(message);
        }
    }
    public void handlePhoto(Message message) {
        if (newsType.equals(NewsType.PHOTO_MESSAGE)){
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setPhoto(new InputFile(message.getPhoto().get(0).getFileId()));
            sendPhoto.setCaption(message.getCaption());
            UserService.sendMessageToUser(sendPhoto);
            sendMessage(message);
        }
    }
    public void handleVoice(Message message){
        if (newsType.equals(NewsType.AUDIO_MESSAGE)){
            SendVoice sendVoice = new SendVoice();
            sendVoice.setVoice(new InputFile(message.getVoice().getFileId()));
            sendVoice.setCaption(message.getCaption());
            UserService.sendMessageToUser(sendVoice);
            sendMessage(message);
        }
    }
    public void handleVideo(Message message){
        SendVideo sendVideo = new SendVideo();
        sendVideo.setVideo(new InputFile(message.getVideo().getFileId()));
        sendVideo.setCaption(message.getCaption());
        UserService.sendMessageToUser(sendVideo);
        sendMessage(message);
    }

    public void handleCallback(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        Message message = callbackQuery.getMessage();
        User admin = callbackQuery.getFrom();

        if (data.equals(DemoUtil.BUTTON_UZ)) {
            adminLanguage = Language.UZ;
            adminStatus = UserStatus.MENU;

            deleteMessage = new DeleteMessage();
            deleteMessage.setChatId(String.valueOf(message.getChatId()));
            deleteMessage.setMessageId(message.getMessageId());
            ComponentContainer.convertor.sendMessage(deleteMessage);

            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.getAdmintMenuMarkup(adminLanguage));
            sendMessage.setText(admin.getFirstName() + DemoUtil.INFO_ADMIN_UZ);
            ComponentContainer.convertor.sendMessage(sendMessage);
            adminLanguage = Language.UZ;

        } else if (data.equals(DemoUtil.BUTTON_RU)) {
            adminLanguage = Language.RU;
            adminStatus = UserStatus.MENU;
            deleteMessage = new DeleteMessage();
            deleteMessage.setChatId(String.valueOf(message.getChatId()));
            deleteMessage.setMessageId(message.getMessageId());
            ComponentContainer.convertor.sendMessage(deleteMessage);

            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.getAdmintMenuMarkup(adminLanguage));
            sendMessage.setText(admin.getFirstName() + DemoUtil.INFO_ADMIN_RU);
            ComponentContainer.convertor.sendMessage(sendMessage);
            adminLanguage = Language.RU;

        } else if (data.equals(DemoUtil.BUTTON_ENG)) {
            adminLanguage = Language.ENG;
            adminStatus = UserStatus.MENU;
            deleteMessage = new DeleteMessage();
            deleteMessage.setChatId(String.valueOf(message.getChatId()));
            deleteMessage.setMessageId(message.getMessageId());
            ComponentContainer.convertor.sendMessage(deleteMessage);

            ReplyKeyboardMarkup markup = KeyboardButtonUtil.getAdmintMenuMarkup(adminLanguage);
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(markup);
            sendMessage.setText(admin.getFirstName() + DemoUtil.INFO_ADMIN_ENG);
            ComponentContainer.convertor.sendMessage(sendMessage);

        } else if (data.equals(DemoUtil.TEXT_MESSAGE)) {
            deleteMessage(message);
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            newsType = NewsType.TEXT_MESSAGE;
            if (adminLanguage.equals(Language.UZ)){
                sendMessage.setText("Xabar matnini yuboring !");
            }else if (adminLanguage.equals(Language.RU)){
                sendMessage.setText("Отправить текст сообщения !");
            }else {
                sendMessage.setText("Send message text !");
            }
            
            ComponentContainer.convertor.sendMessage(sendMessage);
        } else if (data.equals(DemoUtil.PHOTO_MESSAGE)) {
            deleteMessage(message);
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            newsType = NewsType.PHOTO_MESSAGE;
            if (adminLanguage.equals(Language.UZ)){
                sendMessage.setText("Rasm va xabar matnini yuboring !");
            }else if (adminLanguage.equals(Language.RU)){
                sendMessage.setText("Присылайте фото и текст сообщения !");
            }else {
                sendMessage.setText("Send a picture and text of the message !");
            }
            ComponentContainer.convertor.sendMessage(sendMessage);
        } else if (data.equals(DemoUtil.AUDIO_MESSAGE)) {
            deleteMessage(message);
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            newsType = NewsType.AUDIO_MESSAGE;
            if (adminLanguage.equals(Language.UZ)){
                sendMessage.setText("Audio va xabar matnini yuboring !");
            }else if (adminLanguage.equals(Language.RU)){
                sendMessage.setText("Отправить аудио и текст сообщения !");
            }else {
                sendMessage.setText("Send audio and message text !");
            }
            ComponentContainer.convertor.sendMessage(sendMessage);
        } else if (data.equals(DemoUtil.VIDEO_MESSAGE)) {
            deleteMessage(message);
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            newsType = NewsType.VIDEO_MESSAGE;
            if (adminLanguage.equals(Language.UZ)){
                sendMessage.setText("Video va xabar matnini yuboring !");
            }else if (adminLanguage.equals(Language.RU)){
                sendMessage.setText("Отправить видео и текст сообщения !");
            }else {
                sendMessage.setText("Send video and message text !");
            }
            ComponentContainer.convertor.sendMessage(sendMessage);
        }
    }
    public void sendMessage(Message message){
        sendMessage = new SendMessage();
        if (adminLanguage.equals(Language.UZ)){
            sendMessage.setText("Xabar yuborildi !");
        } else if (adminLanguage.equals(Language.RU)) {
            sendMessage.setText("Сообщение успешно отправлено !");
        }else {
            sendMessage.setText("Message sent successfully !");
        }
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        ComponentContainer.convertor.sendMessage(sendMessage);
    }
    public void deleteMessage(Message message){
        deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(message.getMessageId());
        deleteMessage.setChatId(String.valueOf(message.getChatId()));
        ComponentContainer.convertor.sendMessage(deleteMessage);
    }
}
