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
package com.mygdx.hanto.core.common;
import com.mygdx.hanto.common.HantoException;
import com.mygdx.hanto.util.HantoPieceType;
import com.mygdx.hanto.util.HantoPlayerColor;
import com.mygdx.hanto.util.MoveResult;


/**
 * This is the rule set for all the hanto games. All the rule set
 * for specific hanto game should implements this interface. 
 * 
 * @author Peng Ren
 * @version Feb 3, 2013
 */
public abstract class HantoRuleSet {
	protected HantoState state;

	/**
	 * Perform any checks that should be made before actually making the move.
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
		//TODO make the move inside the respective rule set of the hanto games
	}

	/**
	 * Perform any checks that should be made after actually making the move.
	 * 
	 * @throws HantoException
	 */
	public void performPostMoveChecks() throws HantoException{
		if (!state.getBoard().isConnected()) {
			throw new HantoException("The move left the pieces disconnected");
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
		final int resultCondition = checkEnding();
		if(resultCondition == 0){
			result = MoveResult.DRAW;
			state.setGameOver(true);
		}
		else if(resultCondition == 1){
			result = MoveResult.BLUE_WINS;
			state.setGameOver(true);
		}
		else if(resultCondition == 2){
			result = MoveResult.RED_WINS;
			state.setGameOver(true);
		}
		else{
			result = MoveResult.OK;
		}
		switchTurn();
		return result;
	}
	/**
	 * If the game is over, then a player cannot make a move.
	 * @throws HantoException
	 */
	protected void ensureGameIsNotOver() throws HantoException
	{
		if (state.isGameOver()) {
			throw new HantoException("You cannot make movement in a already ended game.");
		}
	}

	/**
	 * Make sure that the butterfly has been placed by the fourth move.
	 * @param pieceType The type of piece on move
	 * @throws HantoException
	 */
	protected void ensureButterflyPlacedByFourthMove(HantoPieceType pieceType) throws HantoException
	{
		if(state.getTurnNum() == 4 && !isButterFlyOnBoard() 
				&& pieceType != HantoPieceType.BUTTERFLY){
			throw new HantoException("You must put butterfly on board in this turn.");
		}
	}

	/**
	 * Make sure that the piece that's being moved is actually part of the game.
	 * 
	 * @param pieceType the type of the piece that's moving
	 * @throws HantoException
	 */
	protected void ensurePieceIsValid(HantoPieceType pieceType) throws HantoException
	{
		// TODO: encapsulate the needed information in the state
	}

	/**
	 * If the piece is being placed, make sure that there is, in fact, a piece of
	 * that type off the board for the player.
	 * @param pieceType the piece type to be moved
	 * @param from The coordinate from the piece from
	 * @throws HantoException
	 */
	protected void ensurePieceIsAvailable(HantoPieceType pieceType, final Coordinate from)
			throws HantoException
			{
		if (from == null) {
			if(state.getPlayerOnMove() == HantoPlayerColor.BLUE 
					&& pieceType == HantoPieceType.BUTTERFLY){
				if(state.getBlueButterflyPlaced() == 1){
					throw new HantoException("BLUE cannot place two butterflies");
				}
			}
			else if(state.getPlayerOnMove() == HantoPlayerColor.RED 
					&& pieceType == HantoPieceType.BUTTERFLY){
				if(state.getRedButterflyPlaced() == 1){
					throw new HantoException("RED cannot place two butterflies");
				}
			}
		}
			}

	/**
	 * If this is the first move, make sure it's at (0, 0), otherwise it must be
	 * adjacent to a piece. For this version, the destination must be empty.
	 * @param to the destination coordinate
	 * @throws HantoException
	 */
	protected void checkValidDestination(final Coordinate to) throws HantoException
	{
		if (state.getBoard().isEmpty()) {
			if (to.getX() != 0 || to.getY() != 0) {
				throw new HantoException("First move must be at (0, 0)");
			}
		} else {
			if (!state.getBoard().hasPieceAdjacentTo(to)) {
				throw new HantoException(
						"Piece was not placed adjacent to another piece");
			}
		}
		if (state.getBoard().getPieceAt(to) != null) {
			throw new HantoException("You cannot put a piece on top of another piece");
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
		if (from != null) {
			final HantoPiece piece = state.getBoard().getPieceAt(from);
			if (piece == null) {
				throw new HantoException("You cannot move from an unoccupied hex");
			}
		}
			}


	/**
	 * This helper function is used to tell if the butterfly of the player of 
	 * current turn is already put on gameState.getBoard().
	 * 
	 * @return true if the current turn player already put butterfly on gameState.getBoard()
	 */
	protected boolean isButterFlyOnBoard(){
		boolean result = false;
		final HantoPiece[] arrayPieces = state.getBoard().getPieceArray();
		for(HantoPiece hp : arrayPieces){
			if(hp != null && hp.getPlayer() == state.getPlayerOnMove() &&
					hp.getPiece() == HantoPieceType.BUTTERFLY){
				result = true;
			}
		}
		return result;
	}

	/**
	 * This is the helper function to keep track the players' butterfly
	 * and the sparrows on board.
	 * 
	 * @param player the player playing the game
	 * @param piece the piece to be placed
	 */
	protected void addPiecePlacedNum(HantoPlayerColor player, HantoPieceType piece){
		if(piece == HantoPieceType.BUTTERFLY){
			if(player == HantoPlayerColor.BLUE){
				state.setBlueButterflyPlaced(
						state.getBlueButterflyPlaced() + 1);
			}
			else{
				state.setRedButterflyPlaced(
						state.getRedButterflyPlaced() + 1);
			}
		}
	}

	/**
	 * Helper function to switch the player at the beginning of 
	 * the turn and this function also add the turn number at the
	 * end of each turn.
	 */
	protected void switchTurn(){
		final int initialTurnNum;
		initialTurnNum = state.getTurnNum();
		if(state.getPlayerOnMove() == HantoPlayerColor.BLUE){
			state.setPlayerOnMove(HantoPlayerColor.RED);
			if(state.getFirstPlayer() == HantoPlayerColor.RED){
				state.setTurnNum(initialTurnNum + 1);
			}
		}
		else{
			state.setPlayerOnMove(HantoPlayerColor.BLUE);
			if(state.getFirstPlayer() == HantoPlayerColor.BLUE){
				state.setTurnNum(initialTurnNum + 1);
			}
		}
	}

	/**
	 * This is a helper function to check if the game ends according to different
	 * conditions.
	 * 
	 * @return 0 if the game ends in draw
	 *         1 if the blue player wins
	 *         2 if the red player wins
	 *         3 if the game doesn't end
	 */
	private int checkEnding(){
		int resultNum;
		boolean blueWon = false;
		boolean redWon = false;
		final HantoPiece[] arrayPieces = state.getBoard().getPieceArray();
		for(HantoPiece hp : arrayPieces){
			blueWon = (isRedEnclosed(hp)) ? true : blueWon;
			redWon = (isBlueEnclosed(hp)) ? true : redWon;
		}
		final boolean bothWon = blueWon && redWon;
		if(blueWon && !redWon){
			resultNum = 1; //blue won
		}
		else if(redWon && !blueWon){
			resultNum = 2; //red won
		}
		else if(bothWon){
			resultNum = 0; //game ends in draw
		}
		else{
			resultNum = 3; //not ending
		}
		return resultNum;
	}

	/**
	 * This is a helper function to tell if a piece is the blue and
	 * if it is enclosed by other pieces.
	 *  
	 * @param piece a gammahanto game piece
	 * @return true if blue butterfly is enclosed
	 */
	private boolean isBlueEnclosed(HantoPiece piece){
		boolean enclosed = false;
		if(piece != null){
			final HantoPieceType type = piece.getPiece();
			final HantoPlayerColor player = piece.getPlayer();
			if(player == HantoPlayerColor.BLUE && type == HantoPieceType.BUTTERFLY){
				enclosed = state.getBoard().isSourrounded(piece);
			}
		}
		else{
			enclosed = false;
		}
		return enclosed;
	}

	/**
	 * This is a helper function to tell if a piece is the red and
	 * if it is enclosed by other pieces.
	 *  
	 * @param piece a gammahanto game piece
	 * @return true if red butterfly is enclosed
	 */
	private boolean isRedEnclosed(HantoPiece piece){
		boolean enclosed = false;
		if(piece != null){
			final HantoPieceType type = piece.getPiece();
			final HantoPlayerColor player = piece.getPlayer();
			if(player == HantoPlayerColor.RED && type == HantoPieceType.BUTTERFLY){
				enclosed = state.getBoard().isSourrounded(piece);
			}
		}
		else{
			enclosed = false;
		}
		return enclosed;
	}
}
