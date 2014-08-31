package com.uprightpath.alchemy.cause.physics;

import com.uprightpath.alchemy.cause.control.Control;

/**
 * Created by Geo on 8/30/2014.
 */
public class EdgePlatformEntity extends PlatformEntity {
    private boolean leftSide;
    private Control requiredModifier;

    public EdgePlatformEntity() {

    }

    public EdgePlatformEntity(boolean leftSide) {
        this.leftSide = leftSide;
    }

    public boolean isLeftSide() {
        return leftSide;
    }

    public void setLeftSide(boolean leftSide) {
        this.leftSide = leftSide;
    }

    public Control getRequiredModifier() {
        return requiredModifier;
    }

    public void setRequiredModifier(Control requiredModifier) {
        this.requiredModifier = requiredModifier;
    }

    public boolean canPlatformCollide(AgentEntity agentEntity) {
        return (leftSide ? (start.x < agentEntity.getRightBoundingSide().x && end.x >= agentEntity.getRightBoundingSide().x) : (start.x <= agentEntity.getLeftBoundingSide().x && end.x > agentEntity.getLeftBoundingSide().x)) && super.canPlatformCollide(agentEntity);
    }

    public int getPriorityImplement(AgentEntity agentEntity) {
        return requiredModifier.isDown() ? 1 : -1;
    }
}
