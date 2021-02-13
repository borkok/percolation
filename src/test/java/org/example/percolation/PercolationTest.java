/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.percolation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PercolationTest {
	private static Stream<Arguments> doesPercolate() {
		return Stream.of(
				Arguments.of(1, 1f, new RandomBoolFake(Collections.singletonList(true)), true),
				Arguments.of(1, 0f, new RandomBoolFake(Collections.singletonList(false)), false),
				Arguments.of(2, 0.5f, new RandomBoolFake(Arrays.asList(false, true, false, true)), true),
				Arguments.of(2, 0.5f, new RandomBoolFake(Arrays.asList(false, false, false, true)), false),
				Arguments.of(5, 0.5f, new RandomBoolFake(Arrays.asList(
						false, false, false, true, false,
						false, false, true, true, false,
						false, true, true, false, false,
						true, true, false, false, false,
						true, false, false, true, false
				)), true),
				Arguments.of(5, 0.5f, new RandomBoolFake(Arrays.asList(
						false, false, false, true, false,
						false, false, true, true, false,
						false, true, true, false, false,
						true, true, false, false, false,
						false, false, true, true, false
				)), false)
		);
	}

	@ParameterizedTest
	@MethodSource
	public void doesPercolate(int n, float p, RandomBool randomBool, boolean expected) {
		Percolation percolation = new Percolation(randomBool)
				.reset(n)
				.makeTrial(p);
		Map<UUID, Boolean> trialsResult = percolation.trialsResult();
		assertEquals(expected, trialsResult.values().stream().allMatch(result -> result));
	}

	@Test
	public void percolation_exceptions() {
		Percolation percolation = new Percolation(new RandomBoolFake(Collections.emptyList()));
		assertThatThrownBy(() -> percolation.reset(0)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> percolation.reset(-1)).isInstanceOf(IllegalArgumentException.class);
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
