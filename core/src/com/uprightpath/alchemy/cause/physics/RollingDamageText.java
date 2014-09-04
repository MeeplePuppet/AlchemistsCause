package com.uprightpath.alchemy.cause.physics;

/**
 * Created by Geo on 9/2/2014.
 */
public class RollingDamageText {
    private String value;
    private int timer;

    public String getValue() {
        return value;
    }

    public boolean isDone() {
        return (timer--) == 0;
    }
}
