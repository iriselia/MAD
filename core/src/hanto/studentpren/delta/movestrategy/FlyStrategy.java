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
package hanto.studentpren.delta.movestrategy;

import hanto.studentpren.common.Coordinate;
import hanto.studentpren.common.HantoBoard;
import hanto.studentpren.common.HantoPiece;
import hanto.studentpren.common.PieceMoveStrategy;
import hanto.studentpren.delta.HantoDeltaState;


/**
 * This class specifies the fly rule that defined in the delta hanto game.
 * 
 * @author Peng Ren
 * @version Feb 22, 2013
 */
public class FlyStrategy implements PieceMoveStrategy{
	
	private final HantoDeltaState gameState;
	
	/**
	 * Constructor for the walkStratgy that import the game state of the game
	 * @param gameState the current state of Delta hanto game
	 */
	public FlyStrategy(HantoDeltaState gameState){
		this.gameState = gameState;
	}

	/**
	 * @see hanto.studentpren.common.PieceMoveStrategy#canIMove
	 * (hanto.studentpren.common.Coordinate, hanto.studentpren.common.Coordinate)
	 */
	@Override
	public boolean canIMove(Coordinate from, Coordinate to) {
		final boolean result;
		final HantoPiece pieceTo = gameState.getBoard().getPieceAt(to);
		if(pieceTo != null){
			result = false;
		}
		else{
			final HantoBoard virtualBoard = gameState.getBoard();
			virtualBoard.movePiece(from, to);
			if(virtualBoard.isConnected()){
				result = true;
			}
			else{
				result = false;
			}
			virtualBoard.movePiece(to, from);
		}
		return result;
	}

}
