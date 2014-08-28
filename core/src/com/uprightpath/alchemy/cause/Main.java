package com.uprightpath.alchemy.cause;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.uprightpath.alchemy.cause.control.Control;
import com.uprightpath.alchemy.cause.control.KeyboardControlSource;
import com.uprightpath.alchemy.cause.physics.PhysicsWorld;
import com.uprightpath.alchemy.cause.physics.builders.SurfaceBuilder;
import com.uprightpath.alchemy.cause.physics.player.PlayerAgent;
import com.uprightpath.alchemy.cause.rendering.WorldRenderer;

public class Main extends ApplicationAdapter {
    private PhysicsWorld physicsWorld;
    private PlayerAgent playerAgent;
    private WorldRenderer worldRenderer;
    private SurfaceBuilder surfaceBuilder = new SurfaceBuilder();
    private float tick = 1f / 60f;
    private float accum;

    @Override
    public void create() {
        physicsWorld = new PhysicsWorld();
        playerAgent = new PlayerAgent();
        worldRenderer = new WorldRenderer();
        Control.setControlSource(new KeyboardControlSource());
        physicsWorld.setFollowingAgent(playerAgent);
        physicsWorld.addAgentEntity(playerAgent);
        playerAgent.setPosition(0, 1);
        physicsWorld.setExtents(new Vector2(0, 0), new Vector2(10, 10));
        surfaceBuilder.buildSurface(physicsWorld, new Vector3[]{new Vector3(0, 1, 1), new Vector3(3, 1, 1), new Vector3(5, 2, 2), new Vector3(10, 2, 2)}, true);
        //surfaceBuilder.buildSurface(physicsWorld, new Vector3[]{new Vector3(7, 1, 1), new Vector3(10, 1, 1)}, true);
        surfaceBuilder.buildSurface(physicsWorld, new Vector3[]{new Vector3(3, 4, .5f), new Vector3(5, 2, .5f)}, false);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        accum += Gdx.graphics.getDeltaTime();
        while (accum >= tick) {
            accum -= tick;
            physicsWorld.update();
        }
        worldRenderer.render(Gdx.graphics.getDeltaTime(), physicsWorld);
    }
}
