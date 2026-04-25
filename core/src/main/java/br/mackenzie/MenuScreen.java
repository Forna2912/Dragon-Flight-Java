package br.mackenzie;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen implements Screen {

    private Main game;
    FitViewport viewport;

    public MenuScreen(Main game) {
        this.game = game;
        this.viewport = game.viewport;
    }
    
    @Override
    public void render(float dt) {
        
        
        viewport.apply();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        
        game.background.update(dt);
        game.chao.update(dt);
        
        game.batch.begin();
        
        game.background.draw();
        game.chao.draw();
        
        game.batch.end();
        
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
        }
    }
    
    @Override public void show() {
    }

    @Override public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}