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

import java.util.List;

import hanto.studentpren.common.Coordinate;
import hanto.studentpren.common.HantoBoard;
import hanto.studentpren.delta.DeltaHantoPiece;
import hanto.studentpren.delta.HantoDeltaState;
import hanto.studentpren.delta.movestrategy.FlyStrategy;
import hanto.studentpren.delta.movestrategy.WalkStrategy;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoPlayerColor;
import static hanto.util.HantoPieceType.BUTTERFLY;
import static hanto.util.HantoPieceType.SPARROW;
import static hanto.util.HantoPieceType.CRAB;
import static hanto.studentpren.tournament.common.EvaluationValue.*;

/**
 * This class specifies the general rules for evaluate the player's
 * scores according the move or placement made.
 * 
 * @author Peng Ren
 * @version Feb 26, 2013
 */
public class EvaluationHandler{
	
	private final HantoPlayerColor playerColor;
	private final HantoDeltaState gameState;
	private final GameStateHandler gameStateHandler;
	
	/**
	 * The constructor for the EvaluationHandler class
	 * 
	 * @param playerColor the color of AI player
	 * @param gameState the state of the current delta hanto game
	 */
	public EvaluationHandler(HantoPlayerColor playerColor, HantoDeltaState gameState){
		this.playerColor = playerColor;
		this.gameState = gameState;
		gameStateHandler = new GameStateHandler(playerColor, gameState);
	}
	
	/**
	 * This method evaluate the player's score based on its own pieces'
	 * situation.
	 * 
	 * @param nextMove the intended move that the player to made
	 * @return a integer indicating the score, the higher the better
	 */
	public int evaluateOwnState(HantoMoveRecord nextMove){
		int ownButterflyScore = evaluateOwnButterflyScore();
		int ownSparrowScore = evaluateOwnSparrowScore();
		int ownCrabScore = evaluateOwnCrabScore();
		final int currentScore = ownButterflyScore + ownSparrowScore + ownCrabScore;
		int bestScore = currentScore;
		final int afterMoveScore;
		doMove(nextMove);
		ownButterflyScore = evaluateOwnButterflyScore();
		ownSparrowScore = evaluateOwnSparrowScore();
		ownCrabScore = evaluateOwnCrabScore();
		afterMoveScore = ownButterflyScore + ownSparrowScore + ownCrabScore;
		if(afterMoveScore > bestScore){
			bestScore = afterMoveScore;
		}
		undoMove(nextMove);
		return bestScore;
	}
	
	/**
	 * This method evaluate the player's score based on its opponent pieces'
	 * situation.
	 * 
	 * @param nextMove the intended move that the player to made
	 * @return a integer indicating the score, the higher the better
	 */
	public int evaluateOpponentState(HantoMoveRecord nextMove){
		int opponentButterflyScore = evaluateOpponentButterflyScore();
		int opponentSparrowScore = evaluateOpponentSparrowScore();
		int opponentCrabScore = evaluateOpponentCrabScore();
		final int currentScore = opponentButterflyScore + opponentSparrowScore + opponentCrabScore;
		int bestScore = currentScore;
		final int afterMoveScore;
		doMove(nextMove);
		opponentButterflyScore = evaluateOpponentButterflyScore();
		opponentSparrowScore = evaluateOpponentSparrowScore();
		opponentCrabScore = evaluateOpponentCrabScore();
		afterMoveScore = opponentButterflyScore + opponentSparrowScore + opponentCrabScore;
		if(afterMoveScore > bestScore){
			bestScore = afterMoveScore;
		}
		undoMove(nextMove);
		return bestScore;
	}
	
	/**
	 * This method does the placement or movement of the nextMove.
	 * 
	 * @param nextMove the placement or movement the player want to do
	 */
	private void doMove(HantoMoveRecord nextMove){
		final DeltaHantoPiece newPiece = 
				new DeltaHantoPiece(nextMove.getTo(), playerColor, nextMove.getPiece());
		final HantoBoard board = gameState.getBoard();
		if(nextMove.getFrom() == null){
			board.putPieceAt(newPiece, Coordinate.guarrenteedCoordinate(nextMove.getTo()));
		}
		else{
			board.movePiece(Coordinate.guarrenteedCoordinate(nextMove.getFrom()), 
					Coordinate.guarrenteedCoordinate(nextMove.getTo()));
		}
	}
	
