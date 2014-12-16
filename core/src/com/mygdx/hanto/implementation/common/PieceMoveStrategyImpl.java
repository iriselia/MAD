package com.mygdx.hanto.implementation.common;

public abstract class PieceMoveStrategyImpl implements PieceMoveStrategy{
	
	protected HantoState gameState;

	public boolean ifConnectedAfterMove(Coordinate from, Coordinate to){
		final boolean result;
		final HantoBoard virtualBoard = gameState.getBoard();
		virtualBoard.movePiece(from, to);
		if(virtualBoard.isConnected()){
			result = true;
		}
		else{
			result = false;
		}
		virtualBoard.movePiece(to, from);
		return result;
	}
}
