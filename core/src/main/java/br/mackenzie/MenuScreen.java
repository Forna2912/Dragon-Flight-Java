package br.mackenzie;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen implements Screen {

    private Main game;
    FitViewport UIViewport;
    FitViewport gameViewport;
    private Stage uiStage;
    Table botoes;
    Table tituloTable;
    BitmapFont font;
    Label titulo;


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

        tituloTable = new Table();
        uiStage = new Stage(UIViewport, game.batch);
        botoes = new Table();
        uiStage.addActor(botoes);
        uiStage.addActor(tituloTable);

        font = game.manager.get("fonte.fnt", BitmapFont.class);

        tituloTable.setFillParent(true);
        tituloTable.top().padTop(100);

        //botoes.setDebug(true);
        // tituloTable.setDebug(true);

        botoes.setFillParent(true);
        botoes.padTop(150); 

        Gdx.input.setInputProcessor(uiStage);


        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = Color.valueOf("383837");
        float escala = 0.75f;
        font.getData().setScale(escala);

        titulo = new Label("Flappy Bird", style);
        titulo.setAlignment(Align.center);
        
        Botao botao_play = new Botao(game, "botao_play.png", "botao_play_pressionado.png", () -> Play());
        Botao botao_sair = new Botao(game, "botao_sair.png", "botao_sair_pressionado.png", () -> Gdx.app.exit());
        
        float largura = 96f;
        float proporcao = botao_play.getPrefHeight() / botao_play.getPrefWidth();
        float altura = largura * proporcao;

        tituloTable.add(titulo);
        botoes.add(botao_play).fill().pad(10).width(largura).height(altura);
        botoes.add(botao_sair).fill().pad(10).width(largura).height(altura);

       // System.out.println("oi");
    }

    void Play() {
        game.setScreen(new GameScreen(game));
        game.TrocarMusica(game.music_fase1);
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
    }
}