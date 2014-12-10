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
package com.mygdx.hanto.implementation.core;

import com.mygdx.hanto.implementation.common.Coordinate;
import com.mygdx.hanto.implementation.common.HantoPiece;
import com.mygdx.hanto.implementation.common.PieceMoveStrategy;
import com.mygdx.hanto.util.HantoCoordinate;
import com.mygdx.hanto.util.HantoPieceType;
import com.mygdx.hanto.util.HantoPlayerColor;

/**
 * This class is used to specifiy a piece of Delta Hanto and a hex on the board 
 * where the piece will be placed. This class is only used for testing purposes.
 * 
 */
public class HantoPieceDevelopment implements HantoPiece{

	private final HantoPieceType piece;
	private final HantoPlayerColor player;
	private HantoCoordinate coordinate;
	private PieceMoveStrategy moveStrategy;

	/**
	 * Constructor.
	 * 
	 * @param coordinate
	 *            the hex where the piece will be placed
	 * @param player
	 *            the color of the player / piece
	 * @param piece
	 *            the piece type
	 * @param moveStrategy
	 * 			  the move strategy of the given hanto piece type
	 */
	
	public HantoPieceDevelopment(HantoCoordinate coordinate, HantoPlayerColor player,
			HantoPieceType piece, PieceMoveStrategy moveStrategy) {
		this.coordinate = coordinate;
		this.player = player;
		this.piece = piece;
		this.moveStrategy = moveStrategy;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param coordinate
	 *            the hex where the piece will be placed
	 * @param player
	 *            the color of the player / piece
	 * @param piece
	 *            the piece type
	 */
	
	public HantoPieceDevelopment(HantoCoordinate coordinate, HantoPlayerColor player,
			HantoPieceType piece) {
		this.coordinate = coordinate;
		this.player = player;
		this.piece = piece;
		moveStrategy = null;
	}
	
	/**
	 * Constructor used for constructing pieces that are not already on theboard. It
     * sets the coordinate to null;
	 * 
	 * @param player 
	 *           the color of the player / piece
	 * @param piece 
	 *           the piece type
	 */
	public HantoPieceDevelopment(HantoPlayerColor player, HantoPieceType piece){
		coordinate = null;
		moveStrategy = null;
		this.player = player;
		this.piece = piece;
	}

	/**
	 * @return the coordinate
	 */
	public HantoCoordinate getCoordinate()
	{
		return coordinate;
	}

	/**
	 * @return the player
	 */
	public HantoPlayerColor getPlayer()
	{
		return player;
	}

	/**
	 * @return the piece
	 */
	public HantoPieceType getPiece()
	{
		return piece;
	}
	
	/**
	 * @return the moveStrategy
	 */
	public PieceMoveStrategy getMoveStrategy() {
		return moveStrategy;
	}

	/**
	 * @see com.mygdx.hanto.implementation.common.HantoPiece#setCoordinate
	 */
	@Override
	public void setCoordinate(Coordinate c) {
		coordinate = c;
	}
	
	/**
	 * set the movestrategy to the given piece
	 */
	public void setPieceMoveStrategy(PieceMoveStrategy moveStrategy){
		this.moveStrategy = moveStrategy;
	}
}
