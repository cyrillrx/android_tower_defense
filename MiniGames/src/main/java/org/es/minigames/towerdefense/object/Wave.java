package org.es.minigames.towerdefense.object;

import org.es.minigames.towerdefense.unit.Enemy;

import java.util.Map;

/**
 * Class that represents a wave of attackers.
 *
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Wave {

    /** Number of attackers of each type to be spawn during the wave. */
    private Map<Enemy.Type, Integer> mAttackers;
    private int mDuration;
}
