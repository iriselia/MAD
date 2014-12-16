package com.mygdx.game.util;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.mygdx.hanto.util.HantoPieceType.*;
import com.mygdx.hanto.implementation.common.Coordinate;
import com.mygdx.hanto.implementation.common.HantoPiece;
import com.mygdx.hanto.implementation.common.PieceMoveStrategy;
import com.mygdx.hanto.implementation.core.HantoPieceDevelopment;
import com.mygdx.hanto.implementation.core.HantoStateDevelopment;
import com.mygdx.hanto.implementation.core.movestrategy.FlyStrategy;
import com.mygdx.hanto.implementation.core.movestrategy.JumpStrategy;
import com.mygdx.hanto.implementation.core.movestrategy.RunStrategy;
import com.mygdx.hanto.implementation.core.movestrategy.TrapStrategy;
import com.mygdx.hanto.implementation.core.movestrategy.WalkStrategy;
import com.mygdx.hanto.util.HantoCoordinate;
import com.mygdx.hanto.util.HantoMoveRecord;
import com.mygdx.hanto.util.HantoPieceType;
import com.mygdx.hanto.util.HantoPlayerColor;

/**
 * Handles switching turns, generating valid piece placement coordinates, etc
 * 
 * @author Peng Ren
 *
 */
public class GameStateHandler {

	private final HantoPlayerColor playerColor;
	private final HantoStateDevelopment gameState;

	/**
	 * This is the constructor for GameStateHandler
	 * 
	 * @param playerColor This indicates the player's color
	 * @param gameState the current game state of delta hanto game
	 */
	public GameStateHandler(HantoPlayerColor playerColor, HantoStateDevelopment gameState){
		this.playerColor = playerColor;
		this.gameState = gameState;
	}

	/**
	 * This is a helper function that generates all possible and valid placement for
	 * the given piece type available in delta hanto game.
	 * 
	 * @param pieceType the type of piece to be placed
	 * @return a list of all possible and valid placement
	 */
	public List<HantoMoveRecord> generatePiecesAvailablePlacements(HantoPieceType pieceType){
		List<HantoMoveRecord> validPlacements = new ArrayList<HantoMoveRecord>();
		final List<HantoMoveRecord> validButterflyPlacements = new ArrayList<HantoMoveRecord>();
		final List<HantoMoveRecord> validSparrowPlacements = new ArrayList<HantoMoveRecord>();
		final List<HantoMoveRecord> validCrabPlacements = new ArrayList<HantoMoveRecord>();

		final List<Coordinate> validPlaceCoords = getAvailableCoordinateForPlace();

		for(int i = 0; i < validPlaceCoords.size(); i++){
			HantoMoveRecord butterflyPlace, sparrowPlace, crabPlace;
			butterflyPlace = new HantoMoveRecord(BUTTERFLY, null, validPlaceCoords.get(i));
			sparrowPlace = new HantoMoveRecord(SPARROW, null, validPlaceCoords.get(i));
			crabPlace = new HantoMoveRecord(CRAB, null, validPlaceCoords.get(i));
			validButterflyPlacements.add(butterflyPlace);
			validSparrowPlacements.add(sparrowPlace);
			validCrabPlacements.add(crabPlace);
		}

		final int pieceTypeNum = gameState.getNumberTypeForPlayer(pieceType, playerColor);
		if(pieceType == BUTTERFLY && pieceTypeNum != 1){
			validPlacements = validButterflyPlacements;
		}
		else if(pieceType == SPARROW && pieceTypeNum != 4){
			validPlacements = validSparrowPlacements;
		}
		else if(pieceType == CRAB && pieceTypeNum != 4){
			validPlacements = validCrabPlacements;
		}
		else{
		}
		return validPlacements;
	}

