package br.mackenzie;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class Botao extends ImageButton {

    public Botao(TextureRegion up, TextureRegion down, Runnable acao) {
        super(criarStyle(up, down));

        this.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                acao.run(); // executa a lambda
            }
        });
    }

    private static ImageButtonStyle criarStyle(TextureRegion up, TextureRegion down) {
        ImageButtonStyle style = new ImageButtonStyle();
        style.up = new TextureRegionDrawable(up);
        style.down = new TextureRegionDrawable(down);
        return style;
    }
}