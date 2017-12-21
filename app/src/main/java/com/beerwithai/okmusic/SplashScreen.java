package com.beerwithai.okmusic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beerwithai.okmusic.CardView.SwipeMusic;

/**
 * Created by nitinissacjoy on 21/12/17.
 */

public class SplashScreen extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.splash_screen, container, false);

        return view;
    }
}
