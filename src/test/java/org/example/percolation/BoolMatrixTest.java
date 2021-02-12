package org.example.percolation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.percolation.Coord.*;
import static org.example.percolation.Coord.of;

public class BoolMatrixTest {

	private static Stream<Arguments> params() {
		return Stream.of(
				Arguments.of(1, 1, new Coord[]{FALSE_TOP, FALSE_BOTTOM}),
				Arguments.of(2, 1, new Coord[]{FALSE_TOP, of(3), of(2)}),
				Arguments.of(2, 2, new Coord[]{FALSE_TOP, of(4), of(1)}),
				Arguments.of(2, 3, new Coord[]{FALSE_BOTTOM, of(1), of(4)}),
				Arguments.of(2, 4, new Coord[]{FALSE_BOTTOM, of(2), of(3)}),
				Arguments.of(3, 5, new Coord[]{of(4), of(6), of(2), of(8)})
		);
	}

	@ParameterizedTest
	@MethodSource("params")
	public void findNeighbours(int dimension, int coord, Coord... coords) {
		List<Coord> neighbours = new BoolMatrix(dimension).findNeighbours(of(coord));
		assertThat(neighbours).containsOnly(coords);
	}
}