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
package com.mygdx.hanto.implementation.common;

import java.util.Deque;

import com.mygdx.hanto.common.HantoException;
import com.mygdx.hanto.implementation.InternalHantoGame;
import com.mygdx.hanto.util.HantoCoordinate;
import com.mygdx.hanto.util.HantoPieceType;
import com.mygdx.hanto.util.HantoPlayerColor;
import com.mygdx.hanto.util.MoveResult;

/**
 * This is the implementation of any Internal Hanto Game. And any specific
 * Hanto Game should be a subclass of this class.
 * 
 */
public abstract class HantoGameImpl implements InternalHantoGame{

	protected HantoRuleSet gameRuleSet;
	protected HantoState gameState;
		
	/**
	 * @see com.mygdx.hanto.common.HantoGame#initialize(hanto.util.HantoPlayerColor)
	 */
	public void initialize(HantoPlayerColor firstPlayer){
		gameState.setInitialCoordinate(new Coordinate(0, 0));
		gameState.setTurnNum(1);
		gameState.setFirstPlayer(firstPlayer);
		gameState.setPlayerOnMove(firstPlayer);
		gameState.getBoard().reset();
		gameState.setBlueButterflyPlaced(0);
		gameState.setRedButterflyPlaced(0);
		gameState.setGameOver(false);
	}

	/**
	 * @see com.mygdx.hanto.common.HantoGame#makeMove
	 */
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		final Coordinate coordFrom, coordTo;
		coordFrom = Coordinate.guarrenteedCoordinate(from);
		coordTo = Coordinate.guarrenteedCoordinate(to);
		final MoveResult result;
		gameRuleSet.performPreMoveChecks(pieceType, coordFrom, coordTo);
		gameRuleSet.doMakeTheMove(pieceType, coordFrom, coordTo);
		gameRuleSet.performPostMoveChecks();
		result = gameRuleSet.determineMoveResult();
		return result;
	}
	
	/**
	 * @see com.mygdx.hanto.common.HantoGame#getPrintableBoard()
	 */
	public String getPrintableBoard() {
		return gameState.getBoard().getPrintableBoard();
	}

	/**
	 * @see com.mygdx.hanto.implementation.InternalHantoGame#getPieceAt(int, int)
	 */
	public Deque<HantoPiece> getPieceAt(int x, int y) {
		final Deque<HantoPiece> pieces;
		final HantoCoordinate coordinate;
		coordinate = new Coordinate(x, y);
		pieces = gameState.getBoard().getPieceAt((Coordinate) coordinate);
		return pieces;
	}
}
