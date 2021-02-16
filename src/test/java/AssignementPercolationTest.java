import org.example.common.Directions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.common.Directions.of;

class AssignementPercolationTest {

	@Test
	void percolation_exceptions() {
		assertThatThrownBy(() -> new Percolation(-1)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> new Percolation(0)).isInstanceOf(IllegalArgumentException.class);

		Percolation percolation = new Percolation(1);

		assertThatThrownBy(() -> percolation.isOpen(0, 0)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> percolation.isOpen(0, -1)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> percolation.isOpen(1, 2)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> percolation.isFull(0, 0)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> percolation.isFull(0, -1)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> percolation.isFull(1, 2)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> percolation.open(0, 0)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> percolation.open(-1, 0)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> percolation.open(1, 2)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void open_1x1() {
		Percolation percolation = new Percolation(1);
		assertThat(percolation.numberOfOpenSites()).isEqualTo(0);
		assertThat(percolation.isOpen(1,1)).isFalse();
		assertThat(percolation.isFull(1,1)).isFalse();
		assertThat(percolation.percolates()).isFalse();

		percolation.open(1,1);
		assertThat(percolation.numberOfOpenSites()).isEqualTo(1);
		assertThat(percolation.isOpen(1,1)).isTrue();
		assertThat(percolation.isFull(1,1)).isTrue();
		assertThat(percolation.percolates()).isTrue();
	}

	@Test
	void open_2x2() {
		Percolation percolation = new Percolation(2);
		assertThat(percolation.numberOfOpenSites()).isEqualTo(0);
		assertThat(percolation.percolates()).isFalse();

		assertThat(percolation.isOpen(1, 1)).isFalse();
		assertThat(percolation.isOpen(1, 2)).isFalse();
		assertThat(percolation.isOpen(2, 1)).isFalse();
		assertThat(percolation.isOpen(2, 2)).isFalse();

		assertThat(percolation.isFull(1, 1)).isFalse();
		assertThat(percolation.isFull(1, 2)).isFalse();
		assertThat(percolation.isFull(2, 1)).isFalse();
		assertThat(percolation.isFull(2, 2)).isFalse();

		percolation.open(1, 2);
		assertThat(percolation.numberOfOpenSites()).isEqualTo(1);
		assertThat(percolation.percolates()).isFalse();

		assertThat(percolation.isOpen(1, 2)).isTrue();
		assertThat(percolation.isFull(1, 2)).isTrue();

		assertThat(percolation.isOpen(1, 1)).isFalse();
		assertThat(percolation.isOpen(2, 1)).isFalse();
		assertThat(percolation.isOpen(2, 2)).isFalse();

		assertThat(percolation.isFull(1, 1)).isFalse();
		assertThat(percolation.isFull(2, 1)).isFalse();
		assertThat(percolation.isFull(2, 2)).isFalse();
	}

	@Test
	void percolates_2x2() {
		Percolation percolationForTwo = new Percolation(2);
		percolationForTwo.open(1,1);
		percolationForTwo.open(2,1);
		assertThat(percolationForTwo.percolates()).isTrue();
	}

	@Test
	void percolates_6x6() {
		Percolation percolation = new Percolation(6);
		List<Directions> directions = List.of(
				of(	1	,	6	),
				of(	2	,	6	),
				of(	3	,	6	),
				of(	4	,	6	),
				of(	5	,	6	),
				of(	5	,	5	),
				of(	4	,	4	),
				of(	3	,	4	),
				of(	2	,	4	),
				of(	2	,	3	),
				of(	2	,	2	),
				of(	2	,	1	),
				of(	3	,	1	),
				of(	4	,	1	),
				of(	5	,	1	),
				of(	5	,	2	),
				of(	6	,	2	),
				of(	5	,	4	)
		);
		open(percolation, directions);
		assertThat(percolation.percolates()).isFalse();
		assertThat(percolation.isFull(1, 6)).isTrue();
	}

	private void open(Percolation percolation, List<Directions> directions) {
		directions.forEach(s -> percolation.open(s.source(), s.destination()));
	}
}