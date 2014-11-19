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
package hanto.studentpren.delta;

import hanto.studentpren.common.Coordinate;
import hanto.studentpren.common.HantoPiece;
import hanto.studentpren.common.PieceMoveStrategy;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

/**
 * This class is used to specifiy a piece of Delta Hanto and a hex on the board 
 * where the piece will be placed. This class is only used for testing purposes.
 * 
 * @author Peng Ren
 * @version Feb 22, 2013
 */
public class DeltaHantoPiece implements HantoPiece{

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
	
	public DeltaHantoPiece(HantoCoordinate coordinate, HantoPlayerColor player,
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
	
	public DeltaHantoPiece(HantoCoordinate coordinate, HantoPlayerColor player,
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
	public DeltaHantoPiece(HantoPlayerColor player, HantoPieceType piece){
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
	 * @see hanto.studentpren.common.HantoPiece#setCoordinate(hanto.studentpren.common.Coordinate)
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
