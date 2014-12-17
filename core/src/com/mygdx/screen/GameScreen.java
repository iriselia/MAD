package com.mygdx.screen;

import static com.mygdx.game.util.Constants.WORLD_HEIGHT;
import static com.mygdx.game.util.Constants.WORLD_WIDTH;
import static com.mygdx.game.util.Constants.h;
import static com.mygdx.game.util.Constants.w;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Assets;
import com.mygdx.game.Hanto;
import com.mygdx.game.ai.HantoAIPlayer;
import com.mygdx.game.util.CameraController;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.GameController;
import com.mygdx.game.util.GameStateHandler;
import com.mygdx.hanto.common.HantoException;
import com.mygdx.hanto.implementation.core.HantoGameDevelopment;
import com.mygdx.hanto.util.HantoPieceType;
import com.mygdx.hanto.util.HantoPlayerColor;


/**
 * Screen for playing Hanto Game
 * 
 * @author Peng Ren, Xi Wen
 *
 */
public class GameScreen implements Screen {

	/**
	 * default public constructor
	 */
	public GameScreen(boolean ifPlayWithAI) {
		this.ifPlayWithAI = ifPlayWithAI;
		Hanto.gameInstance = new HantoGameDevelopment();
		if(!ifPlayWithAI){
			GameController.gameHandlerYellow = new GameStateHandler(HantoPlayerColor.RED, Hanto.gameInstance.getGameState());
		}
		else{
			Hanto.ifPlayWithAI = true;
			Hanto.AIPlayer = new HantoAIPlayer(HantoPlayerColor.RED, false);
		}
		GameController.gameHandlerBlue = new GameStateHandler(HantoPlayerColor.BLUE, Hanto.gameInstance.getGameState());
		camera = new OrthographicCamera();
		stage = new Stage();
	}

	private OrthographicCamera camera;

	private CameraController camController;
	GestureDetector gestureDetector;

	private Stage stage;

	private Table table;
	
	private TextButton btnResign;

	private ImageButton yellowButterflyButton;
	private ImageButton yellowCrabButton;
	private ImageButton yellowSparrowButton;
	private ImageButton yellowCraneButton;
	private ImageButton yellowHorseButton;
	private ImageButton yellowDoveButton;
	private ImageButton blueButterflyButton;
	private ImageButton blueCrabButton;
	private ImageButton blueSparrowButton;
	private ImageButton blueCraneButton;
	private ImageButton blueHorseButton;
	private ImageButton blueDoveButton;

	private boolean yellowButterflyButtonChanged;
	private boolean yellowCrabButtonChanged;
	private boolean yellowSparrowButtonChanged;
	private boolean yellowCraneButtonChanged;
	private boolean yellowHorseButtonChanged;
	private boolean yellowDoveButtonChanged;
	private boolean blueButterflyButtonChanged;
	private boolean blueCrabButtonChanged;
	private boolean blueSparrowButtonChanged;
	private boolean blueCraneButtonChanged;
	private boolean blueHorseButtonChanged;
	private boolean blueDoveButtonChanged;

	private Label turnLabel;
	private final boolean ifPlayWithAI;


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
		Assets.loadAssets();

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

		yellowButterflyButton = new ImageButton(Assets.hantoSkin, "yellowButterfly");
		yellowButterflyButton.setName("yellowButterfly");

		yellowCrabButton = new ImageButton(Assets.hantoSkin, "yellowCrab");
		yellowCrabButton.setName("yellowCrab");

		yellowSparrowButton = new ImageButton(Assets.hantoSkin, "yellowSparrow");
		yellowSparrowButton.setName("yellowSparrow");

		yellowCraneButton = new ImageButton(Assets.hantoSkin, "yellowCrane");
		yellowCraneButton.setName("yellowCrane");

		yellowHorseButton = new ImageButton(Assets.hantoSkin, "yellowHorse");
		yellowHorseButton.setName("yellowHorse");

		yellowDoveButton = new ImageButton(Assets.hantoSkin, "yellowDove");
		yellowDoveButton.setName("yellowDove");

