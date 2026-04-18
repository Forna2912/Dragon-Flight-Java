package br.mackenzie;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    Texture naveTexture;
    Texture backgroundTexture;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Music music;
    Player player;
    private int score;
    private float gameTime;
    
    // NOVIDADE: Elementos de UI
    private Stage uiStage;
    private Label scoreLabel;
    private Label timeLabel;
    private Label lifesLabel;
    private BitmapFont font;

    float bgOffset1 = 0;
    
    //private float gameTime;

    @Override
    public void create() {
        naveTexture = new Texture("nave.png");
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        backgroundTexture = new Texture("background.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();
        player = new Player(naveTexture, 1f, 3f, 1, 1, viewport);

        score = 0;
        gameTime = 0;
        uiStage = new Stage();
        font = new BitmapFont();
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
        
        scoreLabel = new Label("Score: " + score, style);
        scoreLabel.setPosition(10, Gdx.graphics.getHeight() - 30);

        timeLabel = new Label("Time: 0", style);
        timeLabel.setPosition(10, Gdx.graphics.getHeight() - 60);   

        lifesLabel = new Label("Lifes: " + player.vida, style);
        lifesLabel.setPosition(10, Gdx.graphics.getHeight() - 90);   
        
        uiStage.addActor(scoreLabel);
        uiStage.addActor(timeLabel);
        uiStage.addActor(lifesLabel);
        
        // Prepare your application here.
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        // Draw your application here.
        float dt = Gdx.graphics.getDeltaTime();
        gameTime += dt;

        bgOffset1 += 1f * dt;

        float w = viewport.getWorldWidth();

        if (bgOffset1 > w) bgOffset1 = 0;

        updateGameObjects(dt);
        handleCollisions();
        drawGameObjects();
        drawUI();
    }
    private void updateGameObjects(float dt) {
        player.update(dt);
    }

    private void handleCollisions() {
        
    }

    private void drawGameObjects() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        float w = viewport.getWorldWidth();
        float h = viewport.getWorldHeight();

        spriteBatch.begin();

        spriteBatch.draw(backgroundTexture,  -bgOffset1,0, w, h);
        spriteBatch.draw(backgroundTexture,  -bgOffset1 + w,0, w, h);
        
        player.draw(spriteBatch);
        
        spriteBatch.end();
        
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
        spriteBatch.dispose();
        naveTexture.dispose();
        backgroundTexture.dispose();
        music.dispose();
        font.dispose(); 
        uiStage.dispose(); 
    }
}
