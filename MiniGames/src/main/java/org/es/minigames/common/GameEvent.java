package org.es.minigames.common;

/**
 * Class that defines possible user inputs.
 *
 * Created by Cyril on 25/09/13.
 */
public class GameEvent {

    public static final int ACTION_LEFT = 0;
    public static final int ACTION_UP = 1;
    public static final int ACTION_RIGHT = 2;
    public static final int ACTION_DOWN = 3;

    private int mAction;

    public GameEvent(int action) {
        mAction = action;
    }

    public int getAction() {
        return mAction;
    }
}
