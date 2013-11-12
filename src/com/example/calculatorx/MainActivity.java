package com.example.calculatorx;

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

	@Override
	public void onClick(View view) {

		if (view instanceof Button) {
			String input = ((Button) view).getText().toString();
			Log.i("pressed: ", input);

			if (errorState) {
				if (input.equals("C")) {
					setBackValues(input);
				}
				Log.i("input blocked through error state", input);
			} else if (input.equals("1") || input.equals("2")
					|| input.equals("3") || input.equals("4")
					|| input.equals("5") || input.equals("6")
					|| input.equals("7") || input.equals("8")
					|| input.equals("9")|| input.equals("0")) {

				// prevent leading nulls
				if (Double.parseDouble(display.getText().toString()) == 0.0d) {
					display.setText("");
				}

				// set back display if operation was pressed
				if (operationWasPressed) {
					display.setText("");
					display.append(input);
					operationWasPressed = false;
				} else {
					display.append(input);
					Log.i("append", input);
				}

			} else if (input.equals("=")) {

				// if operation was pressed
				if (lastOperation != null) {
					secoundValue = Double.parseDouble(display.getText()
							.toString());
					Log.i("secoundValue",secoundValue.toString());
					
					calcResult();

					//catch exceptions
					if (String.valueOf(result).length() > MAX_NUMS || result.isInfinite() ||result.isNaN()) {
						display.setText(FAIL_NOTIFICATION);
						errorState=true;
					} else {
						display.setText(result.toString());
						Log.i("result", result.toString());
					}

					lastOperation = null;
				}
			} else if (input.equals("/") || input.equals("*")
					|| input.equals("+") || input.equals("-")) {
				
				firstValue = Double.parseDouble(display.getText().toString());
				
				Log.i("operation pressed", input);
				Log.i("firstValue",firstValue.toString());

				lastOperation = input;
				operationWasPressed = true;
			} else if (input.equals("C")) {
				setBackValues(input);
			}
		}
	}

	/**
	 * calculate the result Value with the last operation
	 */
	private void calcResult() {
		if (lastOperation.equals("*")) {
			result = logic.mul(firstValue, secoundValue);
			Log.i("calculate", "mult");
		} else if (lastOperation.equals("/")) {
			try{
				result = logic.div(firstValue, secoundValue);
				Log.i("calculate", "div");
			}catch(ArithmeticException e){
				result=Double.NaN;
			}
		} else if (lastOperation.equals("-")) {
			result = logic.sub(firstValue, secoundValue);
			Log.i("calculate", "sub");
		} else if (lastOperation.equals("+")) {
			result = logic.add(firstValue, secoundValue);
			Log.i("calculate", "add");
		}
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
