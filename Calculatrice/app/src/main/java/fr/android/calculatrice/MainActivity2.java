package fr.android.calculatrice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    private static final String _NUMBERS = "0123456789";
    private static final String _OPERATORS = "+-*/";

    private String _buffer = "";
    private int _value = 0;
    private String _operator = "";
    private Stage _stage = Stage.OPERAND;

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
            if (isNumber(button)) {
                _buffer += button;
                printOperation();
            } else if (isOperator(button)) {
                _value = _operator != "" ? calculate(_value, _operator, Integer.parseInt(_buffer)): Integer.parseInt(_buffer);
                _buffer = "";
                _operator = button;
                _stage = Stage.OPERATOR;
                printOperation();
            } else if (isEquals(button)) {
                printResult("" + calculate(_value, _operator, Integer.parseInt(_buffer)));
                _stage = Stage.RESULT;
            }
        } else if (_stage == Stage.OPERATOR) {
            if (isNumber(button)) {
                _buffer += button;
                _stage = Stage.OPERAND;
                printOperation();
            } else if (isOperator(button)) {
                _operator = button;
                printOperation();
            } else if (isEquals(button)) {
                _buffer = "" + _value;
                _operator = "";
                printOperation();
                _stage = Stage.RESULT;
                printResult("" + _value);
            }

        } else if (_stage == Stage.RESULT) {
            if (isNumber(button)) {
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
                _stage = Stage.RESULT;
                printResult("" + calculate(_value, _operator, Integer.parseInt(_buffer)));
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
    private boolean isOperator(String button) {
        return _OPERATORS.contains(button);
    }

    private void printOperation(){
        String operandText = _value == 0 ? "" : ("" + _value);
        String operationText = operandText + _operator + _buffer;
        TextView text = (TextView) findViewById(R.id.operation_text);
        text.setText(operationText);
    }

    private void printResult(String resultText){
        TextView text = (TextView) findViewById(R.id.result_text);
        text.setText(resultText);
    }

    private boolean isNumber(String button) {
        return _NUMBERS.contains(button);
    }

    private boolean isEquals(String button) {
        return button.equals("=");
    }

    private int calculate(int value, String operator, int buffer) {
        System.out.println("Calculate: " + value + " " + operator + " " + buffer);
        switch (operator) {
            case "+":
                return value + buffer;
            case "-":
                return value - buffer;
            case "*":
                return value * buffer;
            case "/":
                return value / buffer;
            default:
                return value;
        }
    }


    private String parseButton(int buttonId) {
        switch (buttonId) {
            case R.id.button1:
                return "1";
            case R.id.button2:
                return "2";
            case R.id.button3:
                return "3";
            case R.id.button4:
                return "4";
            case R.id.button5:
                return "5";
            case R.id.button6:
                return "6";
            case R.id.button7:
                return "7";
            case R.id.button8:
                return "8";
            case R.id.button9:
                return "9";
            case R.id.button0:
                return "0";
            case R.id.button_plus:
                return "+";
            case R.id.button_minus:
                return "-";
            case R.id.button_multiply:
                return "*";
            case R.id.button_divide:
                return "/";
            case R.id.button_equal:
                return "=";
            default:
                throw new IllegalArgumentException("Invalid button id");
        }
    }
}