package com.uprightpath.alchemy.cause;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.uprightpath.alchemy.cause.control.Control;
import com.uprightpath.alchemy.cause.control.KeyboardControlSource;
import com.uprightpath.alchemy.cause.physics.PhysicsWorld;
import com.uprightpath.alchemy.cause.physics.builders.WorldBuilder;
import com.uprightpath.alchemy.cause.physics.player.PlayerAgent;
import com.uprightpath.alchemy.cause.rendering.WorldRenderer;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    private PhysicsWorld physicsWorld;
    private PlayerAgent playerAgent;
    private WorldRenderer worldRenderer;
    private WorldBuilder worldBuilder = new WorldBuilder();
    private float tick = 1f / 60f;
    private float accum;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        physicsWorld = new PhysicsWorld();
        playerAgent = new PlayerAgent();
        worldRenderer = new WorldRenderer();
        Control.setControlSource(new KeyboardControlSource());
        physicsWorld.setFollowingAgent(playerAgent);
        physicsWorld.addAgentEntity(playerAgent);
        playerAgent.setPosition(43, 4.5f);
        physicsWorld.setExtents(new Vector2(0, 0), new Vector2(108, 20));

        // Solid Surfaces
        worldBuilder.buildSurface("Tower Floor", physicsWorld, new Vector3[]{new Vector3(1, 4, 4), new Vector3(7, 4, 4)}, true);
        worldBuilder.buildSurface("Wall Road", physicsWorld, new Vector3[]{new Vector3(7, 4, 4), new Vector3(13, 4, 4)}, true);
        worldBuilder.buildSurface("MC's House; Backyard", physicsWorld, new Vector3[]{new Vector3(13, 4, 4), new Vector3(27, 4, 4)}, true);
        worldBuilder.buildSurface("MC's House; First Floor", physicsWorld, new Vector3[]{new Vector3(27, 4.5f, 4.5f), new Vector3(44, 4.5f, 4.5f)}, true);
        worldBuilder.buildUnevenSurface("MC's House; MC's Room", physicsWorld, new Vector3[]{
                new Vector3(29, 8, .5f), new Vector3(34, 8, .5f),
                new Vector3(34, 8, 1f), new Vector3(35, 8, 1f)}, true);
        worldBuilder.buildUnevenSurface("MC's House; Roof", physicsWorld, new Vector3[]{
                        new Vector3(26, 8, 0f), new Vector3(29, 11, 3f),
                        new Vector3(29, 11, 1f), new Vector3(30, 12, 1f),
                        new Vector3(30, 12, 1f), new Vector3(31, 13, 2f),
                        new Vector3(31, 13, 2f), new Vector3(40, 13, 2f),
                        new Vector3(40, 13, 2f), new Vector3(41, 12, 1f),
                        new Vector3(41, 12, 1f), new Vector3(42, 11, 1f),
                        new Vector3(42, 11, 3f), new Vector3(45, 8, 0f)},
                true);
        worldBuilder.buildSurface("MC's House; Front Yard", physicsWorld, new Vector3[]{new Vector3(44, 4f, 4f), new Vector3(55, 4f, 4f)}, true);
        worldBuilder.buildSurface("Left Ford; Step 2", physicsWorld, new Vector3[]{new Vector3(55, 3f, 3f), new Vector3(56, 3f, 3f)}, true);
        worldBuilder.buildSurface("Left Ford; Step 1", physicsWorld, new Vector3[]{new Vector3(56, 2f, 2f), new Vector3(57, 2f, 2f)}, true);
        worldBuilder.buildSurface("Ford Road & Bridge", physicsWorld, new Vector3[]{new Vector3(57, 1f, 1f), new Vector3(62, 1f, 1f), new Vector3(64, 2f, 2f), new Vector3(68, 3f, 3f), new Vector3(72, 2f, 2f), new Vector3(74, 1f, 1f), new Vector3(79, 1f, 1f)}, true);
        worldBuilder.buildSurface("Right Ford; Step 1", physicsWorld, new Vector3[]{new Vector3(79, 2f, 2f), new Vector3(80, 2f, 2f)}, true);
        worldBuilder.buildSurface("Right Ford; Step 2", physicsWorld, new Vector3[]{new Vector3(80, 3f, 3f), new Vector3(81, 3f, 3f)}, true);
        worldBuilder.buildSurface("School Yard", physicsWorld, new Vector3[]{new Vector3(81, 4f, 4f), new Vector3(106, 4f, 4f)}, true);
        worldBuilder.buildSurface("Shoolhouse Steps", physicsWorld, new Vector3[]{new Vector3(106, 4.5f, 4.5f), new Vector3(107, 4.5f, 4.5f)}, true);

        // Platforms
        worldBuilder.buildStairs("Wall Tower; First Floor Stair", physicsWorld, new Vector3[]{new Vector3(2, 7, .5f), new Vector3(5, 4, .5f)});
        worldBuilder.buildSurface("Wall Tower; Second Floor", physicsWorld, new Vector3[]{new Vector3(1, 7, .5f), new Vector3(1.5f, 7, .5f), new Vector3(5.5f, 7, .5f), new Vector3(6, 7, .5f)}, false);
        worldBuilder.buildStairs("Wall Tower; Second Floor Stair", physicsWorld, new Vector3[]{new Vector3(2, 7, .5f), new Vector3(5, 10, .5f)});
        worldBuilder.buildSurface("Wall Tower; Third Floor", physicsWorld, new Vector3[]{new Vector3(1, 10, .5f), new Vector3(1.5f, 10, .5f), new Vector3(5.5f, 10, .5f), new Vector3(6, 10, .5f)}, false);
        worldBuilder.buildStairs("Wall Tower; Third Floor Stair", physicsWorld, new Vector3[]{new Vector3(2, 13, .5f), new Vector3(5, 10, .5f)});
        worldBuilder.buildSurface("Wall Tower; Fourth Floor", physicsWorld, new Vector3[]{new Vector3(1, 13, .5f), new Vector3(1.5f, 13, .5f), new Vector3(5.5f, 13, .5f), new Vector3(6, 13, .5f)}, false);
        worldBuilder.buildPlatform("MC's House; Second Floor", physicsWorld, new Vector3[]{new Vector3(35, 8, .5f), new Vector3(42, 8, .5f)});

        // Walls
        worldBuilder.buildWall("Wall Tower; Left Wall", physicsWorld, new Vector2(0, 0), new Vector2(1, 20));
        worldBuilder.buildWall("Wall Tower; Right Wall", physicsWorld, new Vector2(6, 6.5f), new Vector2(7, 20));
        worldBuilder.buildWall("MC's House; Left Wall", physicsWorld, new Vector2(28, 7), new Vector2(29, 9));
        worldBuilder.buildWall("MC's House; Right Wall", physicsWorld, new Vector2(42, 7), new Vector2(43, 9));
        worldBuilder.buildWall("Schoolhouse; Wall", physicsWorld, new Vector2(107, 4.5f), new Vector2(108, 8f));

        // Ladders
        worldBuilder.buildLadder("Wall Tower; Ladder", physicsWorld, new Vector2[]{new Vector2(3.5f, 15), new Vector2(3.5f, 20)});
        worldBuilder.buildLadder("MC's House; Ladder", physicsWorld, new Vector2[]{new Vector2(37.5f, 4.5f), new Vector2(37.5f, 8)});

        for (int i = 0; i < 10; i++) {
            worldBuilder.buildStupidSlime("Slime " + i, physicsWorld, new Vector2(MathUtils.random(7f, 107f), 19));
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        accum += Gdx.graphics.getDeltaTime();
        Control.update();
        if (accum >= tick) {
            while (accum >= tick) {
                accum -= tick;
                physicsWorld.update();
                Control.reset(false);
            }
            Control.reset(true);
        }
        worldRenderer.render(Gdx.graphics.getDeltaTime(), physicsWorld);

        batch.begin();
        font.setColor(Color.YELLOW);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond() + "\t d: " + playerAgent.getVelocity(), 0, 480);
        font.draw(batch, "Standing On: " + (playerAgent.getRail() == null ? "NOTHING" : playerAgent.getRail().getRailSeries().getName() + " - " + playerAgent.getRail().getName()), 0, 440);
        batch.end();
    }
}
