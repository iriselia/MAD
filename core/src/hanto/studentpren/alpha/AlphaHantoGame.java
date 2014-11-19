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
package hanto.studentpren.alpha;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentpren.InternalHantoGame;
import hanto.studentpren.common.Coordinate;
import hanto.studentpren.gamma.GammaHantoPiece;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * Description
 * 
 * @author Peng Ren
 * @version Jan 19, 2013
 */
public class AlphaHantoGame implements HantoGame, InternalHantoGame{


	/**
	 * The initial board coordinate for the game.
	 */
	private HantoCoordinate initialCoordinate;
	/**
	 * The current turn number;
	 */
	private int turn;
	/**
	 * The final position of the red butterfly.
	 */
	private HantoCoordinate redCoord;


	/**
	 * Default constructor for the class that is to
	 * set up the initial coordinate to (0, 0) so that
	 * players are ready to play.
	 * 
	 */
	public AlphaHantoGame(){
		turn = 0;
		initialCoordinate = new Coordinate(0, 0);
		redCoord = null;
	}

	/**
	 * Get the initialCoordiante.
	 * 
	 * @return initial Coordinate.
	 */
	public HantoCoordinate getInitialCoordinate(){
		return initialCoordinate;
	}

	/**
	 * Get the red butterfly's coordinate.
	 * 
	 * @return red butterfly's coordinate
	 */
	public HantoCoordinate getRedCoord(){
		return redCoord;
	}

	/**
	 * Get the number of turn.
	 * 
	 * @return number of turn.
	 */
	public int getTurn(){
		return turn;
	}

	/**
	 * @see hanto.common.HantoGame#initialize(hanto.util.HantoPlayerColor)
	 */
	@Override
	public void initialize(HantoPlayerColor firstPlayer) {
		turn = 0;
		redCoord = null;
		initialCoordinate = new Coordinate(0, 0);
	}

	/**
	 * @see hanto.common.HantoGame#makeMove(hanto.util.HantoPieceType,
	 *                                      hanto.util.HantoCoordinate, hanto.util.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		MoveResult result;
		final Coordinate coordTo;
		coordTo = Coordinate.guarrenteedCoordinate(to);
		if(turn == 0 && from == null && coordTo.equals(initialCoordinate)
				&& pieceType == HantoPieceType.BUTTERFLY){
			result = MoveResult.OK;
		}
		else if(turn == 1 && from == null && pieceType == HantoPieceType.BUTTERFLY &&
				(coordTo.equals(new Coordinate(0, 1)) ||
						coordTo.equals(new Coordinate(1, 0)) ||
						coordTo.equals(new Coordinate(1, -1)) ||
						coordTo.equals(new Coordinate(0, -1)) ||
						coordTo.equals(new Coordinate(-1, 0)) ||
						coordTo.equals(new Coordinate(-1, 1)))){
			result = MoveResult.DRAW;
		}
		else{
			throw new HantoException("Error");
		}
		turn++;
		redCoord = to;
		return result;
	}

	/**
	 * When the method is called during the game, it will print the state
	 * of hex pieces on board and is understandable.
	 * 
	 * @return a series of string describing the state of hex pieces
	 */
	@Override
	public String getPrintableBoard() {
		String board = "No meaningful board.";
		if(turn == 1){
			board = "Blue Butterfly (0, 0)";
		}
		else if(turn == 2){
			board = "Blue Butterfly (0, 0)\nRed Butterfly " + 
					"(" + redCoord.getX() + ", " + redCoord.getY() + ")";
		}
		return board;
	}

	/**
	 * @see hanto.studentpren.InternalHantoGame#getPieceAt(int, int)
	 */
	@Override
	public GammaHantoPiece getPieceAt(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

}
