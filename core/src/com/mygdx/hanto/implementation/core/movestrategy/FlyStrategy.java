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

import com.mygdx.hanto.implementation.common.Coordinate;
import com.mygdx.hanto.implementation.common.PieceMoveStrategy;
import com.mygdx.hanto.implementation.common.PieceMoveStrategyImpl;
import com.mygdx.hanto.implementation.core.HantoStateDevelopment;


/**
 * This class specifies the fly rule that defined in the delta hanto game.
 * 
 */
public class FlyStrategy extends PieceMoveStrategyImpl implements PieceMoveStrategy{

	/**
	 * Constructor for the walkStratgy that import the game state of the game
	 * @param gameState the current state of Delta hanto game
	 */
	public FlyStrategy(HantoStateDevelopment gameState){
		this.gameState = gameState;
	}

	/**
	 * @see com.mygdx.hanto.implementation.common.PieceMoveStrategy#canIMove
	 */
	@Override
	public boolean canIMove(Coordinate from, Coordinate to) {
		final boolean result;
		result = ifConnectedAfterMove(from, to);
		return result;
	}
}
