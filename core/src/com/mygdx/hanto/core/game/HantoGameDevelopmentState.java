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
package com.mygdx.hanto.core.game;

import com.mygdx.hanto.core.common.HantoState;


/**
 * This class describes the gamma game state.
 * 
 * @author Peng Ren
 * @version Feb 5, 2013
 */
public class HantoGameDevelopmentState extends HantoState{
	final int MAX_TURNS = 10;
	int blueSparrowPlaced;
	int redSparrowPlaced;
	
	/**
	 * @return the mAX_TURNS
	 */
	public int getMAX_TURNS() {
		return MAX_TURNS;
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
}
