import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
		Percolation percolation = new Percolation(10);
		List<RowCol> directions = List.of(
				RowCol.of(10,2),
				RowCol.of(2,10),
				RowCol.of(6,8),
				RowCol.of(2,6),
				RowCol.of(1,4),
				RowCol.of(8,4),
				RowCol.of(10,1),
				RowCol.of(4,2),
				RowCol.of(4,8),
				RowCol.of(9,3),
				RowCol.of(2,2),
				RowCol.of(9,1),
				RowCol.of(4,3),
				RowCol.of(5,5),
				RowCol.of(5,7),
				RowCol.of(2,8),
				RowCol.of(6,4),
				RowCol.of(7,5),
				RowCol.of(9,6),
				RowCol.of(3,7),
				RowCol.of(4,7),
				RowCol.of(7,1),
				RowCol.of(9,4),
				RowCol.of(3,10),
				RowCol.of(1,10),
				RowCol.of(10,10),
				RowCol.of(9,7),
				RowCol.of(1,5),
				RowCol.of(9,8),
				RowCol.of(6,1),
				RowCol.of(2,5),
				RowCol.of(3,4),
				RowCol.of(6,9),
				RowCol.of(5,8),
				RowCol.of(3,2),
				RowCol.of(4,6),
				RowCol.of(1,7),
				RowCol.of(7,9),
				RowCol.of(3,9),
				RowCol.of(4,4),
				RowCol.of(4,10),
				RowCol.of(3,5),
				RowCol.of(3,8),
				RowCol.of(1,8),
				RowCol.of(3,1),
				RowCol.of(6,7),
				RowCol.of(2,3),
				RowCol.of(7,4),
				RowCol.of(9,10),
				RowCol.of(7,6),
				RowCol.of(5,2),
				RowCol.of(8,3),
				RowCol.of(10,8),
				RowCol.of(7,10),
				RowCol.of(4,5),
				RowCol.of(8,10)
		);
		open(percolation, directions);
		assertThat(percolation.isFull(9, 1)).isFalse();
		assertThat(percolation.percolates()).isTrue();
	}

	private void open(Percolation percolation, List<RowCol> points) {
		points.forEach(s -> percolation.open(s.row, s.col));
	}

}