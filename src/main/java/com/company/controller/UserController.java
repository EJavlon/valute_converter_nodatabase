package com.company.controller;

import com.company.container.ComponentContainer;
import com.company.db.Database;
import com.company.entity.ConverterBotUser;
import com.company.enums.Language;
import com.company.enums.UserStatus;
import com.company.service.CurrencyService;
import com.company.service.UserService;
import com.company.utils.DemoUtil;
import com.company.utils.InlineKeyboardButtonUtil;
import com.company.utils.KeyboardButtonUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Objects;

public class UserController {
    private Message message;
    private SendMessage sendMessage;
    private EditMessageText editMessageText;
    private DeleteMessage deleteMessage;
    private User user;
    private ConverterBotUser converterBotUser;
    private Language userLanguage;
    private UserStatus userStatus;
    private String data;
    private Integer pageIndex;
    private String currency1;
    private String currency2;
    private Boolean isAmount;

    public void handleText(User user, Message message) {

        if (message.getText().equals(DemoUtil.START_COMMAND)) {
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setText(DemoUtil.BOT_LANGUAGE);
            sendMessage.setReplyMarkup(InlineKeyboardButtonUtil.getLanguageMarkup());
            ComponentContainer.convertor.sendMessage(sendMessage);
            converterBotUser = new ConverterBotUser(user.getId(), user.getFirstName(), user.getLastName(),
                    user.getUserName(), String.valueOf(userLanguage), String.valueOf(UserStatus.LANGUAGE), false, true);
            Database.converterBotUsers.add(converterBotUser);
        } else if (message.getText().equals(DemoUtil.COURSE_INFO_UZ)
                || message.getText().equals(DemoUtil.COURSE_INFO_RU)
                || message.getText().equals(DemoUtil.COURSE_INFO_ENG)) {
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            if (userLanguage.equals(Language.UZ)) {
                sendMessage.setText(String.format("%s «%s»", DemoUtil.COURSE_UZ, CurrencyService.getCourseDate()));
            } else if (userLanguage.equals(Language.RU)) {
                sendMessage.setText(String.format("%s «%s»", DemoUtil.COURSE_RU, CurrencyService.getCourseDate()));
            } else {
                sendMessage.setText(String.format("%s «%s»", DemoUtil.COURSE_ENG, CurrencyService.getCourseDate()));
            }
            sendMessage.setReplyMarkup(KeyboardButtonUtil.getBackMenu(userLanguage));
            ComponentContainer.convertor.sendMessage(sendMessage);

            if (userLanguage.equals(Language.UZ)) {
                sendMessage.setText(DemoUtil.SELECT_VALUTE_UZ);
            } else if (userLanguage.equals(Language.RU)) {
                sendMessage.setText(DemoUtil.SELECT_VALUTE_RU);
            } else {
                sendMessage.setText(DemoUtil.SELECT_VALUTE_ENG);
            }
            sendMessage.setReplyMarkup(InlineKeyboardButtonUtil.getInlineCourseButton(userLanguage));
            ComponentContainer.convertor.sendMessage(sendMessage);
            userStatus = UserStatus.COURSE_INFO;
        } else if (message.getText().equals(DemoUtil.VALUTE_CONVERTOR_UZ)
                || message.getText().equals(DemoUtil.VALUTE_CONVERTOR_RU)
                || message.getText().equals(DemoUtil.VALUTE_CONVERTOR_ENG)) {
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setText(message.getText());
            sendMessage.setReplyMarkup(KeyboardButtonUtil.getBackMenu(userLanguage));
            ComponentContainer.convertor.sendMessage(sendMessage);

            if (userLanguage.equals(Language.UZ)) {
                sendMessage.setText(DemoUtil.CURRENCY1_UZ);
            } else if (userLanguage.equals(Language.RU)) {
                sendMessage.setText(DemoUtil.CURRENCY1_RU);
            } else {
                sendMessage.setText(DemoUtil.CURRENCY1_ENG);
            }
            sendMessage.setReplyMarkup(InlineKeyboardButtonUtil.getInlineValuteConverterButton());
            ComponentContainer.convertor.sendMessage(sendMessage);
            userStatus = UserStatus.VALUTE_CONVERTOR;
            currency1 = null;
            currency2 = null;
        } else if (message.getText().equals(DemoUtil.BACK_UZ)
                || message.getText().equals(DemoUtil.BACK_RU)
                || message.getText().equals(DemoUtil.BACK_ENG)) {
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setText(message.getText());
            sendMessage.setReplyMarkup(KeyboardButtonUtil.getUserMenuMarkup(userLanguage));
            ComponentContainer.convertor.sendMessage(sendMessage);
        } else if (Objects.nonNull(isAmount) && isAmount) {
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setText(UserService.valuteConverter(currency1, currency2, message.getText(), userLanguage));
            ComponentContainer.convertor.sendMessage(sendMessage);
        }
    }

