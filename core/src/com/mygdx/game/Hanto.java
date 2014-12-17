package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.ai.HantoGamePlayer;
import com.mygdx.hanto.implementation.core.HantoGameDevelopment;
import com.mygdx.screen.StartScreen;

/**
 * Main class for Hanto Game
 * 
 * @author Peng Ren
 *
 */
public class Hanto extends Game {
	
	public static final String TITLE = "Hanto";
	public static final String VERSION = "1.0";
	public static Music backgroundMusic;
	public static HantoGameDevelopment gameInstance;
	public static boolean ifPlayWithAI;
	public static HantoGamePlayer AIPlayer;
	
	/**
	 * Called when game starts
	 * @return The splash screen, followed by the MainMenu Screen
	 */
	public StartScreen getMainMenu() {
		return new StartScreen();
	}
	
	/**
	 * sets the background music for game
	 */
	public void setMusic() {
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/backgroundMusic.mp3"));
	}

	@Override
	public void create() {
		setMusic();
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
		backgroundMusic.dispose();
	}
}
