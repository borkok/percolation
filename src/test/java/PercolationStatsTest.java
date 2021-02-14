import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PercolationStatsTest {

	@Test
	public void stats() {
		PercolationStats testee = new PercolationStats(5, 30);
		double stddev = testee.stddev();
		assertThat(stddev).isGreaterThan(0d);
		double mean = testee.mean();
		assertThat(mean).isGreaterThan(0d);
		double actualLo = testee.confidenceLo();
		assertThat(actualLo).isGreaterThan(0d);
		double actualHi = testee.confidenceHi();
		assertThat(actualHi).isGreaterThan(0d);
	}

}