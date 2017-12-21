package com.beerwithai.okmusic.CardView;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.beerwithai.okmusic.R;
import com.daprlabs.aaron.swipedeck.SwipeDeck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SwipeMusic extends android.support.v4.app.Fragment {

    SwipeDeckAdapter swAdapter;
    FloatingActionButton btn, btn2;
    JSONArray jsonArray;
    SwipeDeck cardStack;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    private View view;
    private static int cardPos = 0, oldCardPos = -1;

    public SwipeMusic() {

        Constants.titleTexts = new ArrayList<String>();
        Constants.artistList = new ArrayList<String>();
        Constants.urlTexts = new ArrayList<URL>();
        Constants.coverImages = new ArrayList<Bitmap>();

        Constants.titleStringArray = null;
        Constants.artistListArray = null;

        Constants.urlStringArray = null;
        Constants.coverImageArray = null;

    }

    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }

    }

    public static JSONArray getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        String jsonString = "";
        if (isInternetAvailable()) {
            URL url = new URL(urlString);
            URLConnection urlConn = url.openConnection();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            InputStream in;
            if (responseCode == 200) {
                // response code is OK
                in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                in.close();
                jsonString = sb.toString();
            } else {
                // response code is not OK
            }
            connection.disconnect();
        }
        JSONArray jsonArray = new JSONArray(jsonString);

        return (jsonArray);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();

        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public void loadMusic() {
        String url = "http://starlord.hackerearth.com/studio";
        try {
            //if(titleStringArray == null || titleStringArray.length == 0)
            new AsyncTaskRunner().execute(url).get();
        } catch (Exception e) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadMusic();
        settings = getActivity().getSharedPreferences("favorite_music", MODE_PRIVATE);
        editor = settings.edit();
        if (view == null) {
            view = inflater.inflate(R.layout.content_swipe, container, false);
        }
        cardStack = (SwipeDeck) view.findViewById(R.id.swipe_deck);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.musicPlaying) {
                    Constants.mp.stop();
                    Constants.mp = null;
                    Constants.musicPlaying = false;
                }

                if(cardPos != oldCardPos){
                    Constants.mp = new MediaPlayer();
                    try {
                        String dataSource = Constants.urlStringArray[cardPos].toString();
                        Constants.mp.setDataSource(dataSource);
                        Constants.mp.prepare();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    Constants.mp.start();
                    Constants.musicPlaying = true;
                }

                oldCardPos = cardPos;
            }
        });
        // Inflate the layout for this fragment


        Constants.titleStringArray = Constants.titleTexts.toArray(new String[Constants.titleTexts.size()]);
        Constants.urlStringArray = Constants.urlTexts.toArray(new URL[Constants.urlTexts.size()]);
        Constants.coverImageArray = Constants.coverImages.toArray(new Bitmap[Constants.coverImages.size()]);
        Constants.artistListArray = Constants.artistList.toArray(new String[Constants.artistList.size()]);

        swAdapter = new SwipeDeckAdapter(Constants.titleStringArray, Constants.artistListArray, Constants.coverImageArray, Constants.urlStringArray, getContext());
        cardStack.setAdapter(swAdapter);
        swAdapter.notifyDataSetChanged();
        cardStack.setCallback(new SwipeDeck.SwipeDeckCallback() {
            @Override
            public void cardSwipedLeft(long positionInAdapter) {
                Log.i("NavigationActivity", "card was swiped left, position in adapter: " + positionInAdapter);
                SwipeMusic.cardPos = (int) positionInAdapter;
            }

            @Override
            public void cardSwipedRight(long positionInAdapter) {
                Log.i("NavigationActivity", "card was swiped right, position in adapter: " + positionInAdapter);
                SwipeMusic.cardPos = (int) positionInAdapter;
                editor.putLong(String.valueOf(positionInAdapter), positionInAdapter);
                editor.commit();
            }


        });

        btn = (FloatingActionButton) view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardLeft(180);
            }
        });

        btn2 = (FloatingActionButton) view.findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardRight(180);
            }
        });

        return view;
    }

    class AsyncTaskRunner extends AsyncTask<String, String, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            try {
                jsonArray = getJSONObjectFromURL(params[0]);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonobject = jsonArray.getJSONObject(i);
                    String title = jsonobject.getString("song");
                    String artist = jsonobject.getString("artists");
                    final URL musicUrl = new URL(jsonobject.getString("url"));
                    final URL coverURL = new URL(jsonobject.getString("cover_image"));
                    final HttpURLConnection ucon = (HttpURLConnection) coverURL.openConnection();
                    Constants.titleTexts.add(title);
                    Constants.artistList.add(artist);

                    ucon.setInstanceFollowRedirects(false);
                    URL secondURL = new URL(ucon.getHeaderField("Location"));
                    Bitmap bmp = BitmapFactory.decodeStream(secondURL.openConnection().getInputStream());

                    Constants.coverImages.add(getResizedBitmap(bmp, 400, 300));
                    ucon.disconnect();


                    final HttpURLConnection ucon2 = (HttpURLConnection) musicUrl.openConnection();

                    ucon2.setInstanceFollowRedirects(false);
                    URL musicActualURL = new URL(ucon2.getHeaderField("Location"));
                    Constants.urlTexts.add(musicActualURL);
                    ucon2.disconnect();


                }
            } catch (Exception e) {

            }
            return jsonArray;
        }


        @Override
        protected void onPreExecute() {

        }


        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
        }

        @Override
        protected void onProgressUpdate(String... text) {
            //finalResult.setText(text[0]);

        }
    }

}

