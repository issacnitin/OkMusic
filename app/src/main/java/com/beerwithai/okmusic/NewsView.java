package com.beerwithai.okmusic;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class NewsView extends Activity {
    public static boolean active = false;
    private WebView mWebView = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_view);

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");
        mWebView.loadUrl(url);


    }

    public void onStart() {
        super.onStart();
        active = true;
    }

    public void onStop() {
        super.onStop();
        active = false;
    }

    private class CheckForInternet extends AsyncTask<String, String, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            int iHTTPStatus = 0;
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpRequest = new HttpGet(params[0]);

                HttpResponse httpResponse = httpClient.execute(httpRequest);
                iHTTPStatus = httpResponse.getStatusLine().getStatusCode();
            } catch (Exception e) {
                return 0;
            }
            if (iHTTPStatus == 200)
                return 1;
            return 0;
        }
    }

}