	/**
	 * This method undoes the placement or movement of the nextMove.
	 * 
	 * @param nextMove the placement or movement the player want to do
	 */
	private void undoMove(HantoMoveRecord nextMove){
		final HantoBoard board = gameState.getBoard();
		if(nextMove.getFrom() == null){
			final DeltaHantoPiece piece = 
					(DeltaHantoPiece) board.getPieceAt
					(Coordinate.guarrenteedCoordinate(nextMove.getTo()));
			board.remove(piece);
		}
		else{
			board.movePiece(Coordinate.guarrenteedCoordinate(nextMove.getTo()), 
					Coordinate.guarrenteedCoordinate(nextMove.getFrom()));
		}
	}
	
	/**
	 * Evaluate the own butterfly score according to the current game state.
	 * 
	 * @return an integer, the higher the value, the less dangerous of own butterfly
	 */
	private int evaluateOwnButterflyScore(){
		int ownButterflyScore;
		int neighborNum = 0;
		int movability = 0;
		final List<DeltaHantoPiece> ownPieces = gameStateHandler.getOwnPiecesOnBoard();
		DeltaHantoPiece butterfly = null;
		for(DeltaHantoPiece piece : ownPieces){
			if(piece.getPiece() == BUTTERFLY){
				piece.setPieceMoveStrategy(new WalkStrategy(gameState));
				butterfly = piece;
			}
		}
		if(butterfly != null){
			neighborNum = countNeighbors(butterfly);
			movability = countMovabiltyForWalk(butterfly);
		}
		if(movability >= 3){
			ownButterflyScore = neighborNum * OWN_BUTTERFLY_NEIGHBORS.getScore() + 
					movability * OWN_BUTTERFLY_MOVABILITY.getScore();
		}
		else{
			ownButterflyScore = neighborNum * OWN_BUTTERFLY_NEIGHBORS.getScore() + 
					movability * OWN_BUTTERFLY_MOVABILITY.getScore() - 100;
		}
		return ownButterflyScore;
	}
	
	/**
	 * Evaluate the own sparrow score according to the current game state.
	 * 
	 * @return an integer, the higher the value, the less dangerous of own sparrow
	 */
	private int evaluateOwnSparrowScore(){
		final int ownSparrowScore;
		int ownSparrowNum = 0;
		int movability = 0;
		final List<DeltaHantoPiece> ownPieces = gameStateHandler.getOwnPiecesOnBoard();
		for(DeltaHantoPiece piece : ownPieces){
			if(piece.getPiece() == SPARROW){
				piece.setPieceMoveStrategy(new FlyStrategy(gameState));
				ownSparrowNum++;
				movability =+ countMovabiltyForFly(piece);
			}
		}
		ownSparrowScore = ownSparrowNum * OWN_SPARROW_NUM.getScore() +
				movability * OWN_SPARROW_MOVABILITY.getScore();
		return ownSparrowScore;
	}
	
	/**
	 * Evaluate the own crab score according to the current game state.
	 * 
	 * @return an integer, the higher the value, the less dangerous of own crab
	 */
	private int evaluateOwnCrabScore(){
		final int ownCrabScore;
		int ownCrabNum = 0;
		int movability = 0;
		final List<DeltaHantoPiece> ownPieces = gameStateHandler.getOwnPiecesOnBoard();
		for(DeltaHantoPiece piece : ownPieces){
			if(piece.getPiece() == CRAB){
				piece.setPieceMoveStrategy(new WalkStrategy(gameState));
				ownCrabNum++;
				movability =+ countMovabiltyForWalk(piece);
			}
		}
		ownCrabScore = ownCrabNum * OWN_CRAB_NUM.getScore() +
				movability * OWN_CRAB_MOVABILITY.getScore();
		return ownCrabScore;
	}
	