	/**
	 * This is a helper function that generates all possible and valid Movement for
	 * that type of piece in the delta hanto game.
	 * 
	 * @param pieceType the type of piece to be gotten valid movement
	 * @return a hashmap of all possible and valid Movement
	 */
	public List<HantoMoveRecord> generatePiecesAvailableMoves(HantoPieceType pieceType){
		List<HantoMoveRecord> validMovements = new ArrayList<HantoMoveRecord>();

		final List<HantoPieceDevelopment> ownButterfly = getOwnPiecesOnBoardByType(BUTTERFLY);
		final List<HantoPieceDevelopment> ownSparrows = getOwnPiecesOnBoardByType(SPARROW);
		final List<HantoPieceDevelopment> ownCrabs = getOwnPiecesOnBoardByType(CRAB);
		final List<HantoPieceDevelopment> ownCranes = getOwnPiecesOnBoardByType(CRANE);
		final List<HantoPieceDevelopment> ownHorses = getOwnPiecesOnBoardByType(HORSE);
		final List<HantoPieceDevelopment> ownDoves = getOwnPiecesOnBoardByType(DOVE);

		final List<HantoMoveRecord> butterflyValidMoves = new ArrayList<HantoMoveRecord>();
		final List<HantoMoveRecord> crabValidMoves = new ArrayList<HantoMoveRecord>();
		final List<HantoMoveRecord> sparrowValidMoves = new ArrayList<HantoMoveRecord>();
		final List<HantoMoveRecord> craneValidMoves = new ArrayList<HantoMoveRecord>();
		final List<HantoMoveRecord> horseValidMoves = new ArrayList<HantoMoveRecord>();
		final List<HantoMoveRecord> doveValidMoves = new ArrayList<HantoMoveRecord>();

		for(int i = 0; i < ownButterfly.size(); i++){
			HantoPieceDevelopment butterfly = ownButterfly.get(i);
			butterfly.setPieceMoveStrategy(new WalkStrategy(gameState));
			List<HantoMoveRecord> butterflyValidDests = getWalkPieceValidMoveDestination(butterfly);
			butterflyValidMoves.addAll(butterflyValidDests);
		}

		for(int i = 0; i < ownSparrows.size(); i++){
			HantoPieceDevelopment sparrow = ownSparrows.get(i);
			sparrow.setPieceMoveStrategy(new FlyStrategy(gameState));
			List<HantoMoveRecord> sparrowValidDests = getFlyPieceValidMoveDestination(sparrow);
			sparrowValidMoves.addAll(sparrowValidDests);
		}

		for(int i = 0; i < ownCrabs.size(); i++){
			HantoPieceDevelopment crab = ownCrabs.get(i);
			crab.setPieceMoveStrategy(new WalkStrategy(gameState));
			List<HantoMoveRecord> crabValidDests = getWalkPieceValidMoveDestination(crab);
			crabValidMoves.addAll(crabValidDests);
		}
		
		for(int i = 0; i < ownCranes.size(); i++){
			HantoPieceDevelopment crane = ownCranes.get(i);
			crane.setPieceMoveStrategy(new TrapStrategy(gameState));
			List<HantoMoveRecord> craneValidDests = getTrapPieceValidMoveDestination(crane);
			craneValidMoves.addAll(craneValidDests);
		}
		
		for(int i = 0; i < ownHorses.size(); i++){
			HantoPieceDevelopment horse = ownHorses.get(i);
			horse.setPieceMoveStrategy(new RunStrategy(gameState));
			List<HantoMoveRecord> horseValidDests = getRunPieceValidMoveDestination(horse);
			horseValidMoves.addAll(horseValidDests);
		}
		
		for(int i = 0; i < ownDoves.size(); i++){
			HantoPieceDevelopment dove = ownDoves.get(i);
			dove.setPieceMoveStrategy(new JumpStrategy(gameState));
			List<HantoMoveRecord> doveValidDests = getJumpPieceValidMoveDestination(dove);
			doveValidMoves.addAll(doveValidDests);
		}

		if(pieceType == BUTTERFLY){
			validMovements = butterflyValidMoves;
		}
		if(pieceType == SPARROW){
			validMovements = sparrowValidMoves;
		}
		if(pieceType == CRAB){
			validMovements = crabValidMoves;
		}
		if(pieceType == CRANE){
			validMovements = craneValidMoves;
		}
		if(pieceType == HORSE){
			validMovements = horseValidMoves;
		}
		if(pieceType == DOVE){
			validMovements = doveValidMoves;
		}
		return validMovements;
	}

