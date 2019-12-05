import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

class Translate {
    private static final String urlStr = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + Translate.getToken();

    static String translate(String input) throws IOException {
        URL urlObj = new URL(Translate.urlStr);
        HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
        dataOutputStream.writeBytes("text=" + URLEncoder.encode(input, StandardCharsets.UTF_8) + "&lang=" + "en");

        InputStream response = connection.getInputStream();
        String json = new java.util.Scanner(response).nextLine();
        int start = json.indexOf("[");
        int end = json.indexOf("]");
        return json.substring(start + 2, end - 1);
    }

    private static String getToken() {
        //        try {
//            token = Files.readString(Paths.get("translateToken.txt"));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return System.getenv("TRANSLATE_TOKEN");
    }
}
