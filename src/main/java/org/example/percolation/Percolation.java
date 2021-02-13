/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.percolation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Percolation {
	private final RandomBool randomBool;
	private float probability;
	private int matrixDimension;
	private BoolMatrix boolMatrix;
	private Segments segments;
	private Map<UUID, Boolean> trials = new HashMap<>();

	public Percolation(RandomBool randomBool) {
		if (randomBool == null) {
			throw new IllegalArgumentException();
		}
		this.randomBool = randomBool;
	}

	public Percolation reset(int matrixDimension) {
		if (matrixDimension <= 0) {
			throw new IllegalArgumentException();
		}
		this.matrixDimension = matrixDimension;
		this.boolMatrix = new BoolMatrix(matrixDimension);
		this.trials = new HashMap<>();
		return this;
	}

	public Percolation makeTrial(float p) {
		if (p < 0 || p > 1) {
			throw new IllegalArgumentException();
		}
		this.probability = p;
		this.segments = new Segments();

		tryOpenCells();
		trials.put(generateTrialId(), existsPathFromTopToBottom());

		return this;
	}

	private UUID generateTrialId() {
		return UUID.randomUUID();
	}

	private boolean existsPathFromTopToBottom() {
		Graph graph = new Graph(segments, countPointsPlusFakes());
		return graph.existsPathFor(Segment.of(Coord.FAKE_TOP, Coord.FAKE_BOTTOM));
	}

	public Map<UUID, Boolean> trialsResult() {
		return trials;
	}

	private int countPointsPlusFakes() {
		return matrixDimension * matrixDimension + 2;
	}

	private void tryOpenCells() {
		boolMatrix.forEach(this::tryOpenCell);
	}

	private void tryOpenCell(Coord coord) {
		if (!randomBool.next(probability)) {
			return;
		}
		boolMatrix.open(coord);
		boolMatrix.findOpenNeighbours(coord)
				.forEach(openNeighbour -> segments.add(coord, openNeighbour));
	}
}
