package com.uprightpath.alchemy.cause.characters.modifiers.character;

/**
 * Created by Geo on 9/2/2014.
 */
public class ValueModifier extends CharacterModifier {
    private ValueModifierType valueModifierType;
    private int valueModifier;

    public ValueModifier() {

    }

    public int getValueModifier() {
        return valueModifier;
    }

    public void setValueModifier(int valueModifier) {
        this.valueModifier = valueModifier;
    }

    public ValueModifierType getValueModifierType() {
        return valueModifierType;
    }

    public void setValueModifierType(ValueModifierType valueModifierType) {
        this.valueModifierType = valueModifierType;
    }

    public enum ValueModifierType {HEALTH, AETHER, STRENGTH, DEXTERITY, INTELLIGENCE, CHARISMA}
}
