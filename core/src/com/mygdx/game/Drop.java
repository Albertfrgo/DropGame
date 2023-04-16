package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Amb aquesta classe creem l'escenari on instanciar els sprite de gotes

public class Drop extends Game{

    public SpriteBatch batch;
    public BitmapFont font;

    private MainMenuScreen menuScreen;

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        menuScreen = new MainMenuScreen(this);
        this.setScreen(menuScreen);
    }

    public void render(){
        super.render();
    }

    public void dispose(){
        batch.dispose();
        font.dispose();
        menuScreen.dispose();
    }
}
