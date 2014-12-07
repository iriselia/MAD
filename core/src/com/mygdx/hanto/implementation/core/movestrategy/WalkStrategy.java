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
package com.mygdx.hanto.implementation.core.movestrategy;

import java.util.ArrayList;
import java.util.List;

import com.mygdx.hanto.implementation.common.Coordinate;
import com.mygdx.hanto.implementation.common.HantoBoard;
import com.mygdx.hanto.implementation.common.HantoPiece;
import com.mygdx.hanto.implementation.common.PieceMoveStrategy;
import com.mygdx.hanto.implementation.core.HantoStateDevelopment;

/**
 * This class specified the move rule that defined for the delta hanto game.
 * 
 * @author Peng Ren
 * @version Feb 22, 2013
 */
public class WalkStrategy implements PieceMoveStrategy{
	
	private final HantoStateDevelopment gameState;
	
	/**
	 * Constructor for the walkStratgy that import the game state of the game
	 * @param gameState the current state of Delta hanto game
	 */
	public WalkStrategy(HantoStateDevelopment gameState){
		this.gameState = gameState;
	}

	/**
	 * @see hanto.studentpren.common.PieceMoveStrategy#canIMove
	 * (hanto.studentpren.common.Coordinate, hanto.studentpren.common.Coordinate)
	 */
	@Override
	public boolean canIMove(Coordinate from, Coordinate to) {
		final boolean result;
		final List<Coordinate> jointNeighbors = new ArrayList<Coordinate>();
		if(!from.isAdjacentTo(to)){
			result = false;
		}
		else{
			final Coordinate[] fromNeighbors = from.getNeighbors();
			final Coordinate[] toNeighbors = to.getNeighbors();
			final HantoPiece jointPiece1;
			final HantoPiece jointPiece2;
			for(Coordinate coord1 : fromNeighbors){
				for(Coordinate coord2 : toNeighbors){
					if(coord1.equals(coord2)){
						jointNeighbors.add(coord1);
					}
				}
			}
			jointPiece1 = gameState.getBoard().getPieceAt(jointNeighbors.get(0));
			jointPiece2 = gameState.getBoard().getPieceAt(jointNeighbors.get(1));
			if(jointPiece1 != null && jointPiece2 != null){
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
		}
		return result;
	}

}
