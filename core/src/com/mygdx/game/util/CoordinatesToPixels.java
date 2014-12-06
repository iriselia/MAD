package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.mygdx.hanto.util.HantoCoordinate;

public class CoordinatesToPixels {
	
	public static class Pixels {
		public int x;
		public int y;
		
		public Pixels(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	
	public static Pixels convertCooridnatesToPixel(HantoCoordinate coord){
		int x = Gdx.graphics.getWidth()/2 - 100;
		int y = Gdx.graphics.getHeight()/2 - 100;
	
		return new Pixels(x, y);
		
	}

}
