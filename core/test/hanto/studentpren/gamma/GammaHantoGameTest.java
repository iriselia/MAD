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

import static hanto.util.HantoPieceType.BUTTERFLY;
import static hanto.util.HantoPieceType.CRAB;
import static hanto.util.HantoPieceType.DOVE;
import static hanto.util.HantoPieceType.SPARROW;
import static hanto.util.MoveResult.BLUE_WINS;
import static hanto.util.MoveResult.DRAW;
import static hanto.util.MoveResult.OK;
import static hanto.util.MoveResult.RED_WINS;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentpren.HantoGameFactory;
import hanto.studentpren.alpha.AlphaHantoGame;
import hanto.studentpren.common.Coordinate;
import hanto.testutil.HexPiece;
import hanto.testutil.TestHantoCoordinate;
import hanto.testutil.TestHantoGame;
import hanto.util.HantoCoordinate;
import hanto.util.HantoGameID;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import org.junit.Before;
import org.junit.Test;

/**
 * This class is used to test Gamma Hanto game.
 * 
 * @author Peng Ren
 * @version Jan 23, 2013
 */
public class GammaHantoGameTest {

	HantoGame gammaHanto;
	HantoCoordinate coord1;
	HantoCoordinate coord2;
	HantoCoordinate coord3;
	HantoCoordinate coord4;
	HantoCoordinate coord5;
	HantoCoordinate coord6;
	HantoCoordinate coord7;

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
	tc0_2 = new TestHantoCoordinate(0, -2),
	tc11 = new TestHantoCoordinate(1, 1);

	private HantoGame alphaGame;
	private HantoGame gammaGame;

