package com.uprightpath.alchemy.cause.characters.modifiers.equipment;

/**
 * Created by Geo on 9/2/2014.
 */
public class ValueModifier extends EquipmentModifier {
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

    @Override
    public boolean isRandomModifier() {
        return false;
    }

    public enum ValueModifierType {DAMAGE, CRITICAL, CRITICAL_MAX, CRITICAL_PIERCE}
}
