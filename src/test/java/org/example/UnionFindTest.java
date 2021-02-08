package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnionFindTest {
	private static Stream<Arguments> unionFind_source() {
		return Stream.of(
				Arguments.of(new int[][]{}, 5, 9, false)
		);
	}

	@ParameterizedTest
	@MethodSource("unionFind_source")
	public void unionFind_whenNoPoints_expectedFalse(int[][] input, int from, int to, boolean result) {
		assertEquals(new UnionFind(input).find(from, to), result);
	}
}
