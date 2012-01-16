package com.github.haw.ai.gkap.graph;

public class Pair<T,U> {
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