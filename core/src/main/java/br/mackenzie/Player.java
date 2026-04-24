package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Esta classe representa o balde. Ela herda de GameObject e implementa a lógica de movimento do jogador, lendo as entradas do teclado ou toque.
public class Player extends GameObject {

    private Viewport viewport;
    private float velocidadeY = 0f;
    private float gravidade = -20f; 
    private float stateTime = 0f;
    Animation<TextureRegion> animation;
    public float chaoHeight;
    
    
    public Player(Texture texture, float x, float y, float width, float height, Viewport viewport) {
        super(texture, x, y, width, height);
        this.viewport = viewport;
        TextureRegion[] frames = TextureRegion.split(texture, 288, 300)[0];
        animation = new Animation<>(0.1f, frames);
        super.margemx = width * 0.2f; 
        super.margemy = height * 0.45f;
        bounds = new Rectangle(x+margemx, y+margemy, width-(margemx*2), height-(margemy*2));
    }


    @Override
    public void update(float dt) {
        float worldHeight = viewport.getWorldHeight();
        // Aplica gravidade (aceleração)
        velocidadeY += gravidade * dt; 
        stateTime += dt;

        // Move o player
        //sprite.setY(sprite.getY() - velocidadeY * dt);
        y += velocidadeY * dt ;

        // Pulo
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            pulo();
        }

        // Clamp tela
        y = MathUtils.clamp(y, chaoHeight - margemy, worldHeight - margemy- bounds.height);


        super.currentFrame = animation.getKeyFrame(stateTime, true);
    }

    public void pulo() {
        this.velocidadeY = 6f;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(currentFrame, x, y, width, height);
    }
} 