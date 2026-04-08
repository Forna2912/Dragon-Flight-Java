package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

//Esta classe representa o balde. Ela herda de GameObject e implementa a lógica de movimento do jogador, lendo as entradas do teclado ou toque.
public class Player extends GameObject {

    private Vector2 touchPos;
    private Viewport viewport;
    
    public Player(Texture texture, float x, float y, float width, float height, Viewport viewport) {
        super(texture, x, y, width, height);
        this.viewport = viewport;
        this.touchPos = new Vector2();
        vida = 1;
        sprite.setOriginCenter();
    }

    @Override
    public void update(float dt) {
        float speed = 4f;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float playerWidth = sprite.getWidth();
        float playerHeight = sprite.getHeight();
        
        // Movimento via teclado
        float rotation = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            sprite.translateX(speed * dt);
            rotation = -25f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            sprite.translateX(-speed * dt);
            rotation = 25f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            sprite.translateY(speed * dt); // move the nave right
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            sprite.translateY(-speed * dt); // move the nave left
        }
        sprite.setRotation(rotation);
        
        // Movimento via toque ou mouse
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);
            sprite.setCenterX(touchPos.x);
            sprite.setCenterY(touchPos.y);
        }

        // Garante que o jogador não saia da tela
        sprite.setX(MathUtils.clamp(sprite.getX(), 0, worldWidth - playerWidth));
        sprite.setY(MathUtils.clamp(sprite.getY(), 0, worldHeight - playerHeight));
    }
}