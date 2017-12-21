package com.beerwithai.okmusic;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beerwithai.okmusic.CardView.SearchMusic;
import com.beerwithai.okmusic.CardView.SwipeMusic;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class FavoriteNews extends android.support.v4.app.Fragment {

    SharedPreferences settings;
    View view;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.content_favorite_news, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_fav);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<Bitmap> covers = new ArrayList<Bitmap>();
        ArrayList<URL> urls = new ArrayList<URL>();
        ArrayList<String> artists = new ArrayList<String>();

        settings = getActivity().getSharedPreferences("favorite_music", MODE_PRIVATE);

        // Writing data to SharedPreferences
        SharedPreferences.Editor editor = settings.edit();

        // Reading from SharedPreferences
        Map<String, ?> keys = settings.getAll();

        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            titles.add(SwipeMusic.titleStringArray[Integer.valueOf(entry.getKey())]);
            covers.add(SwipeMusic.coverImageArray[Integer.valueOf(entry.getKey())]);
            urls.add(SwipeMusic.urlStringArray[Integer.valueOf(entry.getKey())]);
            artists.add(SwipeMusic.artistListArray[Integer.valueOf(entry.getKey())]);
        }

        String[] titlesArr = titles.toArray(new String[titles.size()]);
        URL[] urlArr = urls.toArray(new URL[urls.size()]);
        String[] artistsArr = artists.toArray(new String[artists.size()]);
        Bitmap[] bmpArr = covers.toArray(new Bitmap[covers.size()]);

        final SearchMusic.SongsAdapter arrayAdapter = new SearchMusic.SongsAdapter(bmpArr, titlesArr, artistsArr, urlArr);
        mRecyclerView.setAdapter(arrayAdapter);

        return view;
    }

}
