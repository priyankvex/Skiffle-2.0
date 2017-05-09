package com.priyankvex.skiffle.ui.showtrackdetails;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.priyankvex.skiffle.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by priyankvex on 9/5/17.
 */

public class WebViewActivity extends AppCompatActivity{

    @BindView(R.id.web_view)
    WebView webView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        Log.d("owlcity", url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
