package com.uprightpath.alchemy.cause.physics;

import com.badlogic.gdx.math.Vector2;
import com.uprightpath.alchemy.cause.control.Control;

/**
 * Created by Geo on 8/31/2014.
 */
public class LadderEntity extends RailEntity {
    protected Vector2 start = new Vector2();
    protected Vector2 end = new Vector2();
    protected Vector2 endPosition = new Vector2();
    protected Vector2 speedUp = new Vector2(.01f, .02f);
    protected Vector2 speedDown = new Vector2(.01f, .02f);

    public LadderEntity() {
        this.friction = .25f;
    }

    public LadderEntity(Vector2 start, Vector2 end) {
        this.start.set(start);
        this.end.set(end);
        this.initialStart.set(start);
        this.initialEnd.set(end);
        normal.set(-(end.y - start.y), end.x - start.x);
        float dst = start.dst(end);
        speedUp.set(((end.x - start.x) / dst) * .01f, ((end.y - start.y) / dst) * .1f);
        speedDown.set(-((end.x - start.x) / dst) * .01f, -((end.y - start.y)) / dst * .2f);
    }

    public void setEndPoints(Vector2 start, Vector2 end) {
        this.start.set(start);
        this.end.set(end);
        this.initialStart.set(start);
        this.initialEnd.set(end);
        normal.set(-(end.y - start.y), end.x - start.x);
        float dst = start.dst(end);
        speedUp.set(((end.x - start.x) / dst) * .01f, ((end.y - start.y) / dst) * .1f);
        speedDown.set(-((end.x - start.x) / dst) * .01f, -((end.y - start.y)) / dst * .2f);
    }

    public void updatePosition() {
        this.start.set(initialStart).add(position);
        this.end.set(initialEnd).add(position);
        this.collisionPolygon.setPosition(position.x, position.y);
    }

    public boolean canCollide(AgentEntity agentEntity) {
        return (Control.UP.isDown() || Control.DOWN.isDown()) && (super.canCollide(agentEntity.getPlatformCollisionPolygon()) || super.canCollide(agentEntity));
    }

    @Override
    public int getPriorityImplement(AgentEntity agentEntity) {
        return ((Control.UP.isDown() && super.canCollide(agentEntity)) || (Control.DOWN.isDown() && super.canCollide(agentEntity.getPlatformCollisionPolygon()))) ? 2 : -2;
    }

    @Override
    public Vector2 getPosition(AgentEntity agentEntity) {
        if (super.canCollide(agentEntity)) {
            if (Control.UP.isDown()) {
                return endPosition.set(agentEntity.getPosition()).add(speedUp);
            } else if (Control.DOWN.isDown()) {
                return endPosition.set(agentEntity.getPosition()).add(speedDown);
            }
            return endPosition.set(agentEntity.getPosition());
        } else if (Control.DOWN.isDown() && super.canCollide(agentEntity.getPlatformCollisionPolygon())) {
            return endPosition.set(agentEntity.getPosition()).add(speedDown);
        }
        return null;
    }

    @Override
    public boolean isCollides() {
        return false;
    }
}
