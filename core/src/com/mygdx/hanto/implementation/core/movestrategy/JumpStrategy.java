package com.mygdx.hanto.implementation.core.movestrategy;

import com.mygdx.hanto.implementation.common.Coordinate;
import com.mygdx.hanto.implementation.common.HantoBoard;
import com.mygdx.hanto.implementation.common.HantoPiece;
import com.mygdx.hanto.implementation.common.PieceMoveStrategy;
import com.mygdx.hanto.implementation.core.HantoStateDevelopment;

/**
 * This class specifies the jump rule that defined in the hanto game.
 * 
 */
public class JumpStrategy implements PieceMoveStrategy {
	
	private final HantoStateDevelopment gameState;
	
	/**
	 * Constructor for the walkStratgy that import the game state of the game
	 * @param gameState the current state of Delta hanto game
	 */
	public JumpStrategy(HantoStateDevelopment gameState){
		this.gameState = gameState;
	}

	@Override
	public boolean canIMove(Coordinate from, Coordinate to) {
		// TODO Auto-generated method stub
		final boolean result;
		final HantoPiece pieceTo = gameState.getBoard().getPieceAt(to);
		if(pieceTo != null){
			result = false;
		}
		else{
			//TODO
			if (!inStraightLineAndNoEmptyHexInBetween(from, to)) {
				return false;
			} else {
				final HantoBoard virtualBoard = gameState.getBoard();
				virtualBoard.movePiece(from, to);
				if(virtualBoard.isConnected()){
					result = true;
				}
				else{
					result = false;
				}
				virtualBoard.movePiece(to, from);
			}
		}
		return result;
	}
	
	private boolean inStraightLineAndNoEmptyHexInBetween(Coordinate from, Coordinate to){
		int diff, deltaX, deltaY;
		if (alignHorizontal(from, to)){
			diff = to.getY() - from.getY();
			deltaX = diff / Math.abs(diff);
			return checkNoEmptyHexInBetween(from, to, diff, deltaX, 0);
		} else if (alignVertical(from, to)){
			diff = to.getX() - from.getX();
			deltaY = diff / Math.abs(diff);
			return checkNoEmptyHexInBetween(from, to, diff, 0, deltaY);
		} else if (alignDiagonal(from, to)){
			diff = to.getX() - from.getX();
			deltaX = diff / Math.abs(diff);
			deltaY = 0 - deltaX;
			return checkNoEmptyHexInBetween(from, to, diff, deltaX, deltaY);
		} 
		return false;
	}
	
	
	private boolean alignHorizontal(Coordinate from, Coordinate to) {
		return from.getX() == to.getX();
	}
	private boolean alignVertical(Coordinate from, Coordinate to) {
		return from.getY() == to.getY();
	}
	private boolean alignDiagonal(Coordinate from, Coordinate to) {
		return (from.getX() + to.getX()) == (from.getY() + to.getY());
	}
	
	private boolean checkNoEmptyHexInBetween(Coordinate from, Coordinate to, int diff, int deltaX, int deltaY){
		diff = to.getY() - from.getY();
		Coordinate testCoordinate = from;
		for (int i = 0; i < Math.abs(diff); i++){
			if (gameState.getBoard().getPieceAt(testCoordinate) == null) {
				return false;
			}
			testCoordinate = new Coordinate(testCoordinate.getX() + deltaX, testCoordinate.getY() + deltaY);
		}
		return true;
	}

}
