/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.percolation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

	public void forEach(Consumer<Coord> coordConsumer) {
		for (int i = 1; i <= count(); i++) {
			coordConsumer.accept(Coord.of(i));
		}
	}

	private int count() {
		return n * n;
	}

	public void open(Coord coord) {
		Coord.RowCol rowCol = coord.convertToRowCol(n);
		matrix[rowCol.row()][rowCol.col()] = true;
	}

	public boolean value(Coord coord) {
		if (coord.isFake()) {
			return true;
		}
		Coord.RowCol rowCol = coord.convertToRowCol(n);
		return matrix[rowCol.row()][rowCol.col()];
	}

	public List<Coord> findOpenNeighbours(Coord coord) {
		return findNeighbours(coord).stream()
				.filter(this::value)
				.collect(Collectors.toList());
	}
}
