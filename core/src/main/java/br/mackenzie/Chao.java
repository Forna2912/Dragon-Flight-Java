package br.mackenzie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Chao {

    float bgOffset2;

    float w;

    private Main game;
    FitViewport viewport;

    Texture chaoTexture;

    public Chao(Main game) {
        this.game = game;
        this.viewport = game.viewport;
        w = viewport.getWorldWidth();
        chaoTexture = new Texture("chao.png");

    }

    public void update(float dt){

        bgOffset2 += 2f * dt; //chao
        if (bgOffset2 > w) bgOffset2 = 0;

    }
    
    public void draw(){
    
        game.batch.draw(chaoTexture,  -bgOffset2,-0.5f, w, (float)chaoTexture.getHeight()/300);
        game.batch.draw(chaoTexture,  -bgOffset2 + w,-0.5f, w, (float)chaoTexture.getHeight()/300);

    }

}
