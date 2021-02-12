/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.percolation;

public class Percolation {
	private final RandomBool randomBool;
	private float p;
	private int n;
	private BoolMatrix boolMatrix;

	public Percolation(RandomBool randomBool) {
		if (randomBool == null) {
			throw new IllegalArgumentException();
		}
		this.randomBool = randomBool;
	}

	public Percolation initMatrix(int n) {
		if (n<=0) {
			throw new IllegalArgumentException();
		}
		this.n = n;
		this.boolMatrix = new BoolMatrix(n);
		return this;
	}

	public Percolation makeTrial(float p) {
		if (p < 0 || p > 1) {
			throw new IllegalArgumentException();
		}
		this.p = p;

		tryOpenCells();
		return this;
	}

	private void tryOpenCells() {
		boolMatrix.forEach(this::tryOpenCell);
	}

	private void tryOpenCell(Coord coord) {
		if (randomBool.next(p)) {
			return;
		}
		boolMatrix.open(coord);
		//jeżeli otwarty, to
		//ustaw że otwarty
		//pobierz jego sąsiadów
		//dodaj do listy odcinków odcinki od puntu do otwartych sąsiadów
	}

	public boolean doesPercolate() {
		return p != 0;
	}
}
