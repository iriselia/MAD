package com.mygdx.game.util;

import com.mygdx.hanto.implementation.common.Coordinate;
import com.mygdx.hanto.util.HantoCoordinate;

public class CoordinatesToPixels {
	
	public static final int TILE_NUM_X = (int) (Constants.WORLD_WIDTH / Constants.TILE_LENGTH);
	public static final int TILE_NUM_Y = (int) (Constants.WORLD_HEIGHT / Constants.TILE_LENGTH);
	private static final int originX = (int) Constants.WORLD_WIDTH / 2;
	private static final int originY = (int) Constants.WORLD_HEIGHT /2;
	
	public static Pixels convertCooridnatesToPixels(HantoCoordinate coord){
		final int x = coord.getX();
		final int y = coord.getY();
		
		final int pixelX = x * 150 + originX;;
		final int pixelY = y * 200 + x * 100 + originY;
		
		//TODO:check boundary
		return new Pixels(pixelX - 100, pixelY - 100);
	}
	
	public static HantoCoordinate convertPixelsToCoordinates(Pixels pixels){
		final int x = pixels.x;
		final int y = pixels.y;
		
		final int coordX = (x + 100 - originX) / 150;;
		final int coordY = (y + 100 - originY - coordX * 100) / 200;
		
		//TODO:check boundary
		return new Coordinate(coordX, coordY);
	}
	
	public static Pixels getInitialPixelsForBlue(){
		return new Pixels(originX - 100, originY - 100);
	}
	
	public static Pixels getInitialPixelsForYellow(){
		return new Pixels(originX - 100, originY + 100);
	}

}
