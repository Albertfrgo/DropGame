package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	private Rectangle bucket;
	private int vportWidth = 800, vportHeight =480, bucketSize =64, dropSize =64;
	private final Vector3 touchPos = new Vector3();
	private Array<Rectangle> raindrops;
	private long lastDropTime;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, vportWidth, vportHeight);

		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");

		//Carreguem les imatges del cubell i la gota, seran quadrats de 64 pixels
		bucketImage = new Texture(Gdx.files.internal("bucketSprite.png"));
		dropImage = new Texture(Gdx.files.internal("dropSprite.png"));

		//Carreguem els efectes de caiguda de la gota i de pluja
		dropSound = Gdx.audio.newSound(Gdx.files.internal("dropSound.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rainSound.mp3"));

		// nomes iniciar el joc, comença a sonar el soroll constant de la pluja en bucle
		// el soroll de la pluja es una musica, a causa de la seva duracio no es pot mantenir en memoria
		rainMusic.setLooping(true);
		rainMusic.play();

		bucket = new Rectangle();
		bucket.x = vportHeight / 2 - bucketSize / 2;
		bucket.y = 20;
		bucket.width = bucketSize;
		bucket.height = bucketSize;

		//Comencem a generar gotes
		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	}

	@Override
	public void render () {
		//Algunes caracteristiques de l'escenari
		ScreenUtils.clear(0, 0.5f, 0.6f, 1);
		camera.update();

		//Renderitzat de la galleda
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucketImage, bucket.x, bucket.y);
//		batch.draw(img, 0, 0);
		for(Rectangle raindrop: raindrops){
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		batch.end();

		//Fer que la galleda es mogui quan la pantalla es tocada
		// o detecta polsacio del ratoli, es mou cap a la zona de la polsacio
		if(Gdx.input.isTouched()){
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - bucketSize / 2;
		}

		//La galleda respondra tambe a les tecles fletxa esquerra y dreta del teclat
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= vportWidth/3 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += vportWidth/3 * Gdx.graphics.getDeltaTime();

		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > vportWidth - bucketSize) bucket.x = vportWidth - 64;

		if(TimeUtils.nanoTime() - lastDropTime > 750000000) spawnRaindrop();

		//Moviment cap avall de les gotes, de moment constant
		for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ){
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + dropSize < 0) iter.remove();
			if(raindrop.overlaps(bucket)){
				dropSound.play();
				iter.remove();
			}
		}
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
	
	@Override
	public void dispose () {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
//		img.dispose();
	}
}
