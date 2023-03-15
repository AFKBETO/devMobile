package fr.android.calculatrice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends CalculatriceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }


    public void myClickHandler(View view) {
        int id = view.getId();
        String button = parseButton(id);
        System.out.println("Button: " + button);
        if (_stage == Stage.OPERAND) {
            if (isDigit(button)) {
                if (_buffer.matches("^0*$")) {
                    _buffer = button;
                } else {
                    _buffer += button;
                }
            } else {
                if (!(_operator.equals("/") && _buffer.matches("^0*$"))) {
                    _value = !_operator.equals("") ? calculate(_value, _operator, Integer.parseInt(_buffer)): Integer.parseInt(_buffer);
                }
                _buffer = "";
                _operator = button;
                _stage = Stage.OPERATOR;
            }
        } else if (_stage == Stage.OPERATOR) {
            if (isDigit(button)) {
                _buffer += button;
                _stage = Stage.OPERAND;
            } else {
                _operator = button;
            }
        }
        printOperation();
        printResult();
    }

    public void previousActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void nextActivity(View view) {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
}