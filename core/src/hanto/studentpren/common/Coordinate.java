/*******************************************************************
 * Copyright (c) 2013 Peng Ren
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    feierqi
 *******************************************************************/
package hanto.studentpren.common;

import hanto.util.HantoCoordinate;

/**
 * Implementation of HantoCoordinate that is used in the concrete HantoGame
 * implementations.
 * 
 * @author Peng Ren
 * @version Feb 2, 2013
 */
public class Coordinate implements HantoCoordinate{

	private final int x;
	private final int y;

	/**
	 * Constructor. Values must be supplied for the coordinate values.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
	 * @see hanto.util.HantoCoordinate#getX()
	 */
	@Override
	public int getX() {
		return x;
	}

	/**
	 * @see hanto.util.HantoCoordinate#getY()
	 */
	@Override
	public int getY() {
		return y;
	}

	/**
	 * A type of factory method that guarantees that the client has a Coordinate to
	 * work with. If the actual parameter is a HantoCoordinate that is not a
	 * coordinate, this method returns a corresponding Coordinate object.
	 * Otherwise, it simply returns the argument.
	 * 
	 * @param hantoCoordinate An HantoCoordinate given
	 * @return the guaranteed Coordinate object
	 */
	public static Coordinate guarrenteedCoordinate(HantoCoordinate hantoCoordinate){
		final int x, y;
		Coordinate coordinate;
		if(hantoCoordinate == null){
			coordinate = null;
		}
		else{
			x = hantoCoordinate.getX();
			y = hantoCoordinate.getY();
			if(hantoCoordinate instanceof Coordinate){
				coordinate = (Coordinate) hantoCoordinate;
			}
			else{
				coordinate = new Coordinate(x, y);
			}
		}
		return coordinate;
	}

	/**
	 * Determine if this coordinate is adjacent to the specified coordinate.
	 * 
	 * @param c The coordinate to check for adjacency to this
	 *          coordinate
	 * @return  true if c is adjacent to this coordinate, false otherwise
	 */
	public boolean isAdjacentTo(Coordinate c){
		final Coordinate[] neighbors = getNeighbors();
		boolean result = false;
		for(Coordinate coord : neighbors){
			if(c.equals(coord)){
				result = true;
			}
		}
		return result;
	}

	/**
	 * Get all the coordinate adjacent to this coordinate
	 * 
	 * @return an array containing all of the coordinates 
	 *         that are adjacent to this coordinate.
	 */
	public Coordinate[] getNeighbors(){
		final Coordinate adjacent1 = new Coordinate(x, y + 1);
		final Coordinate adjacent2 = new Coordinate(x, y - 1);
		final Coordinate adjacent3 = new Coordinate(x - 1, y + 1);
		final Coordinate adjacent4 = new Coordinate(x - 1, y);
		final Coordinate adjacent5 = new Coordinate(x + 1, y);
		final Coordinate adjacent6 = new Coordinate(x + 1, y - 1);
		final Coordinate[] neighbors = 
			{adjacent1, adjacent2, adjacent3, adjacent4, adjacent5, adjacent6};
		return neighbors;
	}

	/**
	 * This method is used for test only, to tell if two
	 * types's coordinates are same.
	 */
	@Override
	public boolean equals(Object o) {
		boolean result = false;
		if(this == o){
			result = true;
		}
		if(o instanceof HantoCoordinate){
			final HantoCoordinate that = (Coordinate) o;
			if(that.getX() == x &&
					(that.getY() == y)){
				result = true;
			}
		}
		return result;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (41 * (41 + x) + y);
	}

	/**
	 * This functions is used to get the string representation of 
	 * this coordinate.
	 * 
	 * @return A string representing the coordinate
	 */
	public String toString(){
		final String coordinate;
		coordinate = " (" + x + ", " + y + ")";
		return coordinate;
	}
}
