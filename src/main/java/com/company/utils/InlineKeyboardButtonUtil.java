package com.company.utils;

import com.company.db.Database;
import com.company.enums.Language;
import com.company.service.CurrencyService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class InlineKeyboardButtonUtil {

    private static List<List<InlineKeyboardButton>> rowListUz;
    private static List<List<InlineKeyboardButton>> rowListRu;
    private static List<List<InlineKeyboardButton>> rowListEng;

    public static InlineKeyboardMarkup getLanguageMarkup() {
        InlineKeyboardButton uzButton = new InlineKeyboardButton(DemoUtil.LANGUAGE_UZ);
        InlineKeyboardButton ruButton = new InlineKeyboardButton(DemoUtil.LANGUAGE_RU);
        InlineKeyboardButton engButton = new InlineKeyboardButton(DemoUtil.LANGUAGE_ENG);

        uzButton.setCallbackData(DemoUtil.BUTTON_UZ);
        ruButton.setCallbackData(DemoUtil.BUTTON_RU);
        engButton.setCallbackData(DemoUtil.BUTTON_ENG);

        List<InlineKeyboardButton> row1 = new ArrayList<>(1);
        row1.add(uzButton);

        List<InlineKeyboardButton> row2 = new ArrayList<>(1);
        row2.add(ruButton);

        List<InlineKeyboardButton> row3 = new ArrayList<>(1);
        row3.add(engButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(3);
        rowList.add(row1);
        rowList.add(row2);
        rowList.add(row3);
        return new InlineKeyboardMarkup(rowList);
    }

    public static InlineKeyboardMarkup getInlineCourseButton(Language userLanguage) {

        if (Objects.isNull(rowListEng)) {
            InlineKeyboardButtonUtil.loadInlineButton();
        }

        return getNextInlineButton(0, userLanguage);
    }

    private static void loadInlineButton() {
        List<InlineKeyboardButton> row;
        InlineKeyboardButton button;
        rowListUz = new LinkedList<>();
        rowListRu = new LinkedList<>();
        rowListEng = new LinkedList<>();

        for (int i = 0; i < Database.CurrencyList.size(); i++) {
            row = new LinkedList<>();
            button = new InlineKeyboardButton(String.format("%s (%s)", Database.CurrencyList.get(i).getCcy(), Database.CurrencyList.get(i).getCcyNmUZ()));
            button.setCallbackData(String.valueOf(i));
            row.add(button);
            rowListUz.add(row);

            row = new LinkedList<>();
            button = new InlineKeyboardButton(String.format("%s (%s)", Database.CurrencyList.get(i).getCcy(), Database.CurrencyList.get(i).getCcyNmRU()));
            button.setCallbackData(String.valueOf(i));
            row.add(button);
            rowListRu.add(row);

            row = new LinkedList<>();
            button = new InlineKeyboardButton(String.format("%s (%s)", Database.CurrencyList.get(i).getCcy(), Database.CurrencyList.get(i).getCcyNmEN()));
            button.setCallbackData(String.valueOf(i));
            row.add(button);
            rowListEng.add(row);
        }
    }

    public static void reolad() {
        Database.CurrencyList.clear();
        Database.CurrencyList = CurrencyService.getCurrencyList();
        Database.refreshDate = LocalDateTime.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth(),11,0,0);
    }

    public static InlineKeyboardMarkup getNextInlineButton(int index, Language userLanguage) {
        List<List<InlineKeyboardButton>> rowList = new LinkedList<>();
        int limit;

        if (Objects.equals(index, 7)) {
            limit = index * 10 + 5;
        } else {
            limit = index * 10 + 10;
        }
        switch (userLanguage) {
            case UZ: {
                for (int i = index * 10; i < limit; i++) {
                    rowList.add(rowListUz.get(i));
                }
                break;
            }
            case RU: {
                for (int i = index * 10; i < limit; i++) {
                    rowList.add(rowListRu.get(i));
                }
                break;
            }
            case ENG: {
                for (int i = index * 10; i < limit; i++) {
                    rowList.add(rowListEng.get(i));
                }
                break;
            }
        }

        List<InlineKeyboardButton> row;
        InlineKeyboardButton button;
        row = new LinkedList<>();
        button = new InlineKeyboardButton(DemoUtil.BACK_BUTTON);
        button.setCallbackData(DemoUtil.BACK_BUTTON);
        row.add(button);
        button = new InlineKeyboardButton((index + 1) % 10 + "/8");
        button.setCallbackData(DemoUtil.PAGE);
        row.add(button);
        button = new InlineKeyboardButton(DemoUtil.NEXT_BUTTON);
        button.setCallbackData(DemoUtil.NEXT_BUTTON);
        row.add(button);
        rowList.add(row);
        return new InlineKeyboardMarkup(rowList);
    }

    public static ReplyKeyboard getSendButton(Language userLanguage) {
        InlineKeyboardButton share = new InlineKeyboardButton();
        if (userLanguage.equals(Language.UZ)) {
            share.setText(DemoUtil.SHARE_BOT_UZ);
            share.setUrl(DemoUtil.SHARE_LINK + DemoUtil.SHARE_TEXT_UZ);
        } else if (userLanguage.equals(Language.RU)) {
            share.setText(DemoUtil.SHARE_BOT_RU);
            share.setUrl(DemoUtil.SHARE_LINK + DemoUtil.SHARE_TEXT_RU);
        } else {
            share.setText(DemoUtil.SHARE_BOT_ENG);
            share.setUrl(DemoUtil.SHARE_LINK + DemoUtil.SHARE_TEXT_ENG);
        }

        List<InlineKeyboardButton> row = new LinkedList<>();
        row.add(share);
        List<List<InlineKeyboardButton>> rowList = new LinkedList<>();
        rowList.add(row);
        return new InlineKeyboardMarkup(rowList);
    }

    public static ReplyKeyboard getInlineValuteConverterButton() {
        InlineKeyboardButton usd = new InlineKeyboardButton(DemoUtil.USD);
        InlineKeyboardButton eur = new InlineKeyboardButton(DemoUtil.EUR);
        InlineKeyboardButton rub = new InlineKeyboardButton(DemoUtil.RUB);
        InlineKeyboardButton uzs = new InlineKeyboardButton(DemoUtil.UZS);

        usd.setCallbackData(DemoUtil.USD);
        eur.setCallbackData(DemoUtil.EUR);
        rub.setCallbackData(DemoUtil.RUB);
        uzs.setCallbackData(DemoUtil.UZS);

        List<InlineKeyboardButton> row1 = new LinkedList<>();
        List<InlineKeyboardButton> row2 = new LinkedList<>();
        List<InlineKeyboardButton> row3 = new LinkedList<>();
        List<InlineKeyboardButton> row4 = new LinkedList<>();

        row1.add(usd);
        row2.add(eur);
        row3.add(rub);
        row4.add(uzs);

        List<List<InlineKeyboardButton>> rowList = new LinkedList<>();
        rowList.add(row1);
        rowList.add(row2);
        rowList.add(row3);
        rowList.add(row4);
        return new InlineKeyboardMarkup(rowList);
    }

    public static InlineKeyboardMarkup getInlineConfirmAndCancelButton(Language userLanguage) {
        InlineKeyboardButton confirm = new InlineKeyboardButton();
        InlineKeyboardButton cancel = new InlineKeyboardButton();
        if (userLanguage.equals(Language.UZ)) {
            confirm.setText(DemoUtil.CONFIRM_UZ);
            confirm.setCallbackData(DemoUtil.CONFIRM_ENG);
            cancel.setText(DemoUtil.CANCEL_UZ);
            cancel.setCallbackData(DemoUtil.CANCEL_ENG);
        } else if (userLanguage.equals(Language.RU)) {
            confirm.setText(DemoUtil.CONFIRM_RU);
            confirm.setCallbackData(DemoUtil.CONFIRM_ENG);
            cancel.setText(DemoUtil.CANCEL_RU);
            cancel.setCallbackData(DemoUtil.CANCEL_ENG);
        } else {
            confirm.setText(DemoUtil.CONFIRM_ENG);
            confirm.setCallbackData(DemoUtil.CONFIRM_ENG);
            cancel.setText(DemoUtil.CANCEL_ENG);
            cancel.setCallbackData(DemoUtil.CANCEL_ENG);
        }
        List<InlineKeyboardButton> row1 = new LinkedList<>();
        row1.add(confirm);
        List<InlineKeyboardButton> row2 = new LinkedList<>();
        row2.add(cancel);
        List<List<InlineKeyboardButton>> rowList = new LinkedList<>();
        rowList.add(row1);
        rowList.add(row2);
        return new InlineKeyboardMarkup(rowList);
    }

    public static InlineKeyboardMarkup getInlineNewsButtons() {
        InlineKeyboardButton text = new InlineKeyboardButton(DemoUtil.TEXT_MESSAGE);
        InlineKeyboardButton photo = new InlineKeyboardButton(DemoUtil.PHOTO_MESSAGE);
        InlineKeyboardButton audio = new InlineKeyboardButton(DemoUtil.AUDIO_MESSAGE);
        InlineKeyboardButton video = new InlineKeyboardButton(DemoUtil.VIDEO_MESSAGE);

        text.setCallbackData(DemoUtil.TEXT_MESSAGE);
        photo.setCallbackData(DemoUtil.PHOTO_MESSAGE);
        audio.setCallbackData(DemoUtil.AUDIO_MESSAGE);
        video.setCallbackData(DemoUtil.VIDEO_MESSAGE);

        List<InlineKeyboardButton> row1 = new LinkedList<>();
        List<InlineKeyboardButton> row2 = new LinkedList<>();

        row1.add(text);
        row1.add(photo);
        row2.add(audio);
        row2.add(video);

        List<List<InlineKeyboardButton>> rowList = new LinkedList<>();
        rowList.add(row1);
        rowList.add(row2);
        return new InlineKeyboardMarkup(rowList);
    }
}
