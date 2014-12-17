package com.mygdx.screen;

import java.io.IOException;

import network.HantoClient;
import network.HantoServer;
import network.NetworkUtils;
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
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.examples.chat.ChatServer;
import com.mygdx.game.Assets;
import com.mygdx.game.Hanto;
import com.mygdx.game.util.GameController;
import com.mygdx.tween.ActorAccessor;

public class PlayMultiplayer implements Screen {

	private Stage stage;
	private Table table;
	private TextButton buttonPlayAsHost, buttonPlayAsGuest;
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
		table = new Table(Assets.menuSkin);
		table.setFillParent(true);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// creating buttons
		buttonPlayAsHost = new TextButton("Play as host", Assets.menuSkin);
		buttonPlayAsHost.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener())
				.setScreen(new LobbyScreen());
				// TODO play as host
				// Create server
				String ip = NetworkUtils.getIpAddress();
				System.out.println("PlayMultiPlayer----" + ip);

				try {
					NetworkUtils.hantoServer = new HantoServer();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				// Create Client
				NetworkUtils.hantoClient = new HantoClient("Host");
				GameController.isMultiplayer = true;
				GameController.isHost = true;
				
				//((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
				
			}
		});
		buttonPlayAsHost.pad(50);
		buttonPlayAsGuest = new TextButton("Play as guest", Assets.menuSkin);
		buttonPlayAsGuest.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//TODO player as guest
				((Game) Gdx.app.getApplicationListener())
				.setScreen(new LoginScreen());
				
				
			}
		});
		buttonPlayAsGuest.pad(50);
		
		
		
		// creating heading
		heading = new Label(Hanto.TITLE, Assets.menuSkin);
		heading.setFontScale(6);

		// putting stuff together
		table.add(heading);
		table.getCell(heading).spaceBottom(300);
		table.row();
		table.add(buttonPlayAsHost);
		table.getCell(buttonPlayAsHost).spaceBottom(100);
		table.row();
		table.add(buttonPlayAsGuest);
		table.getCell(buttonPlayAsGuest).spaceBottom(100);
//		table.row();
//		table.add(buttonMultiplayerNetwork);
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
				.push(Tween.set(buttonPlayAsHost, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(buttonPlayAsGuest, ActorAccessor.ALPHA).target(0))
				
				.push(Tween.from(heading, ActorAccessor.ALPHA, 0.5f).target(0))
				.push(Tween.to(buttonPlayAsHost, ActorAccessor.ALPHA, 0.25f)
						.target(1))
				.push(Tween.to(buttonPlayAsGuest, ActorAccessor.ALPHA, 0.25f)
						.target(1))
				.end().start(tweenManager);

		// table fade-in
		Tween.from(table, ActorAccessor.ALPHA, 0.5f).target(0);
		Tween.from(table, ActorAccessor.Y, 0.5f)
				.target(Gdx.graphics.getHeight() / 8).start(tweenManager);

		tweenManager.update(Gdx.graphics.getDeltaTime());
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
		// TODO Auto-generated method stub

	}


}
