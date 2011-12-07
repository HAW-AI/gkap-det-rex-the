package com.github.haw.ai.gkap.graph;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * @author Till Theis <till.theis@haw-hamburg.de>
 * @author Patrick Detlefsen <patrick.detlefsen@haw-hamburg.de>
 * @author Benjamin Rexin <benjamin.rexin@haw-hamburg.de>
 */
abstract public class AbstractEdge<E,V> implements Edge<E,V>{
    private E content;
    private Vertex<V> left;
    private Vertex<V> right;
    private int capacity;
    private int flow;

    protected AbstractEdge(Vertex<V> left, Vertex<V> right, E content, int capacity, int flow) {
        if (left == null || right == null || content == null) {
            throw new NullPointerException();
        }
        
        this.left    = left;
        this.right   = right;
        this.content = content;
        this.capacity = capacity;
        this.flow = flow;
    }

    @Override
    public E content() {
        return content;
    }

    @Override
    public Set<Vertex<V>> vertices() {
        return new HashSet<Vertex<V>>(asList(left, right));
    }


    @Override
    public Vertex<V> left() { return left; }
    
    @Override
    public Vertex<V> right() { return right; }
    
    @Override
    public Vertex<V> otherVertex(Vertex<V> oneSide) {
        if (!this.vertices().contains(oneSide)){
            throw new IllegalArgumentException();
        }
        if(this.left().equals(oneSide)) {
            return this.right();
        } else {
            return this.left();
        }
    }
    
    public boolean isDirected() {
    	return false;
    }
    
    public int capacity() {
    	return capacity;
    }
    
    public int flow() {
    	return flow;
    }
}
