package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.mygdx.game.Assets;

public class GameController {
	
    public static DragAndDrop dragAndDrop = new DragAndDrop();

	public static void addTouchAndDrag(final Stage stage, final Stage targetStage, final String name) {
        Assets.pieceSkin.add("position", new Texture("hexTiles/testTile.png"));
        ImageButton sourceImage = new ImageButton(Assets.pieceSkin, name);
		sourceImage.setBounds(0, 0, 200, 200);
		stage.addActor(sourceImage);
		
		Image validTargetImage = new Image(Assets.pieceSkin, "position");
		//validTargetImage.setBounds(Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight()/2 - 100, 200, 200);
		//stage.addActor(validTargetImage);
		validTargetImage.setBounds(300, 300, 150, 150);
		targetStage.addActor(validTargetImage);
		
		Image invalidTargetImage = new Image(Assets.pieceSkin, "position");
		//invalidTargetImage.setBounds(Gdx.graphics.getWidth()/2 - 300, Gdx.graphics.getHeight()/2 - 100, 200, 200);
		//stage.addActor(invalidTargetImage);
		validTargetImage.setBounds(150, 150, 150, 150);
		targetStage.addActor(invalidTargetImage);
		
		dragAndDrop = new DragAndDrop();
		dragAndDrop.addSource(new Source(sourceImage) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("Butterfly");
				
				ImageButton draggable = new ImageButton(Assets.pieceSkin, name);
				draggable.setSize(200, 200);
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
				ImageButton newPiece = new ImageButton(Assets.pieceSkin, name);
				newPiece.setSize(200, 200);
				newPiece.setPosition(Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight()/2 - 100);
				stage.addActor(newPiece);
			}
		});
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
	}
}
