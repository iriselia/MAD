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
 * This class contain a HantoMoveRecord and a score for that decision. 
 * This class is used to store a player's decision
 * 
 * @author Peng Ren
 * @version Feb 27, 2013
 */
public class PlayerDecision {
	
	private final HantoMoveRecord decision;
	private final int decisionScore;
	
	/**
	 * The constructor for the PlayerDecision class.
	 * 
	 * @param playerDecision the movement or placement the player want to make
	 * @param decisionScore the score that decision gets
	 */
	public PlayerDecision(HantoMoveRecord playerDecision, int decisionScore){
		decision = playerDecision;
		this.decisionScore = decisionScore;
	}

	/**
	 * @return the playerDecision
	 */
	public HantoMoveRecord getPlayerDecision() {
		return decision;
	}

	/**
	 * @return the decisionScore
	 */
	public int getDecisionScore() {
		return decisionScore;
	}
	

}
