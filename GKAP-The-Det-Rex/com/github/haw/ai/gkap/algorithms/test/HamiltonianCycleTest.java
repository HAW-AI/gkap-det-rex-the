package com.github.haw.ai.gkap.algorithms.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

import org.junit.Before;
import org.junit.Test;

import com.github.haw.ai.gkap.graph.*;
import static com.github.haw.ai.gkap.graph.Graphs.*;

import static com.github.haw.ai.gkap.algorithms.HamiltonianCycle.*;

public class HamiltonianCycleTest {

    Graph<String, String> graph;
    Graph<String, String> graph1;
    Graph<String, String> graph2;
    

    @Before
    public void setUp() {
        Set<Vertex<String>> vs = new HashSet<Vertex<String>>(asList(vertex("V1"), vertex("V2"), vertex("V3"), vertex("V4"), vertex("V5")));
        Set<Edge<String, String>> es = new HashSet<Edge<String,String>>();
        es.add(undirectedEdge(vertex("V1"), vertex("V2"), "E1"));
        es.add(undirectedEdge(vertex("V2"), vertex("V3"), "E2"));
        es.add(undirectedEdge(vertex("V3"), vertex("V4"), "E3"));
        es.add(undirectedEdge(vertex("V4"), vertex("V1"), "E4"));
        // explicit copy because Graph#Graph doesn't create copies...
        graph1 = graph(new HashSet<Edge<String,String>>(es), new HashSet<Vertex<String>>(vs));
        
        es.add(undirectedEdge(vertex("V1"), vertex("V5"), "E5"));
        es.add(undirectedEdge(vertex("V2"), vertex("V5"), "E6"));
        es.add(undirectedEdge(vertex("V3"), vertex("V5"), "E7"));
        es.add(undirectedEdge(vertex("V4"), vertex("V5"), "E8"));
        graph = graph(es, vs);
        
        graph2 = graph(new HashSet<Edge<String, String>>(), new HashSet<Vertex<String>>(asList(vertex("V1"))));
    }

    @Test
    public void testHamiltonianCycle() {
        List<Vertex<String>> cycle = hamiltonianCycle(graph);
        assertValid(cycle, graph);
        
        List<Vertex<String>> cycle1 = hamiltonianCycle(graph1);
        assertValid(cycle1, graph1);

        List<Vertex<String>> cycle2 = hamiltonianCycle(graph2);
        assertValid(cycle2, graph2);
    }
    
    private <E,V> void assertValid(List<Vertex<V>> cycle, Graph<E,V> graph) {
        // no path found? still valid
        if (cycle.isEmpty())
            return;
        
        List<Vertex<V>> vs = new ArrayList<Vertex<V>>(graph.vertices());
        
        assertTrue(cycle.containsAll(vs));
        
        if (cycle.size() <= 1) {
            assertEquals(vs.size(), cycle.size());
        } else {
            assertEquals(cycle.get(0), cycle.get(cycle.size()-1));
            
            List<Vertex<V>> path = new ArrayList<Vertex<V>>(cycle);
            path.remove(0);
            assertTrue(vs.containsAll(path));
            assertEquals(vs.size()+1, cycle.size());
        }
        
        for (int i = 0; i < vs.size()-1; ++i) {
            assertTrue(graph.adjacent(vs.get(i)).contains(vs.get(i+1)));
        }
    }

}
