package com.yahoo.apps.tipcalculator.activities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.yahoo.apps.tipcalculator.R;
import com.yahoo.apps.tipcalculator.adapters.TipRatesAdapter;
import com.yahoo.apps.tipcalculator.models.TipRate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class SettingActivity extends Activity {

	private ArrayList<TipRate> items;
	private TipRatesAdapter tipRatesAdapter;
	private ListView lvItems;
	private EditText txtNewTipRate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		this.readItems();
		
		this.txtNewTipRate = (EditText) this.findViewById(R.id.txtNewTipRate);
		this.tipRatesAdapter = new TipRatesAdapter(this, this.items);
		
		this.lvItems = (ListView) this.findViewById(R.id.lvItems);
		lvItems.setAdapter(tipRatesAdapter);
		this.setupListViewListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void addTipRate(View view) {
		String inputTipRate = this.txtNewTipRate.getText().toString();
		Integer tipRate = Integer.parseInt(inputTipRate);
		if (tipRate > 100 || tipRate < 0) {
			return;
		}
		
		double tipRatePercentage =  tipRate / 100.0;
		tipRatePercentage = Double.valueOf(String.format("%.02f", tipRatePercentage));
		this.items.add(new TipRate(tipRatePercentage));
		this.tipRatesAdapter.notifyDataSetChanged();
		
		this.saveItems();
	}
	
	public void backToMainActivity(View view) {
		this.finish();
	}
	
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				items.remove(position);
				tipRatesAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}			
		});	
	}
	
	private void readItems() {
		File filesDir = this.getFilesDir();
		File tipRatesFile = new File(filesDir, "TipRates.txt");
		try {
			List<String> fileItems = FileUtils.readLines(tipRatesFile);
			this.items = new ArrayList<TipRate>();			
			if (fileItems == null || fileItems.size() == 0) {
				this.items = new ArrayList<TipRate>(TipRate.getDefaultTipRates());
				return;
			}
			
			for (String aRate : fileItems) {
				this.items.add(new TipRate(Double.parseDouble(aRate)));
			}
		} catch (IOException e) {
			this.items = new ArrayList<TipRate>(TipRate.getDefaultTipRates());
			e.printStackTrace();		
		}
	}
	
	private void saveItems() {
		File filesDir = this.getFilesDir();
		File tipRatesFile = new File(filesDir, "TipRates.txt");
		try {
			List<String> strItems = new ArrayList<String>();
			
			for (TipRate tipRate : this.items) {
				if (tipRate == null) {
					continue;
				}

				strItems.add(String.valueOf(tipRate.getTipRate()));
			}
			
			FileUtils.writeLines(tipRatesFile, strItems);
		} catch (IOException e) {			
			e.printStackTrace();		
		}
	}
}
