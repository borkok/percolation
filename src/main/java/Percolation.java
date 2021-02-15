import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class Percolation {
	private static final int FAKES_COUNT = 2;

	private final boolean[][] matrix;
	private final int dimension;
	private final WeightedQuickUnionUF weightedQuickUnionUF;
	private int openCellsCount = 0;
	private final int fakeTop;
	private final int fakeBottom;
	private final int cellsCount;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		requireAtLeastOne(n);
		dimension = n;
		cellsCount = n*n;
		matrix = new boolean[n][n];
		fakeTop = 0;
		fakeBottom = getPointCount()-1;

		this.weightedQuickUnionUF = new WeightedQuickUnionUF(getPointCount());
	}

	private int getPointCount() {
		return cellsCount + FAKES_COUNT;
	}

	private void requireAtLeastOne(int n) {
		if (n < 1) {
			throw new IllegalArgumentException();
		}
	}

	//1-based
	private void open(int cell) {
		open(findRow(cell), findCol(cell));
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		if(col == Integer.MAX_VALUE) {
			open(row);
			return;
		}
		validate(row, col);
		if(isOpen(row,col)) {
			return;
		}
		matrix[row-1][col-1] = true;
		openCellsCount++;

		int openCell = convertToOneDimension(row, col);
		findOpenNeighbours(openCell).forEach(n -> weightedQuickUnionUF.union(openCell, n));
	}

	private List<Integer> findOpenNeighbours(int cell) {
		return findMyNeighbours(cell).stream()
				.filter(this::isOpen)
				.collect(Collectors.toList());
	}

	private boolean isOpen(Integer n) {
		if(n == fakeTop || n == fakeBottom) {
			return true;
		}
		return isOpen(findRow(n), findCol(n));
	}

	//1-based row
	private int findRow(int coord) {
		return (coord-1) / dimension +1;
	}

	//1-based col
	private int findCol(int coord) {
		return (coord-1) % dimension +1;
	}

	private List<Integer> findMyNeighbours(int cell) {
		List<Integer> neighbours = new ArrayList<>(List.of(findTopNeighbour(cell), findBottomNeighbour(cell)));
		findLeftNeighbour(cell).ifPresent(neighbours::add);
		findRightNeighbour(cell).ifPresent(neighbours::add);
		return neighbours;
	}

	private int findTopNeighbour(int coord) {
		int topCoord = coord - dimension;
		return Math.max(topCoord, fakeTop);
	}

	private OptionalInt findLeftNeighbour(int coord) {
		if (coord % dimension == 1 || coord == 1) {
			return OptionalInt.empty();
		}
		return OptionalInt.of(coord - 1);
	}

	private OptionalInt findRightNeighbour(int coord) {
		if (coord % dimension == 0) {
			return OptionalInt.empty();
		}
		return OptionalInt.of(coord + 1);
	}

	private Integer findBottomNeighbour(int coord) {
		int bottomCoord = coord + dimension;
		if (bottomCoord > dimension*dimension || dimension == 1) {
			return fakeBottom;
		}
		return bottomCoord;
	}

	private int convertToOneDimension(int row, int col) {
		return (row-1) * dimension + col;
	}

	private void validate(int row, int col) {
		requireAtLeastOne(row);
		requireAtLeastOne(col);

		requireAtMostDimension(row);
		requireAtMostDimension(col);
	}

	private void requireAtMostDimension(int n) {
		if (n > dimension) {
			throw new IllegalArgumentException();
		}
	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		validate(row, col);
		return matrix[row-1][col-1];
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col) {
		validate(row, col);
		return !matrix[row-1][col-1];
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return openCellsCount;
	}

	// does the system percolate?
	public boolean percolates() {
		return weightedQuickUnionUF.find(fakeTop) == weightedQuickUnionUF.find(fakeBottom);
	}
}
