import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class WeatherTest {
    @Test
    public void getWeathe() throws IOException {
        Model model = new Model();
        Weather weather = new Weather();
        String result = Weather.getWeather("Екатеринбург", model);
        String city = "Yekaterinburg";
        //запускаем тест, в случае если список expected и actual не будут равны
        //тест будет провален, о результатах теста читаем в консоли
        Assert.assertEquals(city, model.getName());
    }
}
