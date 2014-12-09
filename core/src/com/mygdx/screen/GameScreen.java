package com.mygdx.screen;

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
import com.mygdx.game.util.GameController;
import com.mygdx.game.util.CameraController;



public class GameScreen implements Screen {

    public final static float w = Gdx.graphics.getWidth();
    public final static float h = Gdx.graphics.getHeight();
    public final static float aspectRatio = h / w;
    public final static float WORLD_WIDTH = w * 3;
    public final static float WORLD_HEIGHT = h * 3;
    public final static int TILE_LENGTH = 200;
    
    public GameScreen() {
        camera = new OrthographicCamera();
        stage = new Stage();
    }    
    
	private OrthographicCamera camera;

    private CameraController camController;
    GestureDetector gestureDetector;
    
    private Stage stage;
    
    private Table table;
    
    private ImageButton sourceImage;

	@Override
	public void render(float delta) {
		camController.update();
        camera.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float left = stage.getCamera().position.x - w / 2;
		float bottom = stage.getCamera().position.y - h / 2;
				
		sourceImage.setBounds(left, bottom, 200, 200);
		table.setBounds(left, bottom, w, h);
		
        stage.act(delta);
        stage.draw();
	}

	@Override
	public void show() {
		Assets.loadMainMenuOrSettings();
		Assets.loadPieces();
						
		//Image map = new Image(new Texture(Gdx.files.internal("world/background.png")));
		//map.setPosition(0, 0);
		//map.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        //stage.addActor(map);
		
		for(int i = 0; i <= WORLD_WIDTH / 200; i++){
			for(int j = 0; j < WORLD_HEIGHT / 200; j++){
				Image tile = new Image(new Texture("hexTiles/testTile.png"));
				if( j % 2 == 0){
					tile.setBounds(i * 200, j * 200, 200, 200);
				}
				else{
					tile.setBounds(100 + i * 200, j * 200, 200, 200);
				}
				stage.addActor(tile);
			}
		}
		
        camera = new OrthographicCamera(w, h);
        camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
        camera.update();
        
        camController = new CameraController(camera);
        gestureDetector = new GestureDetector(100, 0.5f, 2, 0.15f, camController);
        
        stage.getViewport().setCamera(camera);
        
        addButtons();
        drawPieces();
        
        stage.addActor(table);
            
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gestureDetector);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        
        sourceImage = new ImageButton(Assets.pieceSkin, "butterfly");
        
        GameController.addTouchAndDrag(stage, "butterfly", sourceImage);
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
	
	public void drawPieces() {
//		Hanto.gameInstance = new HantoGameDevelopment();
//		Hanto.gameInstance.initialize(HantoPlayerColor.BLUE);
//		HantoPlayerColor currentPlayer = Hanto.gameInstance.getGameState().getPlayerOnMove();
//		Label lblPlayerColor = new Label (currentPlayer.toString(), Assets.skin);
//		if (!Hanto.gameInstance.getGameState().getBoard().isEmpty()) {
//			//TODO: create function drawPiecesOnBoard
//		}
//		ImageButton butterfly = new ImageButton(pieceSkin, "butterfly");
//		ImageButton crab = new ImageButton(pieceSkin, "crab");
//		ImageButton sparrow = new ImageButton(pieceSkin, "sparrow");
//		table.add(lblPlayerColor).top().center();
//		table.row();
//		table.add(butterfly).expand().bottom().left();
//		table.getCell(butterfly).spaceRight(30);
//		table.add(crab);
//		table.add(sparrow);
		
	}
	
}