	/**
	 * This is a helper function to get all the player's own pieces on board.
	 * 
	 * @return all the pieces of the player on board
	 */
	public List<HantoPieceDevelopment> getOwnPiecesOnBoard(){
		final HantoPiece[] pieces = gameState.getBoard().getPieceArray();
		final List<HantoPieceDevelopment> ownPieces = new ArrayList<HantoPieceDevelopment>();
		for(HantoPiece piece : pieces){
			if(piece.getPlayer() == playerColor){
				ownPieces.add((HantoPieceDevelopment) piece);
			}
		}
		return ownPieces;
	}

	/**
	 * This is a helper function to get all the player's opponent's pieces on board.
	 * 
	 * @return all the pieces of the opponent on board
	 */
	public List<HantoPieceDevelopment> getOpponentPiecesOnBoard(){
		final HantoPiece[] pieces = gameState.getBoard().getPieceArray();
		final List<HantoPieceDevelopment> ownPieces = new ArrayList<HantoPieceDevelopment>();
		for(HantoPiece piece : pieces){
			if(piece.getPlayer() != playerColor){
				ownPieces.add((HantoPieceDevelopment) piece);
			}
		}
		return ownPieces;
	}


	/**
	 * This is the helper function to get the available hex for player to place pieces.
	 * 
	 * @return all the coordinate available for player to make a placement
	 */
	public List<Coordinate> getAvailableCoordinateForPlace(){
		final List<HantoPieceDevelopment> ownPieces = getOwnPiecesOnBoard();
		final List<Coordinate> validPlaceCoords = new ArrayList<Coordinate>();
		for(int i = 0; i < ownPieces.size(); i++){
			final HantoPieceDevelopment ownPiece = ownPieces.get(i);
			final Coordinate pieceCoord = 
					Coordinate.guarrenteedCoordinate(ownPiece.getCoordinate());
			final Coordinate[] pieceNeighbors = pieceCoord.getNeighbors();
			for(Coordinate neighbor : pieceNeighbors){
				boolean valid = true;
				final Deque<HantoPiece> pieces = gameState.getBoard().getPieceAt(neighbor);
				if(pieces != null){
					valid = false;
				}
				else{
					final Coordinate[] secNeighbors = neighbor.getNeighbors();
					for(Coordinate secNeighbor : secNeighbors){
						final Deque<HantoPiece> secNeighborPieces = gameState.getBoard().getPieceAt(secNeighbor);
						if(secNeighborPieces != null){
							if(secNeighborPieces.peekFirst().getPlayer() != gameState.getPlayerOnMove()){
								valid = false;
								break;
							}
						}
					}
				}
				if(valid && !validPlaceCoords.contains(neighbor)){
					validPlaceCoords.add(neighbor);
				}
			}
		}
		return validPlaceCoords;
	}

	/**
	 * This is the helper function to get the available hexes adjacent 
	 * to the current pieces on board.
	 * 
	 * @return all the coordinates as long as adjacent to a piece on board
	 */
	public List<Coordinate> getAdjacentCoordinatesToPiecesOnBoard(){
		final HantoPiece[] pieces = gameState.getBoard().getPieceArray();
		final List<Coordinate> adjacentCoords = new ArrayList<Coordinate>();
		for(HantoPiece piece : pieces){
			Coordinate pieceCoord = Coordinate.guarrenteedCoordinate(piece.getCoordinate());
			Coordinate[] neighbors = pieceCoord.getNeighbors();
			for(Coordinate c : neighbors){
				if(gameState.getBoard().getPieceAt(c) == null && !adjacentCoords.contains(c)){
					adjacentCoords.add(c);
				}
			}
		}
		return adjacentCoords;
	}

