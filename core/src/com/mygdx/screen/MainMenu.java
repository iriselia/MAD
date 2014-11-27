package com.mygdx.screen;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Hanto;
import com.mygdx.tween.ActorAccessor;

public class MainMenu implements Screen {

	private Stage stage;
	private Table table;
	private TextButton buttonPlay, buttonExit;
	private Label heading;
	private Skin skin;
	private TextureAtlas atlas;
	private TweenManager tweenManager;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		stage.act(delta);
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		table.invalidateHierarchy();
		table.setSize(width, height);
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		atlas = new TextureAtlas("ui/button.pack");
		skin = new Skin(Gdx.files.internal("ui/mainmenuSkin.json"), atlas);
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			
		//creating buttons
		buttonExit = new TextButton("EXIT", skin);
		buttonExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		buttonExit.pad(50);
		
		buttonPlay = new TextButton("PLAY", skin);
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
			}
		});
		buttonPlay.pad(50);
		
		//creating heading 
		heading = new Label(Hanto.TITLE, skin);
		heading.setFontScale(6);
		
		//putting stuff together
		table.add(heading);
		table.getCell(heading).spaceBottom(300);
		table.row();
		table.add(buttonPlay);
		table.getCell(buttonPlay).spaceBottom(100);
		table.row();
		table.add(buttonExit);
		//table.debug(); //TODO: remove it later
		stage.addActor(table);
		
		//create animation
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		
		//heading color animation
		Timeline.createSequence().beginSequence()
			.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(0, 0, 1))
			.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(0, 1, 0))
			.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(1, 0, 0))
			.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(0, 1, 1))
			.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(1, 1, 0))
			.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(1, 0, 1))
			.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(1, 1, 1))
			.end().repeat(Tween.INFINITY, 0).start(tweenManager);
		
		//heading and buttons fade-in
		Timeline.createSequence().beginSequence()
			.push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
			.push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
			.push(Tween.from(heading, ActorAccessor.ALPHA, 0.5f).target(0))
			.push(Tween.to(buttonPlay, ActorAccessor.ALPHA, 0.25f).target(1))
			.push(Tween.to(buttonExit, ActorAccessor.ALPHA, 0.25f).target(1))
			.end().start(tweenManager);
		
		//table fade-in
		Tween.from(table, ActorAccessor.ALPHA, 0.5f).target(0);
		Tween.from(table, ActorAccessor.Y, 0.5f).target(Gdx.graphics.getHeight() / 8).start(tweenManager);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

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
		stage.dispose();
		atlas.dispose();
		skin.dispose();
	}

}
