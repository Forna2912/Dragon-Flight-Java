package br.mackenzie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

//Esta classe representa o balde. Ela herda de GameObject e implementa a lógica de movimento do jogador, lendo as entradas do teclado ou toque.
public class Torre extends GameObject {

    private float velocidadeX = 2f;
    public Torre2 torre2;
    private Texture texturetorre2;
    public boolean passed = false;
    public int direcao;

    public Torre(Texture texture, float x, float y, float width, float height, Viewport viewport, int irParaDireita) {
        super(texture, x, y, width, height);
        texturetorre2 = new Texture("torre.png");
        this.torre2 = new Torre2(texturetorre2, x, y+height+1.5f, width, height, viewport, irParaDireita);
        bounds = new Rectangle(x+margemx, y+margemy, width-(margemy*2), height-(margemx*2));
        direcao = irParaDireita;
    }


    @Override
    public void update(float dt) {
        x += velocidadeX * dt * direcao;
        torre2.update(dt);
    }


    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
        torre2.draw(batch);
    }

    public void passed(){
        passed = true;
    }

}