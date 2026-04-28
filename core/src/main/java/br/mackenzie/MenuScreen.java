package br.mackenzie;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen implements Screen {

    private Main game;
    ScreenViewport UIViewport;
    FitViewport gameViewport;
    private Stage uiStage;
    Texture playButtonTexture;
    Texture pressedPlayButtonTexture;
    float margemBotoesX = 1f;
    float margemBotoesY = 1f;
    Table botoes;

    public MenuScreen(Main game) {
        this.game = game;
        this.UIViewport = game.UIViewport;
        this.gameViewport = game.gameViewport;
    }
    
    @Override
    public void render(float dt) {
        
        
        gameViewport.apply();
        game.batch.setProjectionMatrix(gameViewport.getCamera().combined);
        
        game.background.update(dt);
        game.chao.update(dt);
        
        game.batch.begin();
        game.background.draw();
        game.chao.draw();
        game.batch.end();

        UIViewport.apply();
        uiStage.act();
	    uiStage.draw();
    }
    
    @Override public void show() {
        
        uiStage = new Stage(UIViewport, game.batch);
        botoes = new Table();
        uiStage.addActor(botoes);

        botoes.setDebug(true);
        botoes.setFillParent(true);
        botoes.center().padTop(150);

        Gdx.input.setInputProcessor(uiStage);

        playButtonTexture = game.manager.get("assets/botao_play.png", Texture.class);
        pressedPlayButtonTexture = game.manager.get("assets/botao_play_pressionado.png", Texture.class);

        new Botao(game, botoes, new TextureRegion(playButtonTexture), new TextureRegion(pressedPlayButtonTexture), 75, 75);
        new Botao(game, botoes, new TextureRegion(playButtonTexture), new TextureRegion(pressedPlayButtonTexture), 75, 75);

    }

    @Override public void resize(int width, int height) {
        UIViewport.update(width, height, true);
        gameViewport.update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {
        dispose();
    }
    @Override public void dispose() {
        uiStage.dispose();
        game.manager.dispose();
    }
}