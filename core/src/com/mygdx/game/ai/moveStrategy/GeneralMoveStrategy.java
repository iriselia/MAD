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
package com.mygdx.game.ai.moveStrategy;

import static com.mygdx.hanto.util.HantoPieceType.BUTTERFLY;
import static com.mygdx.hanto.util.HantoPieceType.CRAB;
import static com.mygdx.hanto.util.HantoPieceType.SPARROW;

import java.util.List;

import com.mygdx.game.ai.EvaluationHandler;
import com.mygdx.game.ai.PlayerDecision;
import com.mygdx.game.ai.PlayerMoveStrategy;
import com.mygdx.game.util.GameStateHandler;
import com.mygdx.hanto.util.HantoMoveRecord;


/**
 * This class specifies the player's general movement strategy after
 * the first turn.
 * 
 * @author Peng Ren
 */
public class GeneralMoveStrategy implements PlayerMoveStrategy{

	private final GameStateHandler gameStateHandler;
	private final EvaluationHandler evaluationHandler;

	/**
	 * This is the constructor for the GenearalMoveStrategy.
	 * 
	 * @param gameStateHandler a handler to handle the game state situations
	 * @param evaluationHandler a handler to handle the evaluation situation
	 */
	public GeneralMoveStrategy(GameStateHandler gameStateHandler, 
			EvaluationHandler evaluationHandler){
		this.gameStateHandler = gameStateHandler;
		this.evaluationHandler = evaluationHandler;
	}
	
	/**
	 * This method is to make a best Move or place for the hanto game player from
	 * all the possible movement and placements it can do.
	 * 
	 * @return bestDecesion the best movement or placement the player could do 
	 */
	public HantoMoveRecord makeBestDecision(){
		PlayerDecision bestDecision;
		final PlayerDecision placeBestDecision = makePlacement();
		final PlayerDecision moveBestDecision = makeMovement();
		if(placeBestDecision.getPlayerDecision() == null){
			bestDecision = moveBestDecision;
		}
		else if(moveBestDecision.getPlayerDecision() == null){
			bestDecision = placeBestDecision;
		}
		else{
			if(placeBestDecision.getDecisionScore() > moveBestDecision.getDecisionScore()){
				bestDecision = placeBestDecision;
			}
			else{
				bestDecision = moveBestDecision;
			}
		}
		return bestDecision.getPlayerDecision();
	}


	/**
	 * @see hanto.studentpren.tournament.common.PlayerMoveStrategy#makePlacement()
	 */
	@Override
	public PlayerDecision makePlacement() {
		final List<HantoMoveRecord> possibleSparrowPlaces = 
				gameStateHandler.generatePiecesAvailablePlacements(SPARROW);
		final List<HantoMoveRecord> possibleCrabPlaces = 
				gameStateHandler.generatePiecesAvailablePlacements(CRAB);
		HantoMoveRecord bestPlacement = null;
		int bestScore = Integer.MIN_VALUE;
		int ownStateScore = 0;
		int opponentStateScore = 0;
		int sumScore;
		for(int i = 0; i < possibleSparrowPlaces.size(); i++){
			HantoMoveRecord nextPlace = possibleSparrowPlaces.get(i);
			ownStateScore = evaluationHandler.evaluateOwnState(nextPlace);
			opponentStateScore = evaluationHandler.evaluateOpponentState(nextPlace);
			sumScore = ownStateScore + opponentStateScore;
			if(sumScore > bestScore){
				bestScore = sumScore;
				bestPlacement = nextPlace;
			}
		}
		for(int i = 0; i < possibleCrabPlaces.size(); i++){
			HantoMoveRecord nextPlace = possibleCrabPlaces.get(i);
			ownStateScore = evaluationHandler.evaluateOwnState(nextPlace);
			opponentStateScore = evaluationHandler.evaluateOpponentState(nextPlace);
			sumScore = ownStateScore + opponentStateScore;
			if(sumScore > bestScore){
				bestScore = sumScore;
				bestPlacement = nextPlace;
			}
		}
		final PlayerDecision placeDecision = new PlayerDecision(bestPlacement, bestScore);
		return placeDecision;
	}

	/**
	 * @see hanto.studentpren.tournament.common.PlayerMoveStrategy#makeMovement()
	 */
	@Override
	public PlayerDecision makeMovement() {
		final List<HantoMoveRecord> possibleButterflyMoves = 
				gameStateHandler.generatePiecesAvailableMoves(BUTTERFLY);
		final List<HantoMoveRecord> possibleSparrowMoves = 
				gameStateHandler.generatePiecesAvailableMoves(SPARROW);
		final List<HantoMoveRecord> possibleCrabMoves = 
				gameStateHandler.generatePiecesAvailableMoves(CRAB);
		HantoMoveRecord bestMovement = null;
		int bestScore = Integer.MIN_VALUE;
		int ownStateScore = 0;
		int opponentStateScore = 0;
		int sumScore;
		for(int i = 0; i < possibleButterflyMoves.size(); i++){
			HantoMoveRecord nextMove = possibleButterflyMoves.get(i);
			ownStateScore = evaluationHandler.evaluateOwnState(nextMove);
			opponentStateScore = evaluationHandler.evaluateOpponentState(nextMove);
			sumScore = ownStateScore + opponentStateScore;
			if(sumScore > bestScore){
				bestScore = sumScore;
				bestMovement = nextMove;
			}
		}
		for(int i = 0; i < possibleSparrowMoves.size(); i++){
			HantoMoveRecord nextMove = possibleSparrowMoves.get(i);
			ownStateScore = evaluationHandler.evaluateOwnState(nextMove);
			opponentStateScore = evaluationHandler.evaluateOpponentState(nextMove);
			sumScore = ownStateScore + opponentStateScore;
			if(sumScore > bestScore){
				bestScore = sumScore;
				bestMovement = nextMove;
			}
		}
		for(int i = 0; i < possibleCrabMoves.size(); i++){
			HantoMoveRecord nextMove = possibleCrabMoves.get(i);
			ownStateScore = evaluationHandler.evaluateOwnState(nextMove);
			opponentStateScore = evaluationHandler.evaluateOpponentState(nextMove);
			sumScore = ownStateScore + opponentStateScore;
			if(sumScore > bestScore){
				bestScore = sumScore;
				bestMovement = nextMove;
			}
		}
		final PlayerDecision moveDecision = new PlayerDecision(bestMovement, bestScore);
		return moveDecision;
	}
}
