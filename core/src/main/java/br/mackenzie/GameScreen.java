package br.mackenzie;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameScreen implements Screen {
    
    public GameScreen(Main main) {
        this.main = main;
        this.viewport = main.gameViewport;
        this.UIViewport = main.UIViewport;
    }

    private Main main;
    FitViewport viewport;
    FitViewport UIViewport;
    Player player;
    
    Texture playerTexture;
    Texture arvoresTexture;
    Texture nuvensTexture;
    Texture montanhaTexture;
    Texture ceuTexture;

    Table InfoTable;
    Table botaoTable;

    TorreManager spawnTorre;
    private int score;
    private float gameTime;
    
    // NOVIDADE: Elementos de UI
    private Stage uiStage;
    private Label scoreLabel;
    private Label timeLabel;
    private BitmapFont font;
    ShapeRenderer shapeRenderer;

    boolean mostrarHitboxes = false;

    float bgOffset2;

    @Override
    public void show() {

        playerTexture = new Texture("passaro.png");

        
        player = new Player(main, playerTexture, 1f, 3f, 1, 1, viewport);
        player.chaoHeight = main.chao.chaoTexture.getHeight() / 600f;
        shapeRenderer = new ShapeRenderer();
        
        spawnTorre = new TorreManager(main, player);

        score = 0;
        gameTime = 0;
        uiStage = new Stage(UIViewport, main.UIbatch);
        Gdx.input.setInputProcessor(uiStage);
        font = new BitmapFont(Gdx.files.internal("UISkin/fonte.fnt"));
        float escala = 0.3f;
        font.getData().setScale(escala);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.valueOf("383837"));

        InfoTable = new Table();
        InfoTable.setFillParent(true);
        InfoTable.top().pad(10).left();

        botaoTable = new Table();
        botaoTable.setFillParent(true);
        botaoTable.top().pad(10).right();

        scoreLabel = new Label("Score: " + score, style);
        timeLabel = new Label("Time: 0", style);

        Botao botao_voltar = new Botao(main, "botao_voltar.png", "botao_voltar_pressionado.png", () -> Voltar());

        // botaoTable.setDebug(true);
        // InfoTable.setDebug(true);
        
        uiStage.addActor(InfoTable);
        uiStage.addActor(botaoTable);

        botaoTable.add(botao_voltar).right();
        
        InfoTable.add(scoreLabel).left().row();;
        InfoTable.add(timeLabel).left();
    
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
        uiStage.getViewport().update(width, height, true);
        UIViewport.update(width, height, true);
    }

    @Override
    public void render(float dt) {
        // Draw your application here.
        gameTime += dt;
        
        updateGameObjects(dt);
        handleCollisions();
        drawGameObjects();
        drawUI();
    }
    
    private void updateGameObjects(float dt) {
        main.background.update(dt);
        main.chao.update(dt);
        player.update(dt);
        spawnTorre.update(dt);
    }

    private void handleCollisions() {
        for (Torre torre : spawnTorre.torres) {
            if ((player.getBounds().overlaps(torre.getBounds()) || player.getBounds().overlaps(torre.torre2.getBounds())) && !torre.passed) {
                if (player.vida <= 0) {
                    System.out.println("Game Over!");
                    torre.passed();
                }
            }
            else if (torre.x+torre.width/2 < player.x+player.width/2 && !torre.passed) {
                score++;
                scoreLabel.setText("Score: " + score);
                torre.passed();
            }
        }
    }

    private void drawGameObjects() {
        ScreenUtils.clear(Color.BLACK);
        UIViewport.apply();
        viewport.apply();
        main.batch.setProjectionMatrix(viewport.getCamera().combined);

        main.batch.begin();

        main.background.draw();
        spawnTorre.draw();
        main.chao.draw();
        player.draw(main.batch);
        
        main.batch.end();



        if (mostrarHitboxes) {
            shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
    
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(0, 1, 0, 1);
    
            Rectangle b = player.getBounds();
            shapeRenderer.rect(b.x, b.y, b.width, b.height);
    
    
            for (Torre torre : spawnTorre.torres) {
                Rectangle c = torre.getBounds();
                shapeRenderer.rect(c.x, c.y, c.width, c.height);
                Rectangle d = torre.torre2.getBounds();
                shapeRenderer.rect(d.x, d.y, d.width, d.height);
            }
            
            shapeRenderer.end();
        }

        

    }

    void Voltar() {
        main.setScreen(new MenuScreen(main));
        main.TrocarMusica(main.music_menu);
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
        main.batch.dispose();
        playerTexture.dispose();
        arvoresTexture.dispose();
        nuvensTexture.dispose();
        montanhaTexture.dispose();
        font.dispose(); 
        uiStage.dispose(); 
    }
    @Override
    public void hide() {
    }

}
