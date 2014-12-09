package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.mygdx.hanto.util.HantoCoordinate;
import com.mygdx.screen.GameScreen;

public class CoordinatesToPixels {
	
	public static final int TILE_NUM_X = (int) (GameScreen.WORLD_WIDTH / GameScreen.TILE_LENGTH);
	public static final int TILE_NUM_Y = (int) (GameScreen.WORLD_HEIGHT / GameScreen.TILE_LENGTH);
	
	
	public static class Pixels {
		public int x;
		public int y;
		
		public Pixels(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	
	public static Pixels convertCooridnatesToPixel(HantoCoordinate coord){
		final int x = coord.getX();
		final int y = coord.getY();
	
		
		return new Pixels(x, y);
	}

}
