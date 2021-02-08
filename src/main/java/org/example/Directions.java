/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example;

public class Directions {
	private final int from;
	private final int to;

	public Directions(int from, int to) {
		this.from = Integer.min(from, to);
		this.to = Integer.max(from, to);
	}

	public int from() {
		return from;
	}

	public int to() {
		return to;
	}
}
