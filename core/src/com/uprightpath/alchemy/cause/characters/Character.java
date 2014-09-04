package com.uprightpath.alchemy.cause.characters;

import com.badlogic.gdx.utils.Array;
import com.uprightpath.alchemy.cause.characters.modifiers.character.CharacterModifier;

/**
 * Created by Geo on 9/2/2014.
 */
public abstract class Character {
    protected int currentHealth;
    protected int maxHealth;
    protected int currentAether;
    protected int focusedAether;
    protected int maxAether;
    protected Equipment currentWeapon;
    protected Equipment currentArmor;
    protected Array<CharacterModifier> modifierArray;

    public DamageInfo getCurrentWeaponDamage() {
        return currentWeapon.createDamageInfo(this);
    }

    public DamageInfo getCurrentArmorDefense() {
        return currentArmor.createDamageInfo(this);
    }

    public abstract void receivedDamage(DamageInfo damage);

    public abstract void providedDamage(DamageInfo damage);
}
