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
package hanto.studentpren.gamma;


import hanto.studentpren.common.Coordinate;
import hanto.studentpren.common.HantoBoard;
import hanto.studentpren.common.HantoGameImpl;
import hanto.testutil.TestHantoGame;
import hanto.util.HantoPlayerColor;


/**
 * This is the Gamma version of Hanto Game.
 * 
 * @author Peng Ren
 * @version Jan 20, 2013
 */
public class GammaHantoGame extends HantoGameImpl implements TestHantoGame{
	
	public GammaHantoGame(){
		gameState = new HantoGammaState();
		gameRuleSet = new GammaRuleSet(gameState);
	}
	
	/**
	 * @see hanto.common.HantoGame#initialize(hanto.util.HantoPlayerColor)
	 */
	public void initialize(HantoPlayerColor firstPlayer){
		super.initialize(firstPlayer);
		((HantoGammaState) gameState).setBlueSparrowPlaced(0);
		((HantoGammaState) gameState).setRedSparrowPlaced(0);
	}


	/**
	 * Initialize a test Hanto game.
	 * 
	 * @param firstPlayer
	 *            the (color of) the player who moves first. If this is null, then the
	 *            default player, as specified by the rule set, moves first.
	 * @param configuration
	 *            the pieces and hexes where they will be for initializing this game for
	 *            testing
	 */
	public void initialize(HantoPlayerColor firstPlayer,
			hanto.testutil.HexPiece[] configuration) {
		final HantoPlayerColor playerOnMove;
		gameState.setFirstPlayer(firstPlayer);
		final HantoPlayerColor nonFirst = (firstPlayer == HantoPlayerColor.BLUE) ? 
				HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		GammaHantoPiece newPiece;
		gameState.setBoard(new HantoBoard());
		for(hanto.testutil.HexPiece hp : configuration){
			newPiece = new GammaHantoPiece(hp.getCoordinate(), hp.getPlayer(), hp.getPiece());
			gameState.getBoard().putPieceAt(newPiece, (Coordinate) newPiece.getCoordinate());
		}
		gameState.setTurnNum((gameState.getBoard().size() / 2) + 1);
		playerOnMove = ((gameState.getBoard().size() % 2) == 0) ? 
				gameState.getFirstPlayer() : nonFirst;
		gameState.setPlayerOnMove(playerOnMove);
	}
}
