/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.percolation;

import org.example.common.Directions;
import org.example.common.Forest;

public class Graph {
	private final Forest forest;
	private final int maxCoord;

	public Graph(Segments segments, int pointCount) {
		this.maxCoord = pointCount - 1;
		forest = new Forest(pointCount);
		segments.forEach(this::union);
	}

	private void union(Coord coordX, Coord coordY) {
		int x = coordX.convertToInt(maxCoord);
		int y = coordY.convertToInt(maxCoord);
		union(x,y);
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

	public boolean existsPathFor(Segment segment) {
		return segment.processCoords(this::hasPathForCoords);
	}

	private boolean hasPathForCoords(Coord coordX, Coord coordY) {
		int start = coordX.convertToInt(maxCoord);
		int end = coordY.convertToInt(maxCoord);
		return hasPathFor(start,end);
	}

	private boolean hasPathFor(int start, int end) {
		return forest.findRootFor(start) == forest.findRootFor(end);
	}
}
