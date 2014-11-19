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
package hanto.studentpren.delta;

import static hanto.util.HantoPieceType.BUTTERFLY;
import static hanto.util.HantoPieceType.CRAB;
import static hanto.util.HantoPieceType.DOVE;
import static hanto.util.HantoPieceType.SPARROW;
import static hanto.util.MoveResult.BLUE_WINS;
import static hanto.util.MoveResult.RED_WINS;
import static hanto.util.MoveResult.DRAW;
import static hanto.util.MoveResult.OK;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentpren.HantoGameFactory;
import hanto.testutil.TestHantoCoordinate;
import hanto.util.HantoGameID;
import hanto.util.HantoPlayerColor;

import org.junit.Before;
import org.junit.Test;

/**
 * This class is used to test delta Hanto game.
 * 
 * @author Peng Ren
 * @version Feb 23, 2013
 */
public class DeltaHantoGameTest {

	private final TestHantoCoordinate
	tc00 = new TestHantoCoordinate(0, 0),
	tc01 = new TestHantoCoordinate(0, 1),
	tc10 = new TestHantoCoordinate(1, 0),
	tc1_1 = new TestHantoCoordinate(1, -1),
	tc_1_1 = new TestHantoCoordinate(-1, -1),
	tc0_1 = new TestHantoCoordinate(0, -1),
	tc_10 = new TestHantoCoordinate(-1, 0),
	tc_11 = new TestHantoCoordinate(-1, 1),
	tc_12 = new TestHantoCoordinate(-1, 2),
	tc02 = new TestHantoCoordinate(0, 2),
	//tc0_2 = new TestHantoCoordinate(0, -2),
	//tc1_2 = new TestHantoCoordinate(1, -2),
	tc11 = new TestHantoCoordinate(1, 1);

private HantoGame deltaGame;
private HantoGameFactory gameFactory;


@Before
public void setup() throws HantoException
{
	gameFactory = HantoGameFactory.getInstance();
	deltaGame = gameFactory.makeHantoGame(HantoGameID.DELTA_HANTO);
}

/*
 * Game Factory tests
 */
@Test
public void generateANewAlphaHantoGame()
{
	assertNotNull(gameFactory.makeHantoGame(HantoGameID.ALPHA_HANTO));
}

@Test
public void generateANewDeltaHantoGame()
{
	assertNotNull(gameFactory.makeHantoGame(HantoGameID.DELTA_HANTO));
}

/*
 * Delta Hanto tests
 */
@Test
public void blueButterflyMovesFirst() throws HantoException
{
	assertThat(deltaGame.makeMove(BUTTERFLY, null, tc00), is(OK));
}

@Test(expected = HantoException.class)
public void placeFirstPiecesInWrongCoordinate() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc11);
}

@Test(expected = HantoException.class)
public void placePieceThatIsNotInTheGame() throws HantoException
{
	deltaGame.makeMove(DOVE, null, tc00);
}

@Test(expected = HantoException.class)
public void placeSecondPieceNotAdjacentToFirstPiece() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00);
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(3, -2));
}

@Test
public void placeValidThirdPiece() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00);
	deltaGame.makeMove(SPARROW, null, tc01);
	deltaGame.makeMove(SPARROW, null, tc0_1);
}

@Test(expected = HantoException.class)
public void placeThirdPieceNotAdjacentToSameColorPiece() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00);
	deltaGame.makeMove(SPARROW, null, tc01);
	deltaGame.makeMove(SPARROW, null, tc11);
}

@Test
public void creatADeltaHantoPieceOnlyWithTwoParameters(){
	assertNotNull(new DeltaHantoPiece(HantoPlayerColor.BLUE, BUTTERFLY));
}

@Test
public void blueWinsWithBlueMove() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); // Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); // Red
	deltaGame.makeMove(SPARROW, null, tc1_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc11); // Red
	deltaGame.makeMove(SPARROW, null, tc0_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc02); // Red
	deltaGame.makeMove(SPARROW, null, tc_10); // Blue
	deltaGame.makeMove(SPARROW, null, tc_12); // Red
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(-2, 1)); // Blue
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(2, 0)); // red
	deltaGame.makeMove(SPARROW, new TestHantoCoordinate(-2, 1), tc_11); // Blue
	deltaGame.makeMove(CRAB, null, new TestHantoCoordinate(0, 3)); // red
	assertThat(deltaGame.makeMove(SPARROW, tc1_1, tc10), is(BLUE_WINS)); // Blue
}

