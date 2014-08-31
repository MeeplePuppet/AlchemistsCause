package com.uprightpath.alchemy.cause.physics;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Geo on 8/25/2014.
 */
public class RailSeries {
    protected String name;
    protected Array<RailEntity> railEntities = new Array<RailEntity>();

    public RailSeries() {

    }

    public RailSeries(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRailEntity(RailEntity railEntity) {
        railEntities.removeValue(railEntity, true);
        railEntities.add(railEntity);
        railEntity.setRailSeries(this);
    }

    public Array<RailEntity> getRailEntities() {
        return railEntities;
    }

    public void removeRailEntity(RailEntity railEntity) {
        railEntities.removeValue(railEntity, true);
    }
}
