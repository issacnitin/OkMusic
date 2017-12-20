package com.beerwithai.newscatcher;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beerwithai.newscatcher.CardView.SwipeMusic;

import java.net.URL;

/**
 * Created by nitinissacjoy on 21/12/17.
 */

public class SplashScreen extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view == null)
            view = inflater.inflate(R.layout.activity_favorite_news, container, false);


        return view;
    }
}
