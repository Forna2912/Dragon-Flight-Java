package br.mackenzie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TorreManager {

    float bgOffset2;
    float w;
    private Main game;
    FitViewport viewport;
    Texture torreTexture;
    Array<Torre> torres;
    private float spawnTime = 0;
    Runnable faseAtual;
    Runnable proximaFase;
    int numFaseAtual = 1;
    int numProximaFase = 2;
    float y = 0;
    float passoY = 0.1f;
    int contadorTorre = 0;
    float tempoEntreFases = 0;
    float tempoDaFase = 0f;
    Player player;
    float delta;
    float tempoTroca;
    float inicio;
    
    
    public TorreManager(Main game, Player player) {
        this.game = game;
        this.viewport = game.gameViewport;
        this.player = player;
        torreTexture = new Texture("torre.png");
        w = viewport.getWorldWidth();
        torres = new Array<>();
        faseAtual = () -> fase1();
        inicio = player.x;
    }

    public void update(float dt){
        delta = dt;
        spawnTime += dt;   
        
        switch (numFaseAtual) {
            case 0:
                if (torres.size == 0){
                    if (tempoEntreFases >= 3){
                        faseAtual = proximaFase;
                        numFaseAtual = numProximaFase;
                    }
                    tempoEntreFases += dt;
                }
                break;
                
            case 1:
                tempoDaFase += dt;
                if (contadorTorre >= 5){
                    trocarFase( 2);
                }
                break;

            case 2:
                tempoDaFase += dt;
                if (tempoDaFase >= 5){
                    proximaFase = () -> fase3();
                    trocarFase( 5);
                }
                break;

            case 3:
                tempoDaFase += dt;
                if (contadorTorre >= 10){
                    //trocarFase( 2);
                }
                break;
        }
            
            
        faseAtual.run();
        for (Torre torre : torres) {
            torre.update(dt);
        }
        if (!torres.isEmpty()){
            Torre primeiraTorre = torres.first();
            if (primeiraTorre.x + primeiraTorre.width < 0 && primeiraTorre.direcao == game.direcao) {
                torres.removeIndex(0);
            }else if (primeiraTorre.x > game.gameViewport.getWorldWidth() && primeiraTorre.direcao == game.direcao){
                torres.removeIndex(0);
            }
        }
    }

    
    private void entreFases(){}
    
    private void fase1(){
        if (spawnTime > 2f) {
            torres.add(new Torre(torreTexture, viewport.getWorldWidth(),Math.round(MathUtils.random(-2f, 0f) * 100f) / 100f , (float)torreTexture.getWidth()/350, (float)torreTexture.getHeight()/350, viewport, game.direcao));
            contadorTorre += 1;
            spawnTime = 0;
        }
    }
    
    private void fase2(){
        if (spawnTime > 0.1f) {
            torres.add(new Torre(torreTexture, viewport.getWorldWidth(), y, (float)torreTexture.getWidth()/350, (float)torreTexture.getHeight()/350, viewport, game.direcao));
            contadorTorre += 1;
            if ( y>=0 || y<=-2 ){
                passoY *= -1;
            }
            y += passoY;
            spawnTime = 0;
        }
    }

    private void trocarLadoTela(){
        
        tempoTroca += delta;
        float t = tempoTroca / 3;
        t = MathUtils.clamp(t, 0f, 1f);
        float smoothT = Interpolation.smooth.apply(t);
        game.direcao *= -1;

        if (t < 1f){
            player.x = MathUtils.lerp(inicio, 7-player.width, smoothT);
        }

        else{
            player.x = MathUtils.lerp(inicio, 7-player.width, smoothT);
            inicio = player.x;
            player.olhandoDireita = false;
            trocarFase(3);
        }
    }
    
    private void fase3(){
        if (spawnTime > 2f) {
            torres.add(new Torre(torreTexture, 0-torreTexture.getWidth()/350,Math.round(MathUtils.random(-2f, 0f) * 100f) / 100f , (float)torreTexture.getWidth()/350, (float)torreTexture.getHeight()/350, viewport, game.direcao));
            contadorTorre += 1;
            spawnTime = 0;
        }
    }

    private void fase4(){
        if (spawnTime > 2f) {
            torres.add(new Torre(torreTexture, 0-torreTexture.getWidth()/350,Math.round(MathUtils.random(-2f, 0f) * 100f) / 100f , (float)torreTexture.getWidth()/350, (float)torreTexture.getHeight()/350, viewport, game.direcao));
            contadorTorre += 1;
            spawnTime = 0;
        }
    }
    
    public void draw(){
        for (Torre torre : torres) {
            torre.draw(game.batch);
        }
    }
    
    private void trocarFase(int nProximaFase){
        numProximaFase = nProximaFase;
        faseAtual = () -> entreFases();
        numFaseAtual = 0;
        contadorTorre = 0;
        tempoDaFase = 0;
        tempoEntreFases = 0;

        switch (nProximaFase) {
            case 1:
                proximaFase = () -> fase1();
                break;
            case 2:
                proximaFase = () -> fase2();
                break;
            case 3:
                proximaFase = () -> fase3();
                break;
            case 5:
                proximaFase = () -> trocarLadoTela();
                break;
        }
    }

}
