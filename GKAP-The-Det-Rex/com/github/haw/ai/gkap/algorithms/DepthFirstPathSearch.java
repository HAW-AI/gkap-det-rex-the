package com.github.haw.ai.gkap.algorithms;

import java.util.Stack;

import com.github.haw.ai.gkap.graph.Graph;
import com.github.haw.ai.gkap.graph.Vertex;

public class DepthFirstPathSearch<E, V> extends AbstractPathSearchAlgorithm<E, V> {
    public static <E,V> DepthFirstPathSearch<E,V> create(Graph<E,V> graph, Vertex<V> source, Vertex<V> target) {
        if (graph == null || source == null || target == null) {
            throw new IllegalArgumentException();
        }
        return new DepthFirstPathSearch<E, V>(graph, source, target);
    }
    
    private DepthFirstPathSearch(Graph<E,V> graph, Vertex<V> source, Vertex<V> target) {
        super(graph, source, target);
    }

    @Override
    protected Stueue<Vertex<V>> createStueue() {
        return new Stueue<Vertex<V>>() {
            private Stack<Vertex<V>> me = new Stack<Vertex<V>>();
            
            @Override
            public void add(Vertex<V> elem) {
                me.push(elem);
            }
            @Override
            public boolean isEmpty() {
                return me.empty();
            }
            @Override
            public Vertex<V> remove() {
                return me.pop();
            }
        };
    }

}
