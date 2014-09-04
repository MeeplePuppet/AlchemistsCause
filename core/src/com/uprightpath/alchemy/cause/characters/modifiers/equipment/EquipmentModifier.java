package com.uprightpath.alchemy.cause.characters.modifiers.equipment;

import com.uprightpath.alchemy.cause.characters.modifiers.Modifier;

/**
 * Created by Geo on 9/2/2014.
 */
public abstract class EquipmentModifier extends Modifier {
    protected boolean[] appliesTo = new boolean[5];

    public boolean appliesToWeapons() {
        return appliesTo[0];
    }

    public boolean appliesToArmor() {
        return appliesTo[1];
    }

    public boolean appliesToOffenseSpells() {
        return appliesTo[2];
    }

    public boolean appliesToHealingSpells() {
        return appliesTo[3];
    }

    public boolean appliesToDefenseSpells() {
        return appliesTo[4];
    }

    public abstract boolean isRandomModifier();
}
