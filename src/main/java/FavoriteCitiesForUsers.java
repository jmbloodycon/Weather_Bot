import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.*;
import java.util.HashMap;

class FavoriteCitiesForUsers {
    private HashMap<String, String[]> favoriteCitiesForUsers = new HashMap<>();
    private String[] defaultCities = new String[]{
        "Москва",
        "Санкт-Петербург",
        "Екатеринбург",
        "Новосибирск",
    };

    FavoriteCitiesForUsers(){
        try {
            FileInputStream fis = new FileInputStream("cities.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            favoriteCitiesForUsers = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    String[] getCities(String chatId){
        if (favoriteCitiesForUsers.containsKey(chatId)) {
            return favoriteCitiesForUsers.get(chatId);
        }

        return defaultCities;
    }

    void setCities(Message message) {
        String[] splitText = message.getText().split(" ");
        String[] cities = new String[4];
        for (int i = 0; i < 4; i++) {
            cities[i] = (splitText[i * 2 + 1]);
        }

        favoriteCitiesForUsers.put(message.getChatId().toString(), cities);
        try {
            FileOutputStream fos = new FileOutputStream("cities.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(favoriteCitiesForUsers);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