	/**
	 * This is a helper function to get the player's own pieces on the board by their type.
	 * 
	 * @param pieceType the piece type that the player want to get own from the current board
	 * @return a list of all the players own pieces of that type on the board
	 */
	private List<HantoPieceDevelopment> getOwnPiecesOnBoardByType(HantoPieceType pieceType){
		final List<HantoPieceDevelopment> ownPieces = getOwnPiecesOnBoard();
		final List<HantoPieceDevelopment> ownButterfly = new ArrayList<HantoPieceDevelopment>();
		final List<HantoPieceDevelopment> ownSparrows = new ArrayList<HantoPieceDevelopment>();
		final List<HantoPieceDevelopment> ownCrabs = new ArrayList<HantoPieceDevelopment>();
		final List<HantoPieceDevelopment> ownCranes = new ArrayList<HantoPieceDevelopment>();
		final List<HantoPieceDevelopment> ownHorses = new ArrayList<HantoPieceDevelopment>();
		final List<HantoPieceDevelopment> ownDoves = new ArrayList<HantoPieceDevelopment>();
		
		for(HantoPieceDevelopment piece : ownPieces){
			if(piece.getPlayer() == playerColor && piece.getPiece() == BUTTERFLY){
				ownButterfly.add(piece);
			}
			if(piece.getPlayer() == playerColor && piece.getPiece() == SPARROW){
				ownSparrows.add(piece);
			}
			if(piece.getPlayer() == playerColor && piece.getPiece() == CRAB){
				ownCrabs.add(piece);
			}
			if(piece.getPlayer() == playerColor && piece.getPiece() == CRANE){
				ownCranes.add(piece);
			}
			if(piece.getPlayer() == playerColor && piece.getPiece() == HORSE){
				ownHorses.add(piece);
			}
			if(piece.getPlayer() == playerColor && piece.getPiece() == DOVE){
				ownDoves.add(piece);
			}
		}
		List<HantoPieceDevelopment> thatOwnTypePieces = new ArrayList<HantoPieceDevelopment>();
		if(pieceType == BUTTERFLY){
			thatOwnTypePieces = ownButterfly;
		}
		if(pieceType == SPARROW){
			thatOwnTypePieces = ownSparrows;
		}
		if(pieceType == CRAB){
			thatOwnTypePieces = ownCrabs;
		}
		if(pieceType == CRANE){
			thatOwnTypePieces = ownCranes;
		}
		if(pieceType == HORSE){
			thatOwnTypePieces = ownHorses;
		}
		if(pieceType == DOVE){
			thatOwnTypePieces = ownDoves;
		}
		return thatOwnTypePieces;
	}

	/**
	 * This is the helper function to get the available hex for player to place pieces.
	 * 
	 * @return all the coordinate available for player to make a placement
	 */
	public List<HantoCoordinate> getValidPlacements(){
		final List<Coordinate> coordinates = getAvailableCoordinateForPlace();
		final List<HantoCoordinate> validPlacements = new ArrayList<HantoCoordinate>();
		validPlacements.addAll(coordinates);
		return validPlacements;
	}

