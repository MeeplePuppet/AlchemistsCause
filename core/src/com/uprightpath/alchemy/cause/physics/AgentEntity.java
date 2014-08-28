package com.uprightpath.alchemy.cause.physics;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.uprightpath.alchemy.cause.scenario.Faction;

/**
 * An entity that is controlled by some internal logic such as enemies and player characters.
 * Created by Geo on 8/25/2014.
 */
public abstract class AgentEntity extends PhysicsEntity {
    protected PlatformEntity platform;
    protected Vector2 velocity = new Vector2();
    protected Faction faction;
    protected boolean fallThrough;
    protected int fallTime;

    public AgentEntity() {
    }

    public abstract void applyLogic();

    public PlatformEntity getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformEntity platform) {
        this.platform = platform;
    }


    @Override
    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    public void applyDeltaVelocity(Vector2 deltaVelocity) {
        applyDeltaVelocity(deltaVelocity.x, deltaVelocity.y);
    }

    public void applyDeltaVelocity(float x, float y) {
        this.velocity.add(x, y);
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    @Override
    public boolean canCollide(AgentEntity physicsAgent) {
        return faction.isAgressiveTowards(physicsAgent.getFaction());
    }

    public abstract Polygon getPlatformCollisionPolygon();

    @Override
    public boolean canCollide(Polygon polygon) {
        return Intersector.overlaps(this.getCollisionPolygon().getBoundingRectangle(), polygon.getBoundingRectangle());
    }

    public boolean isFallThrough() {
        return fallThrough;
    }

    public void setFallThrough(boolean fallThrough) {
        this.fallThrough = fallThrough;
    }

    public int getFallTime() {
        return fallTime;
    }

    public void setFallTime(int fallTime) {
        this.fallTime = fallTime;
    }
}
