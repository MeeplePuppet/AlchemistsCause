package com.uprightpath.alchemy.cause.physics;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Geo on 8/25/2014.
 */
public class PlatformEntity extends RailEntity {
    protected Polygon platformCollisionPolygon;
    protected boolean collides;

    private Vector2 right = new Vector2();
    private Vector2 left = new Vector2();

    public PlatformEntity() {
    }

    public PlatformEntity(Vector2 start, Vector2 end) {
        this.start.set(start);
        this.end.set(end);
        this.initialStart.set(start);
        this.initialEnd.set(end);
        normal.set(-(end.y - start.y), end.x - start.x);
        this.platformCollisionPolygon = new Polygon(new float[]{start.x, start.y, start.x, start.y - .2f, end.x, end.y - .2f, end.x, end.y});
    }

    public void setEndPoints(Vector2 start, Vector2 end) {
        this.start.set(start);
        this.end.set(end);
        this.initialStart.set(start);
        this.initialEnd.set(end);
        normal.set(-(end.y - start.y), end.x - start.x);
        this.platformCollisionPolygon = new Polygon(new float[]{start.x, start.y, start.x, start.y - .2f, end.x, end.y - .2f, end.x, end.y});
    }

    public void updatePosition() {
        this.start.set(initialStart).add(position);
        this.end.set(initialEnd).add(position);
        this.collisionPolygon.setPosition(position.x, position.y);
        this.platformCollisionPolygon.setPosition(position.x, position.y);
    }

    public Polygon getPlatformCollisionPolygon() {
        return platformCollisionPolygon;
    }

    public boolean canCollide(AgentEntity agentEntity) {
        return agentEntity.getRail() != null && agentEntity.getRail().railSeries == railSeries || super.canCollide(agentEntity.getCollisionPolygon());
    }

    public boolean canPlatformCollide(AgentEntity agentEntity) {
        return (!agentEntity.isFallThrough() || this.collides) && ((Intersector.overlapConvexPolygons(platformCollisionPolygon, agentEntity.getPlatformCollisionPolygon()) && agentEntity.getVelocity().dot(normal) <= 0 && agentEntity.getVelocity().y <= 0) || (agentEntity.getRail() != null && agentEntity.getRail().railSeries == railSeries));
    }

    public boolean canCollide(Polygon polygon) {
        return super.canCollide(polygon);
    }

    public Vector2 getPosition(AgentEntity agentEntity) {
        if (agentEntity.getRail() == null && !this.collides && (Intersector.pointLineSide(start, end, agentEntity.leftBoundingSidePrevious) < 0 || Intersector.pointLineSide(start, end, agentEntity.rightBoundingSidePrevious) < 0)) {
            return null;
        }
        float minX = agentEntity.getCollisionPolygon().getBoundingRectangle().x;
        float maxX = minX + agentEntity.getCollisionPolygon().getBoundingRectangle().width;
        if (minX <= start.x && maxX >= end.x) {
            Intersector.intersectLines(start.x, 0, start.x, 1, start.x, start.y, end.x, end.y, left);
            Intersector.intersectLines(end.x, 0, end.x, 1, start.x, start.y, end.x, end.y, right);
            left.x = agentEntity.getCollisionPolygon().getBoundingRectangle().x;
            right.x = agentEntity.getCollisionPolygon().getBoundingRectangle().x;
            return right.y > left.y ? right : left;
        } else if (minX >= start.x && maxX <= end.x) {
            Intersector.intersectLines(minX, 0, minX, 1, start.x, start.y, end.x, end.y, left);
            Intersector.intersectLines(maxX, 0, maxX, 1, start.x, start.y, end.x, end.y, right);
            left.x = agentEntity.getCollisionPolygon().getBoundingRectangle().x;
            right.x = agentEntity.getCollisionPolygon().getBoundingRectangle().x;
            return right.y > left.y ? right : left;
        } else if (maxX > start.x && maxX <= end.x) {
            Intersector.intersectLines(start.x, 0, start.x, 1, start.x, start.y, end.x, end.y, left);
            Intersector.intersectLines(maxX, 0, maxX, 1, start.x, start.y, end.x, end.y, right);
            left.x = agentEntity.getCollisionPolygon().getBoundingRectangle().x;
            right.x = agentEntity.getCollisionPolygon().getBoundingRectangle().x;
            return right.y > left.y ? right : left;
        } else if (minX < end.x && minX >= start.x) {
            Intersector.intersectLines(minX, 0, minX, 1, start.x, start.y, end.x, end.y, left);
            Intersector.intersectLines(end.x, 0, end.x, 1, start.x, start.y, end.x, end.y, right);
            left.x = agentEntity.getCollisionPolygon().getBoundingRectangle().x;
            right.x = agentEntity.getCollisionPolygon().getBoundingRectangle().x;
            return right.y > left.y ? right : left;
        }
        return null;
    }

    public boolean isCollides() {
        return collides;
    }

    public void setCollides(boolean collides) {
        this.collides = collides;
    }

    public int getPriorityImplement(AgentEntity agentEntity) {
        if (this.velocity.len2() > 0) {
            return agentEntity.getRail().normal.dot(this.velocity) > 0 ? 1 : -1;
        }
        return 0;
    }

    public Vector2 getSlip() {
        return slip;
    }

    public void setSlip(Vector2 slip) {
        this.slip = slip;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }
}
