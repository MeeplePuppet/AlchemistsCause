package com.uprightpath.alchemy.cause.physics;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Geo on 8/25/2014.
 */
public class PlatformEntity extends CollisionEntity {
    protected Surface surface;
    protected Polygon platformCollisionPolygon;
    protected boolean collides;
    protected Vector2 start = new Vector2();
    protected Vector2 end = new Vector2();
    protected Vector2 initialStart = new Vector2();
    protected Vector2 initialEnd = new Vector2();
    protected Vector2 normal = new Vector2();

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
        return agentEntity.getPlatform() != null && agentEntity.getPlatform().surface == surface || super.canCollide(agentEntity.getCollisionPolygon());
    }

    public boolean canPlatformCollide(AgentEntity agentEntity) {
        System.out.println(Intersector.overlapConvexPolygons(platformCollisionPolygon, agentEntity.getPlatformCollisionPolygon()) + " " + (agentEntity.getVelocity().dot(normal) <= 0) + " " + (agentEntity.getVelocity().y <= 0));
        return (!agentEntity.isFallThrough() || this.collides) && ((Intersector.overlapConvexPolygons(platformCollisionPolygon, agentEntity.getPlatformCollisionPolygon()) && agentEntity.getVelocity().dot(normal) <= 0 && agentEntity.getVelocity().y <= 0) || (agentEntity.getPlatform() != null && agentEntity.getPlatform().surface == surface));
    }

    public boolean canCollide(Polygon polygon) {
        return super.canCollide(polygon);
    }

    public Vector2 getPosition(AgentEntity agentEntity) {
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

    public Surface getSurface() {
        return surface;
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    public boolean isCollides() {
        return collides;
    }

    public void setCollides(boolean collides) {
        this.collides = collides;
    }
}
