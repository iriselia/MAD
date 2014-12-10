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

	public static Skin menuSkin;
	public static Skin pieceSkin;
	public static TextureAtlas menuAtlas;
	public static TextureAtlas gameAtlas;
	public static TextureAtlas pieceAtlas;
	public static TextureRegionDrawable[] helpImages;

	/**
	 * Load Assets for the MainMenu and SettingsScreen
	 */
	public static void loadMainMenuOrSettings() {
		menuAtlas = new TextureAtlas("ui/atlas.pack");
		menuSkin = new Skin(Gdx.files.internal("ui/mainmenuSkin.json"), menuAtlas);
	}
	
	/**
	 * Load Assets for game pieces
	 */
	public static void loadPieces() {
		pieceAtlas = new TextureAtlas("ui/pieces.pack");
		pieceSkin = new Skin(Gdx.files.internal("ui/gameSkin.json"), pieceAtlas);
	}

	/**
	 * Dispose Assets used for MainMenu and SettingsScreen 
	 */
	public static void disposeMainMenuOrSettings() {
		menuAtlas.dispose();
		menuSkin.dispose();
	}
	
	/**
	 * Dispose Assets used for game pieces
	 */
	public static void disposePieces() {
		pieceAtlas.dispose();
		pieceSkin.dispose();
	}

	/**
	 * Load Assets used for GameScreen
	 */
	public static void loadGame() {
		gameAtlas = new TextureAtlas(Gdx.files.internal(""));
		menuSkin = new Skin(Gdx.files.internal(""), gameAtlas);
	}

	/**
	 * Dispose Assests used for GameScreen
	 */
	public static void disposeGame() {
		gameAtlas.dispose();
		menuSkin.dispose();
	}
	
	/**
	 * Load Assets used for HelpScreen
	 */
	public static void loadHelp() {
		helpImages = new TextureRegionDrawable[Constants.numberOfHelpImages];
		for (int i = 0; i < Constants.numberOfHelpImages; i++) {
			helpImages[i] = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("help/" + i + ".png"))));
		}
	}
}
