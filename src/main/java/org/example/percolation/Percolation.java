/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.percolation;

public class Percolation {
	public Percolation(int n) {
		if (n<=0) {
			throw new IllegalArgumentException();
		}
	}

	public Percolation openWithProbability(float p) {
		if (p < 0 || p > 1) {
			throw new IllegalArgumentException();
		}
		return this;
	}

	public boolean doesPercolate() {
		return true;
	}
}
