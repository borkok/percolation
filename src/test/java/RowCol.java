class RowCol {
	int row;
	int col;

	static RowCol of(int row, int col) {
		return new RowCol(row,col);
	}

	RowCol(int row, int col) {
		this.row = row;
		this.col = col;
	}

}
