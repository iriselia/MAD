package com.mygdx.game.ai;

import com.mygdx.game.ai.moveStrategy.FirstMoveStrategy;
import com.mygdx.game.ai.moveStrategy.GeneralMoveStrategy;
import com.mygdx.game.ai.moveStrategy.SecondMoveStrategy;
import com.mygdx.game.util.GameStateHandler;
import com.mygdx.hanto.common.HantoException;
import com.mygdx.hanto.implementation.core.HantoGameDevelopment;
import com.mygdx.hanto.implementation.core.HantoStateDevelopment;
import com.mygdx.hanto.util.HantoMoveRecord;
import com.mygdx.hanto.util.HantoPlayerColor;

/**
 * This is the AI player to play the delta version of hanto game.
 * 
 */
public class HantoAIPlayer implements HantoGamePlayer{

	private final HantoPlayerColor playerColor;
	private final boolean isFirstMove;
	private final HantoGameDevelopment hantoGame;
	private final HantoStateDevelopment gameState;
	private final GameStateHandler gameStateHandler;
	private final EvaluationHandler evaluationHandler;
	private PlayerMoveStrategy moveStrategy;
	private final PlayerMoveStrategy firstMoveStrategy;
	private final PlayerMoveStrategy secondMoveStrategy;
	private final PlayerMoveStrategy generalMoveStrategy;

	/**
	 * This is the constructor for DeltaHantoPlayer
	 * 
	 * @param playerColor This indicates the player's color
	 * @param isFirstMove A boolean that, if it is true, indicates the player moves first
	 */
	public HantoAIPlayer(HantoPlayerColor playerColor, boolean isFirstMove){
		this.playerColor = playerColor;
		this.isFirstMove = isFirstMove;
		hantoGame = new HantoGameDevelopment();
		gameState = hantoGame.getGameState();
		gameStateHandler = new GameStateHandler(playerColor, gameState);
		evaluationHandler = new EvaluationHandler(playerColor, gameState);
		firstMoveStrategy = new FirstMoveStrategy(gameState);
		secondMoveStrategy = new SecondMoveStrategy(gameStateHandler, evaluationHandler);
		generalMoveStrategy = new GeneralMoveStrategy(gameStateHandler, evaluationHandler);
		moveStrategy = null;
	}

	/**
	 * @see hanto.tournament.HantoGamePlayer#makeMove(hanto.tournament.HantoMoveRecord)
	 */
	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		HantoMoveRecord bestMove = null;
		if(opponentsMove == null){
			moveStrategy = firstMoveStrategy;
			bestMove = moveStrategy.makeBestDecision();
			try {
				hantoGame.makeMove(bestMove.getPiece(), bestMove.getFrom(), bestMove.getTo());
			} catch (HantoException e) {
				e.printStackTrace();
			}
		}
		else{
			try {
				hantoGame.makeMove(opponentsMove.getPiece(), 
						opponentsMove.getFrom(), opponentsMove.getTo());
			} catch (HantoException e) {
				e.printStackTrace();
			}
			if(gameState.getTurnNum() == 1 && gameState.getBoard().size() == 1){
				moveStrategy = firstMoveStrategy;
				bestMove = moveStrategy.makeBestDecision();
				try {
					hantoGame.makeMove(bestMove.getPiece(), bestMove.getFrom(), bestMove.getTo());
				} catch (HantoException e) {
					e.printStackTrace();
				}
			}
			else if(gameState.getTurnNum() == 2){
				moveStrategy = secondMoveStrategy;
				bestMove = moveStrategy.makeBestDecision();
				try {
					hantoGame.makeMove(bestMove.getPiece(), bestMove.getFrom(), bestMove.getTo());
				} catch (HantoException e) {
					e.printStackTrace();
				}
			}
			else{
				moveStrategy = generalMoveStrategy;
				bestMove = moveStrategy.makeBestDecision();
				try {
					hantoGame.makeMove(bestMove.getPiece(), bestMove.getFrom(), bestMove.getTo());
				} catch (HantoException e) {
					e.printStackTrace();
				}
			}
		}
		bestMove.setColor(playerColor);
		return bestMove;
	}

	}

