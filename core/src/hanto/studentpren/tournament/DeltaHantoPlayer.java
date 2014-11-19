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
package hanto.studentpren.tournament;


import hanto.common.HantoException;
import hanto.studentpren.delta.DeltaHantoGame;
import hanto.studentpren.delta.HantoDeltaState;
import hanto.studentpren.tournament.common.EvaluationHandler;
import hanto.studentpren.tournament.common.GameStateHandler;
import hanto.studentpren.tournament.common.PlayerMoveStrategy;
import hanto.studentpren.tournament.playermovestrategy.FirstMoveStrategy;
import hanto.studentpren.tournament.playermovestrategy.GeneralMoveStrategy;
import hanto.studentpren.tournament.playermovestrategy.SecondMoveStrategy;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoPlayerColor;

/**
 * This is the AI player to play the delta version of hanto game.
 * 
 * @author Peng Ren
 * @version Feb 24, 2013
 */
public class DeltaHantoPlayer implements HantoGamePlayer{

	private final HantoPlayerColor playerColor;
	private final boolean isFirstMove;
	private final DeltaHantoGame deltaGame;
	private final HantoDeltaState gameState;
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
	public DeltaHantoPlayer(HantoPlayerColor playerColor, boolean isFirstMove){
		this.playerColor = playerColor;
		this.isFirstMove = isFirstMove;
		deltaGame = new DeltaHantoGame();
		gameState = deltaGame.getGameState();
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
				deltaGame.makeMove(bestMove.getPiece(), bestMove.getFrom(), bestMove.getTo());
			} catch (HantoException e) {
				e.printStackTrace();
			}
		}
		else{
			try {
				deltaGame.makeMove(opponentsMove.getPiece(), 
						opponentsMove.getFrom(), opponentsMove.getTo());
			} catch (HantoException e) {
				e.printStackTrace();
			}
			if(gameState.getTurnNum() == 1 && gameState.getBoard().size() == 1){
				moveStrategy = firstMoveStrategy;
				bestMove = moveStrategy.makeBestDecision();
				try {
					deltaGame.makeMove(bestMove.getPiece(), bestMove.getFrom(), bestMove.getTo());
				} catch (HantoException e) {
					e.printStackTrace();
				}
			}
			else if(gameState.getTurnNum() == 2){
				moveStrategy = secondMoveStrategy;
				bestMove = moveStrategy.makeBestDecision();
				try {
					deltaGame.makeMove(bestMove.getPiece(), bestMove.getFrom(), bestMove.getTo());
				} catch (HantoException e) {
					e.printStackTrace();
				}
			}
			else{
				moveStrategy = generalMoveStrategy;
				bestMove = moveStrategy.makeBestDecision();
				try {
					deltaGame.makeMove(bestMove.getPiece(), bestMove.getFrom(), bestMove.getTo());
				} catch (HantoException e) {
					e.printStackTrace();
				}
			}
		}
		return bestMove;
	}

	}
