package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnionFindTest {
	private static Stream<Arguments> unionFind_source() {
		return Stream.of(
				Arguments.of(new int[][]{}, 5, 9, false),
				Arguments.of(new int[][]{{5,9}}, 5, 9, true)
		);
	}

	@ParameterizedTest
	@MethodSource("unionFind_source")
	public void unionFind_whenNoPoints_expectedFalse(int[][] input, int from, int to, boolean result) {
		Segments segments = new Segments(input);
		Directions directions = new Directions(new Point(from), new Point(to));
		assertEquals(new UnionFind(segments).findPathFor(directions), result);
	}
}
