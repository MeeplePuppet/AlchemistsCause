package com.uprightpath.alchemy.cause.physics.player;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.uprightpath.alchemy.cause.control.Control;
import com.uprightpath.alchemy.cause.physics.AgentEntity;
import com.uprightpath.alchemy.cause.physics.PhysicsWorld;

/**
 * Created by Geo on 8/25/2014.
 */
public class PlayerAgent extends AgentEntity {
    protected Polygon collisionPolygon;
    protected Polygon platformCollisionPolygon;

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
    public void applyLogic(PhysicsWorld physicsWorld) {
        float prevX = velocity.x;
        float prevY = velocity.y;
        if (rail == null) {
            acceleration.set(0, 0);
        } else {
            acceleration.set(rail.getSlip());
        }
        if (Control.JUMP.isJustDown() && Control.DOWN.isDown() && rail != null) {
            System.out.println("Falling!");
            this.rail = null;
            this.fallThrough = true;
        } else if (Control.JUMP.isJustDown() && rail != null) {
            System.out.println("Jumping!");
            acceleration.y = 1f / 10f;
            this.rail = null;
        } else if (Control.JUMP.isDown() && velocity.y > 0) {
            acceleration.y = 1f / 300f;
        }
        if (Control.LEFT.isDown()) {
            acceleration.x = -(rail == null ? .005f : .02f);
        } else if (Control.RIGHT.isDown()) {
            acceleration.x = (rail == null ? .005f : .02f);
        } else if (this.rail == null || Math.abs(this.velocity.x) > physicsWorld.getGroundSpeedMax().x) {
            this.velocity.x = this.velocity.x * physicsWorld.getAirFriction().x;
        } else {
            this.velocity.x = this.velocity.x * this.rail.getFriction();
        }
        if (!Control.DOWN.isDown()) {
            this.fallThrough = false;
        }
        this.applyDeltaVelocity(acceleration);
        if (rail == null) {
            float maxX = Math.max(Math.abs(prevX * physicsWorld.getAirFriction().x), physicsWorld.getAirSpeedMax().x);
            float maxY = Math.max(Math.abs(prevY), physicsWorld.getAirSpeedMax().y);
            velocity.x = MathUtils.clamp(velocity.x, -maxX, maxX);
            velocity.y = MathUtils.clamp(velocity.y, -maxY, maxY);
            if (!(Control.LEFT.isDown() || Control.RIGHT.isDown()) && Math.abs(velocity.x) <= physicsWorld.getAirSpeedMin().x) {
                velocity.x = 0;
            }
        } else {
            float maxX = Math.max(Math.abs(prevX * rail.getFriction()), physicsWorld.getGroundSpeedMax().x);
            float maxY = Math.max(Math.abs(prevY * rail.getFriction()), physicsWorld.getGroundSpeedMax().y);
            velocity.x = MathUtils.clamp(velocity.x, -maxX, maxX);
            velocity.y = MathUtils.clamp(velocity.y, -maxY, maxY);
            if (!(Control.LEFT.isDown() || Control.RIGHT.isDown()) && Math.abs(velocity.x) <= physicsWorld.getGroundSpeedMin().x) {
                velocity.x = 0;
            }
        }
    }

    @Override
    public Polygon getPlatformCollisionPolygon() {
        return platformCollisionPolygon;
    }

    public void setPlatformCollisionPolygon(Polygon platformCollisionPolygon) {
        this.platformCollisionPolygon = platformCollisionPolygon;
    }

    @Override
    public void collidedWithAgent(AgentEntity agentEntity) {

    }
}
