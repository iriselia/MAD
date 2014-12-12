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
package com.mygdx.hanto.implementation.core;

import com.mygdx.hanto.implementation.common.HantoState;
import com.mygdx.hanto.util.HantoPieceType;
import com.mygdx.hanto.util.HantoPlayerColor;


/**
 * This class describes the delta game state.
 * 
 */
public class HantoStateDevelopment extends HantoState{
	int blueSparrowPlaced;
	int redSparrowPlaced;
	int blueCrabPlaced;
	int redCrabPlaced;
	int blueCranePlaced;
	int redCranePlaced;
	int blueHorsePlaced;
	int redHorsePlaced;
	int blueDovePlaced;
	int redDovePlaced;
	
	public int getBlueCranePlaced() {
		return blueCranePlaced;
	}
	public void setBlueCranePlaced(int blueCranePlaced) {
		this.blueCranePlaced = blueCranePlaced;
	}
	public int getRedCranePlaced() {
		return redCranePlaced;
	}
	public void setRedCranePlaced(int redCranePlaced) {
		this.redCranePlaced = redCranePlaced;
	}
	public int getBlueHorsePlaced() {
		return blueHorsePlaced;
	}
	public void setBlueHorsePlaced(int blueHorsePlaced) {
		this.blueHorsePlaced = blueHorsePlaced;
	}
	public int getRedHorsePlaced() {
		return redHorsePlaced;
	}
	public void setRedHorsePlaced(int redHorsePlaced) {
		this.redHorsePlaced = redHorsePlaced;
	}
	public int getBlueDovePlaced() {
		return blueDovePlaced;
	}
	public void setBlueDovePlaced(int blueDovePlaced) {
		this.blueDovePlaced = blueDovePlaced;
	}
	public int getRedDovePlaced() {
		return redDovePlaced;
	}
	public void setRedDovePlaced(int redDovePlaced) {
		this.redDovePlaced = redDovePlaced;
	}
	/**
	 * @param blueSparrowPlaced the blueSparrowPlaced to set
	 */
	public void setBlueSparrowPlaced(int blueSparrowPlaced) {
		this.blueSparrowPlaced = blueSparrowPlaced;
	}
	/**
	 * @param redSparrowPlaced the redSparrowPlaced to set
	 */
	public void setRedSparrowPlaced(int redSparrowPlaced) {
		this.redSparrowPlaced = redSparrowPlaced;
	}
	/**
	 * @param blueCrabPlaced the blueCrabPlaced to set
	 */
	public void setBlueCrabPlaced(int blueCrabPlaced) {
		this.blueCrabPlaced = blueCrabPlaced;
	}
	/**
	 * @param redCrabPlaced the redCrabPlaced to set
	 */
	public void setRedCrabPlaced(int redCrabPlaced) {
		this.redCrabPlaced = redCrabPlaced;
	}
	/**
	 * Get the number of the given piece type placed for the given player
	 * @param player the player of the type
	 * @param pieceType the type number to be gotten
	 * @return the number of that piece type belongs to the player being placed
	 */
	public int getNumberTypeForPlayer(HantoPieceType pieceType, HantoPlayerColor player){
		int typeNum = 0;
		if(player == HantoPlayerColor.BLUE){
			if(pieceType == HantoPieceType.BUTTERFLY){
				typeNum = getBlueButterflyPlaced();
			}
			if(pieceType == HantoPieceType.CRAB){
				typeNum = blueCrabPlaced;
			}
			if(pieceType == HantoPieceType.SPARROW){
				typeNum = blueSparrowPlaced;
			}
			if(pieceType == HantoPieceType.CRANE){
				typeNum = blueCranePlaced;
			}
			if(pieceType == HantoPieceType.HORSE){
				typeNum = blueHorsePlaced;
			}
			if(pieceType == HantoPieceType.DOVE){
				typeNum = blueDovePlaced;
			}
		}
		else{
			if(pieceType == HantoPieceType.BUTTERFLY){
				typeNum = getRedButterflyPlaced();
			}
			if(pieceType == HantoPieceType.CRAB){
				typeNum = redCrabPlaced;
			}
			if(pieceType == HantoPieceType.SPARROW){
				typeNum = redSparrowPlaced;
			}
			if(pieceType == HantoPieceType.CRANE){
				typeNum = redCranePlaced;
			}
			if(pieceType == HantoPieceType.HORSE){
				typeNum = redHorsePlaced;
			}
			if(pieceType == HantoPieceType.DOVE){
				typeNum = redDovePlaced;
			}
		}
		return typeNum;
	}
}
