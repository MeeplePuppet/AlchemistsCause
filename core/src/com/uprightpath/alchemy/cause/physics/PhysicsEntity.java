package com.uprightpath.alchemy.cause.physics;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * An entity that exists within the physics world. These objects are used to control collisions/interactions.
 * Created by Geo on 8/25/2014.
 */
public abstract class PhysicsEntity {
    protected String name;
    protected Vector2 position = new Vector2();

    public PhysicsEntity() {

    }

    public PhysicsEntity(String name) {
        this.name = name;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.setPosition(position.x, position.y);
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
        this.updatePosition();
    }

    public void translate(Vector2 position) {
        this.translate(position.x, position.y);
    }

    public void translate(float x, float y) {
        this.position.add(x, y);
        this.updatePosition();
    }

    protected abstract void updatePosition();

    public abstract Polygon getCollisionPolygon();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract boolean isSolid();


    public abstract boolean canCollide(AgentEntity agentEntity);

    public abstract boolean canCollide(Polygon polygon);

    public abstract Vector2 getVelocity();
}
