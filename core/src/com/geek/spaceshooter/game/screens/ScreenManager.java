package com.geek.spaceshooter.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.geek.spaceshooter.game.Assets;
import com.geek.spaceshooter.game.SpaceGame;

public class ScreenManager {

    private static final ScreenManager ourInstance = new ScreenManager();

    private SpaceGame game;

    private MenuScreen menuScreen;
    private com.geek.spaceshooter.game.screens.GameScreen gameScreen;
    private HallOfFameScreen hallScreen;

    public static ScreenManager getInstance() {
        return ourInstance;
    }

    public SpaceGame getGame() {
        return game;
    }

    public void init(SpaceGame game) {
        this.game = game;
        this.menuScreen = new MenuScreen(this.game.getBatch());
        this.gameScreen = new com.geek.spaceshooter.game.screens.GameScreen(this.game.getBatch());
        this.hallScreen = new HallOfFameScreen(this.game.getBatch());
    }

    public void goToScreen(ScreenType type) {
        Screen actualScreen = this.game.getScreen();
        Assets.getInstance().getAssetManager().dispose();
        Assets.getInstance().setAssetManager(new AssetManager());
        if (actualScreen != null) {
            actualScreen.dispose();
        }

        if (type.equals(ScreenType.MENU)) {
            Assets.getInstance().loadAssets(ScreenType.MENU);
            this.game.setScreen(this.menuScreen);

        } else if (type.equals(ScreenType.HALL_OF_FAME)) {
            Assets.getInstance().loadAssets(ScreenType.HALL_OF_FAME);
            this.game.setScreen(this.hallScreen);

        } else if (type.equals(ScreenType.GAME)) {
            Assets.getInstance().loadAssets(ScreenType.GAME);
            this.game.setScreen(this.gameScreen);

        } else {
            throw new RuntimeException("Wrong screen type.");
        }
    }

}
