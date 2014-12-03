package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


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

	public static void loadMainMenuOrSettings() {
		menuAtlas = new TextureAtlas("ui/atlas.pack");
		menuSkin = new Skin(Gdx.files.internal("ui/mainmenuSkin.json"), menuAtlas);
	}
	
	public static void loadPieces() {
		pieceAtlas = new TextureAtlas("ui/pieces.pack");
		pieceSkin = new Skin(Gdx.files.internal("ui/gameSkin.json"), pieceAtlas);
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
