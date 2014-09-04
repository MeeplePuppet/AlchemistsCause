package com.uprightpath.alchemy.cause.characters.modifiers.equipment;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Geo on 9/1/2014.
 */
public class DiceModifier extends EquipmentModifier implements RandomModifier {
    private int[] damageValues;
    private boolean[] criticalValues;
    private float damageAverage;
    private float criticalAverage;
    private int lastRoll;

    public DiceModifier() {
    }

    public int[] getDamageValues() {
        return damageValues;
    }

    public void setDamageValues(int[] damageValues) {
        this.damageValues = damageValues;
        damageAverage = 0;
        for (int i = 0; i < damageValues.length; i++) {
            damageAverage += damageValues[i];
        }
        damageAverage /= damageValues.length;
    }

    public boolean[] getCriticalValues() {
        return criticalValues;
    }

    public void setCriticalValues(boolean[] criticalValues) {
        this.criticalValues = criticalValues;
        criticalAverage = 0;
        for (int i = 0; i < criticalValues.length; i++) {
            criticalAverage += criticalValues[i] ? 1 : 0;
        }
        damageAverage /= criticalValues.length;
    }

    public void roll() {
        lastRoll = MathUtils.random(damageValues.length - 1);
    }

    public int getLastRoll() {
        return lastRoll;
    }

    public int getLastValue() {
        return damageValues[lastRoll];
    }

    public boolean getLastCritical() {
        return criticalValues[lastRoll];
    }

    public float getMinimum() {
        return damageValues[0];
    }

    public float getMaximum() {
        return damageValues[damageValues.length - 1];
    }

    public float getDamageAverage() {
        return damageAverage;
    }

    @Override
    public boolean isRandomModifier() {
        return true;
    }

    public float getCriticalAverage() {
        return criticalAverage;
    }
}
