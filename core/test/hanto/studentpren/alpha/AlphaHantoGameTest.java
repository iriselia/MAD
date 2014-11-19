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
package hanto.studentpren.alpha;

import static org.junit.Assert.*;

import java.awt.Point;



import hanto.common.HantoException;
import hanto.studentpren.alpha.AlphaHantoGame;
import hanto.studentpren.common.Coordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import org.junit.Before;
import org.junit.Test;

/**
 * Description
 * 
 * @author Peng Ren
 * @version Jan 19, 2013
 */
public class AlphaHantoGameTest {	

	AlphaHantoGame alphaHanto;


	@Before
	public void setUp() throws HantoException{
		alphaHanto = new AlphaHantoGame();
		alphaHanto.initialize(null);
	}

	/**
	 * When initialize the alpha hanto game, it
	 * reset everything makes initial coordinate of
	 * board (0, 0), regardless of first player.
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testInitializeAlphaHanto() {
		alphaHanto.initialize(HantoPlayerColor.BLUE);
		assertEquals(new Coordinate(0, 0), alphaHanto.getInitialCoordinate());
		assertNull(alphaHanto.getRedCoord());
		assertEquals(0, alphaHanto.getTurn());
		alphaHanto.initialize(HantoPlayerColor.RED);
		assertEquals(new Coordinate(0, 0), alphaHanto.getInitialCoordinate());
	}

	/**
	 * The first move of the Alpha hanto game should
	 * return ok as the blue butterfly being put into
	 * the initial coordinate.
	 *  
	 * @throws HantoException
	 */
	@Test
	public void testFirstMoveReturnOK() throws HantoException {
		assertEquals(MoveResult.OK,
				alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null, alphaHanto.getInitialCoordinate()));
	}

	@Test
	public void secondMoveToPoint1ReturnDraw() throws HantoException {
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		assertEquals(MoveResult.DRAW,
				alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null,
						new Coordinate(0, 1)));
	}

	@Test
	public void secondMoveToPoint2ReturnDraw() throws HantoException {
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		assertEquals(MoveResult.DRAW,
				alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null,
						new Coordinate(1, 0)));
	}
	@Test
	public void secondMoveToPoint3ReturnDraw() throws HantoException {
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		assertEquals(MoveResult.DRAW,
				alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null,
						new Coordinate(1, -1)));
	}

	@Test
	public void secondMoveToPoint4ReturnDraw() throws HantoException {
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		assertEquals(MoveResult.DRAW,
				alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null,
						new Coordinate(0, -1)));
	}

	@Test
	public void secondMoveToPoint5ReturnDraw() throws HantoException {
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		assertEquals(MoveResult.DRAW,
				alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null,
						new Coordinate(-1, 0)));
	}

	@Test
	public void secondMoveToPoint6ReturnDraw() throws HantoException {
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		assertEquals(MoveResult.DRAW,
				alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null,
						new Coordinate(-1, 1)));
	}

	@Test(expected=HantoException.class)
	public void testCannotMakeSecondMoveToInitialCoord() throws HantoException{
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
	}
	/**
	 * If the second move is not put the red butterfly in
	 * correct right places, that is not adjacent to blue
	 * butterfly, an error will be throw.
	 * 
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void testWrongCoordinateInFirstMoveThrowError() throws HantoException {
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null,
				new Coordinate(-2, 0));
	}

	/**
	 * If the second move is not put the red butterfly in
	 * correct right places, that is not adjacent to blue
	 * butterfly, an error will be throw.
	 * 
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void testWrongCoordinateInSecondMoveThrowError2() throws HantoException{
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null,
				new Coordinate(0, 2));
	}

	/**
	 * This test is test for the alpha coordinate, that the 
	 * equal method should return false if using it to compare
	 * with other kind of object.
	 * 
	 */
	@Test
	public void testEqualsMethodForOtherObject() {
		assertEquals(false, (alphaHanto.getInitialCoordinate()).equals(new Point(0, 0)));
	}

	/**
	 * Test the hashcode method in alpha coordinate class.
	 */
	@Test
	public void testHashCodeForAlphaCoordinate() {
		assertEquals((new Coordinate(0,0)).hashCode(), (alphaHanto.getInitialCoordinate().hashCode()));
	}

	/**
	 * If the second move is not put the red butterfly in
	 * correct right places, that is not adjacent to blue
	 * butterfly, an error will be throw.
	 * 
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void testWrongCoordinateInSecondMoveThrowError3() throws HantoException {
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null,
				new Coordinate(2, 2));
	}

	@Test(expected=HantoException.class)
	public void SecondTurnWithFromCoordThrowError() throws HantoException{
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, new Coordinate(0, 2), new Coordinate(1, 0));
	}

	/**
	 * If the from position of the blue player is not 
	 * from out of board, then an error will be thrown.
	 * 
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void fromPositionShouldAlwaysBeOutOfBoardForBlue() throws HantoException {
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, new Coordinate(0, 0),
				alphaHanto.getInitialCoordinate());
	}

	/**
	 * If the from position of the red player is not 
	 * from out of board, then an error will be thrown.
	 * 
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void fromPositionShouldAlwaysBeOutOfBoardForRed() throws HantoException {
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, new Coordinate(0, 0),
				new Coordinate(0, 1));
	}

	@Test
	public void printReadableBoard() throws HantoException{
		assertEquals("No meaningful board.", alphaHanto.getPrintableBoard());
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
		assertEquals("Blue Butterfly (0, 0)", alphaHanto.getPrintableBoard());
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(1, 0));
		assertEquals("Blue Butterfly (0, 0)\n" + "Red Butterfly (1, 0)", 
				alphaHanto.getPrintableBoard());
	}
	@Test
	public void getPieceAtReturnNull() throws HantoException{
		assertEquals(null, alphaHanto.getPieceAt(0, 0));
	}
}
