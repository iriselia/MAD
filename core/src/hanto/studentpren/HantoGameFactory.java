/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentpren;

import hanto.common.HantoGame;
import hanto.studentpren.alpha.AlphaHantoGame;
import hanto.studentpren.delta.DeltaHantoGame;
import hanto.studentpren.gamma.GammaHantoGame;
import hanto.util.HantoGameID;

/**
 * This is a singleton class that provides a factory to create an instance of any version
 * of a Hanto game.
 * 
 * @author gpollice, pren
 * @version Feb 5, 2013
 */
public class HantoGameFactory
{
	private static final HantoGameFactory instance = new HantoGameFactory();
	
	/**
	 * Default private descriptor.
	 */
	private HantoGameFactory()
	{
		// Empty, but the private constructor is necessary for the singleton.
	}

	/**
	 * @return the instance
	 */
	public static HantoGameFactory getInstance()
	{
		return instance;
	}
	
	/**
	 * Factory method that returns the appropriately configured Hanto game.
	 * @param gameId the version desired.
	 * @return the makemove result 
	 */
	public HantoGame makeHantoGame(HantoGameID gameId) {
		HantoGame game = null;
		switch (gameId) {
			case ALPHA_HANTO:
				game =  new AlphaHantoGame();
				break;
			case GAMMA_HANTO:
				game = new GammaHantoGame();
				break;
			case DELTA_HANTO:
				game = new DeltaHantoGame();
				break;
			default:
				game = null;
				break;
		}
		return game;
	}
}
