package org.es.towerdefense.battleground;

import android.content.res.Resources;
import android.graphics.Point;

import org.es.engine.graphics.utils.DrawingParam;
import org.es.towerdefense.unit.Tower;
import org.es.towerdefense.unit.TowerFactory;

import java.util.Set;

/**
 * Class for loading a battle ground from a file.
 *
 * @author Cyril Leroux
 *         Created 25/02/14.
 */
public class BattlegroundDAO {

    // TODO This is a temporary function that shall be replaced by a load from file function.

    /**
     * Init a debug Battleground.
     * This function shall be replaced by a classic text loaded
     *
     * @param resources
     * @param param
     * @param towers
     * @return
     */
    public static Battleground loadDebugBattleGround(Resources resources, DrawingParam param, Set<Tower> towers) {

        final int columnCount = 15;
        final int rowCount = 7;
        Battleground battleground = new Battleground(columnCount, rowCount,
                new Point[]{new Point(0, 0)},
                new Point[]{new Point(12, 6)},
                resources, param);

        // init visual map
        int[][] walkingMap = {
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0}
        };

        // fill the battleground with towers
        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < columnCount; x++) {

                if (walkingMap[y][x] == 1) {
                    Tower tower = TowerFactory.createTower(Tower.Type.BASIC, resources);
                    towers.add(tower);
                    battleground.addTower(tower, x, y);
                }
            }
        }

        return battleground;
    }
}
