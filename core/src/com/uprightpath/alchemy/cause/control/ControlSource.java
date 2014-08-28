package com.uprightpath.alchemy.cause.control;

/**
 * Created by Geo on 8/26/2014.
 */
public interface ControlSource {
    public boolean isDown(Control control);

    public boolean isJustDown(Control control);
}
