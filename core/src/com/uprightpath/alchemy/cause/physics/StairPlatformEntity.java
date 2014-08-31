package com.uprightpath.alchemy.cause.physics;

/**
 * Created by Geo on 8/30/2014.
 */
public class StairPlatformEntity extends PlatformEntity {
    public int getPriorityImplement(AgentEntity agentEntity) {
        return agentEntity.getRail().getRailSeries() == this.railSeries ? 1 : -1;
    }

}
