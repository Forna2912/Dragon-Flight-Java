package br.mackenzie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Esta classe representa o balde. Ela herda de GameObject e implementa a lógica de movimento do jogador, lendo as entradas do teclado ou toque.
public class Torre2 extends GameObject {

    private float velocidadeX = 2f;

    public Torre2(Texture texture, float x, float y, float width, float height, Viewport viewport) {
        super(texture, x, y, width, height);
    }


    @Override
    public void update(float dt) {
        x -= velocidadeX * dt ;
    }


    public void draw(SpriteBatch batch) {
        batch.draw(
            texture,
            x,
            y,  
            width, 
            height, 
            0,
            0,
            texture.getWidth(),
            texture.getHeight(),
            false, 
            true
        );
    }

}