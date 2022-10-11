package com.company.service;

import com.company.container.ComponentContainer;
import com.company.db.Database;
import com.company.entity.ConverterBotUser;
import com.company.enums.Language;
import com.company.utils.DemoUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class UserService {


    public static String valuteConverter(String currency1, String currency2, String data, Language userLanguage) {
        data = data.replaceAll(" ", "");
        try {
            Double.parseDouble(data);
        } catch (Exception e) {
            return userLanguage.equals(Language.UZ) ? DemoUtil.INVALID_AMOUNT_UZ
                    : userLanguage.equals(Language.RU) ? DemoUtil.INVALID_AMOUNT_RU
                    : DemoUtil.INVALID_AMOUNT_ENG;
        }
        if (Double.parseDouble(data) < 0) {
            return userLanguage.equals(Language.UZ) ? DemoUtil.INVALID_AMOUNT_UZ
                    : userLanguage.equals(Language.RU) ? DemoUtil.INVALID_AMOUNT_RU
                    : DemoUtil.INVALID_AMOUNT_ENG;
        }
        if (currency1.equals(currency2)) {
            return String.format("%s %s = %s %s", data, currency1, data, currency2);
        }

        double amount1 = 0;
        double amount2 = 0;

        switch (currency1) {
            case "USD" : amount1 = Double.parseDouble(Database.CurrencyList.get(0).getRate());break;
            case "EUR" : amount1 = Double.parseDouble(Database.CurrencyList.get(1).getRate());break;
            case "RUB" : amount1 = Double.parseDouble(Database.CurrencyList.get(2).getRate());break;
            case "UZS" : amount1 = Double.parseDouble(data); break;
        }
        switch (currency2) {
            case "USD" : amount2 = Double.parseDouble(Database.CurrencyList.get(0).getRate());break;
            case "EUR" : amount2 = Double.parseDouble(Database.CurrencyList.get(1).getRate());break;
            case "RUB" : amount2 = Double.parseDouble(Database.CurrencyList.get(2).getRate());break;
            case "UZS" : amount2 = Double.parseDouble(data);break;
        }
        if (currency1.equals("UZS")) {
            return String.format("%s %s = %.3f %s", data, currency1, amount1 / amount2, currency2);
        }
        if (currency2.equals("UZS")) {
            amount2 = 1;
        }
        return String.format("%s %s = %.3f %s", data, currency1, (Double.parseDouble(data) * amount1) / amount2, currency2);
    }

    public static void createOrLeplaceCourse() {
        File file = DemoUtil.COURSE_URL;
        try (PdfWriter pdfWriter = new PdfWriter(file)) {
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.addNewPage();
            Document document = new Document(pdfDocument);

            Paragraph paragraph = new Paragraph();
            paragraph.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            document.add(paragraph);

            Table table = new Table(new float[]{40, 40, 100, 40, 100, 100, 100});
            table.addCell("№");
            table.addCell("Ccy");
            table.addCell("Rate");
            table.addCell("Diff");
            table.addCell("Date");
            table.addCell("CcyNm_UZ");
            table.addCell("CcyNm_ENG");

            for (int i = 0; i < Database.CurrencyList.size(); i++) {
                table.addCell(String.valueOf((i + 1)));
                table.addCell(Database.CurrencyList.get(i).getCcy());
                table.addCell(Database.CurrencyList.get(i).getRate());
                table.addCell(Database.CurrencyList.get(i).getDiff());
                table.addCell(Database.CurrencyList.get(i).getDate());
                table.addCell(Database.CurrencyList.get(i).getCcyNmUZ());
                table.addCell(Database.CurrencyList.get(i).getCcyNmEN());
            }
            document.add(table);
            pdfDocument.close();
            document.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessageToUser(SendMessage sendMessage) {
        new Thread(()->{
            for (ConverterBotUser user : Database.converterBotUsers) {
                if (!user.getAdmin()){
                    sendMessage.setChatId(String.valueOf(user.getUserId()));
                    ComponentContainer.convertor.sendMessage(sendMessage);
                }
            }
        }).start();
    }

    public static void sendMessageToUser(SendPhoto sendMessage) {
       new Thread(()->{
           for (ConverterBotUser user : Database.converterBotUsers) {
               if (!user.getAdmin()){
                   sendMessage.setChatId(String.valueOf(user.getUserId()));
                   ComponentContainer.convertor.sendMessage(sendMessage);
               }
           }
       }).start();
    }

    public static void sendMessageToUser(SendVoice sendMessage) {
        new Thread(()->{
            for (ConverterBotUser user : Database.converterBotUsers) {
                if (!user.getAdmin()){
                    sendMessage.setChatId(String.valueOf(user.getUserId()));
                    ComponentContainer.convertor.sendMessage(sendMessage);
                }
            }
        }).start();
    }

    public static void sendMessageToUser(SendVideo sendMessage) {
        new Thread(()->{
            for (ConverterBotUser user : Database.converterBotUsers) {
                if (!user.getAdmin()){
                    sendMessage.setChatId(String.valueOf(user.getUserId()));
                    ComponentContainer.convertor.sendMessage(sendMessage);
                }
            }
        }).start();
    }

    public static void createOrLeplaceUserDocument() {
        File file = DemoUtil.USERS_URL;
        try (PdfWriter pdfWriter = new PdfWriter(file)) {
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.addNewPage();
            Document document = new Document(pdfDocument);

            Paragraph paragraph = new Paragraph();
            paragraph.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            document.add(paragraph);

            Table table = new Table(new float[]{40, 40, 100, 40, 100});
            table.addCell("№");
            table.addCell("First name");
            table.addCell("Last Name");
            table.addCell("Id");
            table.addCell("Username");

            for (int i = 0; i < Database.converterBotUsers.size(); i++) {
                table.addCell(String.valueOf((i + 1)));
                table.addCell(getData(Database.converterBotUsers.get(i).getFirstName()));
                table.addCell(getData(Database.converterBotUsers.get(i).getLastName()));
                table.addCell(Database.converterBotUsers.get(i).getUserId().toString());
                table.addCell(getData(Database.converterBotUsers.get(i).getUsername()));
            }
            document.add(table);
            pdfDocument.close();
            document.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getData(String value) {
        return Objects.isNull(value) ? "null" : value;
    }
}
