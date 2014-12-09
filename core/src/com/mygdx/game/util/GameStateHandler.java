package com.mygdx.game.util;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.hanto.util.HantoPieceType.*;
import com.mygdx.hanto.implementation.common.Coordinate;
import com.mygdx.hanto.implementation.common.HantoPiece;
import com.mygdx.hanto.implementation.common.PieceMoveStrategy;
import com.mygdx.hanto.implementation.core.HantoPieceDevelopment;
import com.mygdx.hanto.implementation.core.HantoStateDevelopment;
import com.mygdx.hanto.implementation.core.movestrategy.FlyStrategy;
import com.mygdx.hanto.implementation.core.movestrategy.WalkStrategy;
import com.mygdx.hanto.util.HantoCoordinate;
import com.mygdx.hanto.util.HantoMoveRecord;
import com.mygdx.hanto.util.HantoPieceType;
import com.mygdx.hanto.util.HantoPlayerColor;

/**
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
		
		final List<HantoMoveRecord> butterflyValidMoves = new ArrayList<HantoMoveRecord>();
		final List<HantoMoveRecord> crabValidMoves = new ArrayList<HantoMoveRecord>();
		final List<HantoMoveRecord> sparrowValidMoves = new ArrayList<HantoMoveRecord>();
		
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
		
		if(pieceType == BUTTERFLY){
			validMovements = butterflyValidMoves;
		}
		if(pieceType == SPARROW){
			validMovements = sparrowValidMoves;
		}
		if(pieceType == CRAB){
			validMovements = crabValidMoves;
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
				final HantoPieceDevelopment piece = 
						(HantoPieceDevelopment) gameState.getBoard().getPieceAt(neighbor);
				if(piece != null){
					valid = false;
				}
				else{
					final Coordinate[] secNeighbors = neighbor.getNeighbors();
					for(Coordinate secNeighbor : secNeighbors){
						final HantoPieceDevelopment secNeighborPiece = 
								(HantoPieceDevelopment) gameState.getBoard().getPieceAt(secNeighbor);
						if(secNeighborPiece != null 
								&& secNeighborPiece.getPlayer() != gameState.getPlayerOnMove()){
							valid = false;
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
		return thatOwnTypePieces;
	}
	
	public List<HantoCoordinate> getValidDestinations(HantoPieceType pieceType, HantoPlayerColor player,
			HantoCoordinate coord){
		List<HantoCoordinate> result = null;
		final HantoPieceDevelopment newPiece;
		PieceMoveStrategy moveStrategy = null;
		if(pieceType == HantoPieceType.BUTTERFLY || pieceType == HantoPieceType.CRAB){
			moveStrategy = new WalkStrategy(gameState);
		}
		else if(pieceType == HantoPieceType.SPARROW){
			moveStrategy = new FlyStrategy(gameState);
		}
		newPiece = new HantoPieceDevelopment(coord, player, pieceType, moveStrategy);
		if(pieceType == HantoPieceType.BUTTERFLY || pieceType == HantoPieceType.CRAB){
			result = getWalkPieceValidMoveCoordinates(newPiece);
		}
		else if(pieceType == HantoPieceType.SPARROW){
			result = getFlyPieceValidMoveCoordinates(newPiece);
		}
		return result;
	}
	
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
				HantoMoveRecord move = new HantoMoveRecord(walkPiece.getPiece(), pieceCoord, coord);
				walkPieceValidMoves.add(move);
			}
		}
		return walkPieceValidMoves;
	}
	
	/**
	 * This is a helper function to get all available movements for sparrow to move.
	 * 
	 * @param butterfly the player's sparrow piece
	 * @return a list movements for the sparrow to move
	 */
	private List<HantoMoveRecord> getFlyPieceValidMoveDestination(HantoPieceDevelopment flyPiece){
		final List<HantoMoveRecord> flyPieceValidMoves = new ArrayList<HantoMoveRecord>();
		final Coordinate pieceCoord = Coordinate.guarrenteedCoordinate(flyPiece.getCoordinate());
		final List<Coordinate> allAdjacentCoordsToPieces = getAdjacentCoordinatesToPiecesOnBoard();
		for(int i = 0; i < allAdjacentCoordsToPieces.size(); i++){
			Coordinate targetCoord = allAdjacentCoordsToPieces.get(i);
			if(gameState.getBoard().getPieceAt(targetCoord) == null 
					&& flyPiece.getMoveStrategy().canIMove(pieceCoord, targetCoord)){
				HantoMoveRecord move = 
						new HantoMoveRecord(flyPiece.getPiece(), pieceCoord, targetCoord);
				flyPieceValidMoves.add(move);
			}
		}
		return flyPieceValidMoves;
	}

	public HantoPlayerColor getPlayerColor() {
		return playerColor;
	}

	public HantoStateDevelopment getGameState() {
		return gameState;
	}
	
}

