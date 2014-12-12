package com.mygdx.hanto.implementation;

import java.util.Deque;

import com.mygdx.hanto.implementation.common.HantoPiece;

/**
 * All the Hanto Game should implement this interface, which provides a new 
 * method of getPiece to get the HantoPiece at the given x coordinate and 
 * y coordinate.
 * 
 */
public interface InternalHantoGame {

	/**
	 * This function is used to get the piece at the coordinate (x, y)
	 * in a specific Hanto game.
	 * 
	 * @param x coordinate x
	 * @param y coordinate y
	 * @return the piece at coordinate (x, y) or
	 *         null if there is no piece at that position
	 */
	 Deque<HantoPiece> getPieceAt(int x, int y);
}
