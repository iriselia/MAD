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

import com.mygdx.hanto.util.HantoCoordinate;
import com.mygdx.hanto.util.HantoPlayerColor;

/**
 * This abstract class describes the state of the Hanto Game and 
 * all the state classes of the specific hanto game should extend
 * this class.
 * 
 */
public abstract class HantoState {

	protected HantoCoordinate initialCoordinate;
	protected int turnNum;
	protected HantoPlayerColor firstPlayer;
	protected HantoPlayerColor playerOnMove;
	protected HantoBoard board;
	protected int blueButterflyPlaced;
	protected int redButterflyPlaced;
	protected boolean isGameOver;
	
	protected HantoState(){
		initialCoordinate = new Coordinate(0, 0);
		turnNum = 1;
		firstPlayer = HantoPlayerColor.BLUE;
		playerOnMove = firstPlayer;
		board = new HantoBoard();
	}
	
	/**
	 * @param initialCoordinate the initialCoordinate to set
	 */
	public void setInitialCoordinate(HantoCoordinate initialCoordinate) {
		this.initialCoordinate = initialCoordinate;
	}

	/**
	 * @return the turnNum
	 */
	public int getTurnNum() {
		return turnNum;
	}

	/**
	 * @param turnNum the turnNum to set
	 */
	public void setTurnNum(int turnNum) {
		this.turnNum = turnNum;
	}

	/**
	 * @return the firstPlayer
	 */
	public HantoPlayerColor getFirstPlayer() {
		return firstPlayer;
	}

	/**
	 * @param firstPlayer the firstPlayer to set
	 */
	public void setFirstPlayer(HantoPlayerColor firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	/**
	 * @return the playerOnMove
	 */
	public HantoPlayerColor getPlayerOnMove() {
		return playerOnMove;
	}

	/**
	 * @param playerOnMove the playerOnMove to set
	 */
	public void setPlayerOnMove(HantoPlayerColor playerOnMove) {
		this.playerOnMove = playerOnMove;
	}

	/**
	 * @return the board
	 */
	public HantoBoard getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(HantoBoard board) {
		this.board = board;
	}
	
	/**
	 * @return the blueButterflyPlaced
	 */
	public int getBlueButterflyPlaced() {
		return blueButterflyPlaced;
	}

	/**
	 * @param blueButterflyPlaced the blueButterflyPlaced to set
	 */
	public void setBlueButterflyPlaced(int blueButterflyPlaced) {
		this.blueButterflyPlaced = blueButterflyPlaced;
	}

	/**
	 * @return the redButterflyPlaced
	 */
	public int getRedButterflyPlaced() {
		return redButterflyPlaced;
	}

	/**
	 * @param redButterflyPlaced the redButterflyPlaced to set
	 */
	public void setRedButterflyPlaced(int redButterflyPlaced) {
		this.redButterflyPlaced = redButterflyPlaced;
	}

	/**
	 * @return the isGameOver
	 */
	public boolean isGameOver() {
		return isGameOver;
	}

	/**
	 * @param isGameOver the isGameOver to set
	 */
	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}
	
	
}