	@Before
	public void setUp() {
		alphaGame = new AlphaHantoGame();
		gammaGame = new GammaHantoGame();
		gammaHanto = new GammaHantoGame();
		coord1 = new Coordinate(0, 0);
		coord2 = new Coordinate(1, 0);
		coord3 = new Coordinate(2, 0);
		coord4 = new Coordinate(-1, 1);
		coord5 = new Coordinate(1, -1);
		coord6 = new Coordinate(0, 1);
		coord7 = new Coordinate(1, 1);
	}
	@Test
	public void generateAlphaHantoGameViaFactory(){
		HantoGameFactory gameFactory = HantoGameFactory.getInstance();
		assertNotNull(gameFactory.makeHantoGame(HantoGameID.ALPHA_HANTO));
	}
	@Test
	public void generateGammaHantoGameViaFactory(){
		HantoGameFactory gameFactory = HantoGameFactory.getInstance();
		assertNotNull(gameFactory.makeHantoGame(HantoGameID.GAMMA_HANTO));
	}
	@Test(expected=HantoException.class)
	public void noTypeInMakeMoveCauseError() throws HantoException{
		gammaHanto.makeMove(null, null, coord1);
	}
	@Test(expected=HantoException.class)
	public void firstNotPutInInitialCoordCauseError() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
	}
	@Test
	public void creatAGammaHantoPieceOnlyWithTwoParameters(){
		assertNotNull(new GammaHantoPiece(HantoPlayerColor.BLUE, BUTTERFLY));
	}
	@Test
	public void firstPutInInitialCoordPass() throws HantoException{
		assertEquals(MoveResult.OK, gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1));
	}
	@Test(expected=HantoException.class)
	public void noTargetCoordCauseError() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, null);
	}
	@Test(expected=HantoException.class)
	public void cannotSelectUnOccupiedCoordToMove1() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord2, coord3);
	}
	@Test(expected=HantoException.class)
	public void cannotMoveOthersPiece() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord1, coord2);
	}
	@Test(expected=HantoException.class)
	public void inconsistentFromCoordWithPieceType() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.SPARROW, coord1, coord4);
	}
	@Test(expected=HantoException.class)
	public void movePieceOtherThanButterflyCauseError() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, coord4);
		gammaHanto.makeMove(HantoPieceType.SPARROW, coord1, coord5);
	}
	@Test(expected=HantoException.class)
	public void cannotMoveAPieceNotAjacentToOther() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord3);
	}
	@Test
	public void testHashCodeForAlphaCoordinate() {
		assertEquals((new Coordinate(0,0)).hashCode(), (coord1.hashCode()));
	}
	@Test(expected=HantoException.class)
	public void butterflyMustBeingPutInFirstFourthTurn() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 1));
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord2, new Coordinate(0, 1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 0));
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, new Coordinate(0, 1), new Coordinate(-1, 2));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-2, 1));
	}
	@Test
	public void butterflyPutOnFourthTurnPass() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 1));
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord2, new Coordinate(0, 1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 0));
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, new Coordinate(0, 1), new Coordinate(-1, 2));
		assertEquals(MoveResult.OK, gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(-2, 1)));
	}
	@Test
	public void butterflyPutOnFourthTurnPass2() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(-1, 1));
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord2, new Coordinate(0, 1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 0));
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, new Coordinate(0, 1), new Coordinate(-1, 2));
		assertEquals(MoveResult.OK, gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-2, 1)));
	}
	@Test(expected=HantoException.class)
	public void onlyButterflyCanMoveInThisGame() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.SPARROW, coord1, new Coordinate(1, -1));
	}
	@Test(expected=HantoException.class)
	public void canNotPlacePieceIsolated() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord3);
	}
	@Test(expected=HantoException.class)
	public void canNotPlacePieceIsolated2() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(-1, 1));
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord2, new Coordinate(2, 0));
	}

	//@Test(expected=HantoException.class)
	//public void butterflySurroundByFiveCannotMove() throws HantoException{
	//	gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
	//	gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
	//	gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 1));
	//	gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 1));
	//	gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 0));
	//	gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, -1));
	//	gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord1, new Coordinate(1, -1));
	//}
	@Test(expected=HantoException.class)
	public void moveCauseIsolatedClustersThrowError() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(1, 1));
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord1, new Coordinate(-1, 0));
	}
	@Test(expected=HantoException.class)
	public void moveWithSameCoordinateCauseError() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord1, coord1);
	}
	@Test(expected=HantoException.class)
	public void MoveOponOtherPieceCauseError() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, coord6);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord2, coord1);
	}
	@Test(expected=HantoException.class)
	public void PutOponOtherPieceCauseError() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
	}
	@Test(expected=HantoException.class)
	public void cannotPlacePieceIsolated() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, -1));
	}
	@Test(expected=HantoException.class)
	public void inconsisitentTypeCauseError() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.SPARROW, coord1, new Coordinate(1, -1));
	}
	@Test(expected=HantoException.class)
	public void useMoreThanFiveSparrowsCauseError() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(1, -1));
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord2, new Coordinate(-1, 1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, coord2);
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 0));
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(-2, 0));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-2, 1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, -1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, -2));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-3, 0));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-2, 2));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(1, -2));
	}
	@Test
	public void afterTenTurnsEndGame() throws HantoException{
		gammaHanto.initialize(HantoPlayerColor.RED);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord1, coord5);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord2, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord5, coord2);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord1, coord5);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord2, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord5, coord2);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord1, coord5);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord2, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord5, coord2);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord1, coord5);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord2, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord5, coord2);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord1, coord5);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord2, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord5, coord2);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord1, coord5);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord2, coord1);
		assertEquals(MoveResult.DRAW, gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord5, coord2));
	}
	@Test
	public void blueButterflySurroundCauseRedWin() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 0));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, -1));
		assertEquals(MoveResult.RED_WINS, gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(1, -1)));
	}
	@Test
	public void redButterflySurroundCauseRedWin() throws HantoException{
		gammaHanto.initialize(HantoPlayerColor.RED);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 0));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, -1));
		assertEquals(MoveResult.BLUE_WINS, gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(1, -1)));
	}
	@Test
	public void playersWinInDraw() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 0));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, -1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(2, -1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(2, 0));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(1, 1));
		assertEquals(MoveResult.DRAW, gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(1, -1)));
	}
	@Test
	public void atTenTurnAPlayerWins() throws HantoException{
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, null, coord2);
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 1));
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord2, coord7);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord1, coord4);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord7, coord2);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord4, coord1);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord2, coord7);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord1, coord4);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord7, coord2);
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord4, coord1);
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 0));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, -1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(2, -1));
		gammaHanto.makeMove(HantoPieceType.BUTTERFLY, coord2, new Coordinate(1, -1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-1, 2));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(1, 1));
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(-2, 2));
		assertEquals(MoveResult.DRAW, gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(0, 2)));
	}
	@Test
	public void testInitialMethodForTestHantoGame() throws HantoException{
		TestHantoGame gammaHanto = new GammaHantoGame();
		hanto.testutil.HexPiece hex1 = new HexPiece(coord1, HantoPlayerColor.RED, HantoPieceType.BUTTERFLY);
		hanto.testutil.HexPiece hex2 = new HexPiece(coord2, HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY);
		hanto.testutil.HexPiece[] hexBoard = {hex1, hex2};
		gammaHanto.initialize(HantoPlayerColor.RED, hexBoard);
		assertEquals(MoveResult.OK, gammaHanto.makeMove(HantoPieceType.SPARROW, null, new Coordinate(1, -1)));
	}
	@Test
	public void testInitialMethodForTestHantoGame2() throws HantoException{
		TestHantoGame gammaHanto = new GammaHantoGame();
		hanto.testutil.HexPiece hex1 = new HexPiece(coord1, HantoPlayerColor.RED, HantoPieceType.BUTTERFLY);
		hanto.testutil.HexPiece hex2 = new HexPiece(coord2, HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY);
		hanto.testutil.HexPiece hex3 = new HexPiece(coord5, HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY);
		hanto.testutil.HexPiece[] hexBoard = {hex1, hex2, hex3};
		gammaHanto.initialize(HantoPlayerColor.BLUE, hexBoard);
		assertEquals(MoveResult.OK, gammaHanto.makeMove(HantoPieceType.SPARROW, null, coord3));
	}
	@Test
	public void getPrintableBoardGiveTheRepresenationOfBoardToUnderstand() throws HantoException{
		TestHantoGame gammaHanto = new GammaHantoGame();
		hanto.testutil.HexPiece hex1 = new HexPiece(coord1, HantoPlayerColor.RED, HantoPieceType.BUTTERFLY);
		hanto.testutil.HexPiece hex2 = new HexPiece(coord2, HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY);
		hanto.testutil.HexPiece[] hexBoard = {hex1, hex2};
		gammaHanto.initialize(HantoPlayerColor.BLUE, hexBoard);
		gammaHanto.makeMove(HantoPieceType.SPARROW, null, coord3);
		String board = gammaHanto.getPrintableBoard();
		assertEquals("[Blue Sparrow (2, 0)\n" + ", Red Butterfly (0, 0)\n" + ", Blue Butterfly (1, 0)\n]", board);
	}

	/*
	 * Alpha Hanto tests
	 */
	@Test
	public void createANewAlphaHantoGame()
	{
		assertNotNull(new AlphaHantoGame());
	}

	@Test
	public void placeBlueButterflyAsFirstMove() throws HantoException
	{
		assertThat(alphaGame.makeMove(BUTTERFLY, null, tc00),
				is(OK));
	}

	@Test(expected=HantoException.class)
	public void placeButterflyOnWrongHexAsFirstMove() throws HantoException
	{
		alphaGame.makeMove(BUTTERFLY, null, tc01);
	}

	@Test(expected=HantoException.class)
	public void nonButterflyPieceCausesExecption() throws HantoException
	{
		alphaGame.makeMove(CRAB, null, tc00);
	}

	@Test
	public void placeRedButterflyAdjacentToBlueButterflyForDraw() throws HantoException
	{
		alphaGame.makeMove(BUTTERFLY, null, tc00);
		assertThat(alphaGame.makeMove(BUTTERFLY, null, tc10),
				is(DRAW));
	}

	@Test(expected=HantoException.class)
	public void placeRedButterflyOnBlueButterfly() throws HantoException
	{
		alphaGame.makeMove(BUTTERFLY, null, tc00);
		alphaGame.makeMove(BUTTERFLY, null, tc00);
	}

	@Test(expected=HantoException.class)
	public void placeRedButterflyNotAdjacentToBlueButterfly() throws HantoException
	{
		alphaGame.makeMove(BUTTERFLY, null, tc00);
		alphaGame.makeMove(BUTTERFLY, null, new TestHantoCoordinate(2, 3));
	}

	@Test(expected=HantoException.class)
	public void testAdjacencyCalulationDefect() throws HantoException
	{
		alphaGame.makeMove(BUTTERFLY, null, tc00);
		alphaGame.makeMove(BUTTERFLY, null, tc11);
	}

	@Test
	public void initializeHasNoEffect() throws HantoException
	{
		alphaGame.initialize(HantoPlayerColor.RED);
		alphaGame.makeMove(BUTTERFLY, null, tc00);
		assertThat(alphaGame.makeMove(BUTTERFLY, null, tc10),
				is(DRAW));
	}

	@Test
	public void printableBoardIsNotNullAtGameStart()
	{
		assertNotNull(alphaGame.getPrintableBoard());
	}

	@Test
	public void printableBoardWithPiecesIsMeangingful() throws HantoException
	{
		alphaGame.makeMove(BUTTERFLY, null, tc00);
		assertFalse(alphaGame.getPrintableBoard().length() == 0);
		System.out.println(alphaGame.getPrintableBoard());
	}

	/*
	 * Gamma Hanto tests
	 */
	@Test
	public void blueButterflyMovesFirst() throws HantoException
	{
		assertThat(gammaGame.makeMove(BUTTERFLY, null, tc00), is(OK));
	}

	@Test(expected = HantoException.class)
	public void placeFirstPiecesInWrongCoordinate() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc11);
	}

	@Test(expected = HantoException.class)
	public void placePieceThatIsNotInTheGame() throws HantoException
	{
		gammaGame.makeMove(DOVE, null, tc00);
	}

	@Test(expected = HantoException.class)
	public void placeSecondPieceNotAdjacentToFirstPiece() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00);
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(3, -2));
	}

	@Test
	public void placeValidThirdPiece() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00);
		gammaGame.makeMove(SPARROW, null, tc01);
		gammaGame.makeMove(SPARROW, null, tc02);
	}

	@Test(expected = HantoException.class)
	public void placeThirdPieceNotAdjacentToAnyOtherPiece() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00);
		gammaGame.makeMove(SPARROW, null, tc01);
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(3, -2));
	}

	@Test
	public void blueWinsWithBlueMove() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc10); // Blue
		gammaGame.makeMove(SPARROW, null, tc11); // Red
		gammaGame.makeMove(SPARROW, null, tc02); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		assertThat(gammaGame.makeMove(SPARROW, null, tc_11), is(BLUE_WINS)); // Blue
	}

	@Test
	public void blueWinsWithRedMove() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc10); // Blue
		gammaGame.makeMove(SPARROW, null, tc11); // Red
		gammaGame.makeMove(SPARROW, null, tc02); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(SPARROW, null, tc0_1); // Blue
		assertThat(gammaGame.makeMove(SPARROW, null, tc_11), is(BLUE_WINS));
	}

	@Test
	public void redWins() throws HantoException
	{
		gammaGame.initialize(HantoPlayerColor.RED);
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Red
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Blue
		gammaGame.makeMove(SPARROW, null, tc10); // Red
		gammaGame.makeMove(SPARROW, null, tc11); // Blue
		gammaGame.makeMove(SPARROW, null, tc02); // Red
		gammaGame.makeMove(SPARROW, null, tc_12); // Blue
		assertThat(gammaGame.makeMove(SPARROW, null, tc_11), is(RED_WINS)); // Red
	}

	@Test
	public void redWinsOnLastMove() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc_10); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(-1, 3)); // Blue
		gammaGame.makeMove(SPARROW, null, tc11); // Red
		gammaGame.makeMove(SPARROW, null, tc10); // Blue
		gammaGame.makeMove(SPARROW, null, tc1_1); // Red
		gammaGame.makeMove(SPARROW, null, tc0_1); // Blue
		gammaGame.makeMove(SPARROW, null, tc0_2); // Red
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 3)); // Blue
		assertThat(gammaGame.makeMove(SPARROW, null, tc_11), is(RED_WINS)); // RED
	}

	@Test(expected = HantoException.class)
	public void attemptToPlaceTwoBlueButterflies() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00);
		gammaGame.makeMove(BUTTERFLY, null, tc01);
		gammaGame.makeMove(BUTTERFLY, null, tc10);
	}

	@Test(expected = HantoException.class)
	public void attemptToPlaceTwoRedButterflies() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00);
		gammaGame.makeMove(BUTTERFLY, null, tc01);
		gammaGame.makeMove(SPARROW, null, tc11);
		gammaGame.makeMove(BUTTERFLY, null, tc10);
	}

	@Test
	public void drawBySurroundingBothButterflies() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc_10); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(SPARROW, null, tc02); // Blue
		gammaGame.makeMove(SPARROW, null, tc11); // Red
		gammaGame.makeMove(SPARROW, null, tc10); // Blue
		gammaGame.makeMove(SPARROW, null, tc1_1); // Red
		gammaGame.makeMove(SPARROW, null, tc0_1); // Blue
		assertThat(gammaGame.makeMove(SPARROW, null, tc_11), is(DRAW)); // Red
	}

	@Test
	public void drawByExhaustingMoves() throws HantoException
	{
		final TestHantoCoordinate tc1 = tc01, tc2 = tc10, tc3 = tc0_1, tc4 = tc_10;

		gammaGame.makeMove(SPARROW, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc1); // Red
		gammaGame.makeMove(BUTTERFLY, null, tc3); // Blue
		gammaGame.makeMove(BUTTERFLY, tc1, tc2); // Red
		gammaGame.makeMove(BUTTERFLY, tc3, tc4); // Blue
		gammaGame.makeMove(BUTTERFLY, tc2, tc1); // Red
		gammaGame.makeMove(BUTTERFLY, tc4, tc3); // Blue
		gammaGame.makeMove(BUTTERFLY, tc1, tc2); // Red
		gammaGame.makeMove(BUTTERFLY, tc3, tc4); // Blue
		gammaGame.makeMove(BUTTERFLY, tc2, tc1); // Red
		gammaGame.makeMove(BUTTERFLY, tc4, tc3); // Blue
		gammaGame.makeMove(BUTTERFLY, tc1, tc2); // Red
		gammaGame.makeMove(BUTTERFLY, tc3, tc4); // Blue
		gammaGame.makeMove(BUTTERFLY, tc2, tc1); // Red
		gammaGame.makeMove(BUTTERFLY, tc4, tc3); // Blue
		gammaGame.makeMove(BUTTERFLY, tc1, tc2); // Red
		gammaGame.makeMove(BUTTERFLY, tc3, tc4); // Blue
		gammaGame.makeMove(BUTTERFLY, tc2, tc1); // Red
		gammaGame.makeMove(BUTTERFLY, tc4, tc3); // Blue
		assertThat(gammaGame.makeMove(BUTTERFLY, tc1, tc2), is(DRAW)); // Red
	}

	@Test(expected = HantoException.class)
	public void blueDoesNotPlaceButterflyWithinFourMoves() throws HantoException
	{
		gammaGame.makeMove(SPARROW, null, tc00); // Blue
		gammaGame.makeMove(SPARROW, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc_10); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(SPARROW, null, tc02); // Blue
		gammaGame.makeMove(SPARROW, null, tc11); // Red
		gammaGame.makeMove(SPARROW, null, tc10); // Blue
	}

	@Test(expected = HantoException.class)
	public void redDoesNotPlaceButterflyWithinFourMoves() throws HantoException
	{
		gammaGame.makeMove(SPARROW, null, tc00); // Blue
		gammaGame.makeMove(SPARROW, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc_10); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(SPARROW, null, tc02); // Blue
		gammaGame.makeMove(SPARROW, null, tc11); // Red
		gammaGame.makeMove(BUTTERFLY, null, tc10); // Blue
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(2, 0)); // Red
	}

	@Test(expected = HantoException.class)
	public void placePieceOnTopOfAnother() throws HantoException
	{
		gammaGame.makeMove(SPARROW, null, tc00); // Blue
		gammaGame.makeMove(SPARROW, null, tc00); // Red
	}

	@Test(expected = HantoException.class)
	public void attemptMoveAfterGameIsOver() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc10); // Blue
		gammaGame.makeMove(SPARROW, null, tc11); // Red
		gammaGame.makeMove(SPARROW, null, tc02); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(SPARROW, null, tc_11); // Blue wins
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(2, 0));
	}

	@Test
	public void printableBoardTest() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc_10); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(SPARROW, null, tc02); // Blue
		gammaGame.makeMove(SPARROW, null, tc11); // Red
		gammaGame.makeMove(SPARROW, null, tc10); // Blue
		gammaGame.makeMove(SPARROW, null, tc1_1); // Red
		gammaGame.makeMove(SPARROW, null, tc0_1); // Blue
		gammaGame.makeMove(SPARROW, null, tc_11); // Red
		String pb = gammaGame.getPrintableBoard();
		assertNotNull(pb);
		assertTrue(pb.length() > 0);
		System.out.println(pb);
	}

	@Test(expected = HantoException.class)
	public void moveFromUnoccupiedHex() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(BUTTERFLY, tc10, tc11); // Blue
	}

	@Test(expected = HantoException.class)
	public void moveFromSparrowHex() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc_10); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(BUTTERFLY, tc_10, tc11); // Blue
	}

	@Test(expected = HantoException.class)
	public void moveFromWrongButterflyHex() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc_10); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(BUTTERFLY, tc01, tc11); // Blue
	}

	@Test
	public void winByMovingButterfly() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
		gammaGame.makeMove(SPARROW, null, tc10); 	// Blue
		gammaGame.makeMove(SPARROW, null, tc11); 	// Red
		gammaGame.makeMove(BUTTERFLY, tc00, tc1_1);	// Blue
		gammaGame.makeMove(SPARROW, null, tc02); 	// Red
		gammaGame.makeMove(SPARROW, null, tc_12); 	// Blue
		gammaGame.makeMove(SPARROW, null, tc_11);	// Red
		assertThat(gammaGame.makeMove(BUTTERFLY, tc1_1, tc00), is(BLUE_WINS));	// Blue
	}

	@Test(expected = HantoException.class)
	public void attemptToMoveSparrow() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc_10); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(SPARROW, tc_10, tc0_1); // Blue
	}

	@Test(expected = HantoException.class)
	public void blueAttemptsTooPlaceToManySparrows() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
		gammaGame.makeMove(SPARROW, null, tc_10); 	// Blue
		gammaGame.makeMove(SPARROW, null, tc_11); 	// Red
		gammaGame.makeMove(SPARROW, null, tc_1_1); 	// Blue
		gammaGame.makeMove(SPARROW, null, tc0_2); 	// Red
		gammaGame.makeMove(SPARROW, null, tc02); 	// Blue
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 3)); 	// Red
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 4));	// Blue
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 5));	// Red
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 6));	// Blue
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 7));	// Red
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 8));	// Blue
	}

	@Test(expected = HantoException.class)
	public void redAttemptsTooPlaceToManySparrows() throws HantoException
	{
		gammaGame.initialize(HantoPlayerColor.RED);
		gammaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
		gammaGame.makeMove(SPARROW, null, tc_10); 	// Blue
		gammaGame.makeMove(SPARROW, null, tc_11); 	// Red
		gammaGame.makeMove(SPARROW, null, tc_1_1); 	// Blue
		gammaGame.makeMove(SPARROW, null, tc0_2); 	// Red
		gammaGame.makeMove(SPARROW, null, tc02); 	// Blue
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 3)); 	// Red
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 4));	// Blue
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 5));	// Red
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 6));	// Blue
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 7));	// Red
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 8));	// Blue
	}

	@Test(expected = HantoException.class)
	public void attemptToMoveButterflyTwoHexes() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
		gammaGame.makeMove(BUTTERFLY, tc00, tc11);	// Blue
	}

	@Test(expected = HantoException.class)
	public void moveDividesTheConfiguration() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
		gammaGame.makeMove(SPARROW, null, tc_10); 	// Blue
		gammaGame.makeMove(SPARROW, null, tc02); 	// Red
		gammaGame.makeMove(BUTTERFLY, tc00, tc0_1);	// Blue
	}
}
