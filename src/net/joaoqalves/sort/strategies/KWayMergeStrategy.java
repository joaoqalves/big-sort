package net.joaoqalves.sort.strategies;

import java.io.BufferedReader;
import java.util.Map;

public class KWayMergeStrategy implements MergeStrategy<BufferedReader, String> {

    public BufferedReader select(Map<BufferedReader, String> candidates) {
        String toCompare = null;
        BufferedReader selected = null;

        for(Map.Entry<BufferedReader, String> entry: candidates.entrySet()) {
            if(toCompare == null || entry.getValue().compareTo(toCompare) <= 0) {
                toCompare = entry.getValue();
                selected = entry.getKey();
            }
        }
        return selected;
    }
}
