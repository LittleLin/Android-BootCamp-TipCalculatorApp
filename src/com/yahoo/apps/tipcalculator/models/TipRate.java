package com.yahoo.apps.tipcalculator.models;

import java.util.ArrayList;
import java.util.List;

public class TipRate {
	private double tipRate;
	private String displayText;
	
	public TipRate(double tipRate) {
		this.tipRate = tipRate;
		this.displayText = String.format("  %2d%%", (int) (tipRate * 100));
	}	

	public double getTipRate() {
		return tipRate;
	}

	public void setTipRate(double tipRate) {
		this.tipRate = tipRate;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}		
	
	public static List<TipRate> getDefaultTipRates() {
		ArrayList<TipRate> items = new ArrayList<TipRate>();
		items.add(new TipRate(0.1));
		items.add(new TipRate(0.15));
		items.add(new TipRate(0.2));
		
		return items;		
	}
}
