package com.mygdx.game.ai.moveStrategy;

import java.util.List;

import static com.mygdx.hanto.util.HantoPieceType.*;
import com.mygdx.game.ai.EvaluationHandler;
import com.mygdx.game.ai.PlayerDecision;
import com.mygdx.game.ai.PlayerMoveStrategy;
import com.mygdx.game.util.GameStateHandler;
import com.mygdx.hanto.util.HantoMoveRecord;

/**
 * This class specifies the players movement on the second turn.
 * 
 * @author Peng Ren
 */
public class SecondMoveStrategy implements PlayerMoveStrategy{
	
	private final GameStateHandler gameStateHandler;
	private final EvaluationHandler evaluationHandler;
	
	/**
	 * This is the constructor for the FourthMoveStrategy.
	 * 
	 * @param gameStateHandler a handler to handle the game state situations
	 * @param evaluationHandler a handler to handle the evaluation situation
	 */
	public SecondMoveStrategy(GameStateHandler gameStateHandler, 
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
		final PlayerDecision bestPlaceDecision = makePlacement();
		return bestPlaceDecision.getPlayerDecision();
	}

	/**
	 * @see hanto.studentpren.tournament.common.PlayerMoveStrategy#makePlacement()
	 */
	@Override
	public PlayerDecision makePlacement() {
		final List<HantoMoveRecord> possiblePlaces = 
				gameStateHandler.generatePiecesAvailablePlacements(BUTTERFLY);
		HantoMoveRecord bestPlacement = null;
		int bestScore = Integer.MIN_VALUE;
		int ownStateScore = 0;
		int opponentStateScore = 0;
		int sumScore;
		for(int i = 0; i < possiblePlaces.size(); i++){
			HantoMoveRecord nextPlace = possiblePlaces.get(i);
			ownStateScore = evaluationHandler.evaluateOwnState(nextPlace);
			opponentStateScore = evaluationHandler.evaluateOpponentState(nextPlace);
			sumScore = ownStateScore + opponentStateScore;
			if(sumScore > bestScore){
				bestScore = sumScore;
				bestPlacement = nextPlace;
			}
		} //to do if butterfly equals to null
		final PlayerDecision placeDecision = new PlayerDecision(bestPlacement, bestScore);
		return placeDecision;
	}

	/**
	 * @see hanto.studentpren.tournament.common.PlayerMoveStrategy#makeMovement()
	 */
	@Override
	public PlayerDecision makeMovement() {
		// not do a movement in this turn
		return null;
	}
	

}
