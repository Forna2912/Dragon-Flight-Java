package br.mackenzie;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {

    public SpriteBatch batch;
    FitViewport viewport;
    Background background;
    Chao chao;

    @Override
    public void create() {

        this.viewport = new FitViewport(8, 5);
        this.background = new Background(this);
        this.chao = new Chao(this);
        batch = new SpriteBatch();
        setScreen(new MenuScreen(this));
    }
}