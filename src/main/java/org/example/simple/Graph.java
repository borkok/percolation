/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.simple;

import org.example.common.Directions;
import org.example.common.Forest;

public class Graph {
	protected final Forest forest;

	protected Graph(int pointCount) {
		forest = new Forest(pointCount);
	}

	public Graph(Segments segments, int pointCount) {
		forest = new Forest(pointCount);
		segments.forEach(this::union);
	}

	protected void union(Integer x, Integer y) {
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
		return hasPathFor(directions.source(), directions.destination());
	}

	protected boolean hasPathFor(int start, int end) {
		return forest.findRootFor(start) == forest.findRootFor(end);
	}
}
