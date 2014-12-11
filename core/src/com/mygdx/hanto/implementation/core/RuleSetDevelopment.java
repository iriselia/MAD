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
package com.mygdx.hanto.implementation.core;


import java.util.Deque;

import com.mygdx.hanto.common.HantoException;
import com.mygdx.hanto.implementation.common.Coordinate;
import com.mygdx.hanto.implementation.common.HantoPiece;
import com.mygdx.hanto.implementation.common.HantoRuleSet;
import com.mygdx.hanto.implementation.common.HantoState;
import com.mygdx.hanto.implementation.common.PieceMoveStrategy;
import com.mygdx.hanto.implementation.core.movestrategy.FlyStrategy;
import com.mygdx.hanto.implementation.core.movestrategy.WalkStrategy;
import com.mygdx.hanto.util.HantoPieceType;
import com.mygdx.hanto.util.HantoPlayerColor;
import com.mygdx.hanto.util.MoveResult;


/**
 * This is the rule set for the version of delta hanto game.
 * 
 */
public class RuleSetDevelopment extends HantoRuleSet{

	private final HantoStateDevelopment gameState;

	/**
	 * This is the default constructor for the gammaruleset class.
	 * 
	 * @param gameState the state of the gamma hanto game 
	 */
	public RuleSetDevelopment(HantoState gameState){
		state = gameState;
		this.gameState = (HantoStateDevelopment) gameState;
	}

	/**
	 * This is the helper function that makes the move for the player.
	 * 
	 * @param pieceType
	 *            the type of piece making the move
	 * @param from
	 *            the source of the move (null -> placing the piece)
	 * @param to
	 *            the move target hex
	 */
	public void doMakeTheMove(HantoPieceType pieceType, Coordinate from, Coordinate to){
		final HantoPieceDevelopment newPiece;
		final PieceMoveStrategy moveStrategy;
		moveStrategy = getMoveStrategy(pieceType);
		newPiece = new HantoPieceDevelopment(to, gameState.getPlayerOnMove(), pieceType, moveStrategy);
		if(from != null){
			gameState.getBoard().movePiece(from, to);
		}
		else{
			gameState.getBoard().putPieceAt(newPiece, to);
			addPiecePlacedNum(gameState.getPlayerOnMove(), pieceType);
		}
	}

	/**
	 * This is a helper function to determine the move result after 
	 * the player made a movement.
	 * 
	 * @return the result of the move
	 */
	public MoveResult determineMoveResult(){
		final MoveResult result;
		result = super.determineMoveResult();
		return result;
	}

	/**
	 * This function checks if the player would like to resign in this round 
	 * or have to make a resign
	 * @param pieceType
	 *            the type of piece making the move
	 * @param from
	 *            the source of the move 
	 * @param to
	 *            the move target hex
	 * @return blue wins if red resigned
	 * 		   rad wins if blue resigned
	 * 		   null if no player resigned
	 */
	public MoveResult checkResign(HantoPieceType pieceType, Coordinate from, Coordinate to){
		MoveResult result;
		if(pieceType != null || from != null || to != null){
			result = null;
		}
		else{
			if(gameState.getPlayerOnMove() == HantoPlayerColor.BLUE){
				result = MoveResult.RED_WINS;
			}
			else{
				result = MoveResult.BLUE_WINS;
			}
		}
		return result;
	}

	/**
	 * If the piece is being placed, make sure that there is, in fact, a piece of
	 * that type off the board for the player.
	 * @param pieceType The type of the piece
	 * @throws HantoException
	 */
	protected void ensurePieceIsAvailable(HantoPieceType pieceType, final Coordinate from)
			throws HantoException
			{
		super.ensurePieceIsAvailable(pieceType, from);
		if (from == null) {
			if(gameState.getPlayerOnMove() == HantoPlayerColor.BLUE 
					&& pieceType == HantoPieceType.SPARROW){
				if(gameState.blueSparrowPlaced == 4){
					throw new HantoException("You don't have a Sparrow left");
				}
			}
			else if(gameState.getPlayerOnMove() == HantoPlayerColor.RED 
					&& pieceType == HantoPieceType.SPARROW){
				if(gameState.redSparrowPlaced == 4){
					throw new HantoException("You don't have a Sparrow left");
				}
			}
			if(gameState.getPlayerOnMove() == HantoPlayerColor.BLUE 
					&& pieceType == HantoPieceType.CRAB){
				if(gameState.blueCrabPlaced == 4){
					throw new HantoException("You don't have a Crab left");
				}
			}
			else if(gameState.getPlayerOnMove() == HantoPlayerColor.RED 
					&& pieceType == HantoPieceType.CRAB){
				if(gameState.redCrabPlaced == 4){
					throw new HantoException("You don't have a Crab left");
				}
			}
		}
			}

