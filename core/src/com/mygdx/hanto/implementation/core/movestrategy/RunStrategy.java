package com.mygdx.hanto.implementation.core.movestrategy;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mygdx.hanto.implementation.common.Coordinate;
import com.mygdx.hanto.implementation.common.HantoBoard;
import com.mygdx.hanto.implementation.common.HantoPiece;
import com.mygdx.hanto.implementation.common.PieceMoveStrategy;
import com.mygdx.hanto.implementation.core.HantoStateDevelopment;

public class RunStrategy implements PieceMoveStrategy{
	
	private final HantoStateDevelopment gameState;
	
	/**
	 * Constructor for the runStratgy that import the game state of the game
	 * @param gameState the current state of hanto game
	 */
	public RunStrategy(HantoStateDevelopment gameState){
		this.gameState = gameState;
	}

	@Override
	public boolean canIMove(Coordinate from, Coordinate to) {
		Map<Coordinate, Boolean> ifMarked = new HashMap<Coordinate, Boolean>();
		return canIMoveHelper(from, to, ifMarked, 0);
	}
	
	private boolean canIMoveHelper(Coordinate from, Coordinate to, Map<Coordinate, Boolean> ifMarked, int stepNum){
		if(stepNum == 4){
			return false;
		}
		final Coordinate[] fromNeighbors = from.getNeighbors();
		for(Coordinate neighbor : fromNeighbors){
			if(gameState.getBoard().getPieceAt(neighbor) == null && !ifMarked.get(neighbor) && canIWalk(from, neighbor)){
				ifMarked.put(neighbor, true);
				if(neighbor == to && stepNum == 3){
					return true;
				}
				return canIMoveHelper(neighbor, to, ifMarked, stepNum + 1);
			}
		}
		return false;
	}
	
	
	public boolean canIWalk(Coordinate from, Coordinate to) {
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
				result = false;
			}
			else{
				result = ifConnectedAfterMove(from, to);
			}
		}
		return result;
	}
	
	private boolean ifConnectedAfterMove(Coordinate from, Coordinate to){
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
