package com.uprightpath.alchemy.cause.characters.modifiers.equipment;

/**
 * Created by Geo on 9/2/2014.
 */
public class RerollModifier extends EquipmentModifier {
    private int limit;
    private RerollModifierType rerollModifierType;

    public RerollModifier() {
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public RerollModifierType getRerollModifierType() {
        return rerollModifierType;
    }

    public void setRerollModifierType(RerollModifierType rerollModifierType) {
        this.rerollModifierType = rerollModifierType;
    }

    @Override
    public boolean isRandomModifier() {
        return false;
    }

    public enum RerollModifierType {HIGH, LOW, RANDOM}
}
