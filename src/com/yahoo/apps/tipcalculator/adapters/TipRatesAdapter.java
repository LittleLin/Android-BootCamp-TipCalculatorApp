package com.yahoo.apps.tipcalculator.adapters;

import java.util.ArrayList;

import com.yahoo.apps.tipcalculator.models.TipRate;
import com.yahoo.apps.tipcalculator.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TipRatesAdapter extends ArrayAdapter<TipRate> {
	
	public TipRatesAdapter(Context context, ArrayList<TipRate> tipRates) {
	       super(context, 0, tipRates);
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
		TipRate tipRateItem = getItem(position);
		
       // Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tip_rate, parent, false);
       }
       
       // Lookup view for data population
       TextView tvTipRate = (TextView) convertView.findViewById(R.id.labelTipRateItem);
       
       // Populate the data into the template view using the data object
       tvTipRate.setText(tipRateItem.getDisplayText());
       
       // Return the completed view to render on screen
       return convertView;
   }
}