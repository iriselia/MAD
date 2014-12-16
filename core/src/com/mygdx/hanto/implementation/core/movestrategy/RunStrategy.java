package com.mygdx.hanto.implementation.core.movestrategy;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.mygdx.hanto.implementation.common.Coordinate;
import com.mygdx.hanto.implementation.common.HantoPiece;
import com.mygdx.hanto.implementation.common.PieceMoveStrategy;
import com.mygdx.hanto.implementation.common.PieceMoveStrategyImpl;
import com.mygdx.hanto.implementation.core.HantoPieceDevelopment;
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
		final boolean result;
		final Map<Coordinate, Boolean> ifMarked = new HashMap<Coordinate, Boolean>();
		final HantoPiece originPiece = gameState.getBoard().getPieceAt(from).peekFirst();
		gameState.getBoard().remove(originPiece);
		result = canIMoveHelper(from, to, ifMarked);
		gameState.getBoard().putPieceAt(originPiece, from);
		return result;
	}

	private boolean canIMoveHelper(Coordinate from, Coordinate to, Map<Coordinate, Boolean> ifMarked){
		final Deque<Coordinate> queue = new LinkedList<Coordinate>();
		int level = 0;
		Coordinate levelPointer = null;
		ifMarked.put(from, true);
		queue.addLast(from);
		while(!queue.isEmpty()){
			final Coordinate current = queue.removeFirst();
			if(current.equals(levelPointer)){
				level++;
			}
			if(level == 3 && current.equals(to)){
				return true;
			}
			else if(level == 4){
				return false;
			}
			else{
				final Coordinate[] currentNeighbors = current.getNeighbors();
				for(Coordinate neighbor : currentNeighbors){
					if(gameState.getBoard().getPieceAt(neighbor) == null && !ifMarked.containsKey(neighbor) && canIWalk(current, neighbor)){
						if(levelPointer == null || current.equals(levelPointer)){
							levelPointer = neighbor;
						}
						queue.addLast(neighbor);
					}
				}
				ifMarked.put(current, true);
			}
		}
		return false;
	}


	public boolean canIWalk(Coordinate from, Coordinate to) {
		boolean ifPutPiece = false;
		final HantoPiece fakePiece = new HantoPieceDevelopment(from, gameState.getFirstPlayer(), null);
		if(gameState.getBoard().getPieceAt(from) == null){
			ifPutPiece = true;
			gameState.getBoard().putPieceAt(fakePiece, from);
		}
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
			if(ifPutPiece){
				gameState.getBoard().remove(fakePiece);
			}
		}
		return result;
	}

	public boolean ifConnectedAfterMove(Coordinate from, Coordinate to){
		final boolean result;
		gameState.getBoard().movePiece(from, to);
		if(gameState.getBoard().isConnected()){
			result = true;
		}
		else{
			result = false;
		}
		gameState.getBoard().movePiece(to, from);
		return result;
	}

}
