package com.github.haw.ai.gkap.algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import com.github.haw.ai.gkap.graph.*;

public class HamiltonianCycle {

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
    public static <E,V> List<Vertex<V>> hamiltonianCycle(Graph<E, V> graph) {        
        return loggedHamiltonianCycle(graph)._1;
    }
    
    public static <E,V> Pair<List<Vertex<V>>, Stats<E, V>> loggedHamiltonianCycle(Graph<E, V> graph) {
        // Algorithm from http://en.wikipedia.org/wiki/Hamiltonian_path_problem
        //
        // A trivial heuristic algorithm for locating Hamiltonian paths is to
        // construct a path abc... and extend it until no longer possible; when
        // the path abc...xyz cannot be extended any longer because all
        // neighbours of z already lie in the path, one goes back one step,
        // removing the edge yz and extending the path with a different
        // neighbour of y; if no choice produces a Hamiltonian path, then one
        // takes a further step back, removing the edge xy and extending the
        // path with a different neighbour of x, and so on.
        
        long start = System.currentTimeMillis();
        AccessStats<E, V> stats = new AccessStats<E, V>();
        
        List<Vertex<V>> vs = new ArrayList<Vertex<V>>(graph.vertices());
        
        if (vs.size() <= 1) {
            return pair(vs, new Stats<E, V>(stats, System.currentTimeMillis()-start));
        }
        
        // Pair< NeighborList, CurrentNeighborIndex >
        Stack<Pair<List<Vertex<V>>, Integer>> cycle =
            new Stack<Pair<List<Vertex<V>>,Integer>>();
        
        // begin at random vertex
        cycle.push(pair(list(vs), 0));
        
        Vertex<V> root = vs.get(0);
        stats.increment(root);
        
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
                if (!containsVertex(cycle, nextNeigbor, stats)) {
                    cycle.push(pair(nextNeighbors, i));
                    foundNext = true;
                }
            }
            
            while (!foundNext) {
                // unwind and continue at previous level
                if (idx+1 < neighbors.size() && !containsVertex(cycle, neighbors.get(idx+1), stats)) {
                    cycle.pop();
                    cycle.push(pair(neighbors, idx+1));
                    foundNext = true;
                } else if (cycle.isEmpty()) {
                    return pair((List<Vertex<V>>)new ArrayList<Vertex<V>>(),
                                 new Stats<E, V>(stats, System.currentTimeMillis()-start));
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
        
        return pair(list, new Stats<E, V>(stats, System.currentTimeMillis()-start));
    }
    
    private static <T, U> Pair<T, U> pair(T l, U r) {
        return new Pair<T, U>(l, r);
    }
    
    private static <E> List<E> list(Collection<E> col) {
        return new ArrayList<E>(col);
    }
    
    private static <V> boolean containsVertex(Stack<Pair<List<Vertex<V>>, Integer>> coll, Vertex<V> v, AccessStats<?, V> stats) {
        if (v == null) return false;
        stats.increment(v);
        for (Pair<List<Vertex<V>>, Integer> p : coll) {
            stats.increment(p._1.get(p._2));
            if (v.equals(p._1.get(p._2))) return true;
        }
        return false;
    }
}
