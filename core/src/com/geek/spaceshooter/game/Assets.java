package com.geek.spaceshooter.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {
    private static final Assets ourInstance = new Assets();

    public static Assets getInstance() {
        return ourInstance;
    }

    private AssetManager assetManager;

    private TextureAtlas mainAtlas;

    private Assets() {
        assetManager = new AssetManager();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public TextureAtlas getMainAtlas() {
        return mainAtlas;
    }

    public void loadAssets(com.geek.spaceshooter.game.screens.ScreenType type) {
        switch (type) {
            case MENU:
                assetManager.load("my.pack", TextureAtlas.class);
                assetManager.load("bg.png", Texture.class);
                assetManager.load("font2.fnt", BitmapFont.class);
                assetManager.finishLoading();
                mainAtlas = assetManager.get("my.pack", TextureAtlas.class);
                break;
            case GAME:
                assetManager.load("font2.fnt", BitmapFont.class);
                assetManager.load("my.pack", TextureAtlas.class);
                assetManager.load("bg.png", Texture.class);
                assetManager.load("music.mp3", Music.class);
                assetManager.load("laser.wav", Sound.class);
                assetManager.load("CollapseNorm.wav", Sound.class);
                assetManager.finishLoading();
                mainAtlas = assetManager.get("my.pack", TextureAtlas.class);
                break;
            case HALL_OF_FAME:
                assetManager.load("scoreFont.fnt", BitmapFont.class);
                assetManager.load("my.pack", TextureAtlas.class);
                assetManager.load("bg.png", Texture.class);
                assetManager.finishLoading();
                mainAtlas = assetManager.get("my.pack", TextureAtlas.class);
                break;
        }
    }

    public void clear() {
        assetManager.clear();
    }
}
