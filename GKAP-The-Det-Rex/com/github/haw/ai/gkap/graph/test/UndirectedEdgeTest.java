package com.github.haw.ai.gkap.graph.test;

/**
 * @author Till Theis <till.theis@haw-hamburg.de>
 */
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import com.github.haw.ai.gkap.graph.*;

import static com.github.haw.ai.gkap.graph.Graphs.*;
import static java.util.Arrays.asList;

public class UndirectedEdgeTest {
    Integer c1, c2, c3, c4;
    String c5, c6;
    
    Vertex<Integer> v1, v2, v3;
    Vertex<String> v4, v5;
    
    Edge<Integer, Integer> e1, e2, e3;
    Edge<Integer, String> e4, e5, e6, e7;
    
    @Before
    public void setUp() {
        c1 = -1;
        c2 = 0;
        c3 = 9;
        c4 = -255;
        
        c5 = "";
        c6 = "hans";
        
        v1 = vertex(c1);
        v2 = vertex(c2);
        v3 = vertex(c3);

        v4 = vertex(c5);
        v5 = vertex(c6);
        
        e1 = undirectedEdge(v1, v2, c2);
        e2 = undirectedEdge(v2, v3, c1);
        e3 = undirectedEdge(v3, v1, c3);

        e4 = undirectedEdge(v4, v5, c2);
        e5 = undirectedEdge(v5, v4, c4);
        e6 = undirectedEdge(v4, v4, c4);
        e7 = undirectedEdge(v5, v4, c2);
    }
    
    @Test
    public void testContent() {
        assertEquals(c2, e1.content());
        assertEquals(c1, e2.content());
        assertEquals(c3, e3.content());
        assertEquals(c2, e4.content());
        assertEquals(c4, e5.content());
    }

    @Test
    public void testVertices() {
        assertTrue(e1.vertices().containsAll(asList(v1, v2)));
        assertTrue(e2.vertices().containsAll(asList(v3, v2)));
        assertTrue(e3.vertices().containsAll(asList(v1, v3)));
        assertTrue(e4.vertices().containsAll(asList(v4, v5)));
        assertTrue(e5.vertices().containsAll(asList(v4, v5)));
    }

    @Test
    public void testVerticesSize() {
        assertEquals(2, e1.vertices().size());
        assertEquals(2, e2.vertices().size());
        assertEquals(2, e3.vertices().size());
        assertEquals(2, e4.vertices().size());
        assertEquals(2, e5.vertices().size());
        assertEquals(1, e6.vertices().size());
    }

    @Test
    public void testIsReachable() {
        assertTrue(e1.isReachable(v1, v2));
        assertTrue(e2.isReachable(v2, v3));
        assertTrue(e3.isReachable(v3, v1));
        assertTrue(e4.isReachable(v4, v5));
        assertTrue(e5.isReachable(v5, v4));
        
        assertTrue(e1.isReachable(v2, v1));
        assertTrue(e2.isReachable(v3, v2));
        assertTrue(e3.isReachable(v1, v3));
        assertTrue(e4.isReachable(v5, v4));
        assertTrue(e5.isReachable(v4, v5));
        
        assertTrue(e6.isReachable(v4, v4));
    }
    
    @Test
    public void testIsReachableNegative() {
        assertFalse(e1.isReachable(v2, v3));
        assertFalse(e2.isReachable(v1, v2));
        assertFalse(e3.isReachable(v2, v2));
    }
    
    @Test
    public void testEquality() {
        assertTrue(e4.equals(e7));
        assertTrue(e7.equals(e4));
        
        assertFalse(e4.equals(e5));
        assertFalse(e5.equals(e4));
    }
    
    @Test
    public void testIsBridge() {
        Vertex<String> vertex1, vertex2, vertex3, vertex4, vertex5, vertex6;
        Edge<Integer, String> edge1, edge2, edge3, edge4, edge5, edge6, edge7;
        
        vertex1 = vertex("vertex1");
        vertex2 = vertex("vertex2");
        vertex3 = vertex("vertex3");
        vertex4 = vertex("vertex4");
        vertex5 = vertex("vertex5");
        vertex6 = vertex("vertex6");
        
        edge1 = undirectedEdge(vertex1, vertex2, 1);
        edge2 = undirectedEdge(vertex1, vertex3, 1);
        edge3 = undirectedEdge(vertex2, vertex3, 1);
        edge4 = undirectedEdge(vertex3, vertex4, 1); // bridge
        edge5 = undirectedEdge(vertex4, vertex5, 1);
        edge6 = undirectedEdge(vertex5, vertex6, 1);
        edge7 = undirectedEdge(vertex4, vertex6, 1);
        
        Set<Edge<Integer,String>> edges = new HashSet<Edge<Integer,String>>(Arrays.asList(edge1, edge2, edge3, edge4, edge5, edge6, edge7));
        Set<Vertex<String>> vertices = new HashSet<Vertex<String>>(Arrays.asList(vertex1, vertex2, vertex3, vertex4, vertex5, vertex6));
        
        Graph<Integer, String> bridgeGraph = Graphs.graph(edges, vertices);
        
        assertFalse(edge1.isBridge(bridgeGraph));
        assertFalse(edge2.isBridge(bridgeGraph));
        assertFalse(edge3.isBridge(bridgeGraph));
        assertFalse(edge5.isBridge(bridgeGraph));
        assertFalse(edge6.isBridge(bridgeGraph));
        assertFalse(edge7.isBridge(bridgeGraph));
        
        assertTrue(edge4.isBridge(bridgeGraph));
	}

}
