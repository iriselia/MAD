package com.mygdx.screen;

import java.io.IOException;

import network.HantoClient;
import network.NetworkUtils;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.mygdx.screen.StartScreen.MyInputProcessor;
import com.mygdx.tween.ActorAccessor;
import com.mygdx.tween.SpriteAccessor;

public class LobbyScreen implements Screen {
	private Stage stage;
	private Table table;
	private TextButton btnIP;
	private Label heading;
	private TweenManager tweenManager;
	
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();

		if (NetworkUtils.enterGame)
		{
			((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
		}
		
		tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height, true);
		table.invalidateHierarchy();
		table.setSize(width, height);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		Assets.loadMainMenuOrSettings();
		table = new Table(Assets.menuSkin);
		table.setFillParent(true);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// creating buttons
		String ip =NetworkUtils.getIpAddress();
		System.out.println("LobbyScreen: ----" + ip);
		btnIP = new TextButton("Tell your friend: " + ip, Assets.menuSkin);
		btnIP.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});
		
		
		// creating heading
		heading = new Label(Hanto.TITLE, Assets.menuSkin);
		heading.setFontScale(6);

		// putting stuff together
		table.add(heading);
		table.getCell(heading).spaceBottom(300);
		table.row();
		table.add(btnIP);
		table.getCell(btnIP).spaceBottom(100);
		table.row();
//		table.row();
//		table.add(buttonMultiplayerNetwork);
		stage.addActor(table);
		
		tweenManager = new TweenManager();
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
