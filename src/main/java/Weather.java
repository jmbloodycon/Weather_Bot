import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

class Weather {

    static String[] getWeather(String message, Model model) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message
                + "&units=metric&appid=6fff53a641b9b9a799cfd6b079f5cd4e");

        Scanner in = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (in.hasNext()) {
            result.append(in.nextLine());
        }

        JSONObject object = new JSONObject(result.toString());
        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
        }

        String info = "\uD83C\uDF06 Город: " + model.getName() + "\n" +
                "\u26A1 Температура: " + model.getTemp() + " C" + "\n" +
                "\uD83D\uDCA7 Влажность: " + model.getHumidity() + "%" + "\n";
        String icon = "http://openweathermap.org/img/w/" + model.getIcon() + ".png" + "\n";
        return (new String[] {info, icon});
    }
}
