package com.yahoo.apps.tipcalculator.activities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.yahoo.apps.tipcalculator.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ArrayList<String> items;
	private ArrayAdapter<String> itemAdapter;
	
	private EditText txtAmount;
	private TextView labelTip;
	private Spinner spTipRates;
	double currentTipRate = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// bind view component to local variable
		this.labelTip = (TextView) this.findViewById(R.id.labelTip);
		this.txtAmount = (EditText) this.findViewById(R.id.txtAmount);
		this.spTipRates = (Spinner) this.findViewById(R.id.spTipRates);
		
		this.setSpinnerData();		
		this.setSpinnerListener();				
		this.setTxtAmountListener();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		this.setSpinnerData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(this, SettingActivity.class);
        	this.startActivity(intent);
        	
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void calculateTip() {
		if (this.txtAmount.getText().length() == 0) {
			this.labelTip.setText("");
			return;
		}

		double amount = Double.parseDouble(this.txtAmount.getText().toString());
		double tip = amount * currentTipRate;

		String output = String.format("Tip is:     $%.2f", tip);
		this.labelTip.setText(output);
	}
    
    private void readItems() {		
		try {
			File filesDir = this.getFilesDir();
			File todoFile = new File(filesDir, "TipRates.txt");
			
			items = new ArrayList<String>();
			if (!todoFile.exists()) {
				items.add("0.1");
				items.add("0.15");
				items.add("0.2");
				return;
			} else {
				items = new ArrayList<String>(FileUtils.readLines(todoFile));
			}			
		} catch (IOException e) {
			items = new ArrayList<String>();
			e.printStackTrace();
		}
	}
       
    private void setSpinnerData() {
		// load preset percentages into Adapter
		this.readItems();
				
		ArrayList<String> displayItems = new ArrayList<String>();
		for (String anItem : this.items) {
			String displayText = String.format("%2.0f%%", Double.parseDouble(anItem) * 100);
			displayItems.add(displayText);
		}
		itemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, displayItems);
		itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spTipRates.setAdapter(itemAdapter);
		
		// set the current tip rate as first item from data file		
		String firstItem = items.get(0);
		this.currentTipRate = Double.parseDouble(firstItem);
	}

    private void setSpinnerListener() {
		this.spTipRates.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				//String selectItem = (String) parent.getItemAtPosition(position);
				String selectItem = items.get(position);
				
				currentTipRate = Double.parseDouble(selectItem);
				calculateTip();				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}			
		});
	}
  
	private void setTxtAmountListener() {
		txtAmount.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				calculateTip();
			}
		});
	}
}
