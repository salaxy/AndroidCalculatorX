package com.example.calculatorx;

import java.text.DecimalFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author Andy Klay <klay@fh-brandenburg.de>
 * 
 */
public class MainActivity extends Activity implements OnClickListener {

	private Rechner logic;	
	private TextView display;

	private Double result;
	public Double secoundValue;
	public Double firstValue;
	
	private String lastOperation = null;
	private boolean operationWasPressed = false;
	private boolean errorState=false;
	
	private boolean firstValueTypedIn = true;

	private final String FAIL_NOTIFICATION = "error";
	private final int MAX_NUMS=9;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		initViews();
		logic = new Rechner();
		firstValue = 0.0d;
		secoundValue = 0.0d;
		result = 0.0d;

		return true;
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View view) {

		if (view instanceof Button) {
			String input = ((Button) view).getText().toString();
			Log.i("pressed: ", input);
			
			analyzeInput(input);
		}
	}

	/**
	 * Analyzing button input
	 * @param input - String name of clicked Button
	 */
	private void analyzeInput(String input) {
		
		if (errorState) {
			allowPressingSetBackButton(input);
		} else if (isNumber(input)) {
			addingNewNumberToDisplay(input);
		} else if (isEquals(input)) {
			if (lastOperation != null) {
				readInSecoundValueFromDisplay();
				calcResult();
				displayResult();
				lastOperation = null;
				operationWasPressed=false;
				firstValueTypedIn=true;
			}
		} else if (isOperator(input)) {
			lastOperation = input;
			
			if(firstValueTypedIn){
				Log.i("chaining","operator pressed");
				
				readInSecoundValueFromDisplay();
				calcResult();
				displayResult();
				firstValueTypedIn=true; 
			}else{
				readInFirstValueFromDisplay();
			}
			
			Log.i("operation pressed", input);
			Log.i("firstValue",firstValue.toString());

			operationWasPressed = true;
		} else if (input.equals("C")) {
			setBackValues(input);
		}
	}

	private boolean isEquals(String input) {
		return input.equals("=");
	}

	private boolean isOperator(String input) {
		return input.equals("/") || input.equals("*")
				|| input.equals("+") || input.equals("-");
	}

	private boolean isNumber(String input) {
		return input.equals("1") || input.equals("2")
				|| input.equals("3") || input.equals("4")
				|| input.equals("5") || input.equals("6")
				|| input.equals("7") || input.equals("8")
				|| input.equals("9")|| input.equals("0");
	}

	private void allowPressingSetBackButton(String input) {
		if (input.equals("C")) {
			setBackValues(input);
		}else{
			Log.i("input blocked through error state", input);			
		}
	}

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
		Log.i("readInSecoundValueFromDisplay",secoundValue.toString());
	}

	private void readInFirstValueFromDisplay() {
		firstValue = Double.parseDouble(display.getText().toString());
		Log.i("readInSecoundValueFromDisplay", firstValue.toString());
	}

	private void displayResult() {
		//cut long digits after decimal point etc
		if(!result.isInfinite() &&!result.isNaN()){
			restrictResultToSevenSigns();		
		}

		//catch exceptions
		if (String.valueOf(result).length() > MAX_NUMS || result.isInfinite() ||result.isNaN()) {
			display.setText(FAIL_NOTIFICATION);
			errorState=true;
		} else {
			display.setText(result.toString());
			Log.i("display result", result.toString());
		}
	}

	private void restrictResultToSevenSigns() {
		DecimalFormat formatter = new DecimalFormat("#.##");
		result=Double.parseDouble(formatter.format(result));
		Double myDoubleString = Math.round(result*10000000) / 10000000.0;
		Log.i("formated result",myDoubleString.toString());
		result=myDoubleString;
	}

	/**
	 * calculate the result Value with the last operation
	 */
	private void calcResult() {
		if (lastOperation.equals("*")) {
			result = logic.mul(firstValue, secoundValue);
			Log.i("calculate", firstValue + " mult " + secoundValue);
		} else if (lastOperation.equals("/")) {
			try{
				result = logic.div(firstValue, secoundValue);
				Log.i("calculate",firstValue + " div " + secoundValue);
			}catch(ArithmeticException e){
				result=Double.NaN;
			}
		} else if (lastOperation.equals("-")) {
			result = logic.sub(firstValue, secoundValue);
			Log.i("calculate", firstValue + " sub " + secoundValue);
		} else if (lastOperation.equals("+")) {
			result = logic.add(firstValue, secoundValue);
			Log.i("calculate", firstValue + " add " + secoundValue);
		}
		Log.i("result", result.toString());
		
		//setze result als  firstValue um gleich weiter rechnen zu können
		//TODO kritische Stelle, intern wird mit dem result als fisrtValue weitergerechnet
		firstValue=result;
	}

	/**
	 * set Back all Values
	 * @param input - last input
	 */
	private void setBackValues(String input) {
		display.setText("0");
		operationWasPressed = false;
		lastOperation=null;
		firstValue=0.0d;
		secoundValue=0.0d;
		errorState=false;
		Log.i("setback pressed", input);
	}

	private void initViews() {
		display = (TextView) findViewById(R.id.output);
	}

}
