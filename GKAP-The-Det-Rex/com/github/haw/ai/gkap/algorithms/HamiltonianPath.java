package com.github.haw.ai.gkap.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.github.haw.ai.gkap.graph.*;

public class HamiltonianPath {
    public static <V> List<Vertex<V>> hamiltonianPath(Graph<?, V> graph) {
        List<Vertex<V>> vs = new ArrayList<Vertex<V>>(graph.vertices());
        if (vs.size() <= 1) return vs;
        List<Integer> idxs = new ArrayList<Integer>(vs.size());
        for (int i = 0; i < vs.size(); ++i) idxs.add(i);
        PermutationIterator<Integer> iter = new PermutationIterator<Integer>(idxs);
        
        while (iter.hasNext()) {
            List<Integer> path = iter.next();
            
            boolean checkNextPerm = false;
            
            for (int i = 0; i < path.size()-1 && !checkNextPerm; ++i) {
                int j = path.get(i);
                int k = path.get(i+1);
                if (!graph.isAdjacent(vs.get(j), vs.get(k))) {
                    checkNextPerm = true;
                }
            }
            
            if (!checkNextPerm) {
                // found valid path
                return idxsToVs(path, vs);
            }
        }
        
        return new ArrayList<Vertex<V>>();
    }
    
    private static <V> List<Vertex<V>> idxsToVs(List<Integer> idxs, List<Vertex<V>> vs) {
        List<Vertex<V>> path = new ArrayList<Vertex<V>>(idxs.size());
        for (int idx : idxs) path.add(vs.get(idx));
        return path;
    }
}
