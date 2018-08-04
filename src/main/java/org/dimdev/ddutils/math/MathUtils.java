package org.dimdev.ddutils.math;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import org.dimdev.dimdoors.DimDoors;

public final class MathUtils {

    public static <T> T weightedRandom(Map<T, Float> weights) {
        if (weights.size() == 0) return null;
        int totalWeight = 0;
        for (float weight : weights.values()) {
            totalWeight += weight;
        }
        Random random = new Random();
        float f = random.nextFloat() * totalWeight;
        for (Map.Entry<T, Float> e : weights.entrySet()) {
            f -= e.getValue();
            if (f < 0) return e.getKey();
        }
        return null;
    }

    /**
     * Recursively generates a single seed from any number of input seeds in
     * which the order of input seeds does matter.
     * This is used in the placement of worldgen structures.
     *
     * @param seed1
     * @param seeds
     * @return
     */
    public static int mergeSeeds(int seed1, int... seeds) {
        if (seeds == null || seeds.length < 1) {
            return seed1;
        }
        return mergeSeeds(hash(seed1 ^ seeds[0]), Arrays.copyOfRange(seeds, 1, seeds.length));
    }

    /**
     * Simple and quick hash algorithm found at
     * https://stackoverflow.com/questions/9624963/java-simplest-integer-hash .
     * If this hash function does not suffice, there are quite a few other hash
     * function variants given at this source.
     *
     * @param original
     * @return
     */
    private static int hash(int original) {
        double s = Math.sin(original);
        return (int) Math.floor((1 << 31) * s);
    }
}
