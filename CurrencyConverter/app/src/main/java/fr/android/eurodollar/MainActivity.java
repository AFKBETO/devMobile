package fr.android.eurodollar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void myClickHandler(View view) {
        if (view.getId() == R.id.button1) {
            EditText text = (EditText) findViewById(R.id.editText1);
            RadioButton euroButton = (RadioButton) findViewById(R.id.radioButton1);
            RadioButton dollarButton = (RadioButton) findViewById(R.id.radioButton2);
            if (text.getText().length() == 0) {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_LONG).show();
                return;
            }
            float inputValue = Float.parseFloat(text.getText().toString());
            if (euroButton.isChecked()) {
                text.setText(String.valueOf(convertDollarToEuro(inputValue)));
                euroButton.setChecked(false);
                dollarButton.setChecked(true);
            } else {
                text.setText(String.valueOf(convertEuroToDollar(inputValue)));
                dollarButton.setChecked(false);
                euroButton.setChecked(true);
            }
        }
    }

    private float convertDollarToEuro(float dollar) {
        return dollar * 0.93f;
    }
    private float convertEuroToDollar(float euro) {
        return euro *1.08f;
    }
}