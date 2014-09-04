package com.uprightpath.alchemy.cause.characters;

import com.badlogic.gdx.utils.Pool;
import com.uprightpath.alchemy.cause.Element;

/**
 * Created by Geo on 9/1/2014.
 */
public class DamageInfo implements Pool.Poolable {
    public static final Pool<DamageInfo> damagePool = new Pool<DamageInfo>() {
        @Override
        protected DamageInfo newObject() {
            return new DamageInfo();
        }
    };
    public int damage = 0;
    public int critical = 0;
    public int criticalMax = 0;
    public int criticalPierce = 0;
    public int[] element = new int[Element.values().length];

    public void applyDefense(DamageInfo damageInfo) {
        this.critical = Math.min(this.critical, this.criticalMax);
        damageInfo.critical = Math.min(damageInfo.critical, damageInfo.criticalMax);
        this.critical -= damageInfo.criticalPierce;
        damageInfo.critical -= this.criticalPierce;
        this.critical -= Math.max(0, damageInfo.critical);
        if (this.critical > 0) {
            this.damage = (int) (this.damage * (1f + this.critical * .2f));
        }
        this.damage -= damageInfo.damage;
    }

    @Override
    public void reset() {
        this.damage = 0;
        this.critical = 0;
        this.criticalMax = 0;
        this.criticalPierce = 0;
        this.element = new int[Element.values().length];
    }
}
