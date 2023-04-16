package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Classe per al nostre menu

public class MainMenuScreen implements Screen{
    final Drop game;
    OrthographicCamera camera;
    private GameScreen gameScreen;
    private int vportWidth = 800, vportHeight =480;


    public MainMenuScreen(final Drop game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, vportWidth, vportHeight);
    }

    public void show() {

    }

    public void render(float delta) {
        gameScreen = new GameScreen(game);
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        /*game.font.draw(game.batch, "Welcome to Drop Game", vportWidth/2 -100, vportHeight -100);*/

        // Incrementem una mica la mida del text, el problema d'aquest metode
        // es que ens reescala un dibuix ja fet, tenint una aparença pixelada
        game.font.getData().setScale(1.3f);

        // Creem la classe GlyphLayout amb la que podrem obtenir les mides del text,
        // amb les quals podrem centrar del tot el text a la pantalla
        GlyphLayout layout = new GlyphLayout();
        layout.setText(game.font, "Welcome to Drop Game");
        float textWidth = layout.width;
        float textHeight = layout.height;

        game.font.draw(game.batch, "Welcome to Drop Game",(vportWidth - textWidth) / 2, (vportHeight + textHeight +100) / 2);

        game.font.getData().setScale(1);
        layout = new GlyphLayout();
        layout.setText(game.font, "Tap anywhere to begin");
        textWidth = layout.width;
        textHeight = layout.height;

        /*Fi del codi d'intent */

        game.font.draw(game.batch, "Tap anywhere to begin", (vportWidth - textWidth) / 2, (vportHeight + textHeight -100) / 2);
        game.batch.end();

        //Quan el jugador toqui algun punt de la pantalla comencem el joc,
        // per a això establirem la pantalla o escena a GameScreen, on es dura a terme el joc
        if(Gdx.input.isTouched()){
            game.setScreen(gameScreen);
        }
    }

    public void resize(int width, int height) {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void hide() {

    }

    public void dispose() {
    gameScreen.dispose();
    }
}
