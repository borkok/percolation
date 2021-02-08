package org.example;

public class UnionFind
{
    private final Segments segments;

    public UnionFind(Segments segments) {
        this.segments = segments;
    }

    public boolean findPathFor(Directions directions) {
        return segments.existsPathFor(directions);
    }
}
