package org.es.minigames.towerdefense.drawable;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public class Monster extends AbstractUnit {

    public static enum Type {
        SMALL,
        MEDIUM,
        BIG
    }

    private Type mType;
    private Type mWeight;
}
