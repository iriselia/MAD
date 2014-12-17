package com.mygdx.game.util;

import java.util.ArrayList;
import java.util.List;

import com.mygdx.hanto.implementation.common.Coordinate;
import com.mygdx.hanto.util.HantoCoordinate;

/**
 * Handles conversion between Hanto game coordinates and pixel coordinates,
 * which can then be used for drawing actors in screen
 * 
 * @author Peng Ren, Xi Wen
 *
 */
public class CoordinatesToPixels {
	
	public static final int TILE_NUM_X = (int) (Constants.WORLD_WIDTH / Constants.TILE_LENGTH);
	public static final int TILE_NUM_Y = (int) (Constants.WORLD_HEIGHT / Constants.TILE_LENGTH);
	private static final int originX = (int) Constants.WORLD_WIDTH / 2;
	private static final int originY = (int) Constants.WORLD_HEIGHT /2;
	
	/**
	 * converts the given Hanto game coordinate to Pixel coordinate
	 * @param coord Hanto game coordinate to be converted
	 * @return the converted Pixel coordinate
	 */
	public static Pixels convertCooridnatesToPixels(HantoCoordinate coord){
		final int x = coord.getX();
		final int y = coord.getY();
		
		final int pixelX = x * Constants.TILE_LENGTH * 3 / 4 + originX;;
		final int pixelY = y * Constants.TILE_LENGTH + x * Constants.TILE_LENGTH / 2 + originY;
		
		//TODO:check boundary
		return new Pixels(pixelX - Constants.TILE_LENGTH / 2, pixelY - Constants.TILE_LENGTH / 2);
	}
	
	/**
	 * converts the given Pixel coordinate to Hanto game coordinate
	 * @param pixels the Pixel coordinate to be converted
	 * @return the converted Hanto game coordinate
	 */
	public static HantoCoordinate convertPixelsToCoordinates(Pixels pixels){
		final int x = pixels.x;
		final int y = pixels.y;
		
		final int coordX = (x + Constants.TILE_LENGTH / 2 - originX) / (Constants.TILE_LENGTH * 3 / 4);
		final int coordY = (y + Constants.TILE_LENGTH / 2 - originY - coordX * Constants.TILE_LENGTH / 2) / Constants.TILE_LENGTH;
		
		//TODO:check boundary
		return new Coordinate(coordX, coordY);
	}
	
	/**
	 * Get the initial Pixel coordinate for Player Blue
	 * @return Pixel coordinate for Blue's first piece
	 */
	public static Pixels getInitialPixelsForBlue(){
		return new Pixels(originX - Constants.TILE_LENGTH / 2, originY - Constants.TILE_LENGTH / 2);
	}
	
	/**
	 * Get the initial Pixel coordinate for Player Yellow
	 * @return Pixel coordinate for Yellow's first piece
	 */
	public static List<Pixels> getInitialPixelsForYellow(){
		final List<Pixels> result = new ArrayList<Pixels>();
		Coordinate origin = new Coordinate(0, 0);
		for(Coordinate coord : origin.getNeighbors()){
			final Pixels pixels = convertCooridnatesToPixels(coord);
			result.add(pixels);
		}
		return result;
	}

}
