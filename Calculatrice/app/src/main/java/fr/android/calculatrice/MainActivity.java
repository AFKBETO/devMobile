package fr.android.calculatrice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends CalculatriceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void myClickHandler(View view) {
        System.out.println("Stage: " + _stage);
        System.out.println("Buffer: " + _buffer);
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
                printOperation();
            } else if (isOperator(button)) {
                if (_operator.equals("/") && _buffer.equals("0")) {
                    printResult("Error");
                    _operator = "";
                    _buffer = "";
                    _value = 0;
                    _stage = Stage.OPERAND;
                    printOperation();
                } else {
                    _value = !_operator.equals("") ? calculate(_value, _operator, Integer.parseInt(_buffer)): Integer.parseInt(_buffer);
                    _buffer = "";
                    _operator = button;
                    _stage = Stage.OPERATOR;
                    printOperation();
                }
            } else if (isEquals(button)) {
                if (_operator.equals("/") && _buffer.equals("0")) {
                    printResult("Error");
                    _operator = "";
                    _buffer = "";
                    _value = 0;
                } else if (_operator.equals("")) {
                    _value = Integer.parseInt(_buffer);
                    printResult("" + _value);
                } else {
                    printResult("" + calculate(_value, _operator, Integer.parseInt(_buffer)));
                }
                _stage = Stage.RESULT;
            }
        } else if (_stage == Stage.OPERATOR) {
            if (isDigit(button)) {
                _buffer += button;
                _stage = Stage.OPERAND;
                printOperation();
            } else if (isOperator(button)) {
                _operator = button;
                printOperation();
            } else if (isEquals(button)) {
                _buffer = "";
                _operator = "";
                printOperation();
                _stage = Stage.RESULT;
                printResult("" + _value);
            }

        } else if (_stage == Stage.RESULT) {
            if (isDigit(button)) {
                _buffer = button;
                _value = 0;
                _operator = "";
                _stage = Stage.OPERAND;
                printOperation();
            } else if (isOperator(button)) {
                _operator = button;
                _value = 0;
                _stage = Stage.OPERATOR;
                printOperation();
            } else if (isEquals(button)) {
                printOperation();
                printResult("" + calculate(_value, _operator, isNumber(_buffer) ? Integer.parseInt(_buffer) : 0));
            }
        }
    }

    public void previousActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void nextActivity(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    private void printResult(String resultText) {
        TextView text = (TextView) findViewById(R.id.result_text);
        text.setText(resultText);
    }
}