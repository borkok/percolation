/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.percolation;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Optional;

@EqualsAndHashCode
@ToString
public class Coord {
	public static final Coord FALSE_TOP = new Coord(0);
	public static final Coord FALSE_BOTTOM = new Coord(Integer.MAX_VALUE);
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

	public Coord findTopNeighbour(int dimension) {
		int topCoord = coord - dimension;
		if (topCoord <= 0) {
			return FALSE_TOP;
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
			return FALSE_BOTTOM;
		}
		return of(bottomCoord);
	}
}
