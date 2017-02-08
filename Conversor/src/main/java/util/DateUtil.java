package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date fromDateString(String value, String pattern){
        SimpleDateFormat sd = new SimpleDateFormat(pattern);
        try {
            return sd.parse(value);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
