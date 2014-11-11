package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.screen.MainMenuScreen;
//import com.mygdx.screen.SplashScreen;

public class Hanto extends Game {
	public SpriteBatch batch;
	// Texture img;
	public BitmapFont font;

	/*
	 * @Override public void create () { batch = new SpriteBatch(); //img = new
	 * Texture("badlogic.jpg"); font = new BitmapFont();
	 * font.setColor(Color.BLACK); font.scale(5); //font.set }
	 */
	//public SplashScreen getSplashScreen() {
	//	return new SplashScreen(this);
	//}
	public MainMenuScreen getMainMenu() {
		return new MainMenuScreen(this);
	}

	@Override
	public void create() {
		// Gdx.app.log( Tyrian.LOG, "Creating game" );
		// fpsLogger = new FPSLogger();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		font.scale(5);
		
		setScreen(getMainMenu());
	}

	@Override
	public void render() {
		super.render();
		
		  //Gdx.gl.glClearColor(1, 1, 1, 1);
		  //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); batch.begin();
		  //batch.draw(img, 0, 0); int width = Gdx.graphics.getWidth(); 
		  //int height = Gdx.graphics.getHeight(); String title = "Hanto"; 
		  //float fontX = width/2 - font.getBounds(title).width/2; 
		  //float fontY =height/5*4 + font.getBounds(title).height/2; font.draw(batch,
		  //"Hanto", fontX, fontY); batch.end();
		 
	}

	@Override
	public void dispose() {
		//batch.dispose();
		//font.dispose();
	}
}
