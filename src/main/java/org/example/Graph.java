/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example;

public class Graph {
	private int[] forest;
	private int[] treeSize;

	public Graph(Segments segments, int pointCount) {
		initializeForest(pointCount);
		segments.forEach(this::union);
	}

	private void initializeForest(int pointCount) {
		forest = new int[pointCount];
		treeSize = new int[pointCount];
		for (int i = 0; i < pointCount; i++) {
			forest[i] = i;
			treeSize[i] = 1;
		}
	}

	private void union(Integer x, Integer y) {
		int rootX = root(x);
		int rootY = root(y);

		int treeSizeX = treeSize[rootX];
		int treeSizeY = treeSize[rootY];

		if (treeSizeX <= treeSizeY) {
			forest[rootX] = rootY;
			treeSize[rootY] = treeSizeX + treeSizeY;
		} else {
			forest[rootY] = rootX;
			treeSize[rootX] = treeSizeX + treeSizeY;
		}
	}

	private int root(int point) {
		int coord = point;
		while(forest[coord] != coord) {
			coord = forest[coord];
		}
		return coord;
	}

	public boolean existsPathFor(Directions directions) {
		return root(directions.from()) == root(directions.to());
	}
}
