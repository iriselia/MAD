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
package com.mygdx.hanto.implementation;

import com.mygdx.hanto.implementation.common.HantoPiece;

/**
 * All the Hanto Game should implement this interface, which provides a new 
 * method of getPiece to get the HantoPiece at the given x coordinate and 
 * y coordinate.
 * 
 * @author Peng Ren
 * @version Feb 2, 2013
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
	 HantoPiece getPieceAt(int x, int y);
}
