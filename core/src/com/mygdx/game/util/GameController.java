package com.mygdx.game.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
import com.mygdx.hanto.util.MoveResult;
import com.mygdx.screen.ResultScreen;

public class GameController {

	private static DragAndDrop dragAndDrop = new DragAndDrop();
	private static Map<ImageButton, HantoMoveRecord> buttonsColor = new HashMap<ImageButton, HantoMoveRecord>();
	private static Map<HantoCoordinate, ImageButton> aIPieces = new HashMap<HantoCoordinate, ImageButton>();
	public static GameStateHandler gameHandlerYellow;
	public static GameStateHandler gameHandlerBlue;
	private static Group validGroups = new Group();
	public static Image warningButterfly = new Image(new Texture("tags/butterflymustbeplaced.png"));

	public static DragAndDrop getDragAndDrop(){
		return dragAndDrop;
	}

	public static void addTouchAndDrag(final Stage stage, final ImageButton sourceImage, final HantoCoordinate from, List<Pixels> validPositions) {
		final String name = sourceImage.getName();
		final boolean isYellow = Hanto.gameInstance.getGameState().getPlayerOnMove() == HantoPlayerColor.RED;

		validGroups = new Group();

		if(Hanto.gameInstance.getGameState().getTurnNum() == 1){
			if(!isYellow){
				validPositions.add(CoordinatesToPixels.getInitialPixelsForBlue());
			}
			else{
				validPositions.addAll(CoordinatesToPixels.getInitialPixelsForYellow());
			}
		}
		if(Hanto.gameInstance.getGameState().getTurnNum() == 4 
				&& !(Hanto.gameInstance.getGameState().getNumberTypeForPlayer(HantoPieceType.BUTTERFLY, Hanto.gameInstance.getGameState().getPlayerOnMove()) == 1)
				&& !name.contains("Butterfly")){
			stage.addActor(warningButterfly);
			warningButterfly.setBounds(stage.getCamera().position.x - Constants.w / 5, stage.getCamera().position.y, Constants.w / 2, Constants.h / 5);
			warningButterfly.setColor(Color.RED);
			return;
		}

		if(validPositions.size() == 0){
			return;
		}

		for(Pixels pixels : validPositions){
			final Image validTargetImage = new Image(Assets.hantoSkin, "blank");
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

				ImageButton draggable = new ImageButton(Assets.hantoSkin, name);
				draggable.setSize(Constants.TILE_LENGTH, Constants.TILE_LENGTH);
				payload.setDragActor(draggable);

				ImageButton validImage = new ImageButton(Assets.hantoSkin, name);
				validImage.setColor(0, 1, 0, 1);
				payload.setValidDragActor(validImage);

				ImageButton invalidImage = new ImageButton(Assets.hantoSkin, name);
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
				}

				public void drop (Source source, Payload payload, float x, float y, int pointer) {
					System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);
					final String name = (String) payload.getObject(); 
					final ImageButton newPiece = new ImageButton(Assets.hantoSkin, name);
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
					else if(name.contains("Crane")){
						pieceType = HantoPieceType.CRANE;
					}
					else if(name.contains("Horse")){
						pieceType = HantoPieceType.HORSE;
					}
					else if(name.contains("Dove")){
						pieceType = HantoPieceType.DOVE;
					}
					else{
						pieceType = null;
					}

					final HantoMoveRecord newPieceRecord = new HantoMoveRecord(pieceType, from, to, Hanto.gameInstance.getGameState().getPlayerOnMove());
					buttonsColor.put(newPiece, newPieceRecord);

					try {
						MoveResult moveResult = Hanto.gameInstance.makeMove(pieceType, from, to);
						if(moveResult == MoveResult.BLUE_WINS){
							((Game) Gdx.app.getApplicationListener()).setScreen(new ResultScreen("blue"));
						}
						else if(moveResult == MoveResult.RED_WINS){
							((Game) Gdx.app.getApplicationListener()).setScreen(new ResultScreen("yellow"));
						}
						else if(moveResult == MoveResult.DRAW){
							((Game) Gdx.app.getApplicationListener()).setScreen(new ResultScreen("draw"));
						}
					} catch (HantoException e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}

					newPiece.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y) {
							clearAll();
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
					});

					validGroups.remove();
					stage.addActor(newPiece);
					if(Hanto.ifPlayWithAI){
						final HantoMoveRecord AIMovement = Hanto.AIPlayer.makeMove(newPieceRecord);
						try {
							final MoveResult aiMoveResult = Hanto.gameInstance.makeMove(AIMovement.getPiece(), AIMovement.getFrom(), AIMovement.getTo());
							if(aiMoveResult == MoveResult.BLUE_WINS){
								((Game) Gdx.app.getApplicationListener()).setScreen(new ResultScreen("blue"));
							}
							else if(aiMoveResult == MoveResult.RED_WINS){
								((Game) Gdx.app.getApplicationListener()).setScreen(new ResultScreen("yellow"));
							}
							else if(aiMoveResult == MoveResult.DRAW){
								((Game) Gdx.app.getApplicationListener()).setScreen(new ResultScreen("draw"));
							}
						} catch (HantoException e) {
							System.out.println(e.getMessage());
							e.printStackTrace();
						}
						final Pixels aiPiecePlace = CoordinatesToPixels.convertCooridnatesToPixels(AIMovement.getTo());
						final ImageButton aiButton = generateAIPieceButton(AIMovement.getPiece(), AIMovement.getColor(), AIMovement.getTo());
						aIPieces.put(AIMovement.getTo(), aiButton);
						if(AIMovement.getFrom() != null){
							final ImageButton fromButton = aIPieces.get(AIMovement.getFrom());
							fromButton.remove();
						}
						stage.addActor(aiButton);
						aiButton.setBounds(aiPiecePlace.x, aiPiecePlace.y, Constants.TILE_LENGTH, Constants.TILE_LENGTH);
					}
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

	public static void clearAll(){
		validGroups.remove();
	}

	private static ImageButton generateAIPieceButton(HantoPieceType type, HantoPlayerColor player, HantoCoordinate coordinate){
		final String buttonAsset;
		if(player == HantoPlayerColor.RED){
			if(type == HantoPieceType.BUTTERFLY){
				buttonAsset = "yellowButterfly";
			}
			else if(type == HantoPieceType.CRAB){
				buttonAsset = "yellowCrab";
			}
			else if(type == HantoPieceType.SPARROW){
				buttonAsset = "yellowSparrow";
			}
			else if(type == HantoPieceType.CRANE){
				buttonAsset = "yellowCrane";
			}
			else if(type == HantoPieceType.HORSE){
				buttonAsset = "yellowHorse";
			}
			else if(type == HantoPieceType.DOVE){
				buttonAsset = "yellowDove";
			}
			else{
				buttonAsset = "";
			}
		}
		else{
			if(type == HantoPieceType.BUTTERFLY){
				buttonAsset = "blueButterfly";
			}
			else if(type == HantoPieceType.CRAB){
				buttonAsset = "blueCrab";
			}
			else if(type == HantoPieceType.SPARROW){
				buttonAsset = "blueSparrow";
			}
			else if(type == HantoPieceType.CRANE){
				buttonAsset = "blueCrane";
			}
			else if(type == HantoPieceType.HORSE){
				buttonAsset = "blueHorse";
			}
			else if(type == HantoPieceType.DOVE){
				buttonAsset = "blueDove";
			}
			else{
				buttonAsset = "";
			}
		}
		return new ImageButton(Assets.hantoSkin, buttonAsset);		
	}

}
