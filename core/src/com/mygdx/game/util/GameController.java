package com.mygdx.game.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Assets;
import com.mygdx.game.Hanto;
import com.mygdx.game.util.DragAndDrop.Payload;
import com.mygdx.game.util.DragAndDrop.Source;
import com.mygdx.game.util.DragAndDrop.Target;
import com.mygdx.hanto.common.HantoException;
import com.mygdx.hanto.util.HantoCoordinate;
import com.mygdx.hanto.util.HantoMoveRecord;
import com.mygdx.hanto.util.HantoPieceType;
import com.mygdx.hanto.util.HantoPlayerColor;

public class GameController {

	private static DragAndDrop dragAndDrop = new DragAndDrop();
	private static Map<ImageButton, HantoMoveRecord> buttonsColor = new HashMap<ImageButton, HantoMoveRecord>();
	public static GameStateHandler gameHandlerYellow;
	public static GameStateHandler gameHandlerBlue;
	public static boolean ifADragCompleted = true;

	public static DragAndDrop getDragAndDrop(){
		return dragAndDrop;
	}

	public static void addTouchAndDrag(final Stage stage, final ImageButton sourceImage, final HantoCoordinate from, List<Pixels> validPositions) {
		ifADragCompleted = false;
		final String name = sourceImage.getName();
		final boolean isYellow = Hanto.gameInstance.getGameState().getPlayerOnMove() == HantoPlayerColor.RED;

		final Group validGroups = new Group();

		if(Hanto.gameInstance.getGameState().getTurnNum() == 1){
			if(!isYellow){
				validPositions.add(CoordinatesToPixels.getInitialPixelsForBlue());
			}
			else{
				validPositions.add(CoordinatesToPixels.getInitialPixelsForYellow());
			}
		}
		
		if(validPositions.size() == 0){
			GameController.ifADragCompleted = true;
			return;
		}

		for(Pixels pixels : validPositions){
			final Image validTargetImage = new Image(Assets.pieceSkin, "blank");
			validTargetImage.setBounds(pixels.x, pixels.y, Constants.TILE_LENGTH, Constants.TILE_LENGTH);
			validGroups.addActor(validTargetImage);
		}
		stage.addActor(validGroups);

		//Image invalidTargetImage = new Image(Assets.pieceSkin, "position");
		//invalidTargetImage.setBounds(invalidPosition.x, invalidPosition.y, GameScreen.TILE_LENGTH, GameScreen.TILE_LENGTH);
		//stage.addActor(invalidTargetImage);
		
		dragAndDrop.addSource(new Source(sourceImage) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject(sourceImage.getName());

				ImageButton draggable = new ImageButton(Assets.pieceSkin, name);
				draggable.setSize(Constants.TILE_LENGTH, Constants.TILE_LENGTH);
				payload.setDragActor(draggable);

				ImageButton validImage = new ImageButton(Assets.pieceSkin, name);
				validImage.setColor(0, 1, 0, 1);
				payload.setValidDragActor(validImage);

				ImageButton invalidImage = new ImageButton(Assets.pieceSkin, name);
				invalidImage.setColor(1, 0, 0, 1);
				payload.setInvalidDragActor(invalidImage);

				return payload;
			}
		});

		for(final Actor validTargetImage : validGroups.getChildren()){
			dragAndDrop.addTarget(new Target(validTargetImage) {
				public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
					getActor().setColor(Color.GREEN);
					return true;
				}

				public void reset (Source source, Payload payload) {
					getActor().setColor(Color.WHITE);
					//validGroups.remove();
					//ifADragCompleted = true;
				}

				public void drop (Source source, Payload payload, float x, float y, int pointer) {
					System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);
					final String name = (String) payload.getObject(); 
					final ImageButton newPiece = new ImageButton(Assets.pieceSkin, name);
					newPiece.setName(name);
					newPiece.setSize(Constants.TILE_LENGTH, Constants.TILE_LENGTH);
					final int xPixel = (int) validTargetImage.getX();
					final int yPixel = (int) validTargetImage.getY();
					newPiece.setPosition(xPixel, yPixel);
					stage.addActor(newPiece);

					if(from != null){
						source.getActor().remove();
					}
					final HantoCoordinate to = CoordinatesToPixels.convertPixelsToCoordinates(new Pixels(xPixel, yPixel));

					final HantoPieceType pieceType;
					if(name.contains("Butterfly")){
						pieceType = HantoPieceType.BUTTERFLY;
					}
					else if(name.contains("Crab")){
						pieceType = HantoPieceType.CRAB;
					}
					else if(name.contains("Sparrow")){
						pieceType = HantoPieceType.SPARROW;
					}
					else{
						pieceType = null;
					}

					final HantoMoveRecord newPieceRecord = new HantoMoveRecord(pieceType, from, to, Hanto.gameInstance.getGameState().getPlayerOnMove());
					buttonsColor.put(newPiece, newPieceRecord);

					try {
						Hanto.gameInstance.makeMove(pieceType, from, to);
					} catch (HantoException e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}

					newPiece.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y) {
							if(GameController.ifADragCompleted){
								final HantoMoveRecord piece = buttonsColor.get(newPiece);
								final HantoCoordinate to = piece.getTo();
								final HantoPlayerColor color = piece.getColor();
								final HantoPieceType pieceType = piece.getPiece();
								if(color == Hanto.gameInstance.getGameState().getPlayerOnMove()){
									List<HantoCoordinate> availableMoves = getProperGameStateHandler(color).getValidDestinations(pieceType, color, to);
									List<Pixels> availablePixels = new ArrayList<Pixels>();
									for(HantoCoordinate coord : availableMoves){
										availablePixels.add(CoordinatesToPixels.convertCooridnatesToPixels(coord));
									}
									addTouchAndDrag(stage, newPiece, to, availablePixels);
								}
							}
						}
					});

					validGroups.remove();
					stage.addActor(newPiece);
					ifADragCompleted = true;
				}
			});
		}
		/*
		dragAndDrop.addTarget(new Target(invalidTargetImage) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				getActor().setColor(Color.RED);
				return false;
			}

			public void reset (Source source, Payload payload) {
				getActor().setColor(Color.WHITE);
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			}
		});
		 */
	}
	
	private static GameStateHandler getProperGameStateHandler(HantoPlayerColor color){
		GameStateHandler result = null;
		if(color == HantoPlayerColor.RED){
			result = gameHandlerYellow;
		}
		else if(color == HantoPlayerColor.BLUE){
			result = gameHandlerBlue;
		}
		return result;
	}
	
	public static List<Pixels> generatePlacePixels(HantoPieceType pieceType){
		final List<Pixels> pixelsList = new ArrayList<Pixels>();
		final HantoPlayerColor player = Hanto.gameInstance.getGameState().getPlayerOnMove();
		final List<HantoCoordinate> validPlacements = getProperGameStateHandler(player).generatePiecesValidPlacements(pieceType);
		for(HantoCoordinate coord : validPlacements){
			final Pixels pixels = CoordinatesToPixels.convertCooridnatesToPixels(coord);
			pixelsList.add(pixels);
		}
		return pixelsList;
	}
}
