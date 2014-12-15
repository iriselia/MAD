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
import com.mygdx.hanto.implementation.common.PieceMoveStrategyImpl;
import com.mygdx.hanto.implementation.core.HantoStateDevelopment;

public class RunStrategy extends PieceMoveStrategyImpl implements PieceMoveStrategy{
		
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
		final HantoBoard virtualBoard = gameState.getBoard().clone();
		return canIMoveHelper(from, to, ifMarked, 0, virtualBoard);
	}
	
	private boolean canIMoveHelper(Coordinate from, Coordinate to, Map<Coordinate, Boolean> ifMarked, int stepNum, HantoBoard virtualBoard){
		//if(stepNum == 4){
		//	return false;
		//}
		final Coordinate[] fromNeighbors = from.getNeighbors();
		for(Coordinate neighbor : fromNeighbors){
			if(virtualBoard.getPieceAt(neighbor) == null && !ifMarked.containsKey(neighbor) && canIWalk(from, neighbor, virtualBoard)){
				ifMarked.put(neighbor, true);
				if(stepNum == 3){
					if(neighbor == to){
						return true;
					}
					else{
						return false;
					}
				}
				HantoBoard nextBoard = virtualBoard.clone();
				nextBoard.movePiece(from, neighbor);
				return canIMoveHelper(neighbor, to, ifMarked, stepNum + 1, nextBoard);
			}
		}
		return false;
	}
	
	
	public boolean canIWalk(Coordinate from, Coordinate to, HantoBoard virtualBoard) {
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
			jointPiece1 = virtualBoard.getPieceAt(jointNeighbors.get(0));
			jointPiece2 = virtualBoard.getPieceAt(jointNeighbors.get(1));
			if(jointPiece1 != null && jointPiece2 != null){
				result = false;
			}
			else{
				result = ifConnectedAfterMove(from, to, virtualBoard);
			}
		}
		return result;
	}

	public boolean ifConnectedAfterMove(Coordinate from, Coordinate to, HantoBoard virtualBoard){
		final boolean result;
		System.out.println("Virtual:");
		System.out.println(virtualBoard.getPrintableBoard());
		System.out.println("Game Board:");
		System.out.println(gameState.getBoard().getPrintableBoard());
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
