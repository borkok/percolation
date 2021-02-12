/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.percolation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PercolationTest {
	private static Stream<Arguments> doesPercolate() {
		return Stream.of(
				Arguments.of(1, 1f, new RandomBoolFake(Collections.singletonList(true)), true),
				Arguments.of(1, 0f, new RandomBoolFake(Collections.singletonList(false)), false),
				Arguments.of(2, 0.5f, new RandomBoolFake(Arrays.asList(false, true, false, true)), true)
		);
	}

	@ParameterizedTest
	@MethodSource
	public void doesPercolate(int n, float p, RandomBool randomBool, boolean expected) {
		boolean result = new Percolation(randomBool)
				.initMatrix(n)
				.makeTrial(p)
				.doesPercolate();
		assertEquals(expected, result);
	}

	private static Stream<Arguments> makeTrial() {
		return Stream.of(
				Arguments.of(2, 0.5f, new RandomBoolFake(Arrays.asList(false, true, false, true)),
						List.of(
								Segment.of(Coord.FAKE_TOP, Coord.of(2)),
								Segment.of(Coord.of(2), Coord.of(4)),
								Segment.of(Coord.of(4), Coord.FAKE_BOTTOM)
						)
				),
				Arguments.of(4, 0.5f, new RandomBoolFake(Arrays.asList(
								false, true, false, true,
								false, true, true, false,
						 		false, true, false, true,
								true, true, false, false
						)),
						List.of(
								Segment.of(Coord.FAKE_TOP, Coord.of(2)),
								Segment.of(Coord.FAKE_TOP, Coord.of(4)),
								Segment.of(Coord.of(2), Coord.of(6)),
								Segment.of(Coord.of(6), Coord.of(7)),
								Segment.of(Coord.of(6), Coord.of(10)),
								Segment.of(Coord.of(10), Coord.of(14)),
								Segment.of(Coord.of(13), Coord.of(14)),
								Segment.of(Coord.of(13), Coord.FAKE_BOTTOM),
								Segment.of(Coord.of(14), Coord.FAKE_BOTTOM)
						)
				)
		);
	}

	@ParameterizedTest
	@MethodSource
	public void makeTrial(int n, float p, RandomBool randomBool, List<Segment> expectedSegments) {
		Percolation percolation = new Percolation(randomBool)
				.initMatrix(n)
				.makeTrial(p);
		Segments segments = percolation.getSegments();
		segments.removeAll(expectedSegments);
		assertThat(segments.isEmpty()).isTrue();
	}

	@Test
	public void doesPercolate_exceptions() {
		Percolation percolation = new Percolation(new RandomBoolFake(Collections.emptyList()));
		assertThatThrownBy(() -> percolation.initMatrix(0)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> percolation.initMatrix(-1)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> percolation.makeTrial(-1)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> percolation.makeTrial(1.1f)).isInstanceOf(IllegalArgumentException.class);
	}

	private static class RandomBoolFake implements RandomBool {
		private final Iterator<Boolean> iterator;
		private final List<Boolean> values;

		private RandomBoolFake(List<Boolean> values) {
			this.values = values;
			this.iterator = values.iterator();
		}

		@Override
		public Boolean next(float p) {
			return iterator.next();
		}
	}
}
