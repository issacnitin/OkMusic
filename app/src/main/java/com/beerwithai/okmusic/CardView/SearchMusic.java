package com.beerwithai.okmusic.CardView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beerwithai.okmusic.R;

import java.net.URL;

public class SearchMusic extends android.support.v4.app.Fragment {

    private View view;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.content_search_music, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Bitmap[] bmp = SwipeMusic.coverImageArray;
        String[] titles = SwipeMusic.titleStringArray;
        String[] artists = SwipeMusic.artistListArray;
        URL[] urls = SwipeMusic.urlStringArray;

        // specify an adapter (see also next example)
        mAdapter = new SongsAdapter(bmp, titles, artists, urls);
        mRecyclerView.setAdapter(mAdapter);

        android.support.design.widget.TextInputLayout searchBar = view.findViewById(R.id.textInputLayout3);

        return view;
    }

    public static class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {
        private Bitmap[] coverImages;
        private String[] titles, artists;
        private URL[] urls;


        // Provide a suitable constructor (depends on the kind of dataset)
        public SongsAdapter(Bitmap[] coverImages, String[] titles, String[] artists, URL[] urls) {
            this.coverImages = coverImages;
            this.titles = titles;
            this.artists = artists;
            this.urls = urls;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            android.support.v7.widget.CardView view = (android.support.v7.widget.CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_song, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.title.setText(titles[position]);
            holder.artist.setText(artists[position]);
            holder.cover.setImageBitmap(coverImages[position]);

            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SwipeMusic.musicPlaying)
                        SwipeMusic.mp.stop();
                    else {
                        try {
                            String dataSource = urls[position].toString();
                            SwipeMusic.mp.setDataSource(dataSource);
                            SwipeMusic.mp.prepare();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        SwipeMusic.mp.start();
                    }

                    SwipeMusic.musicPlaying = !SwipeMusic.musicPlaying;
                }
            });

            holder.artist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SwipeMusic.musicPlaying)
                        SwipeMusic.mp.stop();
                    else {
                        try {
                            String dataSource = urls[position].toString();
                            SwipeMusic.mp.setDataSource(dataSource);
                            SwipeMusic.mp.prepare();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        SwipeMusic.mp.start();
                    }

                    SwipeMusic.musicPlaying = !SwipeMusic.musicPlaying;
                }
            });

            holder.cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SwipeMusic.musicPlaying)
                        SwipeMusic.mp.stop();
                    else {
                        try {
                            String dataSource = urls[position].toString();
                            SwipeMusic.mp.setDataSource(dataSource);
                            SwipeMusic.mp.prepare();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        SwipeMusic.mp.start();
                    }

                    SwipeMusic.musicPlaying = !SwipeMusic.musicPlaying;
                }
            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return titles.length;
        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView artist, title;
            public ImageView cover;
            public LinearLayout layout;

            public ViewHolder(View v) {
                super(v);
                artist = (TextView) v.findViewById(R.id.artists_rec);
                title = (TextView) v.findViewById(R.id.title_rec);
                cover = (ImageView) v.findViewById(R.id.coverImageSmall);
                layout = (LinearLayout) v.findViewById(R.id.chummaoruid);
            }
        }
    }


}


