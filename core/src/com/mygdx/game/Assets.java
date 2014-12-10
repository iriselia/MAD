package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.util.Constants;


/**
 * Responsible for loading and disposing of all game assets.
 * 
 * @author Peng Ren
 */
public class Assets {

	public static Skin hantoSkin;
	public static TextureAtlas hantoAtlas;
	public static TextureRegionDrawable[] helpImages;

	
	public static void loadAssets() {
		hantoAtlas = new TextureAtlas("ui/pieces.pack");
		hantoSkin = new Skin(Gdx.files.internal("ui/gameSkin.json"), hantoAtlas);
	}
	
	public static void loadHelp() {
		helpImages = new TextureRegionDrawable[Constants.numberOfHelpImages];
		for (int i = 0; i < Constants.numberOfHelpImages; i++) {
			helpImages[i] = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("help/" + i + ".png"))));
		}
		//helpImages[0] = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("help/1.png"))));
		//helpImages[1] = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("help/2.png"))));
		//helpImages[2] = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("help/3.png"))));
		//helpImages[3] = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("help/4.png"))));
		
	}

	public static void disposeAssets() {
		hantoAtlas.dispose();
		hantoSkin.dispose();
	}
}
