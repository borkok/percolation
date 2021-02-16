import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private static final int FAKES_COUNT = 1;
	private static final int FAKE_TOP = 0;
	private static final int NOT_EXISTS = Integer.MAX_VALUE;

	private final boolean[][] matrix;
	private final int dimension;
	private final WeightedQuickUnionUF weightedQuickUnionUF;
	private int openCellsCount = 0;
	private final int cellsCount;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		requireAtLeastOne(n);
		dimension = n;
		cellsCount = n*n;
		matrix = new boolean[n][n];

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

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		validate(row, col);
		if(isOpen(row,col)) {
			return;
		}
		matrix[row-1][col-1] = true;
		openCellsCount++;

		int openCell = convertToOneDimension(row, col);
		Integer[] openNeighbours = findMyOpenNeighbours(openCell);
		for (Integer neighbour : openNeighbours) {
			union(openCell, neighbour);
		}
	}

	private void union(int openCell, Integer neighbour) {
		if(neighbour != null) {
			int a = Integer.min(openCell, neighbour);
			int b = Integer.max(openCell, neighbour);
			weightedQuickUnionUF.union(a, b);
		}
	}

	private boolean isOpen(Integer n) {
		if(n == FAKE_TOP) {
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

	private Integer[] findMyOpenNeighbours(int cell) {
		int topNeighbour = findTopNeighbour(cell);
		Integer bottomNeighbour = findBottomNeighbour(cell);
		Integer leftNeighbour = findLeftNeighbour(cell);
		Integer rightNeighbour = findRightNeighbour(cell);

		Integer[] neighbours = new Integer[4];
		if (isOpen(topNeighbour)) {
			neighbours[0] = topNeighbour;
		}
		if (bottomNeighbour != null && isOpen(bottomNeighbour)) {
			neighbours[1] = bottomNeighbour;
		}
		if (leftNeighbour != null && isOpen(leftNeighbour)) {
			neighbours[2] = leftNeighbour;
		}
		if (rightNeighbour != null && isOpen(rightNeighbour)) {
			neighbours[3] = rightNeighbour;
		}
		return neighbours;
	}

	private int findTopNeighbour(int coord) {
		int topCoord = coord - dimension;
		return Math.max(topCoord, FAKE_TOP);
	}

	private Integer findLeftNeighbour(int coord) {
		if (coord % dimension == 1 || coord == 1) {
			return null;
		}
		return coord - 1;
	}

	private Integer findRightNeighbour(int coord) {
		if (coord % dimension == 0) {
			return null;
		}
		return coord + 1;
	}

	private Integer findBottomNeighbour(int coord) {
		int bottomCoord = coord + dimension;
		if (bottomCoord > dimension*dimension || dimension == 1) {
			return null;
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
		if(!isOpen(row,col)) {
			return false;
		}
		return areConnected(convertToOneDimension(row,col), FAKE_TOP);
	}

	private boolean areConnected(int a, int b) {
		return weightedQuickUnionUF.find(a) == weightedQuickUnionUF.find(b);
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return openCellsCount;
	}

	// does the system percolate?
	public boolean percolates() {
		for (int i = 0; i < dimension; i++) {
			if(areConnected(FAKE_TOP, cellsCount-i)) {
				return true;
			}
		}
		return false;
	}
}
