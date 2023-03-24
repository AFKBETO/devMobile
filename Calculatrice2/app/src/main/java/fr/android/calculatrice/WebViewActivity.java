package fr.android.calculatrice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import tools.DataStorage;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView webView = findViewById(R.id.web_view);
        String url = DataStorage.loadData(this).get("url").toString();
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "https://" + url;
        webView.loadUrl(url);
    }
}