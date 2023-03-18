package fr.android.calculatrice;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivityAsyncTask extends AppCompatActivity {
    private TextView _resultTextView;
    private TextView _operationTextView;

    private static final String _OPERATORS = "+-*/";
    private String _buffer = "";
    private int _value = 0;
    private String _operator = "";

    private int _result = 0;
    private Stage _stage = Stage.OPERAND;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        _resultTextView = findViewById(R.id.result_text);
        _operationTextView = findViewById(R.id.operation_text);
    }

    public void myClickHandler(View view) {
        int id = view.getId();
        String button = parseButton(id);
        System.out.println("Button: " + button);
        if (isClear(button)) {
            _buffer = "";
            _value = 0;
            _operator = "";
            _result = 0;
            _stage = Stage.OPERAND;
        } else if (_stage == Stage.OPERAND) {
            if (isDigit(button)) {
                if (_buffer.matches("^0*$")) {
                    _buffer = button;
                } else {
                    _buffer += button;
                }
            } else {
                if (!divideByZero()) {
                    _value = !_operator.equals("") ? _result: Integer.parseInt(_buffer);
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
        String operationText = getOperationText();
        _operationTextView.setText(operationText);
        String resultText = getResultText();
        if (resultText.equals("calculate")) {
            CalculateTask calculateTask = new CalculateTask();
            calculateTask.execute(_value, Integer.parseInt(_buffer));
        } else {
            _resultTextView.setText(resultText);
        }

    }

    private class CalculateTask extends AsyncTask<Integer, String, Integer> {
        @Override
        protected Integer doInBackground(Integer... integers) {
            Integer value = integers[0];
            Integer buffer = integers[1];

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("Calculate: " + value + " " + _operator + " " + buffer);
            switch (_operator) {
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

        @Override
        protected void onPostExecute(Integer result) {
            _resultTextView.setText("" + result);
        }
    }

    private boolean isDigit(String string) {
        return string.matches("^\\d$");
    }

    private boolean isClear(String string) {
        return string.equals("C");
    }

    private boolean isOperator(String string) {
        return _OPERATORS.contains(string) && string.length() == 1;
    }

    private String getOperationText() {
        if (_stage == Stage.OPERAND && _buffer.length() == 0) {
            return "Operations";
        }
        String operandText = _value == 0 && !isOperator(_operator) ? "" : ("" + _value);
        return operandText + _operator + _buffer;
    }

    private String getResultText(){
        if (_operator.equals("") && _buffer.length() == 0) {
            return "Result";
        }
        if (_stage == Stage.OPERAND) {
            if (_operator.equals("")) {
                _result = Integer.parseInt(_buffer);
            } else if (!divideByZero()) {
                return "calculate";
            }
        } else {
            _result = _value;
        }
        if (_stage == Stage.OPERAND && divideByZero()) {
            return "Error";
        }
        return "" + _result;
    }
    private boolean divideByZero() {
        return _operator.equals("/") && _buffer.matches("^0*$");
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
            case R.id.button_clr:
                return "C";
            default:
                throw new IllegalArgumentException("Invalid button id");
        }
    }

    public void previousActivity(View view) {
        Intent intent = new Intent(this, MainActivityHandler.class);
        startActivity(intent);
    }
}