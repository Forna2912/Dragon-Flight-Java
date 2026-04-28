package br.mackenzie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Background {

    public Background(Main game) {
        this.game = game;
        this.viewport = game.gameViewport;
        w = viewport.getWorldWidth();
        h = viewport.getWorldHeight();
        arvoresTexture = new Texture("arvores.png");
        nuvensTexture = new Texture("nuvens.png");
        montanhaTexture = new Texture("montanhas.png");
        ceuTexture = new Texture("ceu.png");
    }

    float bgOffset1;
    float bgOffset2;
    float bgOffset3;
    float bgOffset4;

    float w;
    float h;

    private Main game;
    FitViewport viewport;

    Texture arvoresTexture;
    Texture nuvensTexture;
    Texture montanhaTexture;
    Texture ceuTexture;

    public void update(float dt){

        bgOffset1 += 0.5f * dt; //arvores

        bgOffset3 += 0.2f * dt; //nuvens
        bgOffset4 += 0.3f * dt; //montanhas
        
        if (bgOffset1 > w) bgOffset1 = 0; //arvores
        if (bgOffset2 > w) bgOffset2 = 0; //chao
        if (bgOffset3 > w) bgOffset3 = 0; //nuvens
        if (bgOffset4 > w) bgOffset4 = 0; //montanhas

        
        
    }
    
    public void draw(){
        
        
        game.batch.draw(ceuTexture,0,0f, w, h);
    
        game.batch.draw(nuvensTexture,  -bgOffset3,0.2f, w, h);
        game.batch.draw(nuvensTexture,  -bgOffset3 + w,0.2f, w, h);
        
        game.batch.draw(montanhaTexture,  -bgOffset4,0f, w, h);
        game.batch.draw(montanhaTexture,  -bgOffset4 + w,0f, w, h);
        
        game.batch.draw(arvoresTexture,  -bgOffset1,0, w, h);
        game.batch.draw(arvoresTexture,  -bgOffset1 + w,0, w, h);
        
    }

}