@Test
public void blueWinsWithRedMove() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); // Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); // Red
	deltaGame.makeMove(SPARROW, null, tc1_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc11); // Red
	deltaGame.makeMove(SPARROW, null, tc0_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc02); // Red
	deltaGame.makeMove(SPARROW, null, tc_10); // Blue
	deltaGame.makeMove(SPARROW, null, tc_12); // Red
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(-2, 1)); // Blue
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(2, 0)); // red
	deltaGame.makeMove(SPARROW, new TestHantoCoordinate(-2, 1), tc_11); // Blue
	deltaGame.makeMove(CRAB, null, new TestHantoCoordinate(1, 2)); // red
	deltaGame.makeMove(SPARROW, tc1_1, new TestHantoCoordinate(1, -2)); // Blue
	assertThat(deltaGame.makeMove(SPARROW, new TestHantoCoordinate(2, 0), tc10), is(BLUE_WINS));
}

@Test
public void redWins() throws HantoException
{
	deltaGame.initialize(HantoPlayerColor.RED);
	deltaGame.makeMove(BUTTERFLY, null, tc00); // red
	deltaGame.makeMove(BUTTERFLY, null, tc01); // blue
	deltaGame.makeMove(SPARROW, null, tc1_1); // red
	deltaGame.makeMove(SPARROW, null, tc11); // blue
	deltaGame.makeMove(SPARROW, null, tc_10); // red
	deltaGame.makeMove(SPARROW, null, tc02); // blue
	deltaGame.makeMove(SPARROW, null, tc0_1); // red
	deltaGame.makeMove(SPARROW, null, tc_12); // blue
	deltaGame.makeMove(SPARROW, tc1_1, tc10); // red
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(-2, 2)); // blue
	assertThat(deltaGame.makeMove(SPARROW, tc_10, tc_11), is(RED_WINS)); // red
}


@Test(expected = HantoException.class)
public void attemptToPlaceTwoBlueButterflies() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00);
	deltaGame.makeMove(BUTTERFLY, null, tc01);
	deltaGame.makeMove(BUTTERFLY, null, tc10);
}

@Test(expected = HantoException.class)
public void attemptToPlaceTwoRedButterflies() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00);
	deltaGame.makeMove(BUTTERFLY, null, tc01);
	deltaGame.makeMove(SPARROW, null, tc0_1);
	deltaGame.makeMove(BUTTERFLY, null, tc10);
}

@Test
public void drawBySurroundingBothButterflies() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); // Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); // Red
	deltaGame.makeMove(SPARROW, null, tc1_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc11); // Red
	deltaGame.makeMove(SPARROW, null, tc0_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc02); // Red
	deltaGame.makeMove(SPARROW, null, tc_10); // Blue
	deltaGame.makeMove(SPARROW, null, tc_12); // Red
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(-2, 1)); // Blue
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(2, 0)); // red
	deltaGame.makeMove(SPARROW, new TestHantoCoordinate(-2, 1), tc_11); // Blue
	assertThat(deltaGame.makeMove(SPARROW, new TestHantoCoordinate(2, 0), tc10), is(DRAW)); // blue
}

@Test(expected = HantoException.class)
public void blueDoesNotPlaceButterflyWithinFourMoves() throws HantoException
{
	deltaGame.makeMove(SPARROW, null, tc00); // Blue
	deltaGame.makeMove(SPARROW, null, tc01); // Red
	deltaGame.makeMove(SPARROW, null, tc_10); // Blue
	deltaGame.makeMove(SPARROW, null, tc_12); // Red
	deltaGame.makeMove(SPARROW, null, tc0_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc11); // Red
	deltaGame.makeMove(SPARROW, null, tc1_1); // Blue
}

@Test(expected = HantoException.class)
public void redDoesNotPlaceButterflyWithinFourMoves() throws HantoException
{
	deltaGame.makeMove(SPARROW, null, tc00); // Blue
	deltaGame.makeMove(SPARROW, null, tc01); // Red
	deltaGame.makeMove(SPARROW, null, tc_10); // Blue
	deltaGame.makeMove(SPARROW, null, tc_12); // Red
	deltaGame.makeMove(SPARROW, null, tc0_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc11); // Red
	deltaGame.makeMove(BUTTERFLY, null, tc1_1); // Blue
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(2, 0)); // Red
}

@Test(expected = HantoException.class)
public void placePieceOnTopOfAnother() throws HantoException
{
	deltaGame.makeMove(SPARROW, null, tc00); // Blue
	deltaGame.makeMove(SPARROW, null, tc00); // Red
}

