package com.uprightpath.alchemy.cause.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by Geo on 8/26/2014.
 */
public class KeyboardControlSource implements ControlSource {
    public ObjectMap<Control, Integer> keyboardMapping = new ObjectMap<Control, Integer>();

    public KeyboardControlSource() {
        keyboardMapping.put(Control.DOWN, Input.Keys.DOWN);
        keyboardMapping.put(Control.UP, Input.Keys.UP);
        keyboardMapping.put(Control.LEFT, Input.Keys.LEFT);
        keyboardMapping.put(Control.RIGHT, Input.Keys.RIGHT);
        keyboardMapping.put(Control.JUMP, Input.Keys.Z);
    }

    public boolean isDown(Control control) {
        return keyboardMapping.containsKey(control) && Gdx.input.isKeyPressed(keyboardMapping.get(control));
    }

    public boolean isJustDown(Control control) {
        return keyboardMapping.containsKey(control) && Gdx.input.isKeyJustPressed(keyboardMapping.get(control));
    }

    public void setKeyboardMapping(Control control, int keyCode) {
        this.keyboardMapping.put(control, keyCode);
    }
}
