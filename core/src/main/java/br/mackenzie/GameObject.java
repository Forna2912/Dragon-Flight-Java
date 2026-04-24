package br.mackenzie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

//Esta é a classe base. Ela define as propriedades e comportamentos que são comuns a todos os objetos do nosso jogo
public abstract class GameObject {

    protected int vida;
    protected Texture texture;
    protected float x, y, width, height, margemx, margemy;
    protected Rectangle bounds;
    protected TextureRegion currentFrame;
    
    public GameObject(Texture texture, float x, float y, float width, float height) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.margemx = 0;
        this.margemy = 0;
        
    }

    public abstract void update(float dt);

    public Rectangle getBounds() {
        // Atualiza a posição do retângulo de colisão para coincidir com a do sprite
        bounds.setPosition(x+margemx, y+margemy);
        return bounds;
    }
}