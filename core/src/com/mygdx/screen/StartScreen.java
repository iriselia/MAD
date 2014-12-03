package com.mygdx.screen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.tween.SpriteAccessor;

public class StartScreen implements Screen {

	private SpriteBatch batch;
	private Sprite startScreen;
	private TweenManager tweenManager;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
					
		batch.begin();
		startScreen.draw(batch);
		batch.end();
		
		tweenManager.update(delta);
	}

	@Override
	public void show() {
		
		Gdx.input.setInputProcessor(new MyInputProcessor());
		
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		Texture screenTexture = new Texture("mainmenu/menubackground.png");
		startScreen = new Sprite(screenTexture);
		startScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Tween.set(startScreen, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(startScreen, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1, 2).setCallback(new TweenCallback(){
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
				
			}
		}).start(tweenManager);
		
		tweenManager.update(Float.MIN_VALUE);
	}

	@Override
	public void hide() {
		dispose();		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		startScreen.getTexture().dispose();
		
	}

	@Override
	public void resize(int width, int height) {
	}
	
	class MyInputProcessor implements InputProcessor {
		   @Override
		   public boolean keyDown (int keycode) {
		      return false;
		   }

		   @Override
		   public boolean keyUp (int keycode) {
		      return false;
		   }

		   @Override
		   public boolean keyTyped (char character) {
		      return false;
		   }

		   @Override
		   public boolean touchDown (int x, int y, int pointer, int button) {
			  ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
		      return false;
		   }

		   @Override
		   public boolean touchUp (int x, int y, int pointer, int button) {
		      return false;
		   }

		   @Override
		   public boolean touchDragged (int x, int y, int pointer) {
		      return false;
		   }

		   @Override
		   public boolean mouseMoved (int x, int y) {
		      return false;
		   }

		   @Override
		   public boolean scrolled (int amount) {
		      return false;
		   }
	}
}