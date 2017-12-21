package com.beerwithai.okmusic.CardView;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beerwithai.okmusic.R;

import java.net.URL;
import java.util.regex.Pattern;

public class SearchMusic extends android.support.v4.app.Fragment {

    private View view;

    private RecyclerView mRecyclerView;
    private SongsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button searchButton;

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
        final String[] titles = SwipeMusic.titleStringArray;
        final String[] artists = SwipeMusic.artistListArray;
        URL[] urls = SwipeMusic.urlStringArray;

        // specify an adapter (see also next example)
        mAdapter = new SongsAdapter(bmp, titles, artists, urls);
        mRecyclerView.setAdapter(mAdapter);

        final android.support.design.widget.TextInputLayout searchBar = view.findViewById(R.id.textInputLayout3);

        searchButton = (Button) view.findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchBar.getEditText().getText().toString();
                mAdapter.reset();
                for(int i = 0; i < titles.length; i++) {
                    if(!(Pattern.compile(Pattern.quote(titles[i]), Pattern.CASE_INSENSITIVE).matcher(text).find() || Pattern.compile(Pattern.quote(artists[i]), Pattern.CASE_INSENSITIVE).matcher(text).find()))
                        mAdapter.hide(i);
                }
                mAdapter.notifyDataSetChanged();
            }
        });


        return view;
    }

    public static class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {
        private Bitmap[] coverImages;
        private String[] titles, artists;
        private URL[] urls;
        private boolean[] hidden;

        // Provide a suitable constructor (depends on the kind of dataset)
        public SongsAdapter(Bitmap[] coverImages, String[] titles, String[] artists, URL[] urls) {
            this.coverImages = coverImages;
            this.titles = titles;
            this.artists = artists;
            this.urls = urls;
            this.hidden = new boolean[this.urls.length];
        }

        public void hide(int position) {
            this.hidden[position] = true;
        }

        public void reset() {
            this.hidden = new boolean[urls.length];
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            android.support.v7.widget.CardView view = (android.support.v7.widget.CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_song, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            if(hidden[position]) {
                holder.playButton.setVisibility(View.INVISIBLE);
                holder.title.setVisibility(View.INVISIBLE);
                holder.artist.setVisibility(View.INVISIBLE);
                holder.cover.setVisibility(View.INVISIBLE);
            } else {
                holder.playButton.setVisibility(View.VISIBLE);
                holder.title.setVisibility(View.VISIBLE);
                holder.artist.setVisibility(View.VISIBLE);
                holder.cover.setVisibility(View.VISIBLE);
            }

            holder.title.setText(titles[position]);
            holder.artist.setText(artists[position]);
            holder.cover.setImageBitmap(coverImages[position]);

            holder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SwipeMusic.musicPlaying) {
                        SwipeMusic.mp.stop();
                        SwipeMusic.mp = null;
                    } else {
                        try {
                            SwipeMusic.mp = new MediaPlayer();
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
            public ImageButton playButton;

            public ViewHolder(View v) {
                super(v);
                artist = (TextView) v.findViewById(R.id.artists_rec);
                title = (TextView) v.findViewById(R.id.title_rec);
                cover = (ImageView) v.findViewById(R.id.coverImageSmall);
                layout = (LinearLayout) v.findViewById(R.id.chummaoruid);
                playButton = (ImageButton) v.findViewById(R.id.playButton);
            }
        }
    }


}


