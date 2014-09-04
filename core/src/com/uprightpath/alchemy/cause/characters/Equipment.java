package com.uprightpath.alchemy.cause.characters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.uprightpath.alchemy.cause.characters.modifiers.equipment.*;

/**
 * Created by Geo on 9/2/2014.
 */
public class Equipment {
    private EquipmentModifier[] baseModifiers;
    private EquipmentModifier[] additionalModifiers;
    private int maxModifiers;
    private Array<DiceModifier> diceModifiers = new Array<DiceModifier>();
    private Array<ValueModifier> valueModifiers = new Array<ValueModifier>();
    private Array<ElementModifier> elementModifiers = new Array<ElementModifier>();
    private Array<RerollModifier> rerollModifiers = new Array<RerollModifier>();
    private Array<RandomModifier> randomModifiers = new Array<RandomModifier>();
    private Vector3 damage = new Vector3();
    private Vector3 critical = new Vector3();
    private int damageModifier;
    private int criticalModifier;
    private int criticalMaxModifier;
    private int criticalPierceModifier;

    public Equipment() {
    }

    public EquipmentModifier[] getBaseModifiers() {
        return baseModifiers;
    }

    public void setBaseModifiers(EquipmentModifier[] baseModifiers) {
        this.baseModifiers = baseModifiers;
    }

    public EquipmentModifier[] getAdditionalModifiers() {
        return additionalModifiers;
    }

    public void setAdditionalModifiers(EquipmentModifier[] additionalModifiers) {
        this.additionalModifiers = additionalModifiers;
    }

    public int getMaxModifiers() {
        return maxModifiers;
    }

    public void setMaxModifiers(int maxModifiers) {
        this.maxModifiers = maxModifiers;
        EquipmentModifier[] newAdditionModifiers = new EquipmentModifier[maxModifiers];
        for (int i = 0; i < additionalModifiers.length && i < maxModifiers; i++) {
            newAdditionModifiers[i] = additionalModifiers[i];
        }
        additionalModifiers = newAdditionModifiers;
    }

    public void updateWeapon() {
        diceModifiers.clear();
        valueModifiers.clear();
        elementModifiers.clear();
        rerollModifiers.clear();
        randomModifiers.clear();
        damage.set(0, 0, 0);
        critical.set(0, 0, 0);
        damageModifier = 0;
        criticalModifier = 0;
        criticalMaxModifier = 0;
        criticalPierceModifier = 0;
        for (int i = 0; i < baseModifiers.length; i++) {
            if (baseModifiers[i] instanceof DiceModifier) {
                diceModifiers.add((DiceModifier) baseModifiers[i]);
            } else if (baseModifiers[i] instanceof ValueModifier) {
                valueModifiers.add((ValueModifier) baseModifiers[i]);
            } else if (baseModifiers[i] instanceof ElementModifier) {
                elementModifiers.add((ElementModifier) baseModifiers[i]);
            } else if (baseModifiers[i] instanceof RerollModifier) {
                rerollModifiers.add((RerollModifier) baseModifiers[i]);
            }
            if (baseModifiers[i] instanceof RandomModifier) {
                randomModifiers.add((RandomModifier) baseModifiers[i]);
            }
        }
        for (int i = 0; i < additionalModifiers.length; i++) {
            if (additionalModifiers[i] instanceof DiceModifier) {
                diceModifiers.add((DiceModifier) additionalModifiers[i]);
            } else if (additionalModifiers[i] instanceof ValueModifier) {
                valueModifiers.add((ValueModifier) additionalModifiers[i]);
            } else if (additionalModifiers[i] instanceof ElementModifier) {
                elementModifiers.add((ElementModifier) additionalModifiers[i]);
            } else if (additionalModifiers[i] instanceof RerollModifier) {
                rerollModifiers.add((RerollModifier) additionalModifiers[i]);
            }
            if (additionalModifiers[i] instanceof RandomModifier) {
                randomModifiers.add((RandomModifier) additionalModifiers[i]);
            }
        }
        for (int i = 0; i < diceModifiers.size; i++) {
            damage.x += diceModifiers.get(i).getMinimum();
            damage.y += diceModifiers.get(i).getDamageAverage();
            damage.z += diceModifiers.get(i).getMaximum();
            critical.x += 0;
            critical.y += diceModifiers.get(i).getCriticalAverage();
            critical.z += diceModifiers.get(i).getCriticalValues()[diceModifiers.get(i).getCriticalValues().length - 1] ? 1 : 0;
        }
        for (int i = 0; i < valueModifiers.size; i++) {
            switch (valueModifiers.get(i).getValueModifierType()) {
                case DAMAGE: {
                    damage.x += valueModifiers.get(i).getValueModifier();
                    damage.y += valueModifiers.get(i).getValueModifier();
                    damage.z += valueModifiers.get(i).getValueModifier();
                    damageModifier += valueModifiers.get(i).getValueModifier();
                    break;
                }
                case CRITICAL: {
                    critical.x += valueModifiers.get(i).getValueModifier();
                    critical.y += valueModifiers.get(i).getValueModifier();
                    critical.z += valueModifiers.get(i).getValueModifier();
                    criticalModifier += valueModifiers.get(i).getValueModifier();
                    break;
                }
                case CRITICAL_MAX: {
                    criticalMaxModifier += valueModifiers.get(i).getValueModifier();
                    break;
                }
                case CRITICAL_PIERCE: {
                    criticalPierceModifier += valueModifiers.get(i).getValueModifier();
                    break;
                }
            }
        }
    }

    public DamageInfo createDamageInfo(Character character) {
        DamageInfo damageInfo = new DamageInfo();
        for (int i = 0; i < randomModifiers.size; i++) {
            randomModifiers.get(i).roll();
        }
        for (int i = 0; i < rerollModifiers.size; i++) {
            switch (rerollModifiers.get(i).getRerollModifierType()) {
                case HIGH: {
                    for (int j = 0; j < randomModifiers.size; j++) {
                        if (randomModifiers.get(j).getLastRoll() >= rerollModifiers.get(i).getLimit()) {
                            randomModifiers.get(j);
                            break;
                        }
                    }
                    break;
                }
                case LOW: {
                    for (int j = 0; j < randomModifiers.size; j++) {
                        if (randomModifiers.get(j).getLastRoll() <= rerollModifiers.get(i).getLimit()) {
                            randomModifiers.get(j);
                            break;
                        }
                    }
                    break;
                }
                case RANDOM: {
                    randomModifiers.get(MathUtils.random(randomModifiers.size - 1));
                    break;
                }
            }
        }
        for (int i = 0; i < diceModifiers.size; i++) {
            damageInfo.damage += diceModifiers.get(i).getLastValue();
            damageInfo.critical += diceModifiers.get(i).getLastCritical() ? 1 : 0;
        }
        damageInfo.damage += damageModifier;
        damageInfo.critical += criticalModifier;
        damageInfo.criticalMax += criticalMaxModifier;
        damageInfo.criticalPierce += criticalPierceModifier;
        return damageInfo;
    }
}
