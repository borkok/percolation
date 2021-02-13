package org.example.simple;

import org.example.common.Directions;

public class UnionFind
{
    private final Graph graph;

    public UnionFind(Segments segments, int pointCount) {
        this.graph = new Graph(segments, pointCount);
    }

    public boolean findPathFor(Directions directions) {
        return graph.existsPathFor(directions);
    }
}