	/**
	 * generates a list of valid destinations for given piece
	 * @param pieceType Type of the given piece
	 * @param player PlayerColor of the given piece
	 * @param coord Coordinate of the piece's current location
	 * @return a list of HantoCoordinate with all valid destinations for the piece given
	 */
	public List<HantoCoordinate> getValidDestinations(HantoPieceType pieceType, HantoPlayerColor player,
			HantoCoordinate coord){
		List<HantoCoordinate> result = new ArrayList<HantoCoordinate>();
		final boolean butterflyPlaced = gameState.getNumberTypeForPlayer(HantoPieceType.BUTTERFLY, playerColor) == 1;
		if(!butterflyPlaced){
			return result;
		}
		final HantoPieceDevelopment newPiece;
		PieceMoveStrategy moveStrategy = null;
		if(pieceType == HantoPieceType.BUTTERFLY || pieceType == HantoPieceType.CRAB){
			moveStrategy = new WalkStrategy(gameState);
		}
		else if(pieceType == HantoPieceType.SPARROW){
			moveStrategy = new FlyStrategy(gameState);
		}
		else if(pieceType == HantoPieceType.CRANE){
			moveStrategy = new TrapStrategy(gameState);
		}
		else if(pieceType == HantoPieceType.HORSE){
			moveStrategy = new RunStrategy(gameState);
		}
		else if(pieceType == HantoPieceType.DOVE){
			moveStrategy = new JumpStrategy(gameState);
		}
		newPiece = new HantoPieceDevelopment(coord, player, pieceType, moveStrategy);
		if(pieceType == HantoPieceType.BUTTERFLY || pieceType == HantoPieceType.CRAB){
			result = getWalkPieceValidMoveCoordinates(newPiece);
		}
		else if(pieceType == HantoPieceType.SPARROW){
			result = getFlyPieceValidMoveCoordinates(newPiece);
		}
		else if(pieceType == HantoPieceType.CRANE){
			result = getTrapPieceValidMoveCoordinates(newPiece);
		}
		else if(pieceType == HantoPieceType.HORSE){
			result = getRunPieceValidMoveCoordinates(newPiece);
		}
		else if(pieceType == HantoPieceType.DOVE){
			result = getJumpPieceValidMoveCoordinates(newPiece);
		}
		return result;
	}

	/**
	 * Gets all valid destinations for the piece given if it were to walk on board
	 * @param walkPiece piece that is going to walk
	 * @return a list of HantoCoordinates that are valid destinations
	 */
	private List<HantoCoordinate> getWalkPieceValidMoveCoordinates(HantoPieceDevelopment walkPiece){
		final List<HantoCoordinate> walkPieceValidCoordinates = new ArrayList<HantoCoordinate>();
		final Coordinate pieceCoord = Coordinate.guarrenteedCoordinate(walkPiece.getCoordinate());
		final Coordinate[] neighbors = 
				Coordinate.guarrenteedCoordinate(walkPiece.getCoordinate()).getNeighbors();
		for(Coordinate coord : neighbors){
			if(gameState.getBoard().getPieceAt(coord) == null 
					&& walkPiece.getMoveStrategy().canIMove(pieceCoord, coord)){
				walkPieceValidCoordinates.add(coord);
			}
		}
		return walkPieceValidCoordinates;
	}

	/**
	 * This is a helper function to get all available movements for sparrow to move.
	 * 
	 * @param butterfly the player's sparrow piece
	 * @return a list movements for the sparrow to move
	 */
	private List<HantoCoordinate> getFlyPieceValidMoveCoordinates(HantoPieceDevelopment flyPiece){
		final List<HantoCoordinate> flyPieceValidCoordinates = new ArrayList<HantoCoordinate>();
		final Coordinate pieceCoord = Coordinate.guarrenteedCoordinate(flyPiece.getCoordinate());
		final List<Coordinate> allAdjacentCoordsToPieces = getAdjacentCoordinatesToPiecesOnBoard();
		for(int i = 0; i < allAdjacentCoordsToPieces.size(); i++){
			Coordinate targetCoord = allAdjacentCoordsToPieces.get(i);
			if(gameState.getBoard().getPieceAt(targetCoord) == null 
					&& flyPiece.getMoveStrategy().canIMove(pieceCoord, targetCoord)){
				flyPieceValidCoordinates.add(targetCoord);
			}
		}
		return flyPieceValidCoordinates;
	}
	
