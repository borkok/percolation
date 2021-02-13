/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.percolation;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

public class Segments {
	private final List<Segment> segments = new LinkedList<>();

	public void add(Coord start, Coord end) {
		segments.add(Segment.of(start, end));
	}

	public void forEach(BiConsumer<Coord, Coord> consumer) {
		for (Segment segment: segments) {
			segment.processCoords(consumer);
		}
	}
}
