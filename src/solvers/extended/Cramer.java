package solvers.extended;

import solvers.unary.Determinant;

public class Cramer {
	private final double[] result;

	public double[] getResult() {
		return result;
	}

	public Cramer(double[][] matrixExtended) throws Exception {
		int rows = matrixExtended.length;
		int cols = matrixExtended[0].length;
		if (cols - rows != 1) {
			throw new Exception("Unable to solve system of this size");
		}

		result = new double[cols - 1];

		Determinant det = new Determinant(matrixExtended);

		for (int i = 0; i < cols - 1; i++) {
			Determinant secDet = new Determinant(rewriteColumn(matrixExtended, i));
			result[i] = secDet.getResult() / det.getResult();
		}
	}

	private double[][] rewriteColumn(double[][] initial, int column) {
		int rows = initial.length;
		int cols = initial[0].length;
		double[][] result = new double[rows][rows];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < rows; col++) {
				if (col != column) {
					result[row][col] = initial[row][col];
				} else {
					result[row][col] = initial[row][cols - 1];
				}
			}
		}
		return result;
	}
}
