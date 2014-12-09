package com.mygdx.screen;

import static com.mygdx.game.util.Constants.WORLD_HEIGHT;
import static com.mygdx.game.util.Constants.WORLD_WIDTH;
import static com.mygdx.game.util.Constants.h;
import static com.mygdx.game.util.Constants.w;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Assets;
import com.mygdx.game.Hanto;
import com.mygdx.game.util.CameraController;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.GameController;
import com.mygdx.game.util.GameStateHandler;
import com.mygdx.hanto.implementation.core.HantoGameDevelopment;
import com.mygdx.hanto.util.HantoPieceType;
import com.mygdx.hanto.util.HantoPlayerColor;



public class GameScreen implements Screen {

	public GameScreen() {
		Hanto.gameInstance = new HantoGameDevelopment();
		GameController.gameHandlerYellow = new GameStateHandler(HantoPlayerColor.RED, Hanto.gameInstance.getGameState());
		GameController.gameHandlerBlue = new GameStateHandler(HantoPlayerColor.BLUE, Hanto.gameInstance.getGameState());
		camera = new OrthographicCamera();
		stage = new Stage();
	}    

	private OrthographicCamera camera;

	private CameraController camController;
	GestureDetector gestureDetector;

	private Stage stage;

	private Table table;

	private ImageButton yellowButterflyButton;
	private ImageButton yellowCrabButton;
	private ImageButton yellowSparrowButton;
	private ImageButton blueButterflyButton;
	private ImageButton blueCrabButton;
	private ImageButton blueSparrowButton;


	@Override
	public void render(float delta) {
		camController.update();
		camera.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		updateUI();

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void show() {
		Assets.loadMainMenuOrSettings();
		Assets.loadPieces();

		Image background = new Image(new Texture(Gdx.files.internal("world/background.png")));
		background.setPosition(0, 0);
		background.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		stage.addActor(background);


		camera = new OrthographicCamera(w, h);
		camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
		camera.update();

		camController = new CameraController(camera);
		gestureDetector = new GestureDetector(100, 0.5f, 2, 0.15f, camController);

		stage.getViewport().setCamera(camera);

		addButtons();        
		stage.addActor(table);

		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(gestureDetector);
		inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);

		yellowButterflyButton = new ImageButton(Assets.pieceSkin, "yellowButterfly");
		yellowButterflyButton.setName("yellowButterfly");
		yellowButterflyButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameController.clearAll();
				GameController.addTouchAndDrag(stage, yellowButterflyButton, null, GameController.generatePlacePixels(HantoPieceType.BUTTERFLY));
			}
		});
		yellowCrabButton = new ImageButton(Assets.pieceSkin, "yellowCrab");
		yellowCrabButton.setName("yellowCrab");
		yellowCrabButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameController.clearAll();
				GameController.addTouchAndDrag(stage, yellowCrabButton, null, GameController.generatePlacePixels(HantoPieceType.CRAB));
			}
		});
		yellowSparrowButton = new ImageButton(Assets.pieceSkin, "yellowSparrow");
		yellowSparrowButton.setName("yellowSparrow");
		yellowSparrowButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameController.clearAll();
				GameController.addTouchAndDrag(stage, yellowSparrowButton, null, GameController.generatePlacePixels(HantoPieceType.SPARROW));

			}
		});

		blueButterflyButton = new ImageButton(Assets.pieceSkin, "blueButterfly");
		blueButterflyButton.setName("blueButterfly");
		blueButterflyButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameController.clearAll();
				GameController.addTouchAndDrag(stage, blueButterflyButton, null, GameController.generatePlacePixels(HantoPieceType.BUTTERFLY));
			}
		});
		blueCrabButton = new ImageButton(Assets.pieceSkin, "blueCrab");
		blueCrabButton.setName("blueCrab");
		blueCrabButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameController.clearAll();
				GameController.addTouchAndDrag(stage, blueCrabButton, null, GameController.generatePlacePixels(HantoPieceType.CRAB));

			}
		});
		blueSparrowButton = new ImageButton(Assets.pieceSkin, "blueSparrow"); 
		blueSparrowButton.setName("blueSparrow");
		blueSparrowButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameController.clearAll();
				GameController.addTouchAndDrag(stage, blueSparrowButton, null, GameController.generatePlacePixels(HantoPieceType.SPARROW));

			}
		});

		stage.addActor(yellowButterflyButton);
		stage.addActor(yellowCrabButton);
		stage.addActor(yellowSparrowButton);
		stage.addActor(blueButterflyButton);
		stage.addActor(blueCrabButton);
		stage.addActor(blueSparrowButton);
	}

	private void updateUI(){
		final float left = stage.getCamera().position.x - w / 2;
		final float bottom = stage.getCamera().position.y - h / 2;

		if(Hanto.gameInstance.getGameState().getPlayerOnMove() == HantoPlayerColor.RED){
			yellowButterflyButton.setBounds(left, bottom, Constants.TILE_LENGTH, Constants.TILE_LENGTH);
			yellowCrabButton.setBounds(left + Constants.TILE_LENGTH, bottom, Constants.TILE_LENGTH, 200);
			yellowSparrowButton.setBounds(left + Constants.TILE_LENGTH  * 2, bottom, Constants.TILE_LENGTH, Constants.TILE_LENGTH);
			blueButterflyButton.setBounds(0, 0, 0, 0);
			blueCrabButton.setBounds(0, 0, 0, 0);
			blueSparrowButton.setBounds(0, 0, 0, 0);
		}
		else{
			blueButterflyButton.setBounds(left, bottom, Constants.TILE_LENGTH, Constants.TILE_LENGTH);
			blueCrabButton.setBounds(left + Constants.TILE_LENGTH, bottom, Constants.TILE_LENGTH, Constants.TILE_LENGTH);
			blueSparrowButton.setBounds(left + Constants.TILE_LENGTH * 2, bottom, Constants.TILE_LENGTH, Constants.TILE_LENGTH);
			yellowButterflyButton.setBounds(0, 0, 0, 0);
			yellowCrabButton.setBounds(0, 0, 0, 0);
			yellowSparrowButton.setBounds(0, 0, 0, 0);
		}

		table.setBounds(left, bottom, w, h);
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
	}

	@Override
	public void resize(int width, int height) { 
		camera.viewportWidth = w;
		camera.viewportHeight = h;
		camera.update();

	}

	public void addButtons() {
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

		TextButton btnQuit = new TextButton("Quit", Assets.menuSkin);
		btnQuit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		btnQuit.pad(10);		

		table.add(btnReturn).expand().top().left(); // Sized to cell horizontally.
		table.add(btnQuit).expand().top().right();
	}
}
