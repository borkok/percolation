package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnionFindTest {
	private static Stream<Arguments> unionFind_source() {
		return Stream.of(
				Arguments.of(new int[][]{}, 10, 5, 9, false),
				Arguments.of(new int[][]{{4,5}}, 10, 5, 9, false),
				Arguments.of(new int[][]{{5,9}}, 10, 5, 9, true),
				Arguments.of(new int[][]{
						{0,5},
						{5,6},
						{6,7},
						{19,14},
						{14,9},
						{8,9},
						{3,8},
						{2,3},
						{7,2},
						{7,12},
						{12,13},
						{13, 18},
						{10,15}
					}, 20, 0, 19, true)
		);
	}

	@ParameterizedTest
	@MethodSource("unionFind_source")
	public void unionFind_whenNoPoints_expectedFalse(int[][] input, int lastPoint, int from, int to, boolean result) {
		Segments segments = new Segments(input);
		Directions directions = new Directions(from, to);
		assertEquals(new UnionFind(segments, lastPoint).findPathFor(directions), result);
	}
}
