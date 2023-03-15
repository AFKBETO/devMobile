package fr.android.calculatrice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity3 extends CalculatriceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTextViews(R.id.result_text, R.id.operation_text);
        setContentView(R.layout.activity_main3);
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
}