	/**
	 * This is a helper function to get all available movements for trap pieces to move.
	 * 
	 * @param the trap piece
	 * @return a list movements for the trap piece to move
	 */
	private List<HantoCoordinate> getTrapPieceValidMoveCoordinates(HantoPieceDevelopment trapPiece){
		final List<HantoCoordinate> trapPieceValidMoves = new ArrayList<HantoCoordinate>();
		final Coordinate pieceCoord = Coordinate.guarrenteedCoordinate(trapPiece.getCoordinate());
		final Coordinate[] neighbors = 
				Coordinate.guarrenteedCoordinate(trapPiece.getCoordinate()).getNeighbors();
		for(Coordinate coord : neighbors){
			if(trapPiece.getMoveStrategy().canIMove(pieceCoord, coord)){
				trapPieceValidMoves.add(coord);
			}
		}
		return trapPieceValidMoves;
	}
	
	/**
	 * This is a helper function to get all available movements for run piece to move.
	 * 
	 * @param the run piece
	 * @return a list movements for the run piece to move
	 */
	private List<HantoCoordinate> getRunPieceValidMoveCoordinates(HantoPieceDevelopment runPiece){
		final List<HantoCoordinate> runPieceValidMoves = new ArrayList<HantoCoordinate>();
		final Coordinate pieceCoord = Coordinate.guarrenteedCoordinate(runPiece.getCoordinate());
		final List<HantoCoordinate> coordsWithinRunRange = withinNSteps(pieceCoord, 3);
		for(HantoCoordinate coordInRange : coordsWithinRunRange){
			if(runPiece.getMoveStrategy().canIMove(pieceCoord, Coordinate.guarrenteedCoordinate(coordInRange))){
				runPieceValidMoves.add(coordInRange);
			}
		}
		return runPieceValidMoves;
	}
	
	/**
	 * This is a helper function to get all available movements for jump piece to move.
	 * 
	 * @param the jump piece
	 * @return a list movements for the jump piece to move
	 */
	private List<HantoCoordinate> getJumpPieceValidMoveCoordinates(HantoPieceDevelopment jumpPiece){
		final List<HantoCoordinate> jumpPieceValidMoves = new ArrayList<HantoCoordinate>();
		final Coordinate pieceCoord = Coordinate.guarrenteedCoordinate(jumpPiece.getCoordinate());
		final List<Coordinate> allAdjacentCoordsToPieces = getAdjacentCoordinatesToPiecesOnBoard();
		for(int i = 0; i < allAdjacentCoordsToPieces.size(); i++){
			Coordinate targetCoord = allAdjacentCoordsToPieces.get(i);
			if(gameState.getBoard().getPieceAt(targetCoord) == null 
					&& jumpPiece.getMoveStrategy().canIMove(pieceCoord, targetCoord)){
				jumpPieceValidMoves.add(targetCoord);
			}
		}
		return jumpPieceValidMoves;
	}

	/**
	 * This is a helper function to get all available movements for butterfly or crab to move.
	 * 
	 * @param butterfly the player's butterfly or Crab piece
	 * @return a list movements for the butterfly to move
	 */
	private List<HantoMoveRecord> getWalkPieceValidMoveDestination(HantoPieceDevelopment walkPiece){
		final List<HantoMoveRecord> walkPieceValidMoves = new ArrayList<HantoMoveRecord>();
		final Coordinate pieceCoord = Coordinate.guarrenteedCoordinate(walkPiece.getCoordinate());
		final Coordinate[] neighbors = 
				Coordinate.guarrenteedCoordinate(walkPiece.getCoordinate()).getNeighbors();
		for(Coordinate coord : neighbors){
			if(gameState.getBoard().getPieceAt(coord) == null 
					&& walkPiece.getMoveStrategy().canIMove(pieceCoord, coord)){
				final HantoMoveRecord move = new HantoMoveRecord(walkPiece.getPiece(), pieceCoord, coord);
				walkPieceValidMoves.add(move);
			}
		}
		return walkPieceValidMoves;
	}

