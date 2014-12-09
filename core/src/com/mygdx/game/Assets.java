package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


/**
 * Responsible for loading and disposing of all game assets.
 * 
 * @author Peng Ren
 */
public class Assets {

	public static Skin menuSkin;
	public static Skin pieceSkin;
	public static TextureAtlas menuAtlas;
	public static TextureAtlas gameAtlas;
	public static TextureAtlas pieceAtlas;
	public static TextureRegionDrawable[] helpImages;

	public static void loadMainMenuOrSettings() {
		menuAtlas = new TextureAtlas("ui/atlas.pack");
		menuSkin = new Skin(Gdx.files.internal("ui/mainmenuSkin.json"), menuAtlas);
	}
	
	public static void loadPieces() {
		pieceAtlas = new TextureAtlas("ui/pieces.pack");
		pieceSkin = new Skin(Gdx.files.internal("ui/gameSkin.json"), pieceAtlas);
	}
	
	public static void loadHelp() {
		helpImages = new TextureRegionDrawable[4];
		helpImages[0] = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("help/1.png"))));
		helpImages[1] = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("help/2.png"))));
		helpImages[2] = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("help/3.png"))));
		helpImages[3] = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("help/4.png"))));
	}

	public static void disposeMainMenuOrSettings() {
		menuAtlas.dispose();
		menuSkin.dispose();
	}
	
	public static void disposePieces() {
		pieceAtlas.dispose();
		pieceSkin.dispose();
	}

	public static void loadGame() {
		gameAtlas = new TextureAtlas(Gdx.files.internal(""));
		menuSkin = new Skin(Gdx.files.internal(""), gameAtlas);
	}

	public static void disposeGame() {
		gameAtlas.dispose();
		menuSkin.dispose();
	}
}
