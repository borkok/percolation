/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.simple;

public class Graph {
	private final Forest forest;

	public Graph(Segments segments, int pointCount) {
		forest = new Forest(pointCount);
		segments.forEach(this::union);
	}

	private void union(Integer x, Integer y) {
		int rootX = forest.findRootFor(x);
		int rootY = forest.findRootFor(y);

		forest.putSourceUnderDestination(
				new Directions(
						forest.getSmallerTree(rootX, rootY),
						forest.getLargerTree(rootX, rootY)
				)
		);
	}

	public boolean existsPathFor(Directions directions) {
		return forest.findRootFor(directions.source()) == forest.findRootFor(directions.destination());
	}
}
