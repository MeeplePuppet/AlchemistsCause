package com.uprightpath.alchemy.cause.physics;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Geo on 8/31/2014.
 */
public abstract class RailEntity extends CollisionEntity {
    protected RailType railType;
    protected RailSeries railSeries;
    protected Vector2 start = new Vector2();
    protected Vector2 end = new Vector2();
    protected Vector2 initialStart = new Vector2();
    protected Vector2 initialEnd = new Vector2();
    protected Vector2 normal = new Vector2();
    protected Vector2 slip = new Vector2();
    protected float friction = .90f;

    public abstract int getPriorityImplement(AgentEntity agentEntity);

    public RailSeries getRailSeries() {
        return railSeries;
    }

    public void setRailSeries(RailSeries railSeries) {
        this.railSeries = railSeries;
    }

    public abstract Vector2 getPosition(AgentEntity agentEntity);

    public float getFriction() {
        return friction;
    }

    public Vector2 getSlip() {
        return slip;
    }

    public RailType getRailType() {
        return railType;
    }

    public void setRailType(RailType railType) {
        this.railType = railType;
    }

    public abstract boolean isCollides();

    public boolean hasPriority(AgentEntity agentEntity) {
        if (agentEntity.getRail() == null) {
            return true;
        } else {
            return getPriorityImplement(agentEntity) > agentEntity.getRail().getPriorityImplement(agentEntity);
        }
    }

    public enum RailType {SURFACE, LADDER}
}
