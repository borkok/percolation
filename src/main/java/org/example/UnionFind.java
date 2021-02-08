package org.example;

public class UnionFind
{
    private final Graph graph;

    public UnionFind(Segments segments, int lastPoint) {
        this.graph = new Graph(segments, lastPoint);
    }

    public boolean findPathFor(Directions directions) {
        return graph.existsPathFor(directions);
    }
}