	/**
	 * This is a helper function to get all available movements for fly piece to move.
	 * 
	 * @param the fly piece
	 * @return a list movements for the fly piece to move
	 */
	private List<HantoMoveRecord> getFlyPieceValidMoveDestination(HantoPieceDevelopment flyPiece){
		final List<HantoMoveRecord> flyPieceValidMoves = new ArrayList<HantoMoveRecord>();
		final Coordinate pieceCoord = Coordinate.guarrenteedCoordinate(flyPiece.getCoordinate());
		final List<Coordinate> allAdjacentCoordsToPieces = getAdjacentCoordinatesToPiecesOnBoard();
		for(int i = 0; i < allAdjacentCoordsToPieces.size(); i++){
			Coordinate targetCoord = allAdjacentCoordsToPieces.get(i);
			if(gameState.getBoard().getPieceAt(targetCoord) == null 
					&& flyPiece.getMoveStrategy().canIMove(pieceCoord, targetCoord)){
				final HantoMoveRecord move = 
						new HantoMoveRecord(flyPiece.getPiece(), pieceCoord, targetCoord);
				flyPieceValidMoves.add(move);
			}
		}
		return flyPieceValidMoves;
	}
	
	/**
	 * This is a helper function to get all available movements for trap pieces to move.
	 * 
	 * @param the trap piece
	 * @return a list movements for the trap piece to move
	 */
	private List<HantoMoveRecord> getTrapPieceValidMoveDestination(HantoPieceDevelopment trapPiece){
		final List<HantoMoveRecord> trapPieceValidMoves = new ArrayList<HantoMoveRecord>();
		final Coordinate pieceCoord = Coordinate.guarrenteedCoordinate(trapPiece.getCoordinate());
		final Coordinate[] neighbors = 
				Coordinate.guarrenteedCoordinate(trapPiece.getCoordinate()).getNeighbors();
		for(Coordinate coord : neighbors){
			if(trapPiece.getMoveStrategy().canIMove(pieceCoord, coord)){
				final HantoMoveRecord move = new HantoMoveRecord(trapPiece.getPiece(), pieceCoord, coord);
				trapPieceValidMoves.add(move);
			}
		}
		return trapPieceValidMoves;
	}
	
	/**
	 * This is a helper function to get all available movements for run piece to move.
	 * 
	 * @param the run piece
	 * @return a list movements for the run piece to move
	 */
	private List<HantoMoveRecord> getRunPieceValidMoveDestination(HantoPieceDevelopment runPiece){
		final List<HantoMoveRecord> runPieceValidMoves = new ArrayList<HantoMoveRecord>();
		final Coordinate pieceCoord = Coordinate.guarrenteedCoordinate(runPiece.getCoordinate());
		final List<HantoCoordinate> coordsWithinRunRange = withinNSteps(pieceCoord, 3);
		for(HantoCoordinate coordInRange : coordsWithinRunRange){
			if(runPiece.getMoveStrategy().canIMove(pieceCoord, Coordinate.guarrenteedCoordinate(coordInRange))){
				final HantoMoveRecord move = new HantoMoveRecord(runPiece.getPiece(), pieceCoord, coordInRange);
				runPieceValidMoves.add(move);
			}
		}
		return runPieceValidMoves;
	}
	
	/**
	 * This is a helper function to get all available movements for jump piece to move.
	 * 
	 * @param the jump piece
	 * @return a list movements for the jump piece to move
	 */
	private List<HantoMoveRecord> getJumpPieceValidMoveDestination(HantoPieceDevelopment jumpPiece){
		final List<HantoMoveRecord> jumpPieceValidMoves = new ArrayList<HantoMoveRecord>();
		final Coordinate pieceCoord = Coordinate.guarrenteedCoordinate(jumpPiece.getCoordinate());
		final List<Coordinate> allAdjacentCoordsToPieces = getAdjacentCoordinatesToPiecesOnBoard();
		for(int i = 0; i < allAdjacentCoordsToPieces.size(); i++){
			Coordinate targetCoord = allAdjacentCoordsToPieces.get(i);
			if(gameState.getBoard().getPieceAt(targetCoord) == null 
					&& jumpPiece.getMoveStrategy().canIMove(pieceCoord, targetCoord)){
				final HantoMoveRecord move = 
						new HantoMoveRecord(jumpPiece.getPiece(), pieceCoord, targetCoord);
				jumpPieceValidMoves.add(move);
			}
		}
		return jumpPieceValidMoves;
	}
	
