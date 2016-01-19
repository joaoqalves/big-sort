package net.joaoqalves.sort.strategies;

import java.util.Map;

public interface MergeStrategy<T, I> {
    T select(Map<T, I> candidates);
}
