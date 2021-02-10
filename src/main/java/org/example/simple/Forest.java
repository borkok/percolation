/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.simple;

public class Forest {
	private int[] forest;
	private int[] treeSize;

	public Forest(int pointCount) {
		initializeForest(pointCount);
	}

	private void initializeForest(int pointCount) {
		forest = new int[pointCount];
		treeSize = new int[pointCount];
		for (int i = 0; i < pointCount; i++) {
			forest[i] = i;
			treeSize[i] = 1;
		}
	}

	int findRootFor(int point) {
		int coord = point;
		while(this.forest[coord] != coord) {
			coord = this.forest[coord];
		}
		return coord;
	}

	int findTreeSize(int root) {
		return treeSize[root];
	}

	public void putSourceUnderDestination(Directions directions) {
		forest[directions.source()] = directions.destination();
		treeSize[directions.destination()] = treeSize[directions.source()] + treeSize[directions.destination()];
	}

	int getSmallerTree(int rootX, int rootY) {
		if (findTreeSize(rootX) <= findTreeSize(rootY)) {
			return rootX;
		}
		return rootY;
	}

	int getLargerTree(int rootX, int rootY) {
		if (findTreeSize(rootX) <= findTreeSize(rootY)) {
			return rootY;
		}
		return rootX;
	}
}
