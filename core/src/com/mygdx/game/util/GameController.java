package com.mygdx.game.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
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
import com.mygdx.hanto.util.HantoPieceType;
import com.mygdx.hanto.util.HantoPlayerColor;

public class GameController {
	
    private static DragAndDrop dragAndDrop = new DragAndDrop();
    private static Map<HantoCoordinate, ImageButton> yellows = new HashMap<HantoCoordinate, ImageButton>();;
    private static Map<HantoCoordinate, ImageButton> blues = new HashMap<HantoCoordinate, ImageButton>();
    public static GameStateHandler gameHandler;
    
    public static DragAndDrop getDragAndDrop(){
    	return dragAndDrop;
    }

	public static void addTouchAndDrag(final Stage stage, final String type, ImageButton sourceImage, final HantoCoordinate from, List<Pixels> validPositions) {
        final boolean isYellow = Hanto.gameInstance.getGameState().getPlayerOnMove() == HantoPlayerColor.RED;
        final String name;
        if(isYellow){
        	name = "yellow" + type;
        }
        else{
        	name = "blue" + type;
        }
		
		stage.addActor(sourceImage);
        final Group validGroups = new Group();
        
        if(gameHandler.getGameState().getTurnNum() == 1){
        	if(gameHandler.getGameState().getPlayerOnMove() == HantoPlayerColor.BLUE){
        		validPositions.add(CoordinatesToPixels.getInitialPixelsForBlue());
        	}
        	else{
        		validPositions.add(CoordinatesToPixels.getInitialPixelsForYellow());
        	}
        }
		
		final Image validTargetImage = new Image(Assets.pieceSkin, "blank");
		for(Pixels pixels : validPositions){
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
				payload.setObject(name);
				
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
				final ImageButton newPiece = new ImageButton(Assets.pieceSkin, (String) payload.getObject());
				newPiece.setSize(Constants.TILE_LENGTH, Constants.TILE_LENGTH);
				final int xPixel = (int) validTargetImage.getX();
				final int yPixel = (int) validTargetImage.getY();
				newPiece.setPosition(xPixel, yPixel);
				final HantoCoordinate to = CoordinatesToPixels.convertPixelsToCoordinates(new Pixels(xPixel, yPixel));
				if(isYellow){
					yellows.put(to, newPiece);
				}
				else{
					blues.put(to, newPiece);
				}

				final HantoPieceType pieceType;
				if(type.equals("Butterfly")){
					pieceType = HantoPieceType.BUTTERFLY;
				}
				else if(type.equals("Crab")){
					pieceType = HantoPieceType.CRAB;
				}
				else if(type.equals("Sparrow")){
					pieceType = HantoPieceType.SPARROW;
				}
				else{
					pieceType = null;
				}
				try {
					Hanto.gameInstance.makeMove(pieceType, from, to);
				} catch (HantoException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				
				newPiece.addListener(new ClickListener(){
		            @Override
		            public void clicked(InputEvent event, float x, float y) {
		            	List<HantoCoordinate> availableMoves = gameHandler.getValidDestinations(pieceType, gameHandler.getGameState().getPlayerOnMove(), to);
		            	List<Pixels> availablePixels = new ArrayList<Pixels>();
		            	for(HantoCoordinate coord : availableMoves){
		            		availablePixels.add(CoordinatesToPixels.convertCooridnatesToPixels(coord));
		            	}
		            	addTouchAndDrag(stage, type, newPiece, to, availablePixels);
		            }
		        });
				
				validGroups.remove();
				stage.addActor(newPiece);
			}
		});
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
}