	/**
	 * If this is a move, rather than placing a piece, it can only be the butterfly
	 * and it can only be one square. Otherwise, the move must be placing a piece.
	 * @param pieceType type of the piece being moved
	 * @param from source hex
	 * @param to destination hex
	 * @throws HantoException
	 */
	protected void checkMoveIsLegal(HantoPieceType pieceType, final Coordinate from,
			final Coordinate to) throws HantoException
			{
		super.checkMoveIsLegal(pieceType, from, to);
		if(from == null){
			if (!gameState.getBoard().isEmpty() && gameState.getTurnNum() != 1
					&& !isOnlySamePieceAdjacentTo(to)){
				throw new HantoException("You must place the piece next to only your own pieces.");
			}
		}
		if(to == null){
			throw new HantoException("You must make a movement.");
		}
		if (from != null) {
			if(pieceType != HantoPieceType.BUTTERFLY){
				if(!isButterFlyOnBoard()){
					throw new HantoException
					("You cannot make a move until the butterfly being placed.");
				}
				else{
					checkPieceValidMove(pieceType, from, to);
				}
			}
			else{
				checkPieceValidMove(pieceType, from, to);
			}
		}
			}


	/**
	 * This is the helper function to keep track the players' butterfly
	 * and the sparrows on board.
	 * 
	 * @param player the player playing the game
	 * @param piece the piece to be placed
	 */
	protected void addPiecePlacedNum(HantoPlayerColor player, HantoPieceType piece){
		super.addPiecePlacedNum(player, piece);
		if(piece == HantoPieceType.SPARROW){
			if(player == HantoPlayerColor.BLUE){
				gameState.blueSparrowPlaced++;
			}
			else{
				gameState.redSparrowPlaced++;
			}
		}
		if(piece == HantoPieceType.CRAB){
			if(player == HantoPlayerColor.BLUE){
				gameState.blueCrabPlaced++;
			}
			else{
				gameState.redCrabPlaced++;
			}
		}
	}

	/**
	 * This is a helper function to tell if the given piece type is
	 * valid in this version of hanto game.
	 * 
	 * @param pieceType a kind of hanto piece type
	 * @return true if the piece type can be used in this game
	 * @throws HantoException if the piece type is not valid
	 */
	protected void ensurePieceIsValid(HantoPieceType pieceType) throws HantoException{
		if(pieceType != HantoPieceType.BUTTERFLY && 
				pieceType != HantoPieceType.SPARROW && pieceType != HantoPieceType.CRAB
				&& pieceType != HantoPieceType.CRANE && pieceType != HantoPieceType.DOVE
				&& pieceType != HantoPieceType.HORSE){
			throw new HantoException("The piece type cannot be used in this game.");
		}
	}

	/**
	 * This function is used to tell if the given coordinate has a 
	 * HantoPiece of same color adjacent to it.
	 * 
	 * @param coordinate A HantoCoordinate
	 * @return true if there is a piece on the board of the same color 
	 *         adjacent to the specified coordinate, false otherwise.
	 */
	private boolean isOnlySamePieceAdjacentTo(Coordinate coordinate){
		boolean result = false;
		final Coordinate[] neighbors = coordinate.getNeighbors();
		for(Coordinate c : neighbors){
			Deque<HantoPiece> pieces = gameState.getBoard().getPieceAt(c);
			if(pieces != null && !pieces.isEmpty()){
				final HantoPiece piece = pieces.peekFirst();
				if(piece != null && piece.getPlayer() != gameState.getPlayerOnMove()){
					result = false;
					break;
				}
				else if (piece != null && piece.getPlayer() == gameState.getPlayerOnMove()){
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * This function is a helper function to get the appropriate move strategy for the 
	 * piece.
	 * 
	 * @param pieceType the type of the piece to use move strategy
	 * @return the appropriate move strategy according the piece type
	 */
	private PieceMoveStrategy getMoveStrategy(HantoPieceType pieceType){
		PieceMoveStrategy moveStrategy = null;
		if(pieceType == HantoPieceType.BUTTERFLY || pieceType == HantoPieceType.CRAB){
			moveStrategy = new WalkStrategy(gameState);
		}
		else if(pieceType == HantoPieceType.SPARROW){
			moveStrategy = new FlyStrategy(gameState);
		}
		return moveStrategy;
	}

	/**
	 * This is a helper function to tell if the given piece is right type, with the right
	 * color and have the right move strategy.
	 * 
	 * @param pieceType type of the piece to be check
	 * @param from source hex
	 * @param to destination hex
	 * @throws HantoException
	 */
	private void checkPieceValidMove(HantoPieceType pieceType, Coordinate from, Coordinate to) 
			throws HantoException{
		final Deque<HantoPiece> pieces = gameState.getBoard().getPieceAt(from);
		final HantoPieceDevelopment piece = (HantoPieceDevelopment) pieces.peekFirst();
		if (piece.getPiece() != pieceType) {
			throw new HantoException("There is no" + pieceType.toString() + "at the source hex");
		}
		if (piece.getPlayer() != gameState.getPlayerOnMove()) {
			throw new HantoException
			("Move is from the wrong color" + pieceType.toString() + "'s hex");
		}
		if (!piece.getMoveStrategy().canIMove(from, to)) {
			throw new HantoException
			("Your movement is not comform the rule for" + pieceType.toString() + ".");
		}
	}
}

