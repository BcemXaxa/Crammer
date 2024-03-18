package solvers.unary;

public class Determinant {
	private final double result;

	public double getResult() {
		return result;
	}

	public enum Method {
		TRIANGLE,
		RECURSIVE,
		PERMUTATIONS
	}

	public Determinant(double[][] matrix) throws Exception {
		this(matrix, Method.RECURSIVE);
	}

	public Determinant(double[][] matrix, Method method) throws Exception {
		int rows = matrix.length;
		int cols = matrix[0].length;
		if (rows != cols) {
			throw new Exception("Unable to find determinant for non-square matrix");
		}

		switch (method) {
			case RECURSIVE -> {
				result = recursiveDeterminant(matrix, rows);
			}
			default -> {
				throw new Exception("This feature is not implemented yet");
			}
		}
	}

	private double recursiveDeterminant(double[][] matrix, int n) {
		DeterminantUtils detUtil = new DeterminantUtils(matrix);
		double result = 0;
		for (int j = 0; j < n; j++) {
			result+= matrix[0][j] * detUtil.algebraicComplement(0, j);
		}
		return result;
	}
}
