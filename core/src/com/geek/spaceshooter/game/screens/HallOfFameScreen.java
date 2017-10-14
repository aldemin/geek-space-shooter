package com.geek.spaceshooter.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.geek.spaceshooter.game.Assets;
import com.geek.spaceshooter.game.Background;
import com.geek.spaceshooter.game.MyInputProcessor;
import com.geek.spaceshooter.game.ScoreManager;
import com.geek.spaceshooter.game.SpaceGame;

import java.util.List;

public class HallOfFameScreen implements Screen {
    private SpriteBatch batch;
    private Background background;
    private TextureAtlas atlas;
    private BitmapFont fnt;
    private List<Integer> scores;
    private TextureRegion textureBack;
    private Rectangle rectBack;
    private Vector2 emptyVelocity;

    private MyInputProcessor mip;

    public HallOfFameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        Assets.getInstance().loadAssets(ScreenType.HALL_OF_FAME);
        this.emptyVelocity = new Vector2(0,0);
        mip = (MyInputProcessor) Gdx.input.getInputProcessor();
        this.atlas = Assets.getInstance().getMainAtlas();
        this.textureBack = this.atlas.findRegion("btBack");
        this.rectBack = new Rectangle(35, SpaceGame.SCREEN_HEIGHT - 115, this.textureBack.getRegionWidth(), this.textureBack.getRegionHeight());
        this.fnt = Assets.getInstance().getAssetManager().get("scoreFont.fnt", BitmapFont.class);
        this.background = new Background(atlas.findRegion("star16"));
        this.scores = ScoreManager.getInstance().getScores();
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(ScreenManager.getInstance().getGame().getCamera().combined);
        batch.begin();
        background.render(batch);
        batch.draw(textureBack, rectBack.x, rectBack.y);
        int yPos =  SpaceGame.SCREEN_HEIGHT - 100;
        this.fnt.draw(this.batch, "Scores:", SpaceGame.SCREEN_WIDTH / 2 - 100, yPos);
        yPos -= 150;
        for (int i = 0; i < this.scores.size(); i++) {
            if (i % 2 == 0) {
                this.fnt.draw(this.batch, String.format("%d - %d", i + 1, this.scores.get(i)), SpaceGame.SCREEN_WIDTH / 2 - 400, yPos);
            } else {
                this.fnt.draw(this.batch, String.format("%d - %d", i + 1, this.scores.get(i)), SpaceGame.SCREEN_WIDTH - 300, yPos);
                yPos -= 70;
            }

        }
        batch.end();
    }

    public void update(float dt) {
        background.update(dt, this.emptyVelocity);
        if (mip.isTouchedInArea(rectBack) != -1) {
            ScreenManager.getInstance().goToScreen(ScreenType.MENU);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
