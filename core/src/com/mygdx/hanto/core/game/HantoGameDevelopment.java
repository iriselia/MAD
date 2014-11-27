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


import com.mygdx.hanto.core.common.HantoGameImpl;
import com.mygdx.hanto.util.HantoPlayerColor;


/**
 * This is the Gamma version of Hanto Game.
 * 
 * @author Peng Ren
 * @version Jan 20, 2013
 */
public class HantoGameDevelopment extends HantoGameImpl{
	
	public HantoGameDevelopment(){
		gameState = new HantoGameDevelopmentState();
		gameRuleSet = new HantoGameDevelopmentRuleSet(gameState);
	}
	
	/**
	 * @see com.mygdx.hanto.common.HantoGame#initialize(com.mygdx.hanto.util.HantoPlayerColor)
	 */
	public void initialize(HantoPlayerColor firstPlayer){
		super.initialize(firstPlayer);
		((HantoGameDevelopmentState) gameState).setBlueSparrowPlaced(0);
		((HantoGameDevelopmentState) gameState).setRedSparrowPlaced(0);
	}
}

