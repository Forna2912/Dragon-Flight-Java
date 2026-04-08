package br.mackenzie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

//Esta é a classe base. Ela define as propriedades e comportamentos que são comuns a todos os objetos do nosso jogo
public abstract class GameObject {

    protected Sprite sprite;
    protected Rectangle bounds;
    protected int vida;

    public GameObject(Texture texture, float x, float y, float width, float height) {
        this.sprite = new Sprite(texture);
        this.sprite.setSize(width, height);
        this.sprite.setPosition(x, y);

        this.bounds = new Rectangle(x, y, width, height);
    }

    public abstract void update(float dt);

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Rectangle getBounds() {
        // Atualiza a posição do retângulo de colisão para coincidir com a do sprite
        bounds.setPosition(sprite.getX(), sprite.getY());
        return bounds;
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
    }
}