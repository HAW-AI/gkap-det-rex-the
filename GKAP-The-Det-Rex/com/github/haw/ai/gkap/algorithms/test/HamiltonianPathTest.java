package com.github.haw.ai.gkap.algorithms.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

import org.junit.Before;
import org.junit.Test;

import com.github.haw.ai.gkap.graph.*;
import static com.github.haw.ai.gkap.graph.Graphs.*;

import static com.github.haw.ai.gkap.algorithms.HamiltonianPath.hamiltonianPath;

public class HamiltonianPathTest {

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
    public void testHamiltonianPath() {
        List<Vertex<String>> path = hamiltonianPath(graph);
        assertTrue(path.containsAll(graph.vertices()) && path.size() == graph.vertices().size()+1);
        
        List<Vertex<String>> path1 = hamiltonianPath(graph1);
        assertTrue(path1.isEmpty());

        List<Vertex<String>> path2 = hamiltonianPath(graph2);
        assertEquals(2, path2.size());
    }

}
