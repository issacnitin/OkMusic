package com.beerwithai.okmusic.CardView;

import android.graphics.Bitmap;
import android.media.MediaPlayer;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by nitinissacjoy on 21/12/17.
 */

public class Constants {
    public static boolean musicPlaying = false;
    public static MediaPlayer mp;

    public static ArrayList<String> titleTexts = new ArrayList<String>(), artistList = new ArrayList<String>();
    public static ArrayList<URL> urlTexts = new ArrayList<URL>();
    public static ArrayList<Bitmap> coverImages = new ArrayList<Bitmap>();

    public static String[] titleStringArray, artistListArray;
    public static URL[] urlStringArray;
    public static Bitmap[] coverImageArray;


    public static android.support.v4.app.Fragment mFragment = null;
}
