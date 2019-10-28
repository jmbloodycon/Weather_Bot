import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        System.getProperties().put( "proxySet", "true" );
        System.getProperties().put( "socksProxyHost", "127.0.0.1" );
        System.getProperties().put( "socksProxyPort", "10150" );
        ApiContextInitializer.init();
        TelegramBotsApi botApi = new TelegramBotsApi();
        try {
            botApi.registerBot(new Bot());
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
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
                    sendMessage(message, "Я бот прогноз погоды. Просто набери название города, в котором тебя интерисует погода, и я покажу ее тебе.");
                    break;
                case "/start":
                    sendMessage(message, "Введитие название города, в котором хотите узнать погоду");
                    break;
                default:
                    if (message.getText().indexOf('/') == 0) {
                        sendMessage(message, "Неизвестная команда");
                        break;
                    }
                    try {
                        sendMessage(message, Weather.getWeather(message.getText(), model));
                    } catch (IOException e) {
                        sendMessage(message, "Город не найден!");
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
