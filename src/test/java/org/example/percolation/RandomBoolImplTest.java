package org.example.percolation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomBoolImplTest {

	@Test
	void next() {
		assertFalse(new RandomBoolImpl().next(0).booleanValue());
		assertTrue(new RandomBoolImpl().next(1).booleanValue());
	}
}