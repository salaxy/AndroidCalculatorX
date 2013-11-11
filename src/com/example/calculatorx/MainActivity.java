package com.example.calculatorx;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity  implements OnClickListener{
	
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
    private TextView output;
    private View rootView;

    private Double result;
    public String value;

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
		
		return true;
	}
	
    @Override
    public void onClick(View view) 
    {
        if(view instanceof Button){
            String statement=((Button)view).getText().toString();
           output.append(((Button)view).getText());

           if(statement.equals("1")){
	
           }else if(statement.equals("2")){
            		
           }else if(statement.equals("3")){
        	   
           }else if(statement.equals("4")){
        	   
           }else if(statement.equals("5")){
        	   
           }else if(statement.equals("6")){
        	   
           }else if(statement.equals("7")){
        	   
           }else if(statement.equals("8")){
        	   
           }else if(statement.equals("9")){
        	   
           }else if(statement.equals("C")){
        	   
           }else if(statement.equals("=")){
        	   
           }else if(statement.equals("/")){
        	   
           }else if(statement.equals("+")){
        	   
           }else if(statement.equals("-")){
        	   
           }else if(statement.equals("*")){
        	   
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

        output = (TextView) findViewById(R.id.output);
    }

}
