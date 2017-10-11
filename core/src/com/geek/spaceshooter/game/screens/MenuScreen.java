package com.geek.spaceshooter.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.geek.spaceshooter.game.Assets;
import com.geek.spaceshooter.game.Background;
import com.geek.spaceshooter.game.MyInputProcessor;

/**
 * Created by FlameXander on 04.10.2017.
 */

public class MenuScreen implements Screen {

    private SpriteBatch batch;
    private Background background;
    private Vector2 emptyVelocity = new Vector2(0, 0);
    private TextureRegion texStart;
    private TextureRegion texExit;
    private Rectangle rectStart;
    private Rectangle rectExit;
    private Rectangle rectScore;

    private MyInputProcessor mip;

    public MenuScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        Assets.getInstance().loadAssets(com.geek.spaceshooter.game.screens.ScreenType.MENU);
        TextureAtlas atlas = Assets.getInstance().getMainAtlas();
        background = new Background(atlas.findRegion("star16"));
        texExit = atlas.findRegion("btExit");
        texStart = atlas.findRegion("btPlay");
        rectStart = new Rectangle(256, 232, texStart.getRegionWidth(), texStart.getRegionHeight());
        rectExit = new Rectangle(1280 - 512, 232, texExit.getRegionWidth(), texExit.getRegionHeight());
        mip = (MyInputProcessor) Gdx.input.getInputProcessor();
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(com.geek.spaceshooter.game.screens.ScreenManager.getInstance().getGame().getCamera().combined);
        batch.begin();
        background.render(batch);
        batch.draw(texStart, rectStart.x, rectStart.y);
        batch.draw(texExit, rectExit.x, rectExit.y);
        batch.end();
    }

    public void update(float dt) {
        background.update(dt, emptyVelocity);
        if (mip.isTouchedInArea(rectStart) != -1) {
            com.geek.spaceshooter.game.screens.ScreenManager.getInstance().goToScreen(com.geek.spaceshooter.game.screens.ScreenType.GAME);
        }
        if (mip.isTouchedInArea(rectExit) != -1) {
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        com.geek.spaceshooter.game.screens.ScreenManager.getInstance().getGame().resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Assets.getInstance().clear();
    }

    @Override
    public void dispose() {
        Assets.getInstance().clear();
    }
}
