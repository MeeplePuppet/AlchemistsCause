package com.uprightpath.alchemy.cause.physics.player;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.uprightpath.alchemy.cause.control.Control;
import com.uprightpath.alchemy.cause.physics.AgentEntity;

/**
 * Created by Geo on 8/25/2014.
 */
public class PlayerAgent extends AgentEntity {
    protected Polygon collisionPolygon;
    protected Polygon platformCollisionPolygon;
    protected Vector2 acceleration = new Vector2();

    public PlayerAgent() {
        this.collisionPolygon = new Polygon(new float[]{0.f, 1.8f, 0.f, 0.f, .8f, 0.f, .8f, 1.8f});
        this.platformCollisionPolygon = new Polygon(new float[]{0f, 0f, 0.f, -.1f, .8f, -.1f, .8f, 0f});
    }

    @Override
    protected void updatePosition() {
        collisionPolygon.setPosition(position.x, position.y);
        platformCollisionPolygon.setPosition(position.x, position.y);
    }

    @Override
    public Polygon getCollisionPolygon() {
        return collisionPolygon;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void applyLogic() {
        this.fallThrough = false;
        acceleration.set(0, 0);
        if (Control.JUMP.isJustDown() && platform != null && Control.DOWN.isDown()) {
            this.platform = null;
        } else if (Control.JUMP.isJustDown() && platform != null) {
            System.out.println("Jumping!");
            acceleration.y = .2f;
            this.platform = null;
        } else if (Control.JUMP.isDown() && fallTime < 30) {

        }
        if (Control.LEFT.isDown()) {
            acceleration.x = -(platform == null ? .01f : .02f);
        } else if (Control.RIGHT.isDown()) {
            acceleration.x = (platform == null ? .01f : .02f);
        }
        if (Control.DOWN.isDown()) {
            this.fallThrough = true;
        }
        this.applyDeltaVelocity(acceleration);
    }

    @Override
    public Polygon getPlatformCollisionPolygon() {
        return platformCollisionPolygon;
    }

    public void setPlatformCollisionPolygon(Polygon platformCollisionPolygon) {
        this.platformCollisionPolygon = platformCollisionPolygon;
    }
}
