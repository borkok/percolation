/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.percolation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PercolationTest {
	private static Stream<Arguments> doesPercolate() {
		return Stream.of(
				Arguments.of(1, 1f, true)
		);
	}

	@ParameterizedTest
	@MethodSource
	public void doesPercolate(int n, float p, boolean expected) {
		boolean result = new Percolation(n).openWithProbability(p)
				.doesPercolate();
		assertEquals(expected, result);
	}

	@Test
	public void doesPercolate_exceptions() {
		assertThatThrownBy(() -> new Percolation(0)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> new Percolation(-1)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> new Percolation(1).openWithProbability(-0.1f)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> new Percolation(1).openWithProbability(1.1f)).isInstanceOf(IllegalArgumentException.class);
	}
}
