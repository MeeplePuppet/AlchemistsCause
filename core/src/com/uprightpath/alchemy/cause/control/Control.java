package com.uprightpath.alchemy.cause.control;

/**
 * Created by Geo on 8/26/2014.
 */
public enum Control {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    JUMP,
    ATTACK,
    INTERACT,
    MAGIC,
    TARGET,
    STATUS,
    MENU;
    private static ControlSource controlSource;

    public static void setControlSource(ControlSource controlSource) {
        Control.controlSource = controlSource;
    }

    public boolean isDown() {
        return controlSource.isDown(this);
    }

    public boolean isJustDown() {
        return controlSource.isJustDown(this);
    }
}
