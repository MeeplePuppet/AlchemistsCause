package com.uprightpath.alchemy.cause.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.uprightpath.alchemy.cause.physics.PhysicsWorld;

/**
 * Created by Geo on 8/27/2014.
 */
public class WorldRenderer {
    private ShapeRenderer shapeRenderer;
    private Vector2 position = new Vector2();
    private OrthographicCamera camera;


    public WorldRenderer() {
        this.shapeRenderer = new ShapeRenderer();
        System.out.println("[" + Gdx.graphics.getWidth() + ", " + Gdx.graphics.getHeight() + "]");
        this.camera = new OrthographicCamera(((float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight()) * 10, 10);
        this.camera.translate(0, 0, 10);
        this.camera.lookAt(0, 0, 0);
    }

    public void render(float delta, PhysicsWorld physicsWorld) {
        physicsWorld.getFollowingAgent().getCollisionPolygon().getBoundingRectangle().getCenter(position);
        Vector3 previous = new Vector3(camera.position);
        camera.position.set(position.x, position.y, 10);
        previous.sub(camera.position);
        camera.update();
        Gdx.gl.glLineWidth(1);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1);
        for (int i = 0; i < physicsWorld.getCollisions().size; i++) {
            shapeRenderer.polygon(physicsWorld.getCollisions().get(i).getCollisionPolygon().getTransformedVertices());
        }
        for (int i = 0; i < physicsWorld.getPlatforms().size; i++) {
            shapeRenderer.setColor(0, 1, 0, 1);
            shapeRenderer.polygon(physicsWorld.getPlatforms().get(i).getCollisionPolygon().getTransformedVertices());
            shapeRenderer.setColor(0, .5f, 0, 1);
            shapeRenderer.polygon(physicsWorld.getPlatforms().get(i).getPlatformCollisionPolygon().getTransformedVertices());
        }
        for (int i = 0; i < physicsWorld.getAgents().size; i++) {
            if (physicsWorld.getAgents().get(i).getPlatform() == null) {
                shapeRenderer.setColor(0, 0, 1, 1);
            } else {
                shapeRenderer.setColor(0, 1, 1, 1);
            }
            shapeRenderer.polygon(physicsWorld.getAgents().get(i).getCollisionPolygon().getTransformedVertices());
            shapeRenderer.setColor(0, .5f, .5f, 1);
            shapeRenderer.polygon(physicsWorld.getAgents().get(i).getPlatformCollisionPolygon().getTransformedVertices());
        }
        shapeRenderer.end();
    }

    public void updateCamera(Vector3 update) {
        camera.position.add(update);
    }

    public void dispose() {
        this.shapeRenderer.dispose();
    }
}
