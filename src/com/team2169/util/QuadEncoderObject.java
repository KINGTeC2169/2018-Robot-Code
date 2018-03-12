package com.team2169.util;

import java.util.ArrayList;
import java.util.List;

public class QuadEncoderObject {
	
	int i = 0;
	List<Integer> values = new ArrayList<Integer>();

	public void inputValue(int value) {
		
		if(value > 10000) {
			values.add(value);
		}
		
	}
	
	public int getLatestValue() {
		
		return values.get(values.size());
		
	}
	
}
