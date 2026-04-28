package br.mackenzie;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class Botao {
    public ImageButton botao;
    public Botao(Main game, Table table, TextureRegion Up, TextureRegion Down, int w, int h) {

        ImageButtonStyle style = new ImageButtonStyle();
        style.up = new TextureRegionDrawable(Up);
        style.down = new TextureRegionDrawable(Down);
        botao = new ImageButton(style);

        table.add(botao).pad(10); // Adicione o botão ao palco

        botao.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game)); 
            }
        });
    };
};


