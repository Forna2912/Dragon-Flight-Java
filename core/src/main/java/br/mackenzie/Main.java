package br.mackenzie;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends Game {

    public SpriteBatch batch;
    FitViewport gameViewport;
    ScreenViewport UIViewport;
    Background background;
    Chao chao;
    public AssetManager manager;

    @Override
    public void create() {
        manager = new AssetManager();

        manager.load("botao_play.png", Texture.class);
        manager.load("botao_play_pressionado.png", Texture.class);
        manager.load("botao_sair.png", Texture.class);
        manager.load("botao_sair_pressionado.png", Texture.class);

        manager.finishLoading();

        this.gameViewport = new FitViewport(8, 5);
        this.UIViewport = new ScreenViewport();
        this.background = new Background(this);
        this.chao = new Chao(this);
        batch = new SpriteBatch();
        setScreen(new MenuScreen(this));
    }
}