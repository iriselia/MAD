package com.mygdx.hanto.implementation.core.movestrategy;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.mygdx.hanto.implementation.common.Coordinate;
import com.mygdx.hanto.implementation.common.HantoPiece;
import com.mygdx.hanto.implementation.common.PieceMoveStrategy;
import com.mygdx.hanto.implementation.common.PieceMoveStrategyImpl;
import com.mygdx.hanto.implementation.core.HantoStateDevelopment;

public class TrapStrategy extends PieceMoveStrategyImpl implements PieceMoveStrategy{
		
	/**
	 * Constructor for the trapStratgy that import the game state of the game
	 * @param gameState the current state of hanto game
	 */
	public TrapStrategy(HantoStateDevelopment gameState){
		this.gameState = gameState;
	}

	@Override
	public boolean canIMove(Coordinate from, Coordinate to) {
		final boolean result;
		final List<Coordinate> jointNeighbors = new ArrayList<Coordinate>();
		if(!from.isAdjacentTo(to)){
			result = false;
		}
		else{
			final Coordinate[] fromNeighbors = from.getNeighbors();
			final Coordinate[] toNeighbors = to.getNeighbors();
			final Deque<HantoPiece> jointPiece1;
			final Deque<HantoPiece> jointPiece2;
			for(Coordinate coord1 : fromNeighbors){
				for(Coordinate coord2 : toNeighbors){
					if(coord1.equals(coord2)){
						jointNeighbors.add(coord1);
					}
				}
			}
			jointPiece1 = gameState.getBoard().getPieceAt(jointNeighbors.get(0));
			jointPiece2 = gameState.getBoard().getPieceAt(jointNeighbors.get(1));
			if(jointPiece1 != null && jointPiece2 != null){
				final int currentStackSize = gameState.getBoard().getPieceAt(from).size();
				final Deque<HantoPiece> toStack = gameState.getBoard().getPieceAt(to);
				final int oneStackSize = jointPiece1.size();
				final int anotherStackSize = jointPiece2.size();
				if(toStack == null && oneStackSize > 0 && oneStackSize > 0){
					result = false;
				}
				else if(currentStackSize < oneStackSize && currentStackSize < anotherStackSize){
					result = false;
				}
				else{
					result = ifConnectedAfterMove(from, to);
				}
			}
			else{
				result = ifConnectedAfterMove(from, to);
			}
		}
		return result;
	}
}
