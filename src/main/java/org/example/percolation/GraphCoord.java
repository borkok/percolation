/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.percolation;

import org.example.simple.Graph;

public class GraphCoord extends Graph {
	private final int maxCoord;

	public GraphCoord(Segments segments, int pointCount) {
		super(pointCount);
		this.maxCoord = pointCount - 1;
		segments.forEach(this::union);
	}

	private void union(Coord coordX, Coord coordY) {
		int x = coordX.convertToInt(maxCoord);
		int y = coordY.convertToInt(maxCoord);
		union(x,y);
	}

	public boolean existsPathFor(Segment segment) {
		return segment.processCoords(this::hasPathForCoords);
	}

	private boolean hasPathForCoords(Coord coordX, Coord coordY) {
		int start = coordX.convertToInt(maxCoord);
		int end = coordY.convertToInt(maxCoord);
		return hasPathFor(start,end);
	}
}
