package solvers.binary;

public class Multiply {
	private final double[][] result;

	public double[][] getResult() {
		return result;
	}

	public Multiply(double[][] matrix1, double[][] matrix2) throws Exception {
		if (matrix1[0].length != matrix2.length) {
			throw new Exception("Matrices do not match for multiplication");
		}
		int common = matrix2.length;
		int rows = matrix1.length;
		int cols = matrix2[0].length;

		result = new double[rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				double sum = 0;
				for (int n = 0; n < common; n++) {
					sum += matrix1[row][n] * matrix2[n][col];
				}
				result[row][col] = sum;
			}
		}
	}
}
