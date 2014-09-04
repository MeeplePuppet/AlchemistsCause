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
    private Array<LadderEntity> ladders = new Array<LadderEntity>();
    private Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
    private Vector2 acceleration = new Vector2(0f, -(1f / 150f));
    private Vector2 groundSpeedMax = new Vector2(.15f, 0f);
    private Vector2 groundSpeedMin = new Vector2(.02f, 0f);
    private Vector2 airSpeedMax = new Vector2(.10f, .98f);
    private Vector2 airSpeedMin = new Vector2(.02f, 0.f);
    private Vector2 airFriction = new Vector2(.995f, .98f);

    public PhysicsWorld() {
    }

    public void update() {
        AgentEntity agentEntity;
        CollisionEntity collisionEntity;
        PlatformEntity platformEntity;
        RailSeries surface;
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
            agentEntity.updatePreviousBoundings();
            agentEntity.applyStateLogic(this);
            agentEntity.applyDeltaVelocity(acceleration.x, 0.f);
            agentEntity.translate(agentEntity.getVelocity());
            if (agentEntity.getRail() == null || agentEntity.isFallThrough()) {
                agentEntity.applyDeltaVelocity(0, acceleration.y);
                agentEntity.translate(0, acceleration.y);
            } else {
                agentEntity.getVelocity().y = Math.min(0, agentEntity.getVelocity().y);
            }
            if (agentEntity.getRail() != null) {
                surface = agentEntity.getRail().getRailSeries();
                for (int j = 0; j < surface.getRailEntities().size; j++) {
                    position = surface.getRailEntities().get(j).getPosition(agentEntity);
                    if (position != null && (currentPosition == null || (position.y > currentPosition.y))) {
                        agentEntity.setPosition(position);
                        agentEntity.setRail(surface.getRailEntities().get(j));
                        currentPosition = position;
                    }
                }
                if (currentPosition == null) {
                    agentEntity.setRail(null);
                }
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
                                agentEntity.getVelocity().y = Math.min(0, agentEntity.getVelocity().y);
                            }
                        }
                    }
                }
            }
            for (int j = 0; j < agents.size; j++) {
                if (agentEntity != agents.get(j)) {
                    if (agents.get(j).canCollide(agentEntity.getCollisionPolygon()) && agentEntity.isSolid()) {
                        if (Intersector.overlapConvexPolygons(agentEntity.getCollisionPolygon(), agents.get(j).getCollisionPolygon(), mtv)) {
                            if (mtv.depth > 0) {
                                agentEntity.translate(mtv.depth * mtv.normal.x, mtv.depth * mtv.normal.y);
                                if (mtv.depth * mtv.normal.x != 0) {
                                    agentEntity.getVelocity().x = 0;
                                }
                                if (mtv.depth * mtv.normal.y != 0) {
                                    agentEntity.getVelocity().y = Math.min(0, agentEntity.getVelocity().y);
                                }
                                if (agents.get(j).isSolid()) {
                                    agentEntity.collidedWithAgent(agents.get(j));
                                }
                            }
                        }
                    }
                }
            }
            for (int j = 0; j < platforms.size; j++) {
                if ((agentEntity.getRail() == null || platforms.get(j).getRailSeries() != agentEntity.getRail().getRailSeries())) {
                    if (platforms.get(j).canPlatformCollide(agentEntity) && (!agentEntity.isFallThrough() || platforms.get(j).isCollides())) {
                        position = platforms.get(j).getPosition(agentEntity);
                        if (position != null && (currentPosition == null || position.y >= currentPosition.y) && platforms.get(j).hasPriority(agentEntity)) {
                            agentEntity.setPosition(position);
                            agentEntity.setRail(platforms.get(j));
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
                                    agentEntity.getVelocity().y = Math.min(0, agentEntity.getVelocity().y);
                                }
                            }
                        }
                    }
                }
            }
            for (int j = 0; j < ladders.size; j++) {
                if ((agentEntity.getRail() == null || ladders.get(j).getRailSeries() != agentEntity.getRail().getRailSeries())) {
                    if (ladders.get(j).canCollide(agentEntity)) {
                        position = ladders.get(j).getPosition(agentEntity);
                        if (position != null && (currentPosition == null || position.y >= currentPosition.y || ladders.get(j).hasPriority(agentEntity))) {
                            agentEntity.setPosition(position);
                            agentEntity.setRail(ladders.get(j));
                            currentPosition = position;
                        }
                    }
                }
            }
            agentEntity.updateState();
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

    public void addLadderEntity(LadderEntity ladderEntity) {
        if (!ladders.contains(ladderEntity, true)) {
            ladders.add(ladderEntity);
        }
    }

    public Vector2 getGroundSpeedMax() {
        return groundSpeedMax;
    }

    public Vector2 getGroundSpeedMin() {
        return groundSpeedMin;
    }

    public Vector2 getAirSpeedMax() {
        return airSpeedMax;
    }

    public Vector2 getAirSpeedMin() {
        return airSpeedMin;
    }

    public Vector2 getAirFriction() {
        return airFriction;
    }

    public Array<LadderEntity> getLadders() {
        return ladders;
    }
}
