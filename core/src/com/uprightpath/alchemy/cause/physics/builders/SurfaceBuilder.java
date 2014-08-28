package com.uprightpath.alchemy.cause.physics.builders;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.uprightpath.alchemy.cause.physics.PhysicsWorld;
import com.uprightpath.alchemy.cause.physics.PlatformEntity;
import com.uprightpath.alchemy.cause.physics.Surface;

/**
 * Created by Geo on 8/27/2014.
 */
public class SurfaceBuilder {
    public SurfaceBuilder() {

    }

    public void buildSurface(PhysicsWorld physicsWorld, Vector3[] surfaceVectors, boolean isCollides) {
        Surface surface = new Surface();
        PlatformEntity platformEntity;
        Vector3 first, second;
        for (int i = 0; i < surfaceVectors.length - 1; i++) {
            first = surfaceVectors[i];
            second = surfaceVectors[i + 1];
            platformEntity = new PlatformEntity();
            platformEntity.setEndPoints(new Vector2(first.x, first.y), new Vector2(second.x, second.y));
            platformEntity.setCollisionPolygon(new Polygon(new float[]{first.x, first.y, first.x, first.y - first.z, second.x, second.y - second.z, second.x, second.y}));
            platformEntity.setCollides(isCollides);
            surface.addPlatform(platformEntity);
            physicsWorld.addPlatformEntity(platformEntity);
        }
        System.out.println("Surface Size: " + surface.getPhysicsPlatforms().size);
    }
}
