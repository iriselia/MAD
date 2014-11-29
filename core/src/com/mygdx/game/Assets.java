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

	public static Skin skin;
	public static TextureAtlas menuAtlas;
	public static TextureAtlas gameAtlas;

	public static void loadMainMenuOrSettings() {
		menuAtlas = new TextureAtlas("ui/atlas.pack");
		skin = new Skin(Gdx.files.internal("ui/mainmenuSkin.json"), menuAtlas);
	}

	public static void disposeMainMenuOrSettings() {
		menuAtlas.dispose();
		skin.dispose();
	}

	public static void loadGame() {
		gameAtlas = new TextureAtlas(Gdx.files.internal(""));
		skin = new Skin(Gdx.files.internal(""), gameAtlas);
	}

	public static void disposeGame() {
		gameAtlas.dispose();
		skin.dispose();
	}
}
