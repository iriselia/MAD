package com.mygdx.screen;

import sun.net.util.IPAddressUtil;
import network.HantoClient;
import network.NetworkUtils;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Assets;
import com.mygdx.game.Hanto;
import com.mygdx.game.util.GameController;

public class LoginScreen implements Screen {
	private Stage stage;
	private Table table;
	private TextField textFieldIP;
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
		//Assets.loadMainMenuOrSettings();
		table = new Table(Assets.menuSkin);
		table.setFillParent(true);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// creating buttons
		String ip =NetworkUtils.getIpAddress();
		System.out.println("LoginScreen: ----" + ip);
		Skin textFieldSkin = new Skin(Gdx.files.internal("data/uiskin.json"));
		textFieldSkin.getFont("default-font").scale(3);
		textFieldIP = new TextField("", textFieldSkin);
		textFieldIP.setMessageText("10.216.171.150");
		textFieldIP.setTextFieldListener(new TextFieldListener() {
			public void keyTyped (TextField textField, char key) {
				if (key == '\n')
				{
					textField.getOnscreenKeyboard().show(false);
					GameController.isMultiplayer = true;
					GameController.isHost = false;
					//NetworkUtils.enterGame = true;
					//NetworkUtils.hantoClient = new HantoClient("Client");
					NetworkUtils.serverIP = textFieldIP.getText();
					NetworkUtils.hantoClient = new HantoClient("Client");
				}
			}
		});

		
		
		// creating heading
		heading = new Label(Hanto.TITLE, Assets.menuSkin);
		heading.setFontScale(6);

		// putting stuff together
		table.add(heading);
		table.getCell(heading).spaceBottom(0);
		table.row();
		table.add(textFieldIP);
		table.getCell(textFieldIP).spaceBottom(100).width(600).height(100);
		table.row();
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
