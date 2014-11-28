package com.mygdx.screen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Hanto;

public class SettingsScreen implements Screen {

	private Stage stage;
	private Skin skin;
	private Table table;
	
	public static boolean vSync() {
		return Gdx.app.getPreferences(Hanto.TITLE).getBoolean("vsync");
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
		
		skin = new Skin(Gdx.files.internal("ui/mainmenuSkin.json"), new TextureAtlas("ui/atlas.pack"));
		
		table = new Table(skin);
		table.setFillParent(true);
		
		final CheckBox vSyncCheckBox = new CheckBox("vSync", skin);
		vSyncCheckBox.setChecked(vSync());
		
		final CheckBox audioCheckBox = new CheckBox("audio", skin);
		audioCheckBox.setChecked(Hanto.backgroundMusic.isPlaying());

		final TextButton backButton = new TextButton("BACK", skin);
		backButton.pad(50);
		
		
		ClickListener buttonHandler = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(event.getListenerActor() == vSyncCheckBox) {
					Gdx.app.getPreferences(Hanto.TITLE).putBoolean("vsync", vSyncCheckBox.isChecked());
					Gdx.graphics.setVSync(vSync());
					Gdx.app.log(Hanto.TITLE, "vSync " + (vSync() ? "enabled" : "disabled"));
				}
				else if(event.getListenerActor() == audioCheckBox){
					if(audioCheckBox.isChecked()){
						Hanto.backgroundMusic.play();
					}
					else{
						Hanto.backgroundMusic.stop();
					}
					Gdx.app.log(Hanto.TITLE, "audio " + Hanto.backgroundMusic.isPlaying());
				} 
				else if(event.getListenerActor() == backButton) {
					Gdx.app.getPreferences(Hanto.TITLE).flush();
					Gdx.app.log(Hanto.TITLE, "settings saved");
					stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {
						@Override
						public void run() {
							((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
						}
					})));
				}
			}
		};
		
		vSyncCheckBox.addListener(buttonHandler);
		audioCheckBox.addListener(buttonHandler);
		backButton.addListener(buttonHandler);	
		
		// putting everything in the table
		final Label settingsLabel = new Label("SETTINGS", skin);
		table.add(settingsLabel).spaceBottom(100).colspan(3).expandX().row();
		table.add().row();
		table.add(vSyncCheckBox).top().expandY();
		table.add(audioCheckBox).top().expandY();
		table.add(backButton).bottom().right();

		stage.addActor(table);

		stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, .5f))); // coming in from top animation
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
		skin.dispose();
	}

}
