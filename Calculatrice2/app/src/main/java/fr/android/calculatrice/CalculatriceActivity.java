package fr.android.calculatrice;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public abstract class CalculatriceActivity extends AppCompatActivity {

    private TextView _resultTextView;
    private TextView _operationTextView;
    protected static final String _OPERATORS = "+-*/";
    protected String _buffer = "";
    protected int _value = 0;
    protected String _operator = "";
    protected Stage _stage = Stage.OPERAND;
    protected void setTextViews(int resultTextId, int operationTextId) {
        _resultTextView = findViewById(resultTextId);
        _operationTextView = findViewById(operationTextId);
    }
    protected boolean isDigit(String string) {
        return string.matches("^\\d$");
    }

    protected boolean isOperator(String string) {
        return _OPERATORS.contains(string) && string.length() == 1;
    }

    protected void printOperation(){
        String operandText = _value == 0 && !isOperator(_operator) ? "" : ("" + _value);
        String operationText = operandText + _operator + _buffer;
        _operationTextView.setText(operationText);
    }

    protected void printResult(){
        String resultText;
        if (_stage == Stage.OPERAND) {
            if (_operator.equals("")) {
                resultText = _buffer;
            } else if (_operator.equals("/") && _buffer.matches("^0*$")) {
                resultText = "Error";
            } else {
                resultText = "" + calculate(_value, _operator, Integer.parseInt(_buffer));
            }
        } else {
            resultText = "" + _value;
        }
        _resultTextView.setText(resultText);
    }

    protected int calculate(int value, String operator, int buffer) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Calculate: " + value + " " + operator + " " + buffer);
        switch (operator) {
            case "+":
                return value + buffer;
            case "-":
                return value - buffer;
            case "*":
                return value * buffer;
            case "/":
                return buffer != 0 ? value / buffer : 0;
            default:
                return value;
        }
    }

    protected String parseButton(int buttonId) {
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
            default:
                throw new IllegalArgumentException("Invalid button id");
        }
    }
}
