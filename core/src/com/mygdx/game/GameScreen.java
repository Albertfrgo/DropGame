package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

//La pantalla del nostre joc, on tindrem les gotes, el cubell, hi haura la musica etc
// ens fara algunes de les funcions originals de la classe MyGdxGame,
// ja que a mes d'ampliar funcionalitats, hem dividit aquestes funcionalitats en diverses classes
public class GameScreen implements Screen{
    final Drop game;

    Texture dropImage;
    Texture bucketImage;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;
    private int vportWidth = 800, vportHeight =480, bucketSize =64, dropSize =64;

    public GameScreen(final Drop game){
        this.game = game;

        //Carreguem les imatges del cubell i la gota, seran quadrats de 64 pixels
        bucketImage = new Texture(Gdx.files.internal("bucketSprite.png"));
        dropImage = new Texture(Gdx.files.internal("dropSprite.png"));

        //Carreguem els efectes de caiguda de la gota i de pluja
        dropSound = Gdx.audio.newSound(Gdx.files.internal("dropSound.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rainSound.mp3"));
        rainMusic.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, vportWidth, vportHeight);

        //Creem el rectangle amb el qual representarem la nostra galleda
        bucket = new Rectangle();
        bucket.x = vportHeight / 2 - bucketSize / 2;
        bucket.y = 20;
        bucket.width = bucketSize;
        bucket.height = bucketSize;

        //Comencem a generar gotes
        raindrops = new Array<Rectangle>();
        spawnRaindrop();
    }

    //Metode que ens una gota aleatoriament al llarg de tot l'ample, a la part superior
    private void spawnRaindrop(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, vportWidth - dropSize);
        raindrop.y = vportHeight;
        raindrop.width = dropSize;
        raindrop.height = dropSize;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    public void show() {
    rainMusic.play();
    }

    public void render(float delta) {
        ScreenUtils.clear(0, 0.5f, 0.6f, 1);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "Drops Collected: " +dropsGathered, 0, 480);
        game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        for(Rectangle raindrop: raindrops){
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        game.batch.end();

        //Amb aquesta classe processarem els inputs (polsacions en pantalla)
        // de l'usuari i mourem la galleda
        if(Gdx.input.isTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject((touchPos));
            bucket.x = touchPos.x - bucketSize /2;
        }

        //Moure la galleda amb les tecles
        if(Gdx.input.isKeyPressed(Keys.LEFT)){
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            bucket.x += 200 * Gdx.graphics.getDeltaTime();
        }

        //Mantenir galleda als limits de la pantalla
        if (bucket.x < 0){
            bucket.x = 0;
        }
        if (bucket.x > vportWidth - bucketSize){
            bucket.x = vportWidth - bucketSize;
        }

        // Veure si necessitem crear una nova gota
        if(TimeUtils.nanoTime() - lastDropTime > 750000000){
            spawnRaindrop();
        }

        //Logica de moviment de les gotes, aquestes cauen cap avall
        // i desapareixen si son recollides per el cubell o toquen el terra
        Iterator <Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()){
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + dropSize < 0){
                iter.remove();
            }
            if (raindrop.overlaps(bucket)){
                dropsGathered++;
                dropSound.play();
                iter.remove();
            }
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
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }
}
