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
package com.mygdx.hanto.core.common;

/**
 * This interface specifies the specific moving strategy behaviors for different
 * hanto pieces.
 * 
 * @author Peng Ren
 * @version Feb 22, 2013
 */
public interface PieceMoveStrategy {

	/**
	 * This function checks if the moving is valid according to the movement
	 * strategy of the hanto piece.
	 * @param from the coordinate where the piece from
	 * @param to the coordinate where the piece to go
	 * @return true if it is valid to move from 'from' to 'to'
	 */
	boolean canIMove(Coordinate from, Coordinate to);
}
