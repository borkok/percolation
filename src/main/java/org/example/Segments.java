/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example;

import java.util.function.BiConsumer;

public class Segments {
	private final int[][] input;

	public Segments(int[][] input) {
		this.input = input;
	}

	public void forEach(BiConsumer<Integer, Integer> consumer) {
		for (int[] ints : input) {
			consumer.accept(ints[0], ints[1]);
		}
	}
}
