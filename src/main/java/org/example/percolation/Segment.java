/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.percolation;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Segment {
	private final Coord start;
	private final Coord end;

	public static Segment of(Coord start, Coord end) {
		return new Segment(start, end);
	}

	private Segment(Coord start, Coord end) {
		this.start = Coord.min(start, end);
		this.end = Coord.max(start, end);
	}
}
