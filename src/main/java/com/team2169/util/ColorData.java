package com.team2169.util;

import java.util.HashMap;

public class ColorData {

	public static class Color {
		
		public int r;
		public int g;
		public int b;
		
		public Color(int r_, int g_, int b_) {
			r = r_;
			g = g_;
			b = b_;
		}
		
		HashMap<String, Color> colorMap = new HashMap<String, Color>();
		
		public void initializeColors() {
		
			colorMap.put("red", new Color(255,0,0));
			colorMap.put("yellow", new Color(255,255,0));
			colorMap.put("orange", new Color(255,165,0));
			colorMap.put("green", new Color(0,255,0));
			colorMap.put("blue", new Color(0,0,255));
			colorMap.put("purple", new Color(128,0,128));
			colorMap.put("gold", new Color(255,215,0));
			colorMap.put("brown", new Color(165,42,42));
			colorMap.put("white ", new Color(255, 255, 255));
			
		}
		

		
		
	}
	
}
