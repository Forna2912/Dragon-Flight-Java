package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameScreen implements Screen {
    
    public GameScreen(Main game) {
        this.game = game;
        this.viewport = game.gameViewport;
    }

    private Main game;
    FitViewport viewport;
    Music music;
    Player player;
    
    Texture playerTexture;
    Texture arvoresTexture;
    Texture torreTexture;
    Texture nuvensTexture;
    Texture montanhaTexture;
    Texture ceuTexture;

    private int score;
    private float gameTime;
    Array<Torre> torres;
    private float spawnTime;
    
    // NOVIDADE: Elementos de UI
    private Stage uiStage;
    private Label scoreLabel;
    private Label timeLabel;
    private BitmapFont font;
    ShapeRenderer shapeRenderer;

    float bgOffset2;

    @Override
    public void show() {

        playerTexture = new Texture("passaro.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));

        torreTexture = new Texture("torre.png");

        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();
        player = new Player(playerTexture, 1f, 3f, 1, 1, viewport);
        player.chaoHeight = game.chao.chaoTexture.getHeight() / 600f;
        shapeRenderer = new ShapeRenderer();
        torres = new Array<>();

        score = 0;
        gameTime = 0;
        spawnTime = 0;
        uiStage = new Stage();
        font = new BitmapFont();
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
        
        scoreLabel = new Label("Score: " + score, style);
        scoreLabel.setPosition(10, Gdx.graphics.getHeight() - 30);

        timeLabel = new Label("Time: 0", style);
        timeLabel.setPosition(10, Gdx.graphics.getHeight() - 60);   
        
        uiStage.addActor(scoreLabel);
        uiStage.addActor(timeLabel);
        
        // Prepare your application here.
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float dt) {
        // Draw your application here.
        gameTime += dt;
        spawnTime += dt;

        
        if (spawnTime > 2f) {
            torres.add(new Torre(torreTexture, viewport.getWorldWidth(),Math.round(MathUtils.random(-2f, 0f) * 100f) / 100f , (float)torreTexture.getWidth()/350, (float)torreTexture.getHeight()/350, viewport));
            spawnTime = 0;
        }
        
        updateGameObjects(dt);
        handleCollisions();
        drawGameObjects();
        drawUI();
    }
    private void updateGameObjects(float dt) {
        game.background.update(dt);
        game.chao.update(dt);
        player.update(dt);
        for (Torre torre : torres) {
            torre.update(dt);
        }
    }

    private void handleCollisions() {
        for (Torre torre : torres) {
            if ((player.getBounds().overlaps(torre.getBounds()) || player.getBounds().overlaps(torre.torre2.getBounds())) && !torre.passed) {
                if (player.vida <= 0) {
                    System.out.println("Game Over!");
                    torre.passed = true;
                    //Gdx.app.exit();
                }
            }
            else if (torre.x+torre.width/2 < player.x+player.width/2 && !torre.passed) {
                score++;
                scoreLabel.setText("Score: " + score);
                torre.passed = true;
            }
        }
    }

    private void drawGameObjects() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);

        game.batch.begin();

        game.background.draw();

        for (Torre torre : torres) {
            torre.draw(game.batch);
        }
        
        game.chao.draw();

        player.draw(game.batch);
        
        game.batch.end();



        //hitboxes

        // shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        // shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        // shapeRenderer.setColor(0, 1, 0, 1);

        // Rectangle b = player.getBounds();
        // shapeRenderer.rect(b.x, b.y, b.width, b.height);


        // for (Torre torre : torres) {
        //     Rectangle c = torre.getBounds();
        //     shapeRenderer.rect(c.x, c.y, c.width, c.height);
        //     Rectangle d = torre.torre2.getBounds();
        //     shapeRenderer.rect(d.x, d.y, d.width, d.height);
        // }
        
        // shapeRenderer.end();
        

    }


    private void drawUI() {
        uiStage.act();
        uiStage.draw();
        timeLabel.setText("Time: " + (int)gameTime);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        game.batch.dispose();
        playerTexture.dispose();
        arvoresTexture.dispose();
        nuvensTexture.dispose();
        montanhaTexture.dispose();
        music.dispose();
        font.dispose(); 
        uiStage.dispose(); 
    }
    @Override
    public void hide() {
    }

}