	/**
	 * Evaluate the oppoent butterfly score according to the current game state.
	 * 
	 * @return an integer, the higher the value, the more dangerous of opponent butterfly
	 */
	private int evaluateOpponentButterflyScore(){
		final int opponentButterflyScore;
		int neighborNum = 0;
		int movability = 0;
		final List<DeltaHantoPiece> opponentPieces = gameStateHandler.getOpponentPiecesOnBoard();
		DeltaHantoPiece butterfly = null;
		for(DeltaHantoPiece piece : opponentPieces){
			if(piece.getPiece() == BUTTERFLY){
				piece.setPieceMoveStrategy(new WalkStrategy(gameState));
				butterfly = piece;
			}
		}
		if(butterfly != null){
			neighborNum = countNeighbors(butterfly);
			movability = countMovabiltyForWalk(butterfly);
		}
		opponentButterflyScore = neighborNum * OPPONENT_BUTTERFLY_NEIGHBORS.getScore() + 
				movability * OPPONENT_BUTTERFLY_MOVABILITY.getScore();
		return opponentButterflyScore;
	}
	
	/**
	 * Evaluate the opponent sparrow score according to the current game state.
	 * 
	 * @return an integer, the higher the value, the more dangerous of opponent sparrow
	 */
	private int evaluateOpponentSparrowScore(){
		final int opponentSparrowScore;
		int movability = 0;
		final List<DeltaHantoPiece> opponentPieces = gameStateHandler.getOpponentPiecesOnBoard();
		for(DeltaHantoPiece piece : opponentPieces){
			if(piece.getPiece() == SPARROW){
				piece.setPieceMoveStrategy(new FlyStrategy(gameState));
				movability =+ countMovabiltyForFly(piece);
			}
		}
		opponentSparrowScore = movability * OPPONENT_SPARROW_MOVABILITY.getScore();
		return opponentSparrowScore;
	}
	
	/**
	 * Evaluate the opponent crab score according to the current game state.
	 * 
	 * @return an integer, the higher the value, the more dangerous of opponent crab
	 */
	private int evaluateOpponentCrabScore(){
		final int opponentCrabScore;
		int movability = 0;
		final List<DeltaHantoPiece> opponentPieces = gameStateHandler.getOpponentPiecesOnBoard();
		for(DeltaHantoPiece piece : opponentPieces){
			if(piece.getPiece() == CRAB){
				piece.setPieceMoveStrategy(new WalkStrategy(gameState));
				movability =+ countMovabiltyForWalk(piece);
			}
		}
		opponentCrabScore = movability * OPPONENT_CRAB_MOVABILITY.getScore();
		return opponentCrabScore;
	}
	
	/**
	 * This is a helper function to count the neighbors pieces adjacent to a given piece
	 * 
	 * @param piece the piece given
	 * @return integer indicating how many neighbors
	 */
	private int countNeighbors(DeltaHantoPiece piece){
		int neighborNum = 0;
		final Coordinate[] neighborCoords = 
				Coordinate.guarrenteedCoordinate(piece.getCoordinate()).getNeighbors();
		for(Coordinate c : neighborCoords){
			if(gameState.getBoard().getPieceAt(c) != null){
				neighborNum++;
			}
		}
		return neighborNum;
	}
	
	/**
	 * This is a helper function to count the movability for a walk driven piece
	 * 
	 * @param piece the delta hanto piece
	 * @return integer indicating the number of direction blocked
	 */
	private int countMovabiltyForWalk(DeltaHantoPiece piece){
		int movability = 0;
		final Coordinate[] neighborCoords = 
				Coordinate.guarrenteedCoordinate(piece.getCoordinate()).getNeighbors();
		for(Coordinate c : neighborCoords){
			if(gameState.getBoard().getPieceAt(c) == null 
					&& piece.getMoveStrategy().canIMove
					(Coordinate.guarrenteedCoordinate(piece.getCoordinate()), c)){
				movability++;
			}
		}
		return movability;
	}
	
	/**
	 * This is a helper function to count the movability for a fly driven piece
	 * 
	 * @param piece the delta hanto piece
	 * @return integer indicating the number of direction blocked
	 */
	private int countMovabiltyForFly(DeltaHantoPiece piece){
		int movability = 0;
		final List<Coordinate> allCoordsValid = 
				gameStateHandler.getAdjacentCoordinatesToPiecesOnBoard();
		for(int i = 0; i < allCoordsValid.size(); i++){
			if(piece.getMoveStrategy().canIMove
					(Coordinate.guarrenteedCoordinate(piece.getCoordinate()), 
							allCoordsValid.get(i))){
				movability++;
			}
		}
		return movability;
	}
}
