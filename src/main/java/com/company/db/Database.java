package com.company.db;
import com.company.entity.ConverterBotUser;
import com.company.entity.Currency;
import com.company.service.CurrencyService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Database {

    public static List<Currency> CurrencyList = new ArrayList<>();
    public static List<ConverterBotUser> converterBotUsers = new ArrayList<>();
    public static List<String> adminList = new ArrayList<>();
    public static LocalDateTime refreshDate;
    public static LocalTime refreshTime;

    public static void load(){
        adminList.add("EJavlon");
        adminList.add("Javlon_6nf4rd5");
        adminList.add("unknown_6nf4rd5");
        adminList.add("unknown_user_6nf4rd5");
        Database.CurrencyList = CurrencyService.getCurrencyList();
        Database.refreshDate = LocalDateTime.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth(),11,0,0);
        Database.refreshTime = LocalTime.of(0,0,0);
    }
}
