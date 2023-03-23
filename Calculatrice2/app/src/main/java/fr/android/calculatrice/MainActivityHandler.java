package fr.android.calculatrice;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivityHandler extends AppCompatActivity {
    private Handler _handler;
    private TextView _resultTextView;
    private TextView _operationTextView;

    private ServerSocket _serverSocket;

    private static final String _OPERATORS = "+-*/";
    private String _buffer = "";
    private double _value = 0;
    private char _operator = ' ';

    private double _result = 0;
    private Stage _stage = Stage.RESULT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _resultTextView = findViewById(R.id.result_text);
        _operationTextView = findViewById(R.id.operation_text);
        _handler = new Handler();
    }

    public void myClickHandler(View view) {
        int id = view.getId();
        String button = parseButton(id);

        System.out.println("Button: " + button);
        if (isEqual(button)) {
            _stage = Stage.RESULT;
            new Thread(new CalculatorRunnable()).start();
        } else if (_stage.equals(Stage.RESULT)) {
            if (isDigit(button)) {
                _value = 0;
                _stage = Stage.OPERAND;
                _buffer = button;
                _operator = ' ';
            } else {
                _value = _result;
                _buffer = "";
                _stage = Stage.OPERATOR;
                _operator = button.charAt(0);
            }
        } else if (_stage.equals(Stage.OPERAND)) {
            if (isDigit(button)) {
                if (_buffer.matches("^0+$")) {
                    _buffer = button;
                } else {
                    _buffer += button;
                }
            } else if (isOperator(button)) {
                _stage = Stage.OPERATOR;
                _operator = button.charAt(0);
                if (_value == 0) {
                    _value = Double.parseDouble(_buffer);
                    _buffer = "";
                }
            }
        } else if (_stage.equals(Stage.OPERATOR)) {
            if (isDigit(button)) {
                _stage = Stage.OPERAND;
                if (_buffer.matches("^0+$")) {
                    _buffer = button;
                } else {
                    _buffer += button;
                }
            } else if (isOperator(button)) {
                _operator = button.charAt(0);
            }
        }
        String operationText = getOperationText();
        _operationTextView.setText(operationText);
    }

    private String getOperationText() {
        if (_value == 0) {
            if (_operator == ' ') {
                if (_buffer.isEmpty()){
                    return "Operation";
                }
                return _buffer;
            } else {
                return "0" + " " + _operator + " " + _buffer;
            }
        } else {
            return String.format("%.0f", _value) + " " + _operator + " " + _buffer;
        }
    }

    private boolean isDigit(String string) {
        return string.matches("^\\d$");
    }

    private boolean isOperator(String string) {
        return _OPERATORS.contains(string) && string.length() == 1;
    }

    private boolean isEqual(String string) {
        return string.equals("=");
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
            case R.id.button_equal:
                return "=";
            default:
                throw new IllegalArgumentException("Invalid button id");
        }
    }

    private class CalculatorRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Socket sock = new Socket("10.0.2.2", 9876);

                DataInputStream dis = new DataInputStream(sock.getInputStream());
                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());

                dos.writeDouble(_value);
                dos.writeChar(_operator);
                dos.writeDouble(Double.parseDouble(_buffer));

                _result = dis.readDouble();

                _handler.post(() -> _resultTextView.setText(String.format("%.2f", _result)));

                dis.close();
                dos.close();
                sock.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

