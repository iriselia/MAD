package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Represents a piece
 * 
 * @author Peng Ren
 */
public class Piece extends Actor {

	public boolean isSelected;

	private final TextureRegion textureRegion;

	public Piece(int x, int y, boolean isRed) {
		this.setBounds(x, y, 1, 1);	
		if (isRed) {
			this.textureRegion = Assets.gameAtlas.findRegion("");
		} else {
			this.textureRegion = Assets.gameAtlas.findRegion("");
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(this.textureRegion, this.getX(), this.getY(), 1, 1);
	}

}
