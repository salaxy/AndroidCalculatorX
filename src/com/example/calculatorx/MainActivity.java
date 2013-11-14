package com.example.calculatorx;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author Andy Klay <klay@fh-brandenburg.de>
 * 
 */
public class MainActivity extends Activity {
	
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button buttonPlus;
    private Button buttonMinus;
    private Button buttonDiv;
    private Button buttonMult;
    private Button buttonC;
    private Button buttonEqual;
	
    
	private Rechner logic;
	private TextView display;

	private Double result;
	public Double secoundValue;
	public Double firstValue;

	private String lastOperation = null;
	private boolean operationWasPressed = false;
	private boolean errorState = false;
	private boolean firstValueTypedIn = false;

	private final String FAIL_NOTIFICATION = "error";
	private final int MAX_NUMS = 9;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
		logic = new Rechner();
		firstValue = 0.0d;
		secoundValue = 0.0d;
		result = 0.0d;
	}

//	@Override
//	public void onClick(View view) {
//
//		if (view instanceof Button) {
//			String input = ((Button) view).getText().toString();
//			Log.i("pressed: ", input);
//
//			analyzeInput(input);
//		}
//	}

	public void onClickNumber(View view) {

		String input = ((Button) view).getText().toString();
		if (!errorState) {
			addingNewNumberToDisplay(((Button) view).getText().toString());
		}
	}

//	/**
//	 * Analyzing button input
//	 * 
//	 * @param input
//	 *            - String name of clicked Button
//	 */
//	private void analyzeInput(String input) {
//
//		if (errorState) {
//			allowPressingSetBackButton(input);
//		} else if (isNumber(input)) {
//			addingNewNumberToDisplay(input);
//		} else if (isEquals(input)) {
//			onClickEquals();
//		} else if (isOperator(input)) {
//			onClickOperator(input);
//		} else if (input.equals("C")) {
//			onClickCancel(input);
//		}
//	}

	
	public void onClickCancel(View view) {
		String input = ((Button) view).getText().toString();
		setBackValues(input);
	}

	public void onClickEquals(View view) {
		String input = ((Button) view).getText().toString();
		if (!errorState) {
			if (lastOperation != null) {
				readInSecoundValueFromDisplay();
				calcResult();
				displayResult();
				lastOperation = null;
				operationWasPressed = false;
				firstValueTypedIn = true;
			}
		}
	}

	public void onClickOperator(View view) {
String input = ((Button) view).getText().toString();

		if (!errorState) {
			Log.i("operation pressed", input);
			lastOperation = input;
			operationWasPressed = true;

			if (firstValueTypedIn) {
				Log.i("chaining", "operator pressed");

				readInSecoundValueFromDisplay();
				calcResult();
				displayResult();
				firstValueTypedIn = true;
			} else {
				readInFirstValueFromDisplay();
			}
		}
	}

//	private boolean isEquals(String input) {
//		return input.equals("=");
//	}
//
//	private boolean isOperator(String input) {
//		return input.equals("/") || input.equals("*") || input.equals("+")
//				|| input.equals("-");
//	}

//	private void allowPressingSetBackButton(String input) {
//		if (input.equals("C")) {
//			onClickCancel(input);
//		} else {
//			Log.i("input blocked through error state", input);
//		}
//	}

	private void addingNewNumberToDisplay(String input) {
		preventTypedinLeadingNulls();

		if (operationWasPressed) {
			display.setText("");
			display.append(input);
			operationWasPressed = false;
		} else {
			display.append(input);
			Log.i("append", input);
		}
	}

	private void preventTypedinLeadingNulls() {
		// prevent leading nulls
		if (Double.parseDouble(display.getText().toString()) == 0.0d) {
			display.setText("");
		}
	}

	private void readInSecoundValueFromDisplay() {
		secoundValue = Double.parseDouble(display.getText().toString());
		Log.i("readInSecoundValueFromDisplay", secoundValue.toString());
	}

	private void readInFirstValueFromDisplay() {
		firstValue = Double.parseDouble(display.getText().toString());
		Log.i("readInSecoundValueFromDisplay", firstValue.toString());
	}

	private void displayResult() {
		// cut long digits after decimal point etc
		if (!result.isInfinite() && !result.isNaN()) {
			restrictResultToNineSigns();
		}

		// catch exceptions
		if (String.valueOf(result).length() > MAX_NUMS || result.isInfinite()
				|| result.isNaN()) {
			display.setText(FAIL_NOTIFICATION);
			errorState = true;
		} else {
			display.setText(result.toString());
			Log.i("display result", result.toString());
		}
	}

	private void restrictResultToNineSigns() {
		// TODO Problem mit der E-Schreibweise treten immer wieder auf und
		// führen zu Error-Meldungen
		DecimalFormat formatter = new DecimalFormat("#.##");
		// Kurz vor der ausagbe nochmal formatter benutzen
		result = Double.parseDouble(formatter.format(result));
		Double roundedResult = Math.round(result * 10000000) / 10000000.0;
		Log.i("formated result", roundedResult.toString());
		result = roundedResult;
	}

	/**
	 * calculate the result Value with the last operation
	 */
	private void calcResult() {
		if (lastOperation.equals("*")) {
			result = logic.mul(firstValue, secoundValue);
			Log.i("calculate", firstValue + " mult " + secoundValue);
		} else if (lastOperation.equals("/")) {
			try {
				result = logic.div(firstValue, secoundValue);
				Log.i("calculate", firstValue + " div " + secoundValue);
			} catch (ArithmeticException e) {
				result = Double.NaN;
			}
		} else if (lastOperation.equals("-")) {
			result = logic.sub(firstValue, secoundValue);
			Log.i("calculate", firstValue + " sub " + secoundValue);
		} else if (lastOperation.equals("+")) {
			result = logic.add(firstValue, secoundValue);
			Log.i("calculate", firstValue + " add " + secoundValue);
		}
		Log.i("result", result.toString());

		// setze result als firstValue um gleich weiter rechnen zu können
		// TODO kritische Stelle, intern wird mit dem result als firstValue
		// weitergerechnet
		firstValue = result;
	}

	/**
	 * set Back all Values
	 * 
	 * @param input
	 *            - last input
	 */
	private void setBackValues(String input) {
		display.setText("0");
		operationWasPressed = false;
		lastOperation = null;
		firstValue = 0.0d;
		secoundValue = 0.0d;
		errorState = false;
		Log.i("setback pressed", input);
	}

	private void initViews() {
		
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        buttonPlus = (Button) findViewById(R.id.buttonPlus);
        buttonMinus = (Button) findViewById(R.id.buttonMinus);
        buttonDiv = (Button) findViewById(R.id.buttonDiv);
        buttonMult = (Button) findViewById(R.id.buttonMult);
        buttonEqual = (Button) findViewById(R.id.buttonEqual);       
        
        buttonC = (Button) findViewById(R.id.buttonC);
		
		display = (TextView) findViewById(R.id.output);
	}

}
