package com.github.haw.ai.gkap.algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import com.github.haw.ai.gkap.graph.*;

public class HamiltonianCycle {
    private static class Pair<T,U> {
        public final T _1;
        public final U _2;
        public Pair(T l, U r) { _1 = l; _2 = r; }
        @Override public String toString() { return "(" + _1 + "," + _2 + ")"; }
        @Override public int hashCode() { return 41 * (41 + _1.hashCode()) + _2.hashCode(); }
        @Override public boolean equals(Object o) {
            if (o == null || !(o instanceof Pair<?,?>)) return false;
            Pair<?,?> p = (Pair<?,?>)o;
            return ((_1 == null ? p._1 == null : _1.equals(p._1)) &&
                    (_2 == null ? p._2 == null : _2.equals(p._2)));
        }
    }
    
    /**
     * Find a hamiltonian cycle in a given graph.
     * If a path is found hamiltonianCycle(g).size() == g.vertices().size()+1
     * because the first and last element of the cycle are connected
     * (start==finish) (but if g.vertices().size()==1 then
     * hamiltonianCycle(g).size()==1 too).
     * 
     * @param graph a graph
     * @return the found hamiltonian cycle or an empty path if not found.
     */
    public static <V> List<Vertex<V>> hamiltonianCycle(Graph<?, V> graph) {
        List<Vertex<V>> vs = new ArrayList<Vertex<V>>(graph.vertices());
        
        if (vs.size() <= 1) {
            return vs;
        }
        
        // Pair< NeighborList, CurrentNeighborIndex >
        Stack<Pair<List<Vertex<V>>, Integer>> cycle =
            new Stack<Pair<List<Vertex<V>>,Integer>>();
        
        // begin at random vertex
        cycle.push(pair(list(vs), 0));
        
        Vertex<V> root = vs.get(0);
        
        while (!(cycle.size() == vs.size() &&
               graph.isAdjacent(root, cycle.peek()._1.get(cycle.peek()._2))))
        {
            List<Vertex<V>> neighbors = cycle.peek()._1;
            int idx = cycle.peek()._2;
            Vertex<V> curNeighbor = neighbors.get(idx);
            
            List<Vertex<V>> nextNeighbors = list(graph.adjacent(curNeighbor));
            boolean foundNext = false;
            for (int i = 0; i < nextNeighbors.size() && !foundNext; ++i) {
                Vertex<V> nextNeigbor = nextNeighbors.get(i);
                if (!containsVertex(cycle, nextNeigbor)) {
                    cycle.push(pair(nextNeighbors, i));
                    foundNext = true;
                }
            }
            
            while (!foundNext) {
                // unwind and continue at previous level
                if (idx+1 < neighbors.size() && !containsVertex(cycle, neighbors.get(idx+1))) {
                    cycle.pop();
                    cycle.push(pair(neighbors, idx+1));
                    foundNext = true;
                } else if (cycle.isEmpty()) {
                    return new ArrayList<Vertex<V>>();
                } else {
                    cycle.pop();
                }
            }
        }
        
        List<Vertex<V>> list = new ArrayList<Vertex<V>>(cycle.size()+1);
        list.add(root); // root occurs in first and last position of the path
        while (!cycle.empty()) {
            Pair<List<Vertex<V>>, Integer> entry = cycle.pop();
            list.add(entry._1.get(entry._2));
        }
        Collections.reverse(list);
        
        return list;
    }
    
    private static <T, U> Pair<T, U> pair(T l, U r) {
        return new Pair<T, U>(l, r);
    }
    
    private static <E> List<E> list(Collection<E> col) {
        return new ArrayList<E>(col);
    }
    
    private static <V> boolean containsVertex(Stack<Pair<List<Vertex<V>>, Integer>> coll, Vertex<V> v) {
        if (v == null) return false;
        for (Pair<List<Vertex<V>>, Integer> p : coll) {
            if (v.equals(p._1.get(p._2))) return true;
        }
        return false;
    }
}
