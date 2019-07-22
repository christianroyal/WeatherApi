package com.example.weatherapi.common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static final String APP_ID="9732ccce9f6658f4d8087467579981fe";
    public static Location current_location=null;

    public static String convertUnixtoDate(long dt){
        Date date =new Date(dt*1000L);
        SimpleDateFormat sdf= new SimpleDateFormat("HH:mm EEE  MM yyyy");
        String formatted= sdf.format(date);

        return formatted;

    }
}
