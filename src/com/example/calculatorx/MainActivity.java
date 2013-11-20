package com.example.calculatorx;

import java.math.BigDecimal;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple Android calculator with chaining.
 * 
 * @author Andy Klay <klay@fh-brandenburg.de>
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

	private ArrayList<Button> buttons = new ArrayList<Button>();

	private Rechner logic;
	private TextView display;

	private Double result;
	public Double secoundValue;
	public Double firstValue;

	private String lastOperation = null;
	private boolean operationWasPressed = false;
	private boolean errorState = false;

	private final String FAIL_NOTIFICATION = "error";
	private final int MAX_NUMS = 9;

	private boolean anfang=true;

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

	public void onClickNumber(View view) {
		if (!errorState) {
			addingNewNumberToDisplay(((Button) view).getText().toString());
		}
	}

	public void onClickCancel(View view) {
		display.setText("0");
		operationWasPressed = false;
		lastOperation = null;
		firstValue = 0.0d;
		secoundValue = 0.0d;
		errorState = false;
		anfang=true;
		
		for( Button actualButton : buttons ){
			actualButton.setEnabled(true);
		}	
		
		Log.i("setback pressed", "...");
	}

	public void onClickEquals(View view) {
		if (!errorState && lastOperation != null) {

			readInSecoundValueFromDisplay();
			calcResult();
			displayResult();

			this.setLastOperation(null);
			anfang=true;
		}
	}

	public void setLastOperation(String lastOperation) {
		this.lastOperation = lastOperation;
		if(lastOperation==null){
			Log.i("lastOperation", "null");
		}else{
			Log.i("lastOperation", lastOperation);	
		}
	}

	public void setOperationWasPressed(boolean operationWasPressed) {
		this.operationWasPressed = operationWasPressed;
		Log.i("operationWasPressed", Boolean.toString(operationWasPressed));
	}


	public void onClickOperator(View view) {
		String input = ((Button) view).getText().toString();

		if (!errorState) {
			Log.i("operation pressed", input);

			if (!anfang&&!operationWasPressed) {
				Log.i("chaining", "...");

				readInSecoundValueFromDisplay();
				calcResult();
				displayResult();
				firstValue=result;
			} else {
				readInFirstValueFromDisplay();
				anfang=false;
			}
			
			setOperationWasPressed(true);
			lastOperation = input;
		}
	}

	private void addingNewNumberToDisplay(String input) {
		preventTypedinLeadingNulls();
		
		if (operationWasPressed) {
			display.setText("");
		}
		
		if(this.getDoubleLength(display.getText().toString())<MAX_NUMS){
			display.append(input);
			Log.i("append", input);
		}
		this.setOperationWasPressed(false);
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
		Log.i("readInfirstValueFromDisplay", firstValue.toString());
	}

	private void displayResult() {
		String formatedResult=FAIL_NOTIFICATION;
		
		// cut long digits after decimal point etc
		if (!result.isInfinite() && !result.isNaN()) {
			formatedResult=restrictResultToNineSigns();
		}

		int resultLength = getDoubleLength(formatedResult);
		
		// catch exceptions
		if (resultLength > MAX_NUMS || result.isInfinite()
				|| result.isNaN()) {
			setErrorStateOn();
		} else {
			display.setText(formatedResult);
			Log.i("display result", formatedResult);
		}
	}

	private void setErrorStateOn() {
		for( Button actualButton : buttons ){
			actualButton.setEnabled(false);
		}
		display.setText(FAIL_NOTIFICATION);
		errorState = true;
	}

	private int getDoubleLength(String formatedResult) {
		int resultLength=formatedResult.length();
		
		if(formatedResult.contains("-")) resultLength--;
		if(formatedResult.contains(".")) resultLength--;
		return resultLength;
	}

	/**
	 * rounding and format result before it is displaying
	 * @return
	 */
	private String restrictResultToNineSigns() {		
		int beforeCommaLength= Integer.toString(result.intValue()).length();
		int afterCommaLength= 9-beforeCommaLength;
		int roundValue=1;
		
		for(int i=afterCommaLength;i>0;i--){
			roundValue=roundValue*10;
		}
		
		Log.i("afterCommaLength", Integer.toString(afterCommaLength));
		Log.i("roundValue", Integer.toString(roundValue));
		Double roundedResult = Math.round(result * roundValue) / (double)roundValue;
		
		Log.i("rounded result", roundedResult.toString());
		
		String endResult =BigDecimal.valueOf(roundedResult).toPlainString();
		Log.i("toPlainString result", roundedResult.toString());
		
		return endResult;
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
	}

	private void initViews() {

		buttons.add(button0 = (Button) findViewById(R.id.button0));
		buttons.add(button1 = (Button) findViewById(R.id.button1));
		buttons.add(button2 = (Button) findViewById(R.id.button2));
		buttons.add(button3 = (Button) findViewById(R.id.button3));
		buttons.add(button4 = (Button) findViewById(R.id.button4));
		buttons.add(button5 = (Button) findViewById(R.id.button5));
		buttons.add(button6 = (Button) findViewById(R.id.button6));
		buttons.add(button7 = (Button) findViewById(R.id.button7));
		buttons.add(button8 = (Button) findViewById(R.id.button8));
		buttons.add(button9 = (Button) findViewById(R.id.button9));
		buttons.add(buttonPlus = (Button) findViewById(R.id.buttonPlus));
		buttons.add(buttonMinus = (Button) findViewById(R.id.buttonMinus));
		buttons.add(buttonDiv = (Button) findViewById(R.id.buttonDiv));
		buttons.add(buttonMult = (Button) findViewById(R.id.buttonMult));
		buttons.add(buttonEqual = (Button) findViewById(R.id.buttonEqual));

		buttonC = (Button) findViewById(R.id.buttonC);
		display = (TextView) findViewById(R.id.output);
	}

}
