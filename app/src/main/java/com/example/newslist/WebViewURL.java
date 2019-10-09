package com.example.newslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewURL extends AppCompatActivity {

    WebView webView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_url);
        Intent i = getIntent();
        url = i.getExtras().getString("URL");


        webView = findViewById(R.id.newsWebView);

        webView.loadUrl(url);
    }
}
