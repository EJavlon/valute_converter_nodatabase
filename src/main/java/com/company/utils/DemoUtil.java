package com.company.utils;

import java.io.File;

public interface DemoUtil {

    File USERS_URL = new File("src/main/resources/User_List.pdf");
    File COURSE_URL = new File("src/main/resources/Currency_course.pdf");

    String START_COMMAND = "/start";

    String BOT_LANGUAGE = "Muloqot uchun tilni tanlang \n\nВыберите язык для общения" +
            "\n\nSelect language";

    String LANGUAGE_RU = "\uD83C\uDDF7\uD83C\uDDFA Русский";
    String LANGUAGE_UZ = "\uD83C\uDDFA\uD83C\uDDFF O'zbek tili";
    String LANGUAGE_ENG = "\uD83C\uDDEC\uD83C\uDDE7 English";

    String BUTTON_UZ = "uz";
    String BUTTON_RU = "ru";
    String BUTTON_ENG = "eng";

    String INFO_UZ = " — bot sizning xizmatingizda! Siz asosiy menyudasiz!";
    String INFO_RU = " — бот к вашим услугам! Вы в главном меню!";
    String INFO_ENG = " — bot at your service! You are on the main menu!";

    String INFO_ADMIN_UZ = " — bot sizning xizmatingizda! Siz admin menyudasiz!";
    String INFO_ADMIN_RU = " — бот к вашим услугам! Вы в админ меню!";
    String INFO_ADMIN_ENG = " — bot at your service! You are on the admin menu!";

    String USER_LIST_UZ = "\uD83D\uDC64 Foydalanuvchilar ro’yxati";
    String HISTORY_CONVERTATSIYA_UZ = "⚖️Konvertatsiyalar tarixi";
    String COURSE_CBU_UZ = "\uD83D\uDCB0 Kurs cbu.uz";
    String SEND_NEWS_UZ = "\uD83C\uDD95 Yangilik yuborish";

    String USER_LIST_RU = "\uD83D\uDC64 Список пользователей";
    String HISTORY_CONVERTATSIYA_RU = "⚖️История конверсий";
    String COURSE_CBU_RU = "\uD83D\uDCB0 Курс cbu.uz";
    String SEND_NEWS_RU = "\uD83C\uDD95 Отправить новость";

    String USER_LIST_ENG = "\uD83D\uDC64 User list";
    String HISTORY_CONVERTATSIYA_ENG = "⚖️History of conversions";
    String COURSE_CBU_ENG = "\uD83D\uDCB0 Course cbu.uz";
    String SEND_NEWS_ENG = "\uD83C\uDD95 Send news";

    String COURSE_INFO_UZ = "\uD83D\uDCB0 Kurs ma'lumotlari";
    String VALUTE_CONVERTOR_UZ = "\uD83D\uDD04 Valyuta konvertasiya qilish";

    String COURSE_INFO_RU = "\uD83D\uDCB0 Информация о курсе";
    String VALUTE_CONVERTOR_RU = "\uD83D\uDD04 Валюта конвертация";

    String COURSE_INFO_ENG = "\uD83D\uDCB0 Course information";
    String VALUTE_CONVERTOR_ENG = "\uD83D\uDD04 Currency conversion";

    String SELECT_VALUTE_UZ = "Valyutani tanlang";
    String SELECT_VALUTE_RU = "Выберите валюту";
    String SELECT_VALUTE_ENG = "Select a currency";

    String ZONE_ID = "Asia/Tashkent";
    String COURSE_UZ = "Valyuta kurslari ";
    String COURSE_RU = "Валюта курсы ";
    String COURSE_ENG = "Exchange rates ";

    String BACK_BUTTON = "❰";
    String PAGE = "Page";
    String NEXT_BUTTON = "❱";

    String RATE_UZ = "\uD83D\uDCB9 Valyuta farqi: ";
    String RATE_RU = "\uD83D\uDCB9 Валютная разница: ";
    String RATE_ENG = "\uD83D\uDCB9 Currency difference: ";