    public void handleCallback(CallbackQuery callbackQuery) {
        data = callbackQuery.getData();
        message = callbackQuery.getMessage();
        user = callbackQuery.getFrom();

        if (data.equals(DemoUtil.BUTTON_UZ)) {
            deleteMessage(message);
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.getUserMenuMarkup(Language.UZ));
            sendMessage.setText(user.getFirstName() + DemoUtil.INFO_UZ);
            ComponentContainer.convertor.sendMessage(sendMessage);
            userStatus = UserStatus.MENU;
            userLanguage = Language.UZ;
            pageIndex = 0;

        } else if (data.equals(DemoUtil.BUTTON_RU)) {
            deleteMessage(message);
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.getUserMenuMarkup(Language.RU));
            sendMessage.setText(user.getFirstName() + DemoUtil.INFO_RU);
            ComponentContainer.convertor.sendMessage(sendMessage);
            userLanguage = Language.RU;
            userStatus = UserStatus.MENU;
            pageIndex = 0;

        } else if (data.equals(DemoUtil.BUTTON_ENG)) {
            deleteMessage(message);
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.getUserMenuMarkup(Language.ENG));
            sendMessage.setText(user.getFirstName() + DemoUtil.INFO_ENG);
            ComponentContainer.convertor.sendMessage(sendMessage);
            userLanguage = Language.ENG;
            userStatus = UserStatus.MENU;
            pageIndex = 0;
        } else if (data.equals(DemoUtil.NEXT_BUTTON)) {
            pageIndex++;
            if (pageIndex > 7) pageIndex = 0;
            editMessageText = new EditMessageText();
            editMessageText.setMessageId(message.getMessageId());
            editMessageText.setChatId(String.valueOf(message.getChatId()));

            if (userLanguage.equals(Language.UZ)) {
                editMessageText.setText(String.format("%s «%s»", DemoUtil.COURSE_UZ, CurrencyService.getCourseDate()));
            } else if (userLanguage.equals(Language.RU)) {
                editMessageText.setText(String.format("%s «%s»", DemoUtil.COURSE_RU, CurrencyService.getCourseDate()));
            } else {
                editMessageText.setText(String.format("%s «%s»", DemoUtil.COURSE_ENG, CurrencyService.getCourseDate()));
            }
            editMessageText.setReplyMarkup(InlineKeyboardButtonUtil.getNextInlineButton(pageIndex, userLanguage));
            ComponentContainer.convertor.sendMessage(editMessageText);
        } else if (data.equals(DemoUtil.BACK_BUTTON)) {
            pageIndex--;
            if (pageIndex < 0) pageIndex = 7;
            editMessageText = new EditMessageText();
            editMessageText.setMessageId(message.getMessageId());
            editMessageText.setChatId(String.valueOf(message.getChatId()));

            if (userLanguage.equals(Language.UZ)) {
                editMessageText.setText(String.format("%s «%s»", DemoUtil.COURSE_UZ, CurrencyService.getCourseDate()));
            } else if (userLanguage.equals(Language.RU)) {
                editMessageText.setText(String.format("%s «%s»", DemoUtil.COURSE_RU, CurrencyService.getCourseDate()));
            } else {
                editMessageText.setText(String.format("%s «%s»", DemoUtil.COURSE_ENG, CurrencyService.getCourseDate()));
            }
            editMessageText.setReplyMarkup(InlineKeyboardButtonUtil.getNextInlineButton(pageIndex, userLanguage));
            ComponentContainer.convertor.sendMessage(editMessageText);
        } else if (userStatus.equals(UserStatus.COURSE_INFO)) {
            deleteMessage(message);
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setText(CurrencyService.getCourse(data, userLanguage));
            sendMessage.setReplyMarkup(InlineKeyboardButtonUtil.getSendButton(userLanguage));
            ComponentContainer.convertor.sendMessage(sendMessage);
        } else if (userStatus.equals(UserStatus.VALUTE_CONVERTOR)) {
            if (Objects.isNull(currency1)) {
                currency1 = data;
                deleteMessage(message);
                sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(message.getChatId()));

                if (userLanguage.equals(Language.UZ)) {
                    sendMessage.setText(DemoUtil.CURRENCY2_UZ);
                } else if (userLanguage.equals(Language.RU)) {
                    sendMessage.setText(DemoUtil.CURRENCY2_RU);
                } else {
                    sendMessage.setText(DemoUtil.CURRENCY2_ENG);
                }
                sendMessage.setReplyMarkup(InlineKeyboardButtonUtil.getInlineValuteConverterButton());
                ComponentContainer.convertor.sendMessage(sendMessage);
            } else if (Objects.isNull(currency2)) {
                currency2 = data;
                editMessageText = new EditMessageText();
                editMessageText.setMessageId(message.getMessageId());
                editMessageText.setChatId(String.valueOf(message.getChatId()));
                if (userLanguage.equals(Language.UZ)) {
                    editMessageText.setText(String.format("%s %s\n%s %s",
                            DemoUtil.CURRENCY1_TEXT_UZ, currency1, DemoUtil.CURRENCY2_TEXT_UZ, currency2));

                } else if (userLanguage.equals(Language.RU)) {
                    editMessageText.setText(String.format("%s %s\n%s %s",
                            DemoUtil.CURRENCY1_TEXT_RU, currency1, DemoUtil.CURRENCY2_TEXT_RU, currency2));

                } else {
                    editMessageText.setText(String.format("%s %s\n%s %s",
                            DemoUtil.CURRENCY1_TEXT_ENG, currency1, DemoUtil.CURRENCY2_TEXT_ENG, currency2));

                }
                editMessageText.setReplyMarkup(InlineKeyboardButtonUtil.getInlineConfirmAndCancelButton(userLanguage));
                ComponentContainer.convertor.sendMessage(editMessageText);
            } else if (data.equals(DemoUtil.CONFIRM_ENG)) {
                deleteMessage(message);
                sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(message.getChatId()));
                if (userLanguage.equals(Language.UZ)) {
                    sendMessage.setText(DemoUtil.AMOUNT_UZ);
                } else if (userLanguage.equals(Language.RU)) {
                    sendMessage.setText(DemoUtil.AMOUNT_RU);
                } else {
                    sendMessage.setText(DemoUtil.AMOUNT_ENG);
                }
                ComponentContainer.convertor.sendMessage(sendMessage);
                isAmount = true;
            } else if (data.equals(DemoUtil.CANCEL_ENG)) {
                deleteMessage(message);
                currency1 = null;
                currency2 = null;
                sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(message.getChatId()));
                sendMessage.setReplyMarkup(KeyboardButtonUtil.getUserMenuMarkup(Language.UZ));
                if (userLanguage.equals(Language.UZ)) {
                    sendMessage.setText(DemoUtil.CANCEL_UZ);
                } else if (userLanguage.equals(Language.RU)) {
                    sendMessage.setText(DemoUtil.CANCEL_RU);
                } else {
                    sendMessage.setText(DemoUtil.CANCEL_ENG);
                }
                ComponentContainer.convertor.sendMessage(sendMessage);
            }
        }
    }

    public void deleteMessage(Message message) {
        deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(message.getMessageId());
        deleteMessage.setChatId(String.valueOf(message.getChatId()));
        ComponentContainer.convertor.sendMessage(deleteMessage);
    }
}