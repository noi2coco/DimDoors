/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dimdev.dimdoors.shared.world.mazeworld;

import java.util.Random;
import org.dimdev.ddutils.math.MathUtils;

/**
 *
 * @author Robijnvogel
 */
public class MazeCell {

    public EnumCellType cellType;
    boolean[] connections = new boolean[4];

    MazeCell(EnumCellType cellType) {
        this.cellType = cellType;
    }

    public enum EnumCellType {
        ROOM, HALL, STAIRS, STAIRS_UPPER
    }

    void setConnections(long worldSeed, int x, int z, int level) {
        int tempX = x * 2 - 1;
        int tempZ = z * 2;
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 1:
                    tempX += 1;
                    tempZ -= 1;
                    break;
                case 2:
                    tempX += 1;
                    tempZ += 1;
                    break;
                case 3:
                    tempX -= 1;
                    tempZ += 1;
                    break;
                default:
                    break;
            }

            int seed = MathUtils.mergeSeeds((int) worldSeed, tempX, tempZ, level);
            random.setSeed(seed);
            connections[i] = random.nextDouble() > 0.45;
        }
    }
}