	/**
	 * This is the helper function to get all the coordinate within n steps from the given
	 * coordinate.
	 * 
	 * @param the given coordinate
	 * @param n steps from the give coordinate
	 * return a list of coordinates within n steps from the given coordiante
	 */
	private List<HantoCoordinate> withinNSteps(HantoCoordinate coord, int n){
		final List<HantoCoordinate> result = new ArrayList<HantoCoordinate>();
		final int x = coord.getX();
		final int y = coord.getY();
		for(int i = x - n; i <= x + n; i++){
			for(int j = y - n; j <= y + n; j++){
				final HantoCoordinate coordWithinRange = new Coordinate(i, j);
				result.add(coordWithinRange);
			}
		}
		return result;
	}

	/**
	 * Generate all valid placement locations for the given piece type
	 * @param pieceType the piece type to check
	 * @return a list of HantoCoordinate that are valid placement locations for the given type
	 */
	public List<HantoCoordinate> generatePiecesValidPlacements(HantoPieceType pieceType){
		List<HantoCoordinate> validPlacements = new ArrayList<HantoCoordinate>();
		final List<HantoCoordinate> validButterflyPlacements = new ArrayList<HantoCoordinate>();
		final List<HantoCoordinate> validSparrowPlacements = new ArrayList<HantoCoordinate>();
		final List<HantoCoordinate> validCrabPlacements = new ArrayList<HantoCoordinate>();
		final List<HantoCoordinate> validCranePlacements = new ArrayList<HantoCoordinate>();
		final List<HantoCoordinate> validHorsePlacements = new ArrayList<HantoCoordinate>();
		final List<HantoCoordinate> validDovePlacements = new ArrayList<HantoCoordinate>();

		final List<Coordinate> validPlaceCoords = getAvailableCoordinateForPlace();

		validButterflyPlacements.addAll(validPlaceCoords);
		validSparrowPlacements.addAll(validPlaceCoords);
		validCrabPlacements.addAll(validPlaceCoords);
		validCranePlacements.addAll(validPlaceCoords);
		validHorsePlacements.addAll(validPlaceCoords);
		validDovePlacements.addAll(validPlaceCoords);

		final int pieceTypeNum = gameState.getNumberTypeForPlayer(pieceType, playerColor);
		if(pieceType == BUTTERFLY && pieceTypeNum != 1){
			validPlacements = validButterflyPlacements;
		}
		else if(pieceType == SPARROW && pieceTypeNum != 4){
			validPlacements = validSparrowPlacements;
		}
		else if(pieceType == CRAB && pieceTypeNum != 4){
			validPlacements = validCrabPlacements;
		}
		else if(pieceType == CRANE && pieceTypeNum != 4){
			validPlacements = validCranePlacements;
		}
		else if(pieceType == HORSE && pieceTypeNum != 4){
			validPlacements = validHorsePlacements;
		}
		else if(pieceType == DOVE && pieceTypeNum != 4){
			validPlacements = validDovePlacements;
		}
		return validPlacements;
	}

	/**
	 * getter for current player color
	 * @return play color of current turn
	 */
	public HantoPlayerColor getPlayerColor() {
		return playerColor;
	}

	/**
	 * getter for current game state
	 * @return game state
	 */
	public HantoStateDevelopment getGameState() {
		return gameState;
	}

}

