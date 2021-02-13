/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.percolation;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Optional;

/**
 * 1-based coordinates
 */
@EqualsAndHashCode
@ToString
public class Coord {
	public static final Coord FAKE_TOP = new Coord(0);
	public static final Coord FAKE_BOTTOM = new Coord(Integer.MAX_VALUE);
	private final int coord;

	public static Coord of(int coord) {
		if(coord<=0) {
			throw new IllegalArgumentException();
		}
		return new Coord(coord);
	}

	private Coord(int coord) {
		this.coord = coord;
	}

	public static Coord min(Coord start, Coord end) {
		return start.coord <= end.coord ? start : end;
	}

	public static Coord max(Coord start, Coord end) {
		return start.coord <= end.coord ? end : start;
	}

	public Coord findTopNeighbour(int dimension) {
		int topCoord = coord - dimension;
		if (topCoord <= 0) {
			return FAKE_TOP;
		}
		return of(topCoord);
	}

	public Optional<Coord> findLeftNeighbour(int dimension) {
		if (coord % dimension == 1 || coord == 1) {
			return Optional.empty();
		}
		return Optional.of(of(coord - 1));
	}

	public Optional<Coord> findRightNeighbour(int dimension) {
		if (coord % dimension == 0) {
			return Optional.empty();
		}
		return Optional.of(of(coord + 1));
	}

	public Coord findBottomNeighbour(int dimension) {
		int bottomCoord = coord + dimension;
		if (bottomCoord > dimension*dimension || dimension == 1) {
			return FAKE_BOTTOM;
		}
		return of(bottomCoord);
	}

	/**
	 * 0-based row
	 */
	public int findRow(int n) {
		return (coord-1) / n ;
	}

	/**
	 * 0-based col
	 */
	public int findCol(int n) {
		return (coord-1) % n;
	}

	public boolean isFake() {
		return equals(FAKE_TOP) || equals(FAKE_BOTTOM);
	}

	public Integer convertToInt(int maxCoord) {
		if (coord > maxCoord) {
			return maxCoord;
		}
		return coord;
	}
}
