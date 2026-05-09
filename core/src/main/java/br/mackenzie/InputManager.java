package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class InputManager {

    Player player;
    Runnable inputType = () -> Pulo();
    private Vector2 mouse = new Vector2();

    void SeguirMouse(){
        mouse = new Vector2();
        mouse.set(Gdx.input.getX(), Gdx.input.getY());
        player.viewport.unproject(mouse);
        player.y = mouse.y - player.height/2;
    }

    void Pulo(){
        AplicarGravidade();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.velocidadeY = 6f;
        }
    }

    void AplicarGravidade(){
        player.velocidadeY += player.gravidade * player.dt; 
        player.y += player.velocidadeY * player.dt ;
    }
    
    boolean lastKeyPressedIsA = false;
    float timeGap;
    boolean pular = false;

    void Pedaleira(){
        
        if (Gdx.input.isKeyPressed(Input.Keys.A) && !lastKeyPressedIsA) {
            pular = true;
            lastKeyPressedIsA = true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D) && lastKeyPressedIsA) {
            pular = true;
            lastKeyPressedIsA = false;
        }
        
        timeGap += Gdx.graphics.getDeltaTime();

        timeGap = Math.min(timeGap, 2);
        
        if (pular){
            player.velocidadeY = 4 - (timeGap);
            timeGap = 0;
            pular = false;
        }

        AplicarGravidade();

        // System.out.println(timeGap); 
        // if (!(timeGap > 0.25f && timeGap < 0.45f)){
        //     AplicarGravidade();
        // }

    }
}
