package com.uprightpath.alchemy.cause.physics;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Geo on 8/25/2014.
 */
public class Surface {
    protected Array<PlatformEntity> physicsPlatforms = new Array<PlatformEntity>();

    public Surface() {

    }

    public void addPlatform(PlatformEntity physicsPlatform) {
        physicsPlatforms.removeValue(physicsPlatform, true);
        physicsPlatforms.add(physicsPlatform);
        physicsPlatform.setSurface(this);
    }

    public Array<PlatformEntity> getPhysicsPlatforms() {
        return physicsPlatforms;
    }

    public void removePlatform(PlatformEntity physicsPlatform) {
        physicsPlatforms.removeValue(physicsPlatform, true);
    }
}
