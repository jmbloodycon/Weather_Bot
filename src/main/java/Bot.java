import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Bot extends TelegramLongPollingBot {
    private HashMap<Long, String> lastMessage = new HashMap<>();
    private FavoriteCitiesForUsers favoriteCities = new FavoriteCitiesForUsers();

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
        createKeyboard(sendMessage);
        try {
            execute(sendMessage);
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendPicture(Message message, String text, String photo) {
        SendPhoto sendPhoto = new SendPhoto()
                .setChatId(message.getChatId().toString())
                .setPhoto(photo)
                .setCaption(text);
        try {
            execute(sendPhoto);
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void createKeyboard(SendMessage sendMessage) {
        ReplyKeyboardMarkup citiesKeyboard = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(citiesKeyboard);

        citiesKeyboard.setSelective(true);
        citiesKeyboard.setResizeKeyboard(true);
        citiesKeyboard.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        citiesKeyboard.setSelective(true);
        citiesKeyboard.setResizeKeyboard(true);
        citiesKeyboard.setOneTimeKeyboard(false);

        String[] cities = favoriteCities.getCities(sendMessage.getChatId());
        row1.add(cities[0]);
        row1.add(cities[1]);
        row2.add(cities[2]);
        row2.add(cities[3]);

        keyboard.add(row1);
        keyboard.add(row2);

        citiesKeyboard.setKeyboard(keyboard);
    }

    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendMessage(message, "\uD83D\uDC7E Я бот прогноз погоды. \n" +
                            "\uD83D\uDC19 Введи название города, и я покажу ее погоду в нем. \n" +
                            "\u2B55Так же вы можете указать 4 избранных города, получить погоду в которых " +
                            "можно просто нажав кнопку в меню");
                    break;

                case "/start":
                    sendMessage(message, "\uD83C\uDF06 Введитие название города, в котором хотите узнать погоду");
                    break;

                case "/set_favorite_city":
                    sendMessage(message, "\uD83C\uDF06 Введите 4 названия избранных городов в формате \n" +
                            "\u26A0\"1. Город1 2. Город2 3. Город3 4. Город 4\"");
                    break;

                default:
                    if (message.getText().indexOf('/') == 0) {
                        sendMessage(message, "\u274C Неизвестная команда");
                        break;
                    }

                    if (Character.isDigit(message.getText().charAt(0))
                            && lastMessage.get(message.getChatId()).equals("/set_favorite_city")) {
                        favoriteCities.setCities(message);
                        sendMessage(message, "\u2705 Список изменен");
                        break;
                    }
                    try {
                        String[] weather;
                        String folkWeather = "";
                        try {
                            weather = Weather.getWeather(message.getText(), model);
                            folkWeather = FolkWeather.getWeather(message.getText());

                        } catch (IOException e) {
                            String city = Translate.translate(message.getText());
                            weather = Weather.getWeather(city, model);
                            folkWeather = FolkWeather.getWeather(message.getText());
                        }

                        sendPicture(message, weather[0], weather[1]);
                        sendPicture(message, "\uD83D\uDC60 Советуем одеться примерно так", weather[2]);
                        String op = getOppositeCity(weather[3]);
                        String mes = "\uD83E\uDD76 Здесь слишком холодно. Как насчет перебраться сюда?\n" + op;
                        if (!op.equals("")) {
                            sendMessage(message, mes);
                        }
                        if (!folkWeather.equals("")) {
                            sendMessage(message, folkWeather);
                        }
                    } catch (IOException e) {
                        sendMessage(message, "\u274C Город не найден!");
                    }
            }
            lastMessage.put(message.getChatId(), message.getText());
        }
    }

    public String getOppositeCity(String sTemp) throws IOException {
        HashMap<Integer,String> cities = new HashMap<Integer, String>();
        cities.put(0, "Phuket");
        cities.put(1, "Sochi");
        cities.put(2, "Sydney");
        cities.put(3, "Malaga");
        cities.put(4, "Yalta");
        double temp = Double.parseDouble(sTemp);
        String[] oppositeCity;
        String infoOpCity = "";
        if (temp < -30){
            Random rd = new Random();
            int num = rd.nextInt(4);
            String city = cities.get(num);
            var mod = new Model();
            oppositeCity = Weather.getWeather(city, mod);
            infoOpCity = oppositeCity[0];
        }
        return infoOpCity;
    }

    public String getBotUsername() {
        return "PopeelaBot";
    }

    public String getBotToken() {
        /*String token = "";
        try {
            token =  Files.readString(Paths.get("token.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;*/
        return System.getenv("TOKEN");
    }
}