@Test(expected = HantoException.class)
public void attemptMoveAfterGameIsOver() throws HantoException
{
	deltaGame.makeMove(SPARROW, null, tc00); // Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); // Red
	deltaGame.makeMove(BUTTERFLY, null, tc1_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc11); // Red
	deltaGame.makeMove(SPARROW, null, tc0_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc02); // Red
	deltaGame.makeMove(SPARROW, null, tc_10); // Blue
	deltaGame.makeMove(SPARROW, null, tc_12); // Red
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(-2, 1)); // Blue
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(2, 0)); // red
	deltaGame.makeMove(SPARROW, new TestHantoCoordinate(-2, 1), tc_11); // Blue
	deltaGame.makeMove(CRAB, null, new TestHantoCoordinate(0, 3)); // red
	deltaGame.makeMove(BUTTERFLY, tc1_1, tc10); // Blue wins
	deltaGame.makeMove(CRAB, null, new TestHantoCoordinate(2, 0));
}

@Test
public void printableBoardTest() throws HantoException
{
	deltaGame.initialize(HantoPlayerColor.RED);
	deltaGame.makeMove(BUTTERFLY, null, tc00); // red
	deltaGame.makeMove(BUTTERFLY, null, tc01); // blue
	deltaGame.makeMove(SPARROW, null, tc1_1); // red
	deltaGame.makeMove(SPARROW, null, tc11); // blue
	deltaGame.makeMove(SPARROW, null, tc_10); // red
	deltaGame.makeMove(SPARROW, null, tc02); // blue
	deltaGame.makeMove(SPARROW, null, tc0_1); // red
	deltaGame.makeMove(SPARROW, null, tc_12); // blue
	deltaGame.makeMove(SPARROW, tc1_1, tc10); // red
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(-2, 2)); // blue
	assertThat(deltaGame.makeMove(SPARROW, tc_10, tc_11), is(RED_WINS)); // red
	String pb = deltaGame.getPrintableBoard();
	assertNotNull(pb);
	assertTrue(pb.length() > 0);
	System.out.println(pb);
}

@Test(expected = HantoException.class)
public void moveFromUnoccupiedHex() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); // Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); // Red
	deltaGame.makeMove(BUTTERFLY, tc10, tc11); // Blue
}

@Test(expected = HantoException.class)
public void sparrowMoveBeforeBetterflyPlaced() throws HantoException
{
	deltaGame.makeMove(SPARROW, null, tc00); // Blue
	deltaGame.makeMove(SPARROW, null, tc01); // Red
	deltaGame.makeMove(SPARROW, null, tc_10); // Blue
	deltaGame.makeMove(SPARROW, null, tc_12); // Red
	deltaGame.makeMove(SPARROW, tc_10, tc_11); // Blue
}

@Test(expected = HantoException.class)
public void crabMoveBeforeBetterflyPlaced() throws HantoException
{
	deltaGame.makeMove(CRAB, null, tc00); // Blue
	deltaGame.makeMove(CRAB, null, tc01); // Red
	deltaGame.makeMove(CRAB, null, tc_10); // Blue
	deltaGame.makeMove(CRAB, null, tc_12); // Red
	deltaGame.makeMove(CRAB, tc_10, tc_11); // Blue
}

@Test(expected = HantoException.class)
public void moveFromWrongButterflyHex() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); // Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); // Red
	deltaGame.makeMove(BUTTERFLY, tc01, tc_11); // Blue
}

@Test
public void winByMovingButterfly() throws HantoException
{
	deltaGame.makeMove(SPARROW, null, tc00); // Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); // Red
	deltaGame.makeMove(BUTTERFLY, null, tc1_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc11); // Red
	deltaGame.makeMove(SPARROW, null, tc0_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc02); // Red
	deltaGame.makeMove(SPARROW, null, tc_10); // Blue
	deltaGame.makeMove(SPARROW, null, tc_12); // Red
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(-2, 1)); // Blue
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(2, 0)); // red
	deltaGame.makeMove(SPARROW, new TestHantoCoordinate(-2, 1), tc_11); // Blue
	deltaGame.makeMove(CRAB, null, new TestHantoCoordinate(0, 3)); // red
	assertThat(deltaGame.makeMove(BUTTERFLY, tc1_1, tc10), is(BLUE_WINS)); // Blue
}
@Test
public void winByMovingSparrow() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); // Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); // Red
	deltaGame.makeMove(SPARROW, null, tc1_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc11); // Red
	deltaGame.makeMove(SPARROW, null, tc0_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc02); // Red
	deltaGame.makeMove(SPARROW, null, tc_10); // Blue
	deltaGame.makeMove(SPARROW, null, tc_12); // Red
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(-2, 1)); // Blue
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(2, 0)); // red
	deltaGame.makeMove(SPARROW, new TestHantoCoordinate(-2, 1), tc_11); // Blue
	deltaGame.makeMove(CRAB, null, new TestHantoCoordinate(0, 3)); // red
	assertThat(deltaGame.makeMove(SPARROW, tc_10, tc10), is(BLUE_WINS)); // Blue
}

