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

import com.mygdx.hanto.common.HantoException;
import com.mygdx.hanto.common.HantoGame;
import com.mygdx.hanto.implementation.common.Coordinate;
import com.mygdx.hanto.implementation.common.HantoGameImpl;
import com.mygdx.hanto.util.HantoCoordinate;
import com.mygdx.hanto.util.HantoPieceType;
import com.mygdx.hanto.util.HantoPlayerColor;
import com.mygdx.hanto.util.MoveResult;

/**
 * This is the delta version of hanto game.
 * 
 */
public class HantoGameDevelopment extends HantoGameImpl implements HantoGame{
	
	private final RuleSetDevelopment deltaRuleSet;

	public HantoGameDevelopment(){
		gameState = new HantoStateDevelopment();
		gameRuleSet = new RuleSetDevelopment(gameState);
		deltaRuleSet = (RuleSetDevelopment) gameRuleSet;
	}
	
	/**
	 * @see com.mygdx.hanto.common.HantoGame#initialize
	 */
	public void initialize(HantoPlayerColor firstPlayer){
		super.initialize(firstPlayer);
		((HantoStateDevelopment) gameState).setBlueSparrowPlaced(0);
		((HantoStateDevelopment) gameState).setRedSparrowPlaced(0);
		((HantoStateDevelopment) gameState).setBlueCrabPlaced(0);
		((HantoStateDevelopment) gameState).setRedCrabPlaced(0);
		((HantoStateDevelopment) gameState).setBlueCranePlaced(0);
		((HantoStateDevelopment) gameState).setRedCranePlaced(0);
		((HantoStateDevelopment) gameState).setBlueHorsePlaced(0);
		((HantoStateDevelopment) gameState).setRedHorsePlaced(0);
		((HantoStateDevelopment) gameState).setBlueDovePlaced(0);
		((HantoStateDevelopment) gameState).setRedDovePlaced(0);
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
	public HantoStateDevelopment getGameState(){
		return (HantoStateDevelopment) gameState;
	}
}