package br.mackenzie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class Botao extends ImageButton {

    Texture upTexture;
    Texture downTexture;
    TextureRegion upTextureRegion;
    TextureRegion downTextureRegion;

    public Botao(Main game, String up, String down, Runnable acao) {
        super(new ImageButtonStyle());

        upTexture = game.manager.get(up, Texture.class);
        downTexture = game.manager.get(down, Texture.class);
        upTextureRegion = new TextureRegion(upTexture);
        downTextureRegion = new TextureRegion(downTexture);
        criarStyle(up, down);
        this.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                acao.run(); // executa a lambda
            }
        });
    }

    private void criarStyle(String up, String down) {
        ImageButtonStyle style = new ImageButtonStyle();
        style.up = new TextureRegionDrawable(upTextureRegion);
        style.down = new TextureRegionDrawable(downTextureRegion);
        this.setStyle(style);
    }
}