    String LAST_UPDATE_UZ = "♻ Oxirgi yangilanish: ";
    String LAST_UPDATE_RU = "♻ Последнее обновление: ";
    String LAST_UPDATE_ENG = "♻ Last update: ";

    String SOURCE_UZ = "🌐️ Manba: cbu.uz";
    String SOURCE_RU = "🌐 Источник: cbu.uz";
    String SOURCE_ENG = "🌐 Source: cbu.uz";

    String BOT_USERNAME = "@valyutaConverterUzBot";

    String SHARE_BOT_UZ = "Botni ulashish";
    String SHARE_BOT_RU = "Поделиться ботом";
    String SHARE_BOT_ENG = "Share bot";

    String SHARE_LINK = "https://telegram.me/share/url?url=t.me/valyutaConverterUzBot ";

    String SHARE_TEXT_UZ = "Sinab ko'ring !";
    String SHARE_TEXT_RU = "Попробуйте !";
    String SHARE_TEXT_ENG = "Give it a try !";

    String CURRENCY1_UZ = "1️⃣ Birinchi valyutani tanlang";
    String CURRENCY1_RU = "1️⃣ Выберите первую валюту";
    String CURRENCY1_ENG = "1️⃣ Select the first currency";

    String CURRENCY2_UZ = "2️⃣ Ikkinchi valyutani tanlang";
    String CURRENCY2_RU = "2️⃣ Выберите вторую валюту";
    String CURRENCY2_ENG = "2️⃣ Select the second currency";

    String CURRENCY1_TEXT_UZ = "\uD83D\uDD39 Birinchi valyuta: ";
    String CURRENCY1_TEXT_RU = "\uD83D\uDD39 Первая валюта: ";
    String CURRENCY1_TEXT_ENG = "\uD83D\uDD39 The first currency: ";

    String CURRENCY2_TEXT_UZ = "\uD83D\uDD38 Ikkinchi valyuta: ";
    String CURRENCY2_TEXT_RU = "\uD83D\uDD38 Вторая валюта: ";
    String CURRENCY2_TEXT_ENG = "\uD83D\uDD38 The second currency: ";

    String USD = "USD";
    String EUR = "EUR";
    String RUB = "RUB";
    String UZS = "UZS";

    String CONFIRM_UZ = "✔ Tadiqlash";
    String CONFIRM_RU = "✔️Подтверждение";
    String CONFIRM_ENG = "✔️Confirm";

    String CANCEL_UZ = "❌ Bekor qilish";
    String CANCEL_RU = "❌ Отмена";
    String CANCEL_ENG = "❌ Cancel";

    String AMOUNT_UZ = "Miqdorini kiriting";
    String AMOUNT_RU = "Введите сумму";
    String AMOUNT_ENG = "Enter the amount";

    String INVALID_AMOUNT_UZ = "Miqdor mos kelmaydi";
    String INVALID_AMOUNT_RU = "Сумма не соответствует";
    String INVALID_AMOUNT_ENG = "The amount does not match";

    String MESSAGE_TYPE_UZ = "Qanday xabar yubormoqchisiz ?";
    String MESSAGE_TYPE_RU = "Какое сообщение вы хотите отправить ?";
    String MESSAGE_TYPE_ENG = "What message do you want to send ?";

    String TEXT_MESSAGE = "\uD83D\uDCDD Text Message";
    String PHOTO_MESSAGE = "\uD83D\uDCF8 Photo Message";
    String AUDIO_MESSAGE = "\uD83C\uDF99 Audio Message";
    String VIDEO_MESSAGE = "\uD83D\uDCF9 Video Message";

    String BACK_UZ = "\uD83D\uDD19 Ortga";
    String BACK_RU = "\uD83D\uDD19 Назад";
    String BACK_ENG = "\uD83D\uDD19 Back";
}
