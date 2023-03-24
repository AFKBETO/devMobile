package fr.android.calculatrice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

import tools.DataStorage;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        TextView historyTextView = findViewById(R.id.history_text);
        StringBuilder stringBuilder = new StringBuilder();

        Map<String, ?> data = DataStorage.loadData(this);
        for (String key: data.keySet()) {
            if (key.startsWith("calc") && data.get(key).getClass().equals(String.class)) {
                stringBuilder.append(data.get(key) + "\n");
            }
        }
        if (stringBuilder.length() > 0) {
            historyTextView.setText(stringBuilder.toString());
        } else {
            historyTextView.setText("No history");
        }
    }

    public void accessToWebView(View view) {
        TextView urlTextView = findViewById(R.id.url);
        String url = urlTextView.getText().toString();
        if (url.length() > 0) {
            DataStorage.saveData(this, "url", url);
            Intent intent = new Intent(this, WebViewActivity.class);
            startActivity(intent);
        } else {
            urlTextView.setError("Please enter an URL");
        }
    }
}