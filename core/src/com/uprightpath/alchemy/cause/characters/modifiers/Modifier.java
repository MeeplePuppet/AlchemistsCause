package com.uprightpath.alchemy.cause.characters.modifiers;

/**
 * Created by Geo on 9/2/2014.
 */
public abstract class Modifier {
    protected String name;
    protected boolean temporary;
    protected int resistance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTemporary() {
        return temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }
}
