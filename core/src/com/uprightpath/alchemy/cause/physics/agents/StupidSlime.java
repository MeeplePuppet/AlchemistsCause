package com.uprightpath.alchemy.cause.physics.agents;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.uprightpath.alchemy.cause.control.Control;
import com.uprightpath.alchemy.cause.physics.AgentEntity;
import com.uprightpath.alchemy.cause.physics.PhysicsWorld;

/**
 * Created by Geo on 8/31/2014.
 */
public class StupidSlime extends AgentEntity {
    protected Polygon collisionPolygon;
    protected Polygon platformCollisionPolygon;
    private boolean facing;

    public StupidSlime() {
        this.collisionPolygon = new Polygon(new float[]{0.f, .5f, 0.f, 0.f, .5f, 0.f, .5f, .5f});
        this.platformCollisionPolygon = new Polygon(new float[]{0f, 0f, 0.f, -.1f, .5f, -.1f, .5f, 0f});
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
    public void applyStateLogic(PhysicsWorld physicsWorld) {
        boolean moved = false;
        boolean down = false;
        if (MathUtils.random(5) == 0) {
            facing = !facing;
        }

        if (MathUtils.random(5) < 4 || rail == null) {
            moved = true;
        } else {
            if (MathUtils.random(5) < 2) {
                down = true && !rail.isCollides();
            }
        }
        if (rail == null) {
            acceleration.set(0, 0);
        } else {
            acceleration.set(rail.getSlip());
        }
        if (down && rail != null) {
            this.rail = null;
            this.fallThrough = true;
        } else if (!moved && rail != null) {
            acceleration.y = 2f / 10f;
            this.rail = null;
        }
        if (!down) {
            this.fallThrough = false;
        }
        if (moved) {
            acceleration.x = (facing ? -1 : 1) * (rail == null ? .005f : .02f);
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
            velocity.x = MathUtils.clamp(velocity.x, -physicsWorld.getAirSpeedMax().x, physicsWorld.getAirSpeedMax().x);
            velocity.y = MathUtils.clamp(velocity.y, -physicsWorld.getAirSpeedMax().y, physicsWorld.getAirSpeedMax().y);
            if (!moved && Math.abs(velocity.x) <= physicsWorld.getAirSpeedMin().x) {
                velocity.x = 0;
            }
        } else {
            velocity.x = MathUtils.clamp(velocity.x, -physicsWorld.getGroundSpeedMax().x, physicsWorld.getGroundSpeedMax().x);
            velocity.y = MathUtils.clamp(velocity.y, -physicsWorld.getGroundSpeedMax().y, physicsWorld.getGroundSpeedMax().y);
            if (!moved && Math.abs(velocity.x) <= physicsWorld.getGroundSpeedMin().x) {
                velocity.x = 0;
            }
        }
    }

    @Override
    public void updateState() {

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
