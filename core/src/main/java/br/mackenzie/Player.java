package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

//Esta classe representa o balde. Ela herda de GameObject e implementa a lógica de movimento do jogador, lendo as entradas do teclado ou toque.
public class Player extends GameObject {

    private Viewport viewport;
    private float velocidadeY = 0f;
    private float gravidade = -20f; 
    
    public Player(Texture texture, float x, float y, float width, float height, Viewport viewport) {
        super(texture, x, y, width, height);
        this.viewport = viewport;
        sprite.setOriginCenter();
    }


    @Override
    public void update(float dt) {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float playerWidth = sprite.getWidth();
        float playerHeight = sprite.getHeight();

        // Aplica gravidade (aceleração)
        velocidadeY += gravidade * dt;

        // Move o player
        //sprite.setY(sprite.getY() - velocidadeY * dt);
        float y = sprite.getY() + velocidadeY * dt + 0.5f * gravidade * dt * dt;
        sprite.setY(y);

        // Pulo
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            pulo();
        }

        // Clamp tela
        sprite.setX(MathUtils.clamp(sprite.getX(), 0, worldWidth - playerWidth));
        sprite.setY(MathUtils.clamp(sprite.getY(), 0, worldHeight - playerHeight));
    }

    public void pulo() {
        this.velocidadeY = 7f;
    }
}