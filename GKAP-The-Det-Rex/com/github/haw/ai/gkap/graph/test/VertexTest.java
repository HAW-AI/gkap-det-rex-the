package com.github.haw.ai.gkap.graph.test;

import static com.github.haw.ai.gkap.graph.Graphs.vertex;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.github.haw.ai.gkap.graph.Graphs;
import com.github.haw.ai.gkap.graph.Vertex;

public class VertexTest {
	Integer i1, i2, i3, i4;
    String s1, s2;
	Vertex<Integer> v1, v2, v3;
    Vertex<String> v4, v5;
	
	@Before
	public void setUp() throws Exception {
	    i1 = -1;
        i2 = 0;
        i3 = 9;
        i4 = -255;
        s1 = "";
        s2 = "foobar";
        // Integer content
        v1 = Graphs.vertex(i1);
        v2 = Graphs.vertex(i2);
        v3 = Graphs.vertex(i3);
        // String content
        v4 = Graphs.vertex(s1);
        v5 = Graphs.vertex(s2);
	}

	@Test
	public void testContent() {
		assertEquals(i1, v1.content());
		assertEquals(i2, v2.content());
		assertEquals(i3, v3.content());
		assertEquals(s1, v4.content());
		assertEquals(s2, v5.content());
	}
}
