/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package org.example.percolation;

import java.util.Random;

public class RandomBoolImpl implements RandomBool {
	@Override
	public Boolean next(float p) {
		return new Random().nextInt(100) < p * 100;
	}
}
