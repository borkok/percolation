/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.percolation;

import java.util.ArrayList;
import java.util.List;

public class BoolMatrix {
	private final int n;
	private final boolean[][] matrix;

	/**
	 * Construc matrix with given dimension.
	 * Initially all cells are false;
	 * @param dimension dimension
	 */
	BoolMatrix(int dimension) {
		if (dimension <= 0) {
			throw new IllegalArgumentException();
		}
		this.n = dimension;
		this.matrix = new boolean[dimension][dimension];
	}

	List<Coord> findNeighbours(Coord coord) {
		List<Coord> neighbours = new ArrayList<>(List.of(coord.findTopNeighbour(n), coord.findBottomNeighbour(n)));
		coord.findLeftNeighbour(n).ifPresent(neighbours::add);
		coord.findRightNeighbour(n).ifPresent(neighbours::add);
		return neighbours;
	}
}
