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
package com.mygdx.hanto.implementation.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.mygdx.hanto.util.HantoCoordinate;
import com.mygdx.hanto.util.HantoPlayerColor;



/**
 * This is the board for all the the Hanto Game. This is basically a
 * map to map the HantoPice with their coordinate on the current Hanto
 * Game.
 * 
 */
public class HantoBoard {

	/**
	 * The data structure to store hexpieces on board.
	 */
	private final Map<HantoCoordinate, Deque<HantoPiece>> board;

	/**
	 * Default constructor. Initializes an empty board.
	 */
	public HantoBoard(){
		board = new HashMap<HantoCoordinate, Deque<HantoPiece>>();
	}

	/**
	 * Places a piece at the specified coordinate. It also updates the specified
	 * pieces coordinate to match the coordinate specified.
	 * 
	 * @param piece The piece to place on the board
	 * @param at The coordinate where the piece will be placed
	 */
	public void putPieceAt(HantoPiece piece, Coordinate at){
		final Deque<HantoPiece> stack;
		if(board.get(at) == null){
			stack = new LinkedList<HantoPiece>();
		}
		else{
			stack = board.get(at);
		}			
		stack.addFirst(piece);
		board.put(at, stack);
	}

	/**
	 * Moves a piece from one position to another on the board. No checks are made
	 * for whether the move is valid or not. This is the responsibility of the client.
	 * 
	 * @param from The source hex
	 * @param to The destination hex
	 */
	public void movePiece(Coordinate from, Coordinate to){
		final HantoPiece fromPiece;
		fromPiece = board.get(from).removeFirst();
		fromPiece.setCoordinate(to);
		final Deque<HantoPiece> destStack;
		if(board.get(to) == null){
			destStack = new LinkedList<HantoPiece>();
		}
		else{
			destStack = board.get(to);
		}
		destStack.addFirst(fromPiece);
		board.put(to, destStack);
		if(board.get(from).isEmpty()){
			board.remove(from);
		}
	}

	/**
	 * Get the Hanto Piece at the given coordinate.
	 * 
	 * @param coordinate A Hanto coordinate
	 * @return returns the piece at the specified coordinate 
	 *                 or null if there is no piece at that coordinate
	 */
	public Deque<HantoPiece> getPieceAt(Coordinate coordinate){
		final Deque<HantoPiece> hantoPieces;
		hantoPieces = board.get(coordinate);
		return hantoPieces;
	}

	/**
	 * This function is used to tell if the given coordinate has a 
	 * HantoPiece adjacent to it.
	 * 
	 * @param coordinate A HantoCoordinate
	 * @return true if there is a piece on the board adjacent to the 
	 *         specified coordinate, false otherwise.
	 */
	public boolean hasPieceAdjacentTo(Coordinate coordinate){
		final Coordinate[] neighbors = coordinate.getNeighbors();
		boolean result = false;
		for(Coordinate c : neighbors){
			if(board.get(c) != null){
				result = true;
			}
		}
		return result;
	}

	/**
	 * This function is to tell if the hanto board is empty.
	 * 
	 * @return true if the board is empty, false otherwise.
	 */
	public boolean isEmpty(){
		boolean isEmpty = false;
		isEmpty = board.isEmpty();
		return isEmpty;
	}

	/**
	 * Reset the board to an empty board.
	 */
	public void reset(){
		board.clear();
	}
	/**
	 * This function is used to tell if the given Hantopiece is 
	 * surrounded by the other Hantopiece.
	 * 
	 * @param piece A Hantopiece given
	 * @return true if the piece is surrounded, false otherwise
	 */
	public boolean isSourrounded(HantoPiece piece){
		boolean isSourrounded = true;
		final Coordinate[] neighbors = ((Coordinate) 
				piece.getCoordinate()).getNeighbors();
		for(Coordinate c : neighbors){
			if(board.get(c) == null){
				isSourrounded = false;
			}
		}
		return isSourrounded;
	}

	/**
	 * This function is used to tell if all the pieces on board
	 * is connected.
	 * 
	 * @return true if all pieces on the board are connected.
	 */
	public boolean isConnected(){
		boolean connected = true;
		if (!isEmpty()) {
			final List<HantoCoordinate> visitedPieces = new ArrayList<HantoCoordinate>();
			final List<HantoCoordinate> unvisitedPieces = new ArrayList<HantoCoordinate>();
			unvisitedPieces.addAll(board.keySet());
			visitedPieces.add(unvisitedPieces.remove(0));
			while (!visitedPieces.isEmpty()) {
				final HantoCoordinate visited = visitedPieces.remove(0);
				final HantoCoordinate[] neighbors = ((Coordinate) visited).getNeighbors();
				for (HantoCoordinate c : neighbors) {
					if (unvisitedPieces.contains(c)) {
						final int ix = unvisitedPieces.indexOf(c);
						visitedPieces.add(unvisitedPieces.remove(ix));
					}
				}
			}
			if (!unvisitedPieces.isEmpty()) {
				connected = false;
			}
		}
		return connected;
	}

	/**
	 * This function is used to describe the board configuration.
	 * 
	 * @return A string describing the board configuration.
	 */
	public String getPrintableBoard(){
		final List<String> board = new ArrayList<String>();
		String hexPiece;
		String player;
		String piece;
		String coordinate;
		final HantoPiece[] arrayPieces = getPieceArray();
		for(HantoPiece hp : arrayPieces){
			player = (hp.getPlayer() == HantoPlayerColor.BLUE) ?
					"Blue " : "Red ";
			piece = hp.getPiece().toString(); 
			coordinate = ((Coordinate) hp.getCoordinate()).toString();
			hexPiece = player + piece + coordinate + "\n";
			board.add(hexPiece);
		}
		return board.toString();
	}
	
	/**
	 * This functions returns the total size of the hanto board
	 * including the coordinate being used before but no longer 
	 * containing pieces.
	 * 
	 * @return the total size of the hanto board
	 */
	public int size(){
		return board.size();
	}
	
	/**
	 * This function puts all the hex piece on the game board currently
	 * into an array.
	 * 
	 * @return an array of hexpiece that on board now
	 */
	public HantoPiece[] getPieceArray(){
		final Collection<Deque<HantoPiece>> allStacks = board.values();
		final List<HantoPiece> allPieces = new ArrayList<HantoPiece>();
		for(Deque<HantoPiece> stack : allStacks){
			allPieces.addAll(stack);
		}
		final HantoPiece[] arrayPieces = allPieces.toArray(new HantoPiece[0]);
		return arrayPieces;
	}
	
	/**
	 * This function removes a piece from the game board
	 * 
	 * @param piece the piece to be removed
	 */
	public void remove(HantoPiece piece){
		final Coordinate key = Coordinate.guarrenteedCoordinate(piece.getCoordinate());
		board.remove(key);
	}
}
