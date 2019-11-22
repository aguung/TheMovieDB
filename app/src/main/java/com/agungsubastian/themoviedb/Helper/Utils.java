package com.agungsubastian.themoviedb.Helper;

import android.content.Context;
import android.widget.Toast;

import com.agungsubastian.themoviedb.R;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Utils {
    public static String API_KEY = "e299e0a5534c5f0428b51e57162e1463";
    static String BASE_URL_API = "https://api.themoviedb.org/3/discover/";
    public static String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/";
    //==============================================================================================================================
    public static void resultConnection(Throwable throwable, Context context){
        if(throwable instanceof SocketTimeoutException){
            Toast.makeText(context, R.string.time_out, Toast.LENGTH_SHORT).show();
        }else if(throwable instanceof UnknownHostException){
            Toast.makeText(context,R.string.no_connection, Toast.LENGTH_SHORT).show();
        }
    }

    //===============================================================================================================================
}
