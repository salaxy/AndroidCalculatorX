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
	private TextView display;
	private View rootView;

	private Double result;
	public Double secoundValue;
	public Double firstValue;
	private String lastOperation = null;
	private boolean inputOperation = false;

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
			Log.e("pressed: ", input);

			if (display.getText().equals(FAIL_NOTIFICATION)) {

				if (input.equals("C")) {
					display.setText("0");
					inputOperation = false;
				}

			} else if (input.equals("0")) {
				display.append(input);

				// prevent leading nulls
				if (Double.parseDouble(display.getText().toString()) == 0.0d) {
					display.setText("0");
				}

			} else if (input.equals("1") || input.equals("2")
					|| input.equals("3") || input.equals("4")
					|| input.equals("5") || input.equals("6")
					|| input.equals("7") || input.equals("8")
					|| input.equals("9")) {

				// prevent leading nulls
				if (Double.parseDouble(display.getText().toString()) == 0.0d) {
					display.setText("");
				}

				// set back display if operation was pressed
				if (inputOperation) {
					display.setText("");
					display.append(input);
					inputOperation = false;
				} else {
					display.append(input);
				}

			} else if (input.equals("=")) {

				// if operation was pressed
				if (lastOperation != null) {
					secoundValue = Double.parseDouble(display.getText()
							.toString());

					if (lastOperation.equals("*")) {
						result = logic.mul(firstValue, secoundValue);
						Log.e("blubb", "mult");
					} else if (lastOperation.equals("/")) {
						result = logic.div(firstValue, secoundValue);
						Log.e("blubb", "div");
					} else if (lastOperation.equals("-")) {
						result = logic.sub(firstValue, secoundValue);
					} else if (lastOperation.equals("+")) {
						result = logic.add(firstValue, secoundValue);
					}

					if (String.valueOf(result).length() > MAX_NUMS || result.isInfinite() ||result.isNaN()) {
						display.setText(FAIL_NOTIFICATION);
					} else {
						display.setText(result.toString());
						Log.e("blubb", result.toString());
					}

					lastOperation = null;
				}
			} else if (input.equals("/") || input.equals("*")
					|| input.equals("+") || input.equals("-")) {
				firstValue = Double.parseDouble(display.getText().toString());
				Log.e("blubb", display.getText().toString());

				lastOperation = input.toString();
				inputOperation = true;
			} else if (input.equals("C")) {
				display.setText("0");
				inputOperation = false;
			}

		}
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

		buttonC = (Button) findViewById(R.id.buttonC);
		buttonEqual = (Button) findViewById(R.id.buttonEqual);

		display = (TextView) findViewById(R.id.output);
	}

}
