package com.uprightpath.alchemy.cause.physics;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Geo on 8/25/2014.
 */
public class CollisionEntity extends PhysicsEntity {

    protected Polygon collisionPolygon;
    protected Vector2 velocity = new Vector2();

    public CollisionEntity() {

    }

    @Override
    protected void updatePosition() {
        this.collisionPolygon.setPosition(position.x, position.y);
    }

    @Override
    public Polygon getCollisionPolygon() {
        return collisionPolygon;
    }

    public void setCollisionPolygon(Polygon collisionPolygon) {
        this.collisionPolygon = collisionPolygon;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public boolean canCollide(AgentEntity agentEntity) {
        return canCollide(agentEntity.getCollisionPolygon());
    }

    @Override
    public boolean canCollide(Polygon collisionPolygon) {
        return Intersector.overlaps(this.collisionPolygon.getBoundingRectangle(), collisionPolygon.getBoundingRectangle());
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
}
