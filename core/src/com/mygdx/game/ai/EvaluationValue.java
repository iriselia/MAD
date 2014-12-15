package com.mygdx.game.ai;

/**
 * This enum specifies the values for different situations of game state
 * to helper the player to evaluate its score.
 * 
 * @author Peng Ren
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