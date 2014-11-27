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
package com.mygdx.hanto.core.game;

import com.mygdx.hanto.common.HantoException;
import com.mygdx.hanto.core.common.Coordinate;
import com.mygdx.hanto.core.common.HantoRuleSet;
import com.mygdx.hanto.core.common.HantoState;
import com.mygdx.hanto.util.HantoPieceType;
import com.mygdx.hanto.util.HantoPlayerColor;
import com.mygdx.hanto.util.MoveResult;


/**
 * This is the rule set for the version of gamma hanto game.
 * 
 * @author Peng Ren
 * @version Feb 3, 2013
 */
public class HantoGameDevelopmentRuleSet extends HantoRuleSet{

	private final HantoGameDevelopmentState gameState;

	/**
	 * This is the default constructor for the gammaruleset class.
	 * 
	 * @param gameState the state of the gamma hanto game 
	 */
	public HantoGameDevelopmentRuleSet(HantoState gameState){
		state = gameState;
		this.gameState = (HantoGameDevelopmentState) gameState;
	}

	/**
	 * Perform any checks that should be made before actually making the move.
	 * This helper function is used to check if the parameters given to the makeMove
	 * function are valid to make a valid movement for the game. Also, the function 
	 * gives all the reason why the parameters passed are not valid for exception throwing.
	 * 
	 * @param pieceType the hanto game piece
	 * @param from the coordinate where to put piece
	 * @param to the coordinate to put the piece
	 * @throws HantoException if any problems are identified
	 */
	public void performPreMoveChecks(HantoPieceType pieceType, 
			Coordinate from, Coordinate to) throws HantoException{
		ensureGameIsNotOver();
		ensureButterflyPlacedByFourthMove(pieceType);
		ensurePieceIsValid(pieceType);
		ensurePieceIsAvailable(pieceType, from);
		checkMoveIsLegal(pieceType, from, to);
		checkValidDestination(to);
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
		if(from == null){
			state.getBoard().putPieceAt(
				new HantoGameDevelopmentPiece(to, gameState.getPlayerOnMove(), pieceType), to);
			addPiecePlacedNum(gameState.getPlayerOnMove(), pieceType);
		}
		else{
			gameState.getBoard().movePiece(from, to);
		}
	}

	/**
	 * This is a helper function to determine the move result after 
	 * the player made a movement.
	 * 
	 * @return the result of the move
	 */
	public MoveResult determineMoveResult(){
		MoveResult result;
		result = super.determineMoveResult();
		if(state.getTurnNum() > ((HantoGameDevelopmentState) state).getMAX_TURNS()){
			result = (result == MoveResult.OK) ? MoveResult.DRAW : result;
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
			if(state.getPlayerOnMove() == HantoPlayerColor.BLUE 
					&& pieceType == HantoPieceType.SPARROW){
				if(gameState.blueSparrowPlaced == 5){
					throw new HantoException("You don't have a Sparrow left");
				}
			}
			else if(state.getPlayerOnMove() == HantoPlayerColor.RED 
					&& pieceType == HantoPieceType.SPARROW){
				if(gameState.redSparrowPlaced == 5){
					throw new HantoException("You don't have a Sparrow left");
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
		if(to == null){
			throw new HantoException("You must make a movement.");
		}
		if (pieceType == HantoPieceType.BUTTERFLY && from != null) {
			final HantoGameDevelopmentPiece piece = (HantoGameDevelopmentPiece) gameState.getBoard().getPieceAt(from);
			if (piece.getPiece() != HantoPieceType.BUTTERFLY) {
				throw new HantoException("There is no Butterfly at the source hex");
			}
			if (piece.getPlayer() != gameState.getPlayerOnMove()) {
				throw new HantoException("Move is from the wrong color Butterfly's hex");
			}
			if (!from.isAdjacentTo(to)) {
				throw new HantoException("Butterflies can only move one hex");
			}
		}
		if (pieceType == HantoPieceType.SPARROW && from != null) {
			throw new HantoException("You may not move a Sparrow in this version of Hanto");
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
				pieceType != HantoPieceType.SPARROW){
			throw new HantoException("The piece type cannot be used in this game.");
		}
	}

}
