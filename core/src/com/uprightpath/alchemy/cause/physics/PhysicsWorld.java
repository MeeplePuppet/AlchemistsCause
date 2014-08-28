package com.uprightpath.alchemy.cause.physics;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Geo on 8/25/2014.
 */
public class PhysicsWorld {
    private CollisionEntity[] extents = new CollisionEntity[4];
    private AgentEntity followingAgent;
    private Array<AgentEntity> agents = new Array<AgentEntity>();
    private Array<CollisionEntity> collisions = new Array<CollisionEntity>();
    private Array<PlatformEntity> platforms = new Array<PlatformEntity>();
    private Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();

    public PhysicsWorld() {
    }

    public void update() {
        AgentEntity agentEntity;
        CollisionEntity collisionEntity;
        PlatformEntity platformEntity;
        Surface surface;
        Vector2 currentPosition, position;
        for (int i = 0; i < platforms.size; i++) {
            platformEntity = platforms.get(i);
            platformEntity.translate(platformEntity.getVelocity());
        }
        for (int i = 0; i < collisions.size; i++) {
            collisionEntity = collisions.get(i);
            collisionEntity.translate(collisionEntity.getVelocity());
        }
        for (int i = 0; i < agents.size; i++) {
            currentPosition = null;
            agentEntity = agents.get(i);
            agentEntity.applyLogic();
            agentEntity.translate(agentEntity.getVelocity());
            if (agentEntity.getPlatform() != null) {
                surface = agentEntity.getPlatform().getSurface();
                for (int j = 0; j < surface.getPhysicsPlatforms().size; j++) {
                    position = surface.getPhysicsPlatforms().get(j).getPosition(agentEntity);
                    if (position != null && (currentPosition == null || position.y > currentPosition.y)) {
                        agentEntity.setPosition(position);
                        agentEntity.setPlatform(surface.getPhysicsPlatforms().get(j));
                        currentPosition = position;
                    }
                }
                if (currentPosition == null) {
                    agentEntity.setPlatform(null);
                }
            }
            if (agentEntity.getPlatform() == null) {
                agentEntity.applyDeltaVelocity(0, -.01f);
                agentEntity.translate(0, -.01f);
                agentEntity.applyDeltaVelocity(0, -.01f);
            } else {
                agentEntity.getVelocity().y = 0;
            }
            for (int j = 0; j < collisions.size; j++) {
                if (collisions.get(j).canCollide(agentEntity.getCollisionPolygon())) {
                    if (Intersector.overlapConvexPolygons(agentEntity.getCollisionPolygon(), collisions.get(j).getCollisionPolygon(), mtv)) {
                        if (mtv.depth > 0) {
                            agentEntity.translate(mtv.depth * mtv.normal.x, mtv.depth * mtv.normal.y);
                            if (mtv.depth * mtv.normal.x != 0) {
                                agentEntity.getVelocity().x = 0;
                            }
                            if (mtv.depth * mtv.normal.y != 0) {
                                agentEntity.getVelocity().y = 0;
                            }
                        }
                    }
                }
            }
            for (int j = 0; j < platforms.size; j++) {
                if ((agentEntity.getPlatform() == null || platforms.get(j).getSurface() != agentEntity.getPlatform().getSurface())) {
                    if (platforms.get(j).canPlatformCollide(agentEntity)) {
                        position = platforms.get(j).getPosition(agentEntity);
                        if (position != null && (currentPosition == null || position.y > currentPosition.y)) {
                            agentEntity.setPosition(position);
                            agentEntity.setPlatform(platforms.get(j));
                            currentPosition = position;
                        }
                    } else if (platforms.get(j).canCollide(agentEntity) && platforms.get(j).isCollides()) {
                        if (Intersector.overlapConvexPolygons(agentEntity.getCollisionPolygon(), platforms.get(j).getCollisionPolygon(), mtv)) {
                            if (mtv.depth > 0) {
                                agentEntity.translate(mtv.depth * mtv.normal.x, mtv.depth * mtv.normal.y);
                                if (mtv.depth * mtv.normal.x != 0) {
                                    agentEntity.getVelocity().x = 0;
                                }
                                if (mtv.depth * mtv.normal.y != 0) {
                                    agentEntity.getVelocity().y = 0;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Array<AgentEntity> getAgents() {
        return agents;
    }

    public Array<CollisionEntity> getCollisions() {
        return collisions;
    }

    public Array<PlatformEntity> getPlatforms() {
        return platforms;
    }

    public void setExtents(Vector2 bl, Vector2 tr) {
        for (int i = 0; i < extents.length; i++) {
            collisions.removeValue(extents[i], true);
        }
        extents[0] = new CollisionEntity();
        extents[0].setCollisionPolygon(new Polygon(new float[]{bl.x - 1, tr.y + 1, bl.x - 1, bl.y - 1, bl.x, bl.y - 1, bl.x, tr.y + 1}));
        extents[1] = new CollisionEntity();
        extents[1].setCollisionPolygon(new Polygon(new float[]{tr.x, tr.y + 1, tr.x, bl.y - 1, tr.x + 1, bl.y - 1, tr.x + 1, tr.y + 1}));
        extents[2] = new CollisionEntity();
        extents[2].setCollisionPolygon(new Polygon(new float[]{bl.x - 1, bl.y, bl.x - 1, bl.y - 1, tr.x + 1, bl.y - 1, tr.x + 1, bl.y}));
        extents[3] = new CollisionEntity();
        extents[3].setCollisionPolygon(new Polygon(new float[]{bl.x - 1, tr.y + 1, bl.x - 1, tr.y, tr.x + 1, tr.y, tr.x + 1, tr.y + 1}));
        for (int i = 0; i < extents.length; i++) {
            collisions.add(extents[i]);
        }
    }

    public AgentEntity getFollowingAgent() {
        return followingAgent;
    }

    public void setFollowingAgent(AgentEntity followingAgent) {
        this.followingAgent = followingAgent;
    }

    public void addPlatformEntity(PlatformEntity platformEntity) {
        if (!platforms.contains(platformEntity, true)) {
            platforms.add(platformEntity);
        }
    }

    public void addCollisionEntity(CollisionEntity collisionEntity) {
        if (!collisions.contains(collisionEntity, true)) {
            collisions.add(collisionEntity);
        }
    }

    public void addAgentEntity(AgentEntity agentEntity) {
        if (!agents.contains(agentEntity, true)) {
            agents.add(agentEntity);
        }
    }
}
