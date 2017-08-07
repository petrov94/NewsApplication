package news.services;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petar on 8/6/2017.
 */
public class TranslateApi {
    public static final String API_KEY = "trnsl.1.1.20170806T170418Z.e31ab0cdfa944831.85d2246c07063eaec30a8e5f2ad1e7a49653fbc8";

    private static String request(String URL,String parameters) throws IOException {
        URL url = new URL(URL);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.addRequestProperty("User-Agent", "Chrome/60.0.3112.90");
        if(parameters!=null){
            // Send post request
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parameters);
            wr.flush();
            wr.close();
        }
        int responseCode = connection.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        InputStream inStream = connection.getInputStream();

        String recieved = new BufferedReader(new InputStreamReader(inStream)).readLine();

        inStream.close();
        return recieved;
    }

    public static Map<String, String> getLangs() throws IOException {
        String langs = request("https://translate.yandex.net/api/v1.5/tr.json/getLangs?&ui=en&"+"key=" + API_KEY,null );
        langs = langs.substring(langs.indexOf("langs")+7);
        langs = langs.substring(0, langs.length()-1);

        String[] splitLangs = langs.split(",");

        Map<String, String> languages = new HashMap<String, String>();
        for (String s : splitLangs) {
            String[] s2 = s.split(":");

            String key = s2[0].substring(1, s2[0].length()-1);
            String value = s2[1].substring(1, s2[1].length()-1);

            languages.put(key, value);
        }
        return languages;
    }

    public static String translate(String text, String sourceLang, String targetLang) throws IOException {
        String response = request("https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + API_KEY + "&lang=" + sourceLang + "-" + targetLang,"&text=" + text);
        return response.substring(response.indexOf("text")+8, response.length()-3);
    }

    public static String detectLanguage(String text) throws IOException {
        String response = request("https://translate.yandex.net/api/v1.5/tr.json/detect?&key=" + API_KEY,"text="+text);
        return response.substring(response.indexOf("lang")+7, response.length()-2);
    }

    public static String getKey(Map<String, String> map, String value) {
        for (String key : map.keySet()) {
            if (map.get(key).equalsIgnoreCase(value)) {
                return key;
            }
        }
        return null;
    }
}
