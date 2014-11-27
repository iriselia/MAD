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

import com.mygdx.hanto.util.HantoCoordinate;
import com.mygdx.hanto.util.HantoPieceType;
import com.mygdx.hanto.util.HantoPlayerColor;

/**
 * This inferface is used to specify a piece and a hex on the board where the piece will be
 * placed. This class is only used for testing purposes.
 * 
 * @author Peng Ren
 * @version Feb 22, 2013
 */
public interface HantoPiece {

	/**
	 * @return the coordinate
	 */
	HantoCoordinate getCoordinate();

	/**
	 * @return the player
	 */
	HantoPlayerColor getPlayer();

	/**
	 * @return the piece
	 */
	HantoPieceType getPiece();
	
	/**
	 * Change the piece Coordinate to the given coordinate
	 * @param c the coordinate to be set to piece
	 */
	void setCoordinate(Coordinate c);
}
