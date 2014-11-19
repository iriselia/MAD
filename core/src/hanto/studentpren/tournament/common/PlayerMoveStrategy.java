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
package hanto.studentpren.tournament.common;

import hanto.tournament.HantoMoveRecord;

/**
 * This interface specifies the move strategies of the delta hanto player.
 * 
 * @author Peng Ren
 * @version Feb 25, 2013
 */
public interface PlayerMoveStrategy {
	
	
	/**
	 * This method is to make a best Move or place for the hanto game player from
	 * all the possible movement and placements it can do.
	 * 
	 * @return bestDecesion the best movement or placement the player could do 
	 */
	HantoMoveRecord makeBestDecision();
	
	/**
	 * This function helps the AI player to place a piece on the board
	 * of the best position according to the current game state.
	 * 
	 * @return a PlayeDecision of that placement to be made
	 */
	PlayerDecision makePlacement();
	
	/**
	 * This function helps the AI player to move a piece on the board of 
	 * the best position according to the current game state
	 * 
	 * @return a PlayerDecision of that movement to be made
	 */
	PlayerDecision makeMovement();

}
