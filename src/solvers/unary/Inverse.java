package solvers.unary;

public class Inverse {
	private double[][] result;

	public double[][] getResult() {
		return result;
	}

	public Inverse(double[][] matrix) throws Exception {
		int rows = matrix.length;
		int cols = matrix[0].length;
		if (rows != cols) {
			throw new Exception("Unable to inverse for non-square matrix");
		}
		double det = new Determinant(matrix).getResult();
		if (det == 0) {
			throw new Exception("Determinant of this matrix is 0, unable to inverse");
		}

		result = new double[rows][cols];
		matrix = new Transpose(matrix).getResult();

		DeterminantUtils detUtils = new DeterminantUtils(matrix);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				result[row][col] = detUtils.algebraicComplement(row, col) / det;
			}
		}
	}
}
