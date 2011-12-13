package com.github.haw.ai.gkap.algorithms;

import java.util.LinkedList;
import java.util.Queue;

import com.github.haw.ai.gkap.graph.Graph;
import com.github.haw.ai.gkap.graph.Vertex;

public class BreadthFirstPathSearch<E, V> extends AbstractPathSearchAlgorithm<E, V> {
    public static <E,V> BreadthFirstPathSearch<E,V> create(Graph<E,V> graph, Vertex<V> source, Vertex<V> target) {
        if (graph == null || source == null || target == null) {
            throw new IllegalArgumentException();
        }
        return new BreadthFirstPathSearch<E, V>(graph, source, target);
    }
    
    private BreadthFirstPathSearch(Graph<E,V> graph, Vertex<V> source, Vertex<V> target) {
        super(graph, source, target);
    }

    @Override
    protected Stueue<Vertex<V>> createStueue() {
        return new Stueue<Vertex<V>>() {
            private Queue<Vertex<V>> me = new LinkedList<Vertex<V>>();
            
            @Override
            public void add(Vertex<V> elem) {
                me.add(elem);
            }
            @Override
            public boolean isEmpty() {
                return me.isEmpty();
            }
            @Override
            public Vertex<V> remove() {
                return me.remove();
            }
        };
    }

}
