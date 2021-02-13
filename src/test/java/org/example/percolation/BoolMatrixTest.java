package org.example.percolation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.percolation.Coord.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoolMatrixTest {

	private static Stream<Arguments> findNeighboursParams() {
		return Stream.of(
				Arguments.of(1, 1, new Coord[]{FAKE_TOP, FAKE_BOTTOM}),
				Arguments.of(2, 1, new Coord[]{FAKE_TOP, of(3), of(2)}),
				Arguments.of(2, 2, new Coord[]{FAKE_TOP, of(4), of(1)}),
				Arguments.of(2, 3, new Coord[]{FAKE_BOTTOM, of(1), of(4)}),
				Arguments.of(2, 4, new Coord[]{FAKE_BOTTOM, of(2), of(3)}),
				Arguments.of(3, 5, new Coord[]{of(4), of(6), of(2), of(8)})
		);
	}

	@ParameterizedTest
	@MethodSource("findNeighboursParams")
	public void findNeighbours(int dimension, int coord, Coord... coords) {
		List<Coord> neighbours = of(coord).findMyNeighbours(dimension);
		assertThat(neighbours).containsOnly(coords);
	}

	@ParameterizedTest
	@CsvSource({"1,1","2,4"})
	public void forEach(int dimension, int expected) {
		AtomicInteger atomicInteger = new AtomicInteger();
		new BoolMatrix(dimension).forEach(c -> atomicInteger.incrementAndGet());
		assertThat(atomicInteger.get()).isEqualTo(expected);
	}

	@ParameterizedTest
	@CsvSource({"1,1","5,25","5,18"})
	public void open(int dimension, int coord) {
		BoolMatrix boolMatrix = new BoolMatrix(dimension);
		boolMatrix.open(of(coord));
		assertTrue(boolMatrix.isOpen(of(coord)));
	}
}