@Test
public void winByMovingCrab() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); // Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); // Red
	deltaGame.makeMove(CRAB, null, tc1_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc11); // Red
	deltaGame.makeMove(SPARROW, null, tc0_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc02); // Red
	deltaGame.makeMove(CRAB, null, tc_10); // Blue
	deltaGame.makeMove(SPARROW, null, tc_12); // Red
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(-2, 1)); // Blue
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(2, 0)); // red
	deltaGame.makeMove(SPARROW, new TestHantoCoordinate(-2, 1), tc_11); // Blue
	deltaGame.makeMove(CRAB, null, new TestHantoCoordinate(0, 3)); // red
	assertThat(deltaGame.makeMove(CRAB, tc1_1, tc10), is(BLUE_WINS)); // Blue
}

@Test(expected = HantoException.class)
public void attemptToMoveCRABlocked() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); // Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); // Red
	deltaGame.makeMove(CRAB, null, tc1_1); // Blue
	deltaGame.makeMove(CRAB, null, tc_12); // Red
	deltaGame.makeMove(CRAB, null, new TestHantoCoordinate(1, -2)); // Blue
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 2)); // Red
	deltaGame.makeMove(CRAB, tc1_1, tc0_1); // Blue
}

@Test(expected = HantoException.class)
public void attemptToMoveButterfloyBlocked() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); // Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); // Red
	deltaGame.makeMove(SPARROW, null, tc1_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc_12); // Red
	deltaGame.makeMove(BUTTERFLY, tc00, tc10); // Blue
}

@Test(expected = HantoException.class)
public void blueAttemptsTooPlaceToManySparrows() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); // Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); // Red
	deltaGame.makeMove(SPARROW, null, tc1_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc11); // Red
	deltaGame.makeMove(SPARROW, null, tc0_1); // Blue
	deltaGame.makeMove(SPARROW, null, tc02); // Red
	deltaGame.makeMove(SPARROW, null, tc_10); // Blue
	deltaGame.makeMove(SPARROW, null, tc_12); // Red
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(-2, 1)); // Blue
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(2, 0)); // red
	deltaGame.makeMove(SPARROW, null, tc_1_1); // Blue
}

@Test(expected = HantoException.class)
public void redAttemptsTooPlaceToManySparrows() throws HantoException
{
	deltaGame.initialize(HantoPlayerColor.RED);
	deltaGame.makeMove(BUTTERFLY, null, tc00); // red
	deltaGame.makeMove(BUTTERFLY, null, tc01); // blue
	deltaGame.makeMove(SPARROW, null, tc1_1); // red
	deltaGame.makeMove(SPARROW, null, tc11); // blue
	deltaGame.makeMove(SPARROW, null, tc0_1); // red
	deltaGame.makeMove(SPARROW, null, tc02); // blue
	deltaGame.makeMove(SPARROW, null, tc_10); // red
	deltaGame.makeMove(SPARROW, null, tc_12); // blue
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(-2, 1)); // red
	deltaGame.makeMove(SPARROW, null, new TestHantoCoordinate(2, 0)); // blue
	deltaGame.makeMove(SPARROW, null, tc_1_1); // red
}

@Test(expected = HantoException.class)
public void blueAttemptsTooPlaceToManyCrabs() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); // Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); // Red
	deltaGame.makeMove(CRAB, null, tc1_1); // Blue
	deltaGame.makeMove(CRAB, null, tc11); // Red
	deltaGame.makeMove(CRAB, null, tc0_1); // Blue
	deltaGame.makeMove(CRAB, null, tc02); // Red
	deltaGame.makeMove(CRAB, null, tc_10); // Blue
	deltaGame.makeMove(CRAB, null, tc_12); // Red
	deltaGame.makeMove(CRAB, null, new TestHantoCoordinate(-2, 1)); // Blue
	deltaGame.makeMove(CRAB, null, new TestHantoCoordinate(2, 0)); // red
	deltaGame.makeMove(CRAB, null, tc_1_1); // Blue
}

@Test(expected = HantoException.class)
public void redAttemptsTooPlaceToManyCrabs() throws HantoException
{
	deltaGame.initialize(HantoPlayerColor.RED);
	deltaGame.makeMove(BUTTERFLY, null, tc00); // red
	deltaGame.makeMove(BUTTERFLY, null, tc01); // blue
	deltaGame.makeMove(CRAB, null, tc1_1); // red
	deltaGame.makeMove(CRAB, null, tc11); // blue
	deltaGame.makeMove(CRAB, null, tc0_1); // red
	deltaGame.makeMove(CRAB, null, tc02); // blue
	deltaGame.makeMove(CRAB, null, tc_10); // red
	deltaGame.makeMove(CRAB, null, tc_12); // blue
	deltaGame.makeMove(CRAB, null, new TestHantoCoordinate(-2, 1)); // red
	deltaGame.makeMove(CRAB, null, new TestHantoCoordinate(2, 0)); // blue
	deltaGame.makeMove(CRAB, null, tc_1_1); // red
}

@Test(expected = HantoException.class)
public void attemptToMoveButterflyTwoHexes() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
	deltaGame.makeMove(BUTTERFLY, tc00, tc11);	// Blue
}

@Test(expected = HantoException.class)
public void attemptToMoveCRABTwoHexes() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
	deltaGame.makeMove(CRAB, null, tc1_1);	// Blue
	deltaGame.makeMove(SPARROW, null, tc_12); 	// Red
	deltaGame.makeMove(CRAB, tc1_1, tc11);	// Blue
}
@Test(expected = HantoException.class)
public void moveButterflyDividesTheConfiguration() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
	deltaGame.makeMove(SPARROW, null, tc_10); 	// Blue
	deltaGame.makeMove(SPARROW, null, tc02); 	// Red
	deltaGame.makeMove(BUTTERFLY, tc00, tc1_1);	// Blue
}

@Test(expected = HantoException.class)
public void moveCrabDividesTheConfiguration() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
	deltaGame.makeMove(CRAB, null, tc_10); 	// Blue
	deltaGame.makeMove(CRAB, null, tc02); 	// Red
	deltaGame.makeMove(CRAB, tc_10, tc_1_1);	// Blue
}

@Test(expected = HantoException.class)
public void moveSparrowDividesTheConfiguration() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
	deltaGame.makeMove(SPARROW, null, tc_10); 	// Blue
	deltaGame.makeMove(SPARROW, null, tc02); 	// Red
	deltaGame.makeMove(SPARROW, tc_10, tc_1_1);	// Blue
}

@Test
public void blueResignCauseRedWins() throws HantoException
{
	assertThat(deltaGame.makeMove(null, null, null), is(RED_WINS));
}

@Test
public void redResignCauseBlueWins() throws HantoException
{
	deltaGame.initialize(HantoPlayerColor.RED);
	assertThat(deltaGame.makeMove(null, null, null), is(BLUE_WINS));
}

@Test(expected=HantoException.class)
public void placePieceNextToSamePieceButAlsoOpponentPiece() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
	deltaGame.makeMove(SPARROW, null, tc10);
}

@Test(expected=HantoException.class)
public void flyToOccupiedPiece() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
	deltaGame.makeMove(SPARROW, null, tc_10); 	// Blue
	deltaGame.makeMove(SPARROW, null, tc02); 	// Red
	deltaGame.makeMove(SPARROW, tc_10, tc01); 	// Blue
}

@Test(expected=HantoException.class)
public void moveAPieceWithConflictedType() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
	deltaGame.makeMove(SPARROW, tc00, tc10); 	// Blue
}

@Test
public void getPrintableBoard() throws HantoException
{
	deltaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
	deltaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
	deltaGame.makeMove(SPARROW, null, tc_10); 	// Blue
	deltaGame.makeMove(SPARROW, null, tc02); 	// Red
	assertEquals(deltaGame.getPrintableBoard(), 
			"[Blue Sparrow (-1, 0)" + "\n" + ", Blue Butterfly (0, 0)" + "\n" + 
	", Red Butterfly (0, 1)" + "\n" + ", Red Sparrow (0, 2)" + "\n" + "]");
}
}
