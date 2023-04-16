package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PlayAgainScreen implements Screen {
    final Drop game;
    OrthographicCamera camera;
    private GameScreen gameScreen;
    private int vportWidth = 800, vportHeight =480;

    private int score;

    private Stage stage;
    private Skin skin;

    public PlayAgainScreen(final Drop game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, vportWidth, vportHeight);

        //Creem la interficie grafica on anira el nostre boto
/*        stage = new Stage(new FitViewport(vportWidth, vportHeight, camera));
        skin = new Skin(Gdx.files.internal("uiskin.json"));*/

        // Crear el botó i afegir-lo a l'Stage
/*        TextButton playAgainButton = new TextButton("Play Again", skin);
        playAgainButton.setPosition(vportWidth / 2 - playAgainButton.getWidth() / 2, vportHeight / 2 - playAgainButton.getHeight() / 2 - 50);
        stage.addActor(playAgainButton);*/

        // Afegir acció al boto
/*        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(gameScreen);
            }
        });*/
    }

    public void setScore(int score){
        this.score = score;
    }

    public void show() {

    }

    public void render(float delta) {
        gameScreen = new GameScreen(game);
        ScreenUtils.clear(0, 0.1f, 0.3f, 1);

        //Dibuixar la interficie grrafica
/*        stage.act(delta);
        stage.draw();*/

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.font.getData().setScale(1.3f);
        GlyphLayout layout = new GlyphLayout();
        String text = "Your Score is: "+score;
        layout.setText(game.font, text);
        float textWidth = layout.width;
        float textHeight = layout.height;

        game.font.draw(game.batch, text,(vportWidth - textWidth) / 2, (vportHeight + textHeight) / 2);
        game.font.getData().setScale(1f);
        game.batch.end();
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

    }
}
