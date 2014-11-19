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

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentpren.common.Coordinate;
import hanto.studentpren.common.HantoGameImpl;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * This is the delta version of hanto game.
 * 
 * @author Peng Ren
 * @version Feb 9, 2013
 */
public class DeltaHantoGame extends HantoGameImpl implements HantoGame{
	
	private final DeltaRuleSet deltaRuleSet;

	public DeltaHantoGame(){
		gameState = new HantoDeltaState();
		gameRuleSet = new DeltaRuleSet(gameState);
		deltaRuleSet = (DeltaRuleSet) gameRuleSet;
	}
	
	/**
	 * @see hanto.common.HantoGame#initialize(hanto.util.HantoPlayerColor)
	 */
	public void initialize(HantoPlayerColor firstPlayer){
		super.initialize(firstPlayer);
		((HantoDeltaState) gameState).setBlueSparrowPlaced(0);
		((HantoDeltaState) gameState).setRedSparrowPlaced(0);
		((HantoDeltaState) gameState).setBlueCrabPlaced(0);
		((HantoDeltaState) gameState).setRedCrabPlaced(0);
	}

	
	/**
	 * @see hanto.common.HantoGame#makeMove(hanto.util.HantoPieceType, 
	 * hanto.util.HantoCoordinate, hanto.util.HantoCoordinate)
	 */
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		final Coordinate coordFrom, coordTo;
		coordFrom = Coordinate.guarrenteedCoordinate(from);
		coordTo = Coordinate.guarrenteedCoordinate(to);
		final MoveResult result;
		final MoveResult resignResult;
		resignResult = deltaRuleSet.checkResign(pieceType, coordFrom, coordTo);
		if(resignResult != null){
			result = resignResult;
		}
		else{
			result = super.makeMove(pieceType, coordFrom, coordTo);
		}
		return result;
	}
	
	/**
	 * Get the current game state of the delta hanto game.
	 */
	public HantoDeltaState getGameState(){
		return (HantoDeltaState) gameState;
	}
}