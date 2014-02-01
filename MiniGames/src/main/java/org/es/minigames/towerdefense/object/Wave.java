package org.es.minigames.towerdefense.object;

import org.es.minigames.towerdefense.drawable.unit.Monster;

import java.util.Map;

/**
 * Class that represents a wave of monsters.
 *
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Wave {

    private Map<Monster.Type, Integer> mMonsterCount;
    private int mDuration;
}
