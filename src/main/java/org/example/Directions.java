/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example;

public class Directions {
	private final int source;
	private final int destination;

	public Directions(int source, int destination) {
		this.source = Integer.min(source, destination);
		this.destination = Integer.max(source, destination);
	}

	public int source() {
		return source;
	}

	public int destination() {
		return destination;
	}
}
