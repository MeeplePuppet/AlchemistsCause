package com.uprightpath.alchemy.cause.physics.player;

import com.badlogic.gdx.math.Polygon;
import com.uprightpath.alchemy.cause.physics.AgentEntity;
import com.uprightpath.alchemy.cause.physics.PhysicsWorld;

/**
 * Created by Geo on 8/31/2014.
 */
public abstract class PlayerState {
    protected PlayerAgent playerAgent;

    public PlayerState() {
    }

    public PlayerState(PlayerAgent playerAgent) {
        this.playerAgent = playerAgent;
    }

    public PlayerAgent getPlayerAgent() {
        return playerAgent;
    }

    public void setPlayerAgent(PlayerAgent playerAgent) {
        this.playerAgent = playerAgent;
    }

    public abstract void applyLogic(PhysicsWorld physicsWorld);

    public abstract void applyLimits(PhysicsWorld physicsWorld);

    protected abstract void updatePosition();

    public abstract Polygon getCollisionPolygon();

    public abstract boolean isSolid();

    public abstract Polygon getPlatformCollisionPolygon();

    public abstract void collidedWithAgent(AgentEntity agentEntity);

    public enum PlayerStateType {FALLING, WALKING, SWIMMING, CLIMBING}
}
