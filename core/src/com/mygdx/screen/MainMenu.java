package com.mygdx.screen;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Assets;
import com.mygdx.game.Hanto;
import com.mygdx.tween.ActorAccessor;

public class MainMenu implements Screen {

	private Stage stage;
	private Table table;
	private TextButton buttonPlay, buttonSettings, buttonExit;
	private Label heading;
	private TweenManager tweenManager;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();

		tweenManager.update(delta);
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
		Assets.loadMainMenuOrSettings();
		table = new Table(Assets.skin);
		table.setFillParent(true);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// creating buttons
		buttonExit = new TextButton("EXIT", Assets.skin);
		buttonExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		buttonExit.pad(50);

		buttonSettings = new TextButton("Settings", Assets.skin);
		buttonSettings.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new SettingsScreen());
			}
		});
		buttonSettings.pad(50);

		buttonPlay = new TextButton("PLAY", Assets.skin);
		buttonPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new GameScreen());
			}
		});
		buttonPlay.pad(50);

		// creating heading
		heading = new Label(Hanto.TITLE, Assets.skin);
		heading.setFontScale(6);

		// putting stuff together
		table.add(heading);
		table.getCell(heading).spaceBottom(300);
		table.row();
		table.add(buttonPlay);
		table.getCell(buttonPlay).spaceBottom(100);
		table.row();
		table.add(buttonSettings);
		table.getCell(buttonSettings).spaceBottom(100);
		table.row();
		table.add(buttonExit);
		stage.addActor(table);

		// create animation
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		// heading color animation
		Timeline.createSequence()
				.beginSequence()
				.push(Tween.to(heading, ActorAccessor.RGB, 0.5f)
						.target(0, 0, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, 0.5f)
						.target(0, 1, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, 0.5f)
						.target(1, 0, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, 0.5f)
						.target(0, 1, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, 0.5f)
						.target(1, 1, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, 0.5f)
						.target(1, 0, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, 0.5f)
						.target(1, 1, 1)).end().repeat(Tween.INFINITY, 0)
				.start(tweenManager);

		// heading and buttons fade-in
		Timeline.createSequence()
				.beginSequence()
				.push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(buttonSettings, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
				.push(Tween.from(heading, ActorAccessor.ALPHA, 0.5f).target(0))
				.push(Tween.to(buttonPlay, ActorAccessor.ALPHA, 0.25f)
						.target(1))
				.push(Tween.to(buttonSettings, ActorAccessor.ALPHA, 0.25f)
						.target(1))
				.push(Tween.to(buttonExit, ActorAccessor.ALPHA, 0.25f)
						.target(1)).end().start(tweenManager);

		// table fade-in
		Tween.from(table, ActorAccessor.ALPHA, 0.5f).target(0);
		Tween.from(table, ActorAccessor.Y, 0.5f)
				.target(Gdx.graphics.getHeight() / 8).start(tweenManager);

		tweenManager.update(Gdx.graphics.getDeltaTime());
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
		stage.dispose();
		Assets.disposeMainMenuOrSettings();
	}

}
