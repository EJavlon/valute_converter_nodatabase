package com.company.service;

import com.company.db.Database;
import com.company.entity.Currency;
import com.company.enums.Language;
import com.company.utils.DemoUtil;
import com.company.utils.InlineKeyboardButtonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CurrencyService {

    private static List<Currency> currencyList = new ArrayList<>();
    private static final String URL = "https://cbu.uz/oz/arkhiv-kursov-valyut/json/";

    static {
        getCurrencyList();
    }

    public static List<Currency> getCurrencyList() {
        try {
            URL url = new URL(URL);
            URLConnection connection = url.openConnection();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Type type = new TypeToken<List<Currency>>() {}.getType();
            currencyList = gson.fromJson(reader, type);
            return currencyList;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCourse(String data, Language language) {

        if (Duration.between(Database.refreshDate, LocalDateTime.now()).getSeconds() >= 3600) {
            InlineKeyboardButtonUtil.reolad();
            Database.refreshTime = LocalTime.now(ZoneId.of(DemoUtil.ZONE_ID));
        }

        String rate = currencyList.get(Integer.parseInt(data)).getRate();

        StringBuilder message = new StringBuilder(String.format("1 %s = %s UZS", currencyList.get(Integer.parseInt(data)).getCcy(), rate));

        if (language.equals(Language.UZ)) {
            message.append("\n\n" + DemoUtil.RATE_UZ + currencyList.get(Integer.parseInt(data)).getDiff() + "\n" +
                    DemoUtil.LAST_UPDATE_UZ + getCourseDate_() + "\n" + DemoUtil.SOURCE_UZ + "\n\n" + DemoUtil.BOT_USERNAME);
        } else if (language.equals(Language.RU)) {
            message.append("\n\n" + DemoUtil.RATE_RU + currencyList.get(Integer.parseInt(data)).getDiff() + "\n" +
                    DemoUtil.LAST_UPDATE_RU + getCourseDate_() + "\n" + DemoUtil.SOURCE_RU + "\n\n" + DemoUtil.BOT_USERNAME);
        } else {
            message.append("\n\n" + DemoUtil.RATE_ENG + currencyList.get(Integer.parseInt(data)).getDiff() + "\n" +
                    DemoUtil.LAST_UPDATE_ENG + getCourseDate_() + "\n" + DemoUtil.SOURCE_ENG + "\n\n" + DemoUtil.BOT_USERNAME);
        }
        return message.toString();
    }

    public static String getCourseDate() {
        return LocalDate.now(ZoneId.of(DemoUtil.ZONE_ID)).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String getCourseDate_() {
        return LocalDate.now(ZoneId.of(DemoUtil.ZONE_ID)).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " " + Database.refreshTime;
    }
}
