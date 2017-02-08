package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class IOUtil {
    public static String readTextFileFormURL(String url) throws MalformedURLException, IOException{
            StringBuilder stringBuilder = new StringBuilder();
            URL content = new URL(url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(content.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                stringBuilder.append(inputLine).append("\n");
            in.close();
            return stringBuilder.toString();
    }
}