		if(!ifPlayWithAI){
			yellowButterflyButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					GameController.clearAll();
					GameController.addTouchAndDrag(stage, yellowButterflyButton, null, GameController.generatePlacePixels(HantoPieceType.BUTTERFLY));
				}
			});

			yellowCrabButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					GameController.clearAll();
					GameController.addTouchAndDrag(stage, yellowCrabButton, null, GameController.generatePlacePixels(HantoPieceType.CRAB));
				}
			});

			yellowSparrowButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					GameController.clearAll();
					GameController.addTouchAndDrag(stage, yellowSparrowButton, null, GameController.generatePlacePixels(HantoPieceType.SPARROW));

				}
			});

			yellowCraneButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					GameController.clearAll();
					GameController.addTouchAndDrag(stage, yellowCraneButton, null, GameController.generatePlacePixels(HantoPieceType.CRANE));

				}
			});

			yellowHorseButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					GameController.clearAll();
					GameController.addTouchAndDrag(stage, yellowHorseButton, null, GameController.generatePlacePixels(HantoPieceType.HORSE));

				}
			});

			yellowDoveButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					GameController.clearAll();
					GameController.addTouchAndDrag(stage, yellowDoveButton, null, GameController.generatePlacePixels(HantoPieceType.DOVE));

				}
			});
		}
		
		blueButterflyButton = new ImageButton(Assets.hantoSkin, "blueButterfly");
		blueButterflyButton.setName("blueButterfly");
		blueButterflyButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameController.clearAll();
				GameController.addTouchAndDrag(stage, blueButterflyButton, null, GameController.generatePlacePixels(HantoPieceType.BUTTERFLY));
			}
		});
		blueCrabButton = new ImageButton(Assets.hantoSkin, "blueCrab");
		blueCrabButton.setName("blueCrab");
		blueCrabButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameController.clearAll();
				GameController.addTouchAndDrag(stage, blueCrabButton, null, GameController.generatePlacePixels(HantoPieceType.CRAB));

			}
		});
		blueSparrowButton = new ImageButton(Assets.hantoSkin, "blueSparrow"); 
		blueSparrowButton.setName("blueSparrow");
		blueSparrowButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameController.clearAll();
				GameController.addTouchAndDrag(stage, blueSparrowButton, null, GameController.generatePlacePixels(HantoPieceType.SPARROW));

			}
		});
		blueCraneButton = new ImageButton(Assets.hantoSkin, "blueCrane"); 
		blueCraneButton.setName("blueCrane");
		blueCraneButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameController.clearAll();
				GameController.addTouchAndDrag(stage, blueCraneButton, null, GameController.generatePlacePixels(HantoPieceType.CRANE));

			}
		});
		blueHorseButton = new ImageButton(Assets.hantoSkin, "blueHorse"); 
		blueHorseButton.setName("blueHorse");
		blueHorseButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameController.clearAll();
				GameController.addTouchAndDrag(stage, blueHorseButton, null, GameController.generatePlacePixels(HantoPieceType.HORSE));

			}
		});
		blueDoveButton = new ImageButton(Assets.hantoSkin, "blueDove"); 
		blueDoveButton.setName("blueDove");
		blueDoveButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameController.clearAll();
				GameController.addTouchAndDrag(stage, blueDoveButton, null, GameController.generatePlacePixels(HantoPieceType.DOVE));

			}
		});

		stage.addActor(yellowButterflyButton);
		stage.addActor(yellowCrabButton);
		stage.addActor(yellowSparrowButton);
		stage.addActor(yellowCraneButton);
		stage.addActor(yellowHorseButton);
		stage.addActor(yellowDoveButton);
		stage.addActor(blueButterflyButton);
		stage.addActor(blueCrabButton);
		stage.addActor(blueSparrowButton);
		stage.addActor(blueCraneButton);
		stage.addActor(blueHorseButton);
		stage.addActor(blueDoveButton);
	}

	/**
	 * Updates the UI of game when switching turns
	 */
	private void updateUI(){
		changeButtons();
		final float left = stage.getCamera().position.x - w / 2;
		final float bottom = stage.getCamera().position.y - h / 2;

		if(Hanto.gameInstance.getGameState().getPlayerOnMove() == HantoPlayerColor.RED){
			yellowButterflyButton.setBounds(left, bottom, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
			yellowCrabButton.setBounds(left + Constants.BUTTON_SIZE, bottom, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
			yellowSparrowButton.setBounds(left + Constants.BUTTON_SIZE  * 2, bottom, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
			yellowCraneButton.setBounds(left + Constants.BUTTON_SIZE  * 3, bottom, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
			yellowHorseButton.setBounds(left + Constants.BUTTON_SIZE  * 4, bottom, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
			yellowDoveButton.setBounds(left + Constants.BUTTON_SIZE  * 5, bottom, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
			blueButterflyButton.setBounds(0, 0, 0, 0);
			blueCrabButton.setBounds(0, 0, 0, 0);
			blueSparrowButton.setBounds(0, 0, 0, 0);
			blueCraneButton.setBounds(0, 0, 0, 0);
			blueHorseButton.setBounds(0, 0, 0, 0);
			blueDoveButton.setBounds(0, 0, 0, 0);
		}
		else{
			blueButterflyButton.setBounds(left, bottom, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
			blueCrabButton.setBounds(left + Constants.BUTTON_SIZE, bottom, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
			blueSparrowButton.setBounds(left + Constants.BUTTON_SIZE * 2, bottom, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
			blueCraneButton.setBounds(left + Constants.BUTTON_SIZE * 3, bottom, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
			blueHorseButton.setBounds(left + Constants.BUTTON_SIZE * 4, bottom, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
			blueDoveButton.setBounds(left + Constants.BUTTON_SIZE * 5, bottom, Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
			yellowButterflyButton.setBounds(0, 0, 0, 0);
			yellowCrabButton.setBounds(0, 0, 0, 0);
			yellowSparrowButton.setBounds(0, 0, 0, 0);
			yellowCraneButton.setBounds(0, 0, 0, 0);
			yellowHorseButton.setBounds(0, 0, 0, 0);
			yellowDoveButton.setBounds(0, 0, 0, 0);
		}

		yellowButterflyButton.toFront();
		yellowCrabButton.toFront();
		yellowSparrowButton.toFront();
		yellowCraneButton.toFront();
		yellowHorseButton.toFront();
		yellowDoveButton.toFront();
		blueButterflyButton.toFront();
		blueCrabButton.toFront();
		blueSparrowButton.toFront();
		blueCraneButton.toFront();
		blueHorseButton.toFront();
		blueDoveButton.toFront();

		if(Hanto.gameInstance.getGameState().getPlayerOnMove() == HantoPlayerColor.BLUE){
			turnLabel.setText("BLUE");
			turnLabel.setColor(Color.BLUE);
		}
		else{
			turnLabel.setText("YELLOW");
			turnLabel.setColor(Color.YELLOW);
		}
		table.setBounds(left, bottom, w, h);
		if(ifPlayWithAI){
			if(Hanto.gameInstance.getGameState().getPlayerOnMove() == HantoPlayerColor.BLUE){
				btnResign.setDisabled(false);
			}
			else{
				btnResign.setDisabled(true);
			}
		}
		table.toFront();
	}

	private void changeButtons(){
		if(!yellowButterflyButtonChanged && Hanto.gameInstance.getGameState().getRedButterflyPlaced() == 1){
			yellowButterflyButton.remove();
			yellowButterflyButton = new ImageButton(Assets.hantoSkin, "greyButterfly");
			stage.addActor(yellowButterflyButton);
			yellowButterflyButtonChanged = true;
		}
		else if(!yellowCrabButtonChanged && Hanto.gameInstance.getGameState().getNumberTypeForPlayer(HantoPieceType.CRAB, HantoPlayerColor.RED) == 4){
			yellowCrabButton.remove();
			yellowCrabButton = new ImageButton(Assets.hantoSkin, "greyCrab");
			stage.addActor(yellowCrabButton);
			yellowCrabButtonChanged = true;
		}
		else if(!yellowSparrowButtonChanged && Hanto.gameInstance.getGameState().getNumberTypeForPlayer(HantoPieceType.SPARROW, HantoPlayerColor.RED) == 4){
			yellowSparrowButton.remove();
			yellowSparrowButton = new ImageButton(Assets.hantoSkin, "greySparrow");
			stage.addActor(yellowSparrowButton);
			yellowSparrowButtonChanged = true;
		}
		else if(!yellowCraneButtonChanged && Hanto.gameInstance.getGameState().getNumberTypeForPlayer(HantoPieceType.CRANE, HantoPlayerColor.RED) == 4){
			yellowCraneButton.remove();
			yellowCraneButton = new ImageButton(Assets.hantoSkin, "greyCrane");
			stage.addActor(yellowCraneButton);
			yellowCraneButtonChanged = true;
		}
		else if(!yellowHorseButtonChanged && Hanto.gameInstance.getGameState().getNumberTypeForPlayer(HantoPieceType.HORSE, HantoPlayerColor.RED) == 4){
			yellowHorseButton.remove();
			yellowHorseButton = new ImageButton(Assets.hantoSkin, "greyHorse");
			stage.addActor(yellowHorseButton);
			yellowHorseButtonChanged = true;
		}
		else if(!yellowDoveButtonChanged && Hanto.gameInstance.getGameState().getNumberTypeForPlayer(HantoPieceType.DOVE, HantoPlayerColor.RED) == 4){
			yellowDoveButton.remove();
			yellowDoveButton = new ImageButton(Assets.hantoSkin, "greyDove");
			stage.addActor(yellowDoveButton);
			yellowDoveButtonChanged = true;
		}
		else if(!blueButterflyButtonChanged && Hanto.gameInstance.getGameState().getBlueButterflyPlaced() == 1){
			blueButterflyButton.remove();
			blueButterflyButton = new ImageButton(Assets.hantoSkin, "greyButterfly");
			stage.addActor(blueButterflyButton);
			blueButterflyButtonChanged = true;
		}
		else if(!blueCrabButtonChanged && Hanto.gameInstance.getGameState().getNumberTypeForPlayer(HantoPieceType.CRAB, HantoPlayerColor.BLUE) == 4){
			blueCrabButton.remove();
			blueCrabButton = new ImageButton(Assets.hantoSkin, "greyCrab");
			stage.addActor(blueCrabButton);
			blueCrabButtonChanged = true;
		}
		else if(!blueSparrowButtonChanged && Hanto.gameInstance.getGameState().getNumberTypeForPlayer(HantoPieceType.SPARROW, HantoPlayerColor.BLUE) == 4){
			blueSparrowButton.remove();
			blueSparrowButton = new ImageButton(Assets.hantoSkin, "greySparrow");
			stage.addActor(blueSparrowButton);
			blueSparrowButtonChanged = true;
		}
		else if(!blueCraneButtonChanged && Hanto.gameInstance.getGameState().getNumberTypeForPlayer(HantoPieceType.CRANE, HantoPlayerColor.BLUE) == 4){
			blueCraneButton.remove();
			blueCraneButton = new ImageButton(Assets.hantoSkin, "greyCrane");
			stage.addActor(blueCraneButton);
			blueCraneButtonChanged = true;
		}
		else if(!blueHorseButtonChanged && Hanto.gameInstance.getGameState().getNumberTypeForPlayer(HantoPieceType.HORSE, HantoPlayerColor.BLUE) == 4){
			blueHorseButton.remove();
			blueHorseButton = new ImageButton(Assets.hantoSkin, "greyHorse");
			stage.addActor(blueHorseButton);
			blueHorseButtonChanged = true;
		}
		else if(!blueDoveButtonChanged && Hanto.gameInstance.getGameState().getNumberTypeForPlayer(HantoPieceType.DOVE, HantoPlayerColor.BLUE) == 4){
			blueDoveButton.remove();
			blueDoveButton = new ImageButton(Assets.hantoSkin, "greyDove");
			stage.addActor(blueDoveButton);
			blueDoveButtonChanged = true;
		}
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

	/**
	 * Adds buttons (Return, Resign) to the GameScreen
	 */
	private void addButtons() {
		table = new Table(Assets.hantoSkin);
		table.setFillParent(true);
		table.setBounds(0, 0, w, h);

		TextButton btnReturn = new TextButton("Return", Assets.hantoSkin);
		btnReturn.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
				dispose();
			}
		});
		btnReturn.pad(10);

		btnResign = new TextButton("Resign", Assets.hantoSkin);
		btnResign.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					Hanto.gameInstance.makeMove(null, null, null);
				} catch (HantoException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				if(Hanto.gameInstance.getGameState().getPlayerOnMove() == HantoPlayerColor.BLUE){
					((Game) Gdx.app.getApplicationListener()).setScreen(new ResultScreen("yellow"));
				}
				else{
					((Game) Gdx.app.getApplicationListener()).setScreen(new ResultScreen("blue"));
				}
			}
		});
		btnResign.pad(10);

		if(Hanto.gameInstance.getGameState().getFirstPlayer() == HantoPlayerColor.BLUE){
			turnLabel = new Label("BLUE", Assets.hantoSkin);
			turnLabel.setColor(Color.BLUE);
		}
		else{
			turnLabel = new Label("YELLOW", Assets.hantoSkin);
			turnLabel.setColor(Color.YELLOW);
		}

		turnLabel.setFontScale(2);

		table.add(btnReturn).expand().top().left(); // Sized to cell horizontally.
		table.add(turnLabel).expand().center().top();
		table.add(btnResign).expand().top().right();
	}

}
