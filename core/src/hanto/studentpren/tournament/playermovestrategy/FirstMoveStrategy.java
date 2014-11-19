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
package hanto.studentpren.tournament.playermovestrategy;

import static hanto.util.HantoPieceType.CRAB;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


import hanto.studentpren.common.Coordinate;
import hanto.studentpren.delta.HantoDeltaState;
import hanto.studentpren.tournament.common.PlayerDecision;
import hanto.studentpren.tournament.common.PlayerMoveStrategy;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoCoordinate;

/**
 * This class specifies the players movement on the first turn.
 * 
 * @author Peng Ren
 * @version Feb 25, 2013
 */
public class FirstMoveStrategy implements PlayerMoveStrategy{
	
	private final HantoDeltaState gameState;
	
	
	/**
	 * This is the constructor for the FirstMoveStrategy.
	 * 
	 * @param gameState the current state of the delta hanto game
	 */
	public FirstMoveStrategy(HantoDeltaState gameState){
		this.gameState = gameState;
	}
	
	/**
	 * This method is to make a best Move or place for the hanto game player from
	 * all the possible movement and placements it can do.
	 * 
	 * @return bestDecesion the best movement or placement the player could do 
	 */
	public HantoMoveRecord makeBestDecision(){
		final PlayerDecision firstPlaceDecision = makePlacement();
		return firstPlaceDecision.getPlayerDecision();
	}

	/**
	 * @see hanto.studentpren.tournament.common.PlayerMoveStrategy#makePlacement()
	 */
	@Override
	public PlayerDecision makePlacement() {
		HantoMoveRecord placement;
		final HantoCoordinate initialCoord = new Coordinate(0, 0);
		if(gameState.getBoard().isEmpty()){
			placement = new HantoMoveRecord(CRAB, null, initialCoord);
		}
		else{
			final Coordinate[] initialNeighbors = 
					Coordinate.guarrenteedCoordinate(initialCoord).getNeighbors();
			final List<Coordinate> poosibleCoords = Arrays.asList(initialNeighbors);
			final Random randomGenerator = new Random();
			final int randInt = randomGenerator.nextInt(6);
			final Coordinate randomCoord = poosibleCoords.get(randInt);
			final HantoMoveRecord newPlace = new HantoMoveRecord(CRAB, null, randomCoord);
			placement = newPlace;
		}
		final PlayerDecision placeDecision = new PlayerDecision(placement, 0);
		return placeDecision;
	}

	/**
	 * @see hanto.studentpren.tournament.common.PlayerMoveStrategy#makeMovement()
	 */
	@Override
	public PlayerDecision makeMovement() {
		//the first turn the player cannot make a movement, so the method is trivial
		return null;
	}

}
