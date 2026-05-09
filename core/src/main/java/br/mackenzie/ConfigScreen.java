package br.mackenzie;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class ConfigScreen implements Screen {

    private Main game;
    FitViewport UIViewport;
    FitViewport gameViewport;
    private Stage uiStage;
    Table configTable;
    Table tituloTable;
    BitmapFont font;
    Label titulo;


    public ConfigScreen(Main game) {
        this.game = game;
        this.UIViewport = game.UIViewport;
        this.gameViewport = game.gameViewport;
    }
    
    @Override
    public void render(float dt) {
        
        gameViewport.apply();
        UIViewport.apply();
        game.batch.setProjectionMatrix(gameViewport.getCamera().combined);
        
        game.background.update(dt);
        game.chao.update(dt);
        
        game.batch.begin();
        game.background.draw();
        game.chao.draw();
        game.batch.end();

        uiStage.act();
	    uiStage.draw();
        
    }
    
    @Override public void show() {

        tituloTable = new Table();
        uiStage = new Stage(UIViewport, game.UIbatch);
        configTable = new Table();
        uiStage.addActor(configTable);
        uiStage.addActor(tituloTable);

        font = game.manager.get("fonte.fnt", BitmapFont.class);

        tituloTable.setFillParent(true);
        tituloTable.top().padTop(50);

        //configTable.setDebug(true);
        // tituloTable.setDebug(true);

        configTable.setFillParent(true);
        configTable.padTop(150); 

        Gdx.input.setInputProcessor(uiStage);


        Label.LabelStyle style_titulo = new Label.LabelStyle();
        style_titulo.font = font;
        style_titulo.fontColor = Color.valueOf("383837");
        font.getData().setScale(0.4f);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = Color.valueOf("383837");
        font.getData().setScale(0.1f);

        titulo = new Label("Configurações", style_titulo);
        titulo.setAlignment(Align.center);

        Skin skin = new Skin(Gdx.files.internal("UISkin/uiskin.json"));
        skin.getFont("default-font").getData().setScale(0.2f);
        
        tituloTable.add(titulo);

        Label volume_textLabel = new Label("Volume", style);
        Slider volume_slider = new Slider(
            0f,   // mínimo
            0.5f,   // máximo
            0.01f,// passo
            false,// horizontal
            skin
        );
        volume_slider.setValue(game.volume_geral);
        volume_slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float valor = volume_slider.getValue();
                game.TrocarVolume(valor);
            }
        });
        
        Label input_textLabel = new Label("Tipo de Comando", style);
        SelectBox<inputOption> selectBox = new SelectBox<>(skin);
        selectBox.setItems(
            new inputOption("Pulo", () -> game.inputManager.Pulo()),
            new inputOption("Pedaleira", () -> game.inputManager.Pedaleira()),
            new inputOption("Seguir mouse", () -> game.inputManager.SeguirMouse())
        );
        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.inputManager.inputType = selectBox.getSelected().acao;
            }
        });
        
        
        Botao botao_voltar = new Botao(game, "botao_voltar.png", "botao_voltar_pressionado.png", () -> game.setScreen(new MenuScreen(game)));
        
        float largura = 96f;
        float proporcao = botao_voltar.getPrefHeight() / botao_voltar.getPrefWidth();
        float altura = largura * proporcao;
        
        configTable.add(input_textLabel);
        configTable.add(volume_textLabel).row();
        configTable.add(selectBox).width(250).pad(10);
        configTable.add(volume_slider).width(300).pad(10).row();
        configTable.add(botao_voltar).pad(10).width(largura).height(altura).colspan(2);

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