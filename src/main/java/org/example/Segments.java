/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example;

public class Segments {
	private final int[][] input;

	public Segments(int[][] input) {
		this.input = input;
	}

	public boolean existsPathFor(Directions directions) {
		if (input.length == 0) {
			return false;
		}
		return true;
	}
}
