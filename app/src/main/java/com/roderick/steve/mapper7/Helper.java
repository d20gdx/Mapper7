package com.roderick.steve.mapper7;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SRoderick on 27/01/2017.
 */

public class Helper {

    public static Date transformDate(String aDate) {

        SimpleDateFormat formatnow = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
        Date date1 = null;
        try {
            date1 = formatnow.parse(aDate.substring(4, aDate.length() - 15));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return date1;
    }

    public static String returnDateFormatTime(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yy");
        String strDate = dateFormat.format(date);

        return strDate;

}

}
