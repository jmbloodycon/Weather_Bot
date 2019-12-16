import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class FolkWeather {
    static String getWeather(String input) throws IOException {
        String urlStr = "https://sinoptik.com.ru/погода-";
        Document html = Jsoup.connect(urlStr + input.toLowerCase()).get();
        Elements weather = html.select("div.weather__article_forecast-text");
        Elements folkWeather = weather.select("span");
        return "\uD83C\uDFD8Народный прогноз погоды: \n" + folkWeather.text();
    }
}
