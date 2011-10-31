package com.github.haw.ai.gkap.graph;

/**
 * @author Till Theis <till.theis@haw-hamburg.de>
 * @author Patrick Detlefsen <patrick.detlefsen@haw-hamburg.de>
 * @author Benjamin Rexin <benjamin.rexin@haw-hamburg.de>
 */
public class VertexImpl<V> implements Vertex<V> {
  private V content;	

  private VertexImpl(V content) {
    this.content = content;
  }

  public static Vertex<V> valueOf(V content) {
		return new VertexImpl<V>(content);
	}
  public V content() {
    return content;
  }

  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((content == null) ? 0 : content.hashCode());
    return result;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (!(o instanceof Vertex)) {
      return false;
    }
    if (content == null) {
      if ((Vertex o).content() != null) {
        return false;
      }
    }
    if (!content.equals((Vertex o).content) {
      return false;
    }
    return true;
  }
}
