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

/**
 * This enum specifies the values for different situations of game state
 * to helper the player to evaluate its score.
 * 
 * @author Peng Ren
 * @version Feb 26, 2013
 */
public enum EvaluationValue {
	OWN_BUTTERFLY_NEIGHBORS(-30),
	OWN_BUTTERFLY_MOVABILITY(20),
	OWN_SPARROW_NUM(6),
	OWN_SPARROW_MOVABILITY(2),
	OWN_CRAB_NUM(3),
	OWN_CRAB_MOVABILITY(1),
	OPPONENT_BUTTERFLY_NEIGHBORS(100),
	OPPONENT_BUTTERFLY_MOVABILITY(-30),
	OPPONENT_SPARROW_MOVABILITY(-3),
	OPPONENT_CRAB_MOVABILITY(-2);
	
	private final int score;
	
	/** 
	 * Constructor for EvaluationValue class
	 * 
	 * @param score the score gotten
	 */
	EvaluationValue(int score) {
		this.score = score;
	}
	
	public int getScore(){
		return score;
	}
	
}