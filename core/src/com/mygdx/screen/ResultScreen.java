package com.mygdx.screen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Assets;
import com.mygdx.game.util.Constants;

public class ResultScreen implements Screen {
	
	private String winner;
	private Stage stage;
	private Table table;
	private Image winImage;
	
	public ResultScreen(String winner){
		this.winner = winner;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
		table.invalidateHierarchy();		
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		Assets.loadAssets();
		
		Image background = new Image(new Texture(Gdx.files.internal("world/winning.png")));
		background.setPosition(0, 0);
		background.setSize(Constants.w, Constants.h);
		stage.addActor(background);
		
		if(winner.equals("blue")){
			winImage = new Image(new Texture("tags/bluewins.png"));;
		}
		else if(winner.equals("yellow")){
			winImage = new Image(new Texture("tags/yellowwins.png"));
		}
		else{
			winImage = new Image(new Texture("tags/draw.png"));
		}
		
		winImage.setBounds(stage.getCamera().position.x - Constants.w / 2, stage.getCamera().position.y - Constants.h / 3, Constants.w, Constants.h / 3);

		stage.addActor(winImage);
		
		table = new Table(Assets.hantoSkin);
		table.setFillParent(true);
		

		final TextButton backButton = new TextButton("BACK TO MAINMENU", Assets.hantoSkin);
		backButton.pad(50);
		
		
		ClickListener buttonHandler = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
					stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {
						@Override
						public void run() {
							((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
						}
					})));
				}
		};
		
		backButton.addListener(buttonHandler);	
		table.add(backButton).center().bottom();

		stage.addActor(table);

		stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, .5f)));
		
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
		Assets.disposeAssets();
	}

}
