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

    protected AbstractEdge(Vertex<V> left, Vertex<V> right, E content) {
        if (left == null || right == null || content == null) {
            throw new NullPointerException();
        }
        
        this.left    = left;
        this.right   = right;
        this.content = content;
    }

    @Override
    public E content() {
        return content;
    }

    @Override
    public Set<Vertex<V>> vertices() {
        return new HashSet<Vertex<V>>(asList(left, right));
    }


    protected Vertex<V> left() { return left; }
    protected Vertex<V> right() { return right; }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((left == null) ? 0 : left.hashCode());
        result = prime * result + ((right == null) ? 0 : right.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractEdge other = (AbstractEdge) obj;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        if (left == null) {
            if (other.left != null)
                return false;
        } else if (!left.equals(other.left))
            return false;
        if (right == null) {
            if (other.right != null)
                return false;
        } else if (!right.equals(other.right))
            return false;
        return true;
    }
}
