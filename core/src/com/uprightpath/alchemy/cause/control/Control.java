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
    private boolean down = false;
    private boolean justDown = false;

    public static void update() {
        for (int i = 0; i < Control.values().length; i++) {
            Control.values()[i].updateControl();
        }
    }

    public static void reset(boolean both) {
        for (int i = 0; i < Control.values().length; i++) {
            Control.values()[i].resetControl(both);
        }
    }

    public static void setControlSource(ControlSource controlSource) {
        Control.controlSource = controlSource;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isJustDown() {
        return justDown;
    }

    private void resetControl(boolean both) {
        down = down && both;
        justDown = false;
    }

    private void updateControl() {
        down = down || controlSource.isDown(this);
        justDown = justDown || controlSource.isJustDown(this);
    }
}
