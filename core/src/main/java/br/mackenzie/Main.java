package br.mackenzie;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {

    public SpriteBatch batch;
    FitViewport gameViewport;
    FitViewport UIViewport;
    Background background;
    Chao chao;
    public AssetManager manager;
    Music music_menu;
    Music music_fase1;
    Music music_fase2;
    float velocidade = 0.05f;
    Music musica_atual;
    Music musica_nova;
    float contador = 0;
    float volume_atual;
    float volume_geral = 0.3f;

    @Override
    public void create() {
        manager = new AssetManager();

        manager.load("botao_play.png", Texture.class);
        manager.load("botao_play_pressionado.png", Texture.class);
        manager.load("botao_config.png", Texture.class);
        manager.load("botao_config_pressionado.png", Texture.class);
        manager.load("botao_sair.png", Texture.class);
        manager.load("botao_sair_pressionado.png", Texture.class);
        manager.load("fonte.fnt", BitmapFont.class);
        manager.load("fase1.mp3", Music.class);
        manager.load("fase2.mp3", Music.class);
        manager.load("menu.mp3", Music.class);

        manager.finishLoading();

        music_menu = manager.get("menu.mp3", Music.class);
        music_fase1 = manager.get("fase1.mp3", Music.class);
        music_fase2 = manager.get("fase2.mp3", Music.class);

        TocarMusica(music_menu);

        this.gameViewport = new FitViewport(8, 5);
        this.UIViewport = new FitViewport(800, 500);
        this.background = new Background(this);
        this.chao = new Chao(this);
        batch = new SpriteBatch();
        setScreen(new MenuScreen(this));
    }

    @Override
    public void render () {
        if (musica_atual != null && musica_nova != null) {
            
            float dt = Gdx.graphics.getDeltaTime();
            contador+=dt;
            
            if (contador >= 0.1f) {
                volume_atual -= 0.01f;

                if (musica_nova.getVolume() >= volume_geral) {
                    musica_nova.setVolume(volume_geral);
                }
                else{
                    musica_nova.setVolume(musica_nova.getVolume()+0.1f);
                }
                if (volume_atual < 0) {
                    musica_atual.setVolume(0);
                    musica_atual.stop();
                }
                else{
                    musica_atual.setVolume(volume_atual);
                }
                contador = 0;
            }
        }
        super.render();
        
    }

    void TrocarMusica(Music nova) {
        volume_atual = musica_atual.getVolume();
        musica_nova = nova;
        musica_nova.setLooping(true);
        musica_nova.setVolume(0f);
        musica_nova.play();
    }

    void TrocarVolume(float volume){
        volume_geral = volume;
        musica_atual.setVolume(volume);
    }

    void TocarMusica(Music nova) {
        if (nova != null) {
            nova.setLooping(true);
            nova.setVolume(volume_geral);
            nova.play();
            musica_atual = nova;
        }
    }
}