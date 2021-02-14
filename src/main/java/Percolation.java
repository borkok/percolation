import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Percolation {

	private final boolean[][] matrix;
	private final int dimension;
	private int openCellsCount = 0;
	private final Graph graph;
	private final int fakeTop = 0;
	private final int fakeBottom;
	private final int cellsCount;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		requireAtLeastOne(n);
		dimension = n;
		cellsCount = n*n;
		matrix = new boolean[n][n];
		graph = new Graph(getPointCount(n));
		fakeBottom = getPointCount(n)-1;
	}

	private int getPointCount(int n) {
		return cellsCount + 2;
	}

	private void requireAtLeastOne(int n) {
		if (n < 1) {
			throw new IllegalArgumentException();
		}
	}

	//1-based
	public void open(int cell) {
		open(findRow(cell), findCol(cell));
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
		findOpenNeighbours(openCell).forEach(n -> graph.union(openCell, n));
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

	public List<Integer> findMyNeighbours(int cell) {
		List<Integer> neighbours = new ArrayList<>(List.of(findTopNeighbour(cell), findBottomNeighbour(cell)));
		findLeftNeighbour(cell).ifPresent(neighbours::add);
		findRightNeighbour(cell).ifPresent(neighbours::add);
		return neighbours;
	}

	private int findTopNeighbour(int coord) {
		int topCoord = coord - dimension;
		return Math.max(topCoord, fakeTop);
	}

	private Optional<Integer> findLeftNeighbour(int coord) {
		if (coord % dimension == 1 || coord == 1) {
			return Optional.empty();
		}
		return Optional.of(coord - 1);
	}

	private Optional<Integer> findRightNeighbour(int coord) {
		if (coord % dimension == 0) {
			return Optional.empty();
		}
		return Optional.of(coord + 1);
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
		return graph.hasPathFor(0, getPointCount(dimension)-1);
	}

	public double fractionOfOpenedCells() {
		return 1.0d * openCellsCount / cellsCount;
	}


	////////////// INNER CLASSES //////////////////////
	private static class Segments {
		private final int[][] input;

		public Segments(int[][] input) {
			this.input = input;
		}

		public void forEach(BiConsumer<Integer, Integer> consumer) {
			for (int[] ints : input) {
				consumer.accept(ints[0], ints[1]);
			}
		}
	}

	private static class Forest {
		private int[] forest;
		private int[] treeSize;

		public Forest(int pointCount) {
			initializeForest(pointCount);
		}

		private void initializeForest(int pointCount) {
			forest = new int[pointCount];
			treeSize = new int[pointCount];
			for (int i = 0; i < pointCount; i++) {
				forest[i] = i;
				treeSize[i] = 1;
			}
		}

		public int findRootFor(int point) {
			int coord = point;
			while(this.forest[coord] != coord) {
				coord = this.forest[coord];
			}
			return coord;
		}

		public int findTreeSize(int root) {
			return treeSize[root];
		}

		public void putSourceUnderDestination(int source, int destination) {
			forest[source] = destination;
			treeSize[destination] = treeSize[source] + treeSize[destination];
		}

		public int getSmallerTree(int rootX, int rootY) {
			if (findTreeSize(rootX) <= findTreeSize(rootY)) {
				return rootX;
			}
			return rootY;
		}

		public int getLargerTree(int rootX, int rootY) {
			if (findTreeSize(rootX) <= findTreeSize(rootY)) {
				return rootY;
			}
			return rootX;
		}
	}

	private static class Graph {
		protected final Forest forest;

		protected Graph(int pointCount) {
			forest = new Forest(pointCount);
		}

		protected void union(Integer x, Integer y) {
			int rootX = forest.findRootFor(x);
			int rootY = forest.findRootFor(y);

			forest.putSourceUnderDestination(
							forest.getSmallerTree(rootX, rootY),
							forest.getLargerTree(rootX, rootY)
			);
		}

		public boolean hasPathFor(int start, int end) {
			return forest.findRootFor(start) == forest.findRootFor(end);
		}
	}
}
