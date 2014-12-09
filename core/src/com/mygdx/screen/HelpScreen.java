package com.mygdx.screen;

import static com.mygdx.game.util.Constants.h;
import static com.mygdx.game.util.Constants.w;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Assets;

public class HelpScreen implements Screen {
	private Table table;
	private Stage stage;
	private int currentImageIndex;
	private Image currentImage;
	
	public HelpScreen () {
		stage = new Stage();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Assets.loadMainMenuOrSettings();
		Assets.loadHelp();
		currentImage = new Image(Assets.helpImages[0]);
		addButtons();
		stage.addActor(currentImage);
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
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
	
	private void addButtons() {
		table = new Table(Assets.menuSkin);
		table.setFillParent(true);
		table.setBounds(0, 0, w, h);
		
		TextButton btnReturn = new TextButton("Return", Assets.menuSkin);
		btnReturn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                dispose();
            }
        });
		btnReturn.pad(10);
		
		TextButton btnPrevious = new TextButton("Previous", Assets.menuSkin);
		btnPrevious.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if (currentImageIndex <= 0) {
            		return;
            	}
            	currentImageIndex -= 1;
            	currentImage.setDrawable(Assets.helpImages[currentImageIndex]);
            }
        });
		btnPrevious.pad(10);
		
		TextButton btnNext = new TextButton("Next", Assets.menuSkin);
		btnNext.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if (currentImageIndex >= 3) {
            		return;
            	}
            	currentImageIndex += 1;
            	currentImage.setDrawable(Assets.helpImages[currentImageIndex]);
            }
        });
		btnNext.pad(10);
		
	    table.add(btnReturn).expand().top().left(); // Sized to cell horizontally.
	    table.row();
	    table.add(btnPrevious).expand().bottom().left();
	    table.add(btnNext).expand().bottom().right();
	}

}
