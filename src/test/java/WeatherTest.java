import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class WeatherTest {
    @Test
    public void getWeatherRu() throws IOException {
        Model model = new Model();
        Weather.getWeather("Екатеринбург", model);
        String city = "Yekaterinburg";
        Assert.assertEquals(city, model.getName());
    }

    @Test
    public void getWeatherEn() throws IOException {
        Model model = new Model();
        Weather.getWeather("Yekaterinburg", model);
        String city = "Yekaterinburg";
        Assert.assertEquals(city, model.getName());
    }

    @Test
    public void getBotName() throws IOException {
        Bot bot = new Bot();
        Assert.assertEquals("PopeelaBot", bot.getBotUsername());
    }
}
