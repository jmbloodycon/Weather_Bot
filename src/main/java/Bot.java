import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botApi = new TelegramBotsApi();
        try {
            botApi.registerBot(new Bot());
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Message message, String text){
        SendMessage sendMessage = new SendMessage()
                .setChatId(message.getChatId().toString())
                .setText(text);
        try {
            execute(sendMessage);
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendPicture(Message message, String photo) {
        SendPhoto sendPhoto = new SendPhoto()
                .setChatId(message.getChatId().toString())
                .setPhoto(photo);
        try {
            execute(sendPhoto);
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendMessage(message, "\uD83D\uDC7E Я бот прогноз погоды. \n" +
                            "\uD83D\uDC19 Введи название города, и я покажу ее погоду в нем.");
                    break;
                case "/start":
                    sendMessage(message, "\uD83C\uDF06 Введитие название города, в котором хотите узнать погоду");
                    break;
                default:
                    if (message.getText().indexOf('/') == 0) {
                        sendMessage(message, "\u274C Неизвестная команда");
                        break;
                    }
                    try {
                        String[] weather;
                        try {
                            weather = Weather.getWeather(message.getText(), model);

                        } catch (IOException e) {
                            String city = Translate.translate(message.getText());
                            weather = Weather.getWeather(city, model);
                        }
                        sendMessage(message, weather[0]);
                        sendPicture(message, weather[1]);
                        sendMessage(message, "\uD83D\uDC60 Советуем одеться примерно так:");
                        sendMessage(message, weather[2]);
                    } catch (IOException e) {
                        sendMessage(message, "\u274C Город не найден!");
                    }
            }
        }
    }

    public String getBotUsername() {
        return "PopeelaBot";
    }

    public String getBotToken() {
        String token = "";
        try {
            token = Files.readString(Paths.get("token.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }
}
