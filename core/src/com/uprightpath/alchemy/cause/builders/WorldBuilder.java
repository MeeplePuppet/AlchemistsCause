package com.uprightpath.alchemy.cause.builders;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.uprightpath.alchemy.cause.control.Control;
import com.uprightpath.alchemy.cause.physics.*;
import com.uprightpath.alchemy.cause.physics.agents.StupidSlime;

/**
 * Created by Geo on 8/27/2014.
 */
public class WorldBuilder {
    public WorldBuilder() {

    }

    public void buildWall(String name, PhysicsWorld physicsWorld, Vector2 origin, Vector2 opposite) {
        CollisionEntity collisionEntity = new CollisionEntity();
        collisionEntity.setName(name);
        collisionEntity.setCollisionPolygon(new Polygon(new float[]{origin.x, opposite.y, origin.x, origin.y, opposite.x, origin.y, opposite.x, opposite.y}));
        physicsWorld.addCollisionEntity(collisionEntity);
    }

    public void buildSurface(String name, PhysicsWorld physicsWorld, Vector3[] surfaceVectors, boolean isCollides) {
        RailSeries surface = new RailSeries(name);
        PlatformEntity platformEntity;
        Vector3 first, second;
        for (int i = 0; i < surfaceVectors.length - 1; i++) {
            first = surfaceVectors[i];
            second = surfaceVectors[i + 1];
            platformEntity = new PlatformEntity();
            platformEntity.setName("Platform " + i);
            platformEntity.setEndPoints(new Vector2(first.x, first.y), new Vector2(second.x, second.y));
            if (first.z == 0) {
                platformEntity.setCollisionPolygon(new Polygon(new float[]{first.x, first.y, second.x, second.y - second.z, second.x, second.y}));
            } else if (second.z == 0) {
                platformEntity.setCollisionPolygon(new Polygon(new float[]{first.x, first.y, first.x, first.y - first.z, second.x, second.y}));
            } else {
                platformEntity.setCollisionPolygon(new Polygon(new float[]{first.x, first.y, first.x, first.y - first.z, second.x, second.y - second.z, second.x, second.y}));
            }
            platformEntity.setSlip(new Vector2(first.y - second.y, first.x - second.x).nor());
            platformEntity.getSlip().scl(.2f);
            platformEntity.setCollides(i == 0 || i == surfaceVectors.length - 1 || isCollides);
            surface.addRailEntity(platformEntity);
            physicsWorld.addPlatformEntity(platformEntity);
        }
        System.out.println("RailSeries Size: " + surface.getRailEntities().size);
    }

    public void buildUnevenSurface(String name, PhysicsWorld physicsWorld, Vector3[] surfaceVectors, boolean isCollides) {
        RailSeries surface = new RailSeries(name);
        PlatformEntity platformEntity;
        Vector3 first, second;
        for (int i = 0; i < surfaceVectors.length - 1; i += 2) {
            first = surfaceVectors[i];
            second = surfaceVectors[i + 1];
            platformEntity = new PlatformEntity();
            platformEntity.setName("Platform " + i);
            platformEntity.setEndPoints(new Vector2(first.x, first.y), new Vector2(second.x, second.y));
            if (first.z == 0) {
                platformEntity.setCollisionPolygon(new Polygon(new float[]{first.x, first.y, second.x, second.y - second.z, second.x, second.y}));
            } else if (second.z == 0) {
                platformEntity.setCollisionPolygon(new Polygon(new float[]{first.x, first.y, first.x, first.y - first.z, second.x, second.y}));
            } else {
                platformEntity.setCollisionPolygon(new Polygon(new float[]{first.x, first.y, first.x, first.y - first.z, second.x, second.y - second.z, second.x, second.y}));
            }
            platformEntity.setSlip(new Vector2(first.y - second.y, first.x - second.x).nor());
            platformEntity.getSlip().scl(.2f);
            platformEntity.setCollides(i == 0 || i == surfaceVectors.length - 1 || isCollides);
            surface.addRailEntity(platformEntity);
            physicsWorld.addPlatformEntity(platformEntity);
        }
        System.out.println("RailSeries Size: " + surface.getRailEntities().size);
    }

    public void buildPlatform(String name, PhysicsWorld physicsWorld, Vector3[] surfaceVectors) {
        RailSeries surface = new RailSeries(name);
        PlatformEntity platformEntity;
        Vector3 first, second;
        for (int i = 0; i < surfaceVectors.length - 1; i++) {
            first = surfaceVectors[i];
            second = surfaceVectors[i + 1];
            platformEntity = new PlatformEntity();
            platformEntity.setName("Platform " + i);
            platformEntity.setEndPoints(new Vector2(first.x, first.y), new Vector2(second.x, second.y));
            platformEntity.setCollisionPolygon(new Polygon(new float[]{first.x, first.y, first.x, first.y - first.z, second.x, second.y - second.z, second.x, second.y}));
            platformEntity.setSlip(new Vector2(first.y - second.y, first.x - second.x).nor());
            platformEntity.getSlip().scl(.2f);
            platformEntity.setCollides(false);
            surface.addRailEntity(platformEntity);
            physicsWorld.addPlatformEntity(platformEntity);
        }
        System.out.println("RailSeries Size: " + surface.getRailEntities().size);
    }

    public void buildStairs(String name, PhysicsWorld physicsWorld, Vector3[] surfaceVectors) {
        RailSeries surface = new RailSeries(name);
        PlatformEntity platformEntity;
        EdgePlatformEntity edgePlatformEntity;
        Vector3 first, second;

        edgePlatformEntity = new EdgePlatformEntity();
        edgePlatformEntity.setName("Left Edge");
        edgePlatformEntity.setEndPoints(new Vector2(
                surfaceVectors[0].x - 1.f, surfaceVectors[0].y), new Vector2(surfaceVectors[0].x, surfaceVectors[0].y));
        edgePlatformEntity.setCollisionPolygon(new Polygon(new float[]{surfaceVectors[0].x - 1.f, surfaceVectors[0].y, surfaceVectors[0].x - 1.f, surfaceVectors[0].y - surfaceVectors[0].z, surfaceVectors[0].x, surfaceVectors[0].y - surfaceVectors[0].z, surfaceVectors[0].x, surfaceVectors[0].y}));
        edgePlatformEntity.setCollides(true);
        edgePlatformEntity.setLeftSide(true);
        edgePlatformEntity.setRequiredModifier(surfaceVectors[0].y < surfaceVectors[surfaceVectors.length - 1].y ? Control.UP : Control.DOWN);
        edgePlatformEntity.setSlip(new Vector2(0, 0));
        surface.addRailEntity(edgePlatformEntity);
        physicsWorld.addPlatformEntity(edgePlatformEntity);
        for (int i = 0; i < surfaceVectors.length - 1; i++) {
            first = surfaceVectors[i];
            second = surfaceVectors[i + 1];
            platformEntity = new StairPlatformEntity();
            platformEntity.setName("Platform " + i);
            platformEntity.setEndPoints(new Vector2(first.x, first.y), new Vector2(second.x, second.y));
            platformEntity.setCollisionPolygon(new Polygon(new float[]{first.x, first.y, first.x, first.y - first.z, second.x, second.y - second.z, second.x, second.y}));
            platformEntity.setCollides(false);
            surface.addRailEntity(platformEntity);
            physicsWorld.addPlatformEntity(platformEntity);
        }
        edgePlatformEntity = new EdgePlatformEntity();
        edgePlatformEntity.setName("Right Edge");
        edgePlatformEntity.setEndPoints(new Vector2(
                surfaceVectors[surfaceVectors.length - 1].x, surfaceVectors[surfaceVectors.length - 1].y), new Vector2(surfaceVectors[surfaceVectors.length - 1].x + 1.f, surfaceVectors[surfaceVectors.length - 1].y));
        edgePlatformEntity.setCollisionPolygon(new Polygon(new float[]{surfaceVectors[surfaceVectors.length - 1].x, surfaceVectors[surfaceVectors.length - 1].y, surfaceVectors[surfaceVectors.length - 1].x, surfaceVectors[surfaceVectors.length - 1].y - surfaceVectors[surfaceVectors.length - 1].z, surfaceVectors[surfaceVectors.length - 1].x + 1.f, surfaceVectors[surfaceVectors.length - 1].y - surfaceVectors[surfaceVectors.length - 1].z, surfaceVectors[surfaceVectors.length - 1].x + 1.f, surfaceVectors[surfaceVectors.length - 1].y}));
        edgePlatformEntity.setCollides(true);
        edgePlatformEntity.setLeftSide(false);
        edgePlatformEntity.setRequiredModifier(surfaceVectors[0].y >= surfaceVectors[surfaceVectors.length - 1].y ? Control.UP : Control.DOWN);
        edgePlatformEntity.setSlip(new Vector2(0, 0));
        surface.addRailEntity(edgePlatformEntity);
        physicsWorld.addPlatformEntity(edgePlatformEntity);
        System.out.println("RailSeries Size: " + surface.getRailEntities().size);
    }

    public void buildLadder(String name, PhysicsWorld physicsWorld, Vector2[] ladderVectors) {
        RailSeries surface = new RailSeries(name);
        LadderEntity ladderEntity;
        Vector2 first, second;
        for (int i = 0; i < ladderVectors.length - 1; i++) {
            first = ladderVectors[i];
            second = ladderVectors[i + 1];
            ladderEntity = new LadderEntity();
            ladderEntity.setName("Platform " + i);
            ladderEntity.setEndPoints(new Vector2(first.x, first.y), new Vector2(second.x, second.y));
            ladderEntity.setCollisionPolygon(new Polygon(new float[]{second.x - .05f, second.y, first.x - .05f, first.y, first.x + .05f, first.y, second.x + .05f, second.y}));
            surface.addRailEntity(ladderEntity);
            physicsWorld.addLadderEntity(ladderEntity);
        }
        System.out.println("RailSeries Size: " + surface.getRailEntities().size);
    }

    public void buildStupidSlime(String name, PhysicsWorld physicsWorld, Vector2 position) {
        AgentEntity agentEntity = new StupidSlime();
        agentEntity.setName(name);
        agentEntity.setPosition(position);
        physicsWorld.addAgentEntity(agentEntity);
    }
}
