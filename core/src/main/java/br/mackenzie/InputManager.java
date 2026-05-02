package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class InputManager {

    Player player;
    Runnable inputType;
    private Vector2 mouse = new Vector2();

    InputManager(Player player){
        this.player = player;
    }

    void SeguirMouse(){
        mouse = new Vector2();
        mouse.set(Gdx.input.getX(), Gdx.input.getY());
        player.viewport.unproject(mouse);
        player.y = mouse.y - player.height/2;
    }

}
