package br.mackenzie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Esta classe representa o balde. Ela herda de GameObject e implementa a lógica de movimento do jogador, lendo as entradas do teclado ou toque.
public class Player extends GameObject {

    Viewport viewport;
    public float velocidadeY = 0f;
    public float gravidade = -20f; 
    private float stateTime = 0f;
    Animation<TextureRegion> animation;
    public float chaoHeight;
    public float dt;
    Main game;
    boolean olhandoDireita = true;
    
    
    public Player(Main game, Texture texture, float x, float y, float width, float height, Viewport viewport) {
        super(texture, x, y, width, height);
        this.game = game;
        this.viewport = viewport;
        TextureRegion[] frames = TextureRegion.split(texture, 288, 300)[0];
        animation = new Animation<>(0.1f, frames);
        super.margemx = width * 0.2f; 
        super.margemy = height * 0.45f;
        bounds = new Rectangle(x+margemx, y+margemy, width-(margemx*2), height-(margemy*2));
        game.inputManager.player = this;
    }


    @Override
    public void update(float dt) {
        this.dt = dt;
        float worldHeight = viewport.getWorldHeight();
        stateTime += dt;

        game.inputManager.inputType.run();

        y = MathUtils.clamp(y, chaoHeight - margemy, worldHeight - margemy- bounds.height);
        velocidadeY = MathUtils.clamp(velocidadeY, -6, 6);

        super.currentFrame = animation.getKeyFrame(stateTime, true);
    }

    public void draw(SpriteBatch batch) {
        if (olhandoDireita){
            batch.draw(currentFrame, x, y, width, height);
        }else{
            batch.draw(currentFrame, x + width, y, -width, height);
        }
    }
} 