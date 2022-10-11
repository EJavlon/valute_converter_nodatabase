package com.company.utils;

import com.company.enums.Language;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class KeyboardButtonUtil {

    private static KeyboardRow getKeyboardRow(KeyboardButton... buttons) {
        return new KeyboardRow(new LinkedList<>(Arrays.asList(buttons)));
    }

    private static List<KeyboardRow> getKeyboardRowList(KeyboardRow... rows) {
        return new LinkedList<>(Arrays.asList(rows));
    }

    private static ReplyKeyboardMarkup getMarkup(List<KeyboardRow> rowList) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        markup.setSelective(true);
        markup.setKeyboard(rowList);
        return markup;
    }

    public static ReplyKeyboardMarkup getAdmintMenuMarkup(Language language) {
        KeyboardButton userListButton = new KeyboardButton();
        KeyboardButton courseCBUButton = new KeyboardButton();
        KeyboardButton sendNewsButton = new KeyboardButton();
        if (language.equals(Language.UZ)) {
            userListButton.setText(DemoUtil.USER_LIST_UZ);
            courseCBUButton.setText(DemoUtil.COURSE_CBU_UZ);
            sendNewsButton.setText(DemoUtil.SEND_NEWS_UZ);
        } else if (language.equals(Language.RU)) {
            userListButton.setText(DemoUtil.USER_LIST_RU);
            courseCBUButton.setText(DemoUtil.COURSE_CBU_RU);
            sendNewsButton.setText(DemoUtil.SEND_NEWS_RU);
        } else {
            userListButton.setText(DemoUtil.USER_LIST_ENG);
            courseCBUButton.setText(DemoUtil.COURSE_CBU_ENG);
            sendNewsButton.setText(DemoUtil.SEND_NEWS_ENG);
        }
        KeyboardRow row1 = getKeyboardRow(userListButton);
        KeyboardRow row3 = getKeyboardRow(courseCBUButton);
        KeyboardRow row4 = getKeyboardRow(sendNewsButton);
        List<KeyboardRow> rowList = getKeyboardRowList(row1, row3, row4);
        return getMarkup(rowList);
    }

    public static ReplyKeyboardMarkup getUserMenuMarkup(Language userLanguage) {
        KeyboardButton courseInfo = new KeyboardButton();
        KeyboardButton valuteConvertation = new KeyboardButton();
        if (userLanguage.equals(Language.UZ)) {
            courseInfo.setText(DemoUtil.COURSE_INFO_UZ);
            valuteConvertation.setText(DemoUtil.VALUTE_CONVERTOR_UZ);
        } else if (userLanguage.equals(Language.RU)) {
            courseInfo.setText(DemoUtil.COURSE_INFO_RU);
            valuteConvertation.setText(DemoUtil.VALUTE_CONVERTOR_RU);
        } else {

            courseInfo.setText(DemoUtil.COURSE_INFO_ENG);
            valuteConvertation.setText(DemoUtil.VALUTE_CONVERTOR_ENG);
        }
        KeyboardRow row1 = getKeyboardRow(courseInfo);
        KeyboardRow row2 = getKeyboardRow(valuteConvertation);
        List<KeyboardRow> rowList = getKeyboardRowList(row1, row2);
        return getMarkup(rowList);
    }

    public static ReplyKeyboardMarkup getBackMenu(Language adminLanguage) {
        KeyboardButton back = new KeyboardButton();
        if (adminLanguage.equals(Language.UZ)) {
            back.setText(DemoUtil.BACK_UZ);
        } else if (adminLanguage.equals(Language.RU)) {
            back.setText(DemoUtil.BACK_RU);
        } else if (adminLanguage.equals(Language.ENG)) {
            back.setText(DemoUtil.BACK_ENG);
        }
        KeyboardRow row1 = getKeyboardRow(back);
        List<KeyboardRow> rowList = getKeyboardRowList(row1);
        return getMarkup(rowList);
    }
}
