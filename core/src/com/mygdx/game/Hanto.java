package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.screen.StartScreen;

public class Hanto extends Game {
	
	public static final String TITLE = "Hanto";
	public static final String VERSION = "1.0";
	
	public StartScreen getMainMenu() {
		return new StartScreen();
	}

	@Override
	public void create() {
		setScreen(getMainMenu());
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		
	}
}
