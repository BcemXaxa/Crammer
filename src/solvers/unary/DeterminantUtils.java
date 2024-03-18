package solvers.unary;

import java.util.HashMap;
import java.util.Map;

class DeterminantUtils {
	private final double[][] matrix;
	private final Map<Long, Double> counted = new HashMap<>();

	public DeterminantUtils(double[][] matrix) {
		this.matrix = matrix;
	}

	public double algebraicComplement(int i, int j) {
		double result = minor(i, j);
		if ((i + j) % 2 == 0) {
			return result;
		} else {
			return -result;
		}
	}

	public double minor(int i, int j) {
		return recursiveDeterminant(mask(-1L, i, j), matrix.length - 1);
	}

	private double recursiveDeterminant(long mask, int n) {
		Double result = counted.get(mask);
		if (result == null) {
			if (n <= 3) {
				result = smallDeterminant(matrix, mask, n);
			} else {
				result = 0d;
				int sign = 1;
				int[] rows = readRow(mask, n);
				int[] cols = readRow(mask >> 32, n);
				for (int col = 0; col < n; col++) {
					long newMask = mask(mask, rows[0], cols[col]);
					result += matrix[rows[0]][cols[col]] * sign *
							recursiveDeterminant(newMask, n - 1);
					sign *= -1;
				}
			}
			counted.put(mask, result);
		}
		return result;
	}

	private long mask(long mask, int row, int col) {
		return mask & ~((1L << col + 32) | (1L << row));
	}

	private double smallDeterminant(double[][] matrix, long mask, int n) {
		switch (n) {
			case 1 -> {
				return matrix[0][0];
			}
			case 2 -> {
				return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
			}
			case 3 -> {
				int[] rows = readRow(mask, n);
				int[] cols = readRow(mask >> 32, n);
				return matrix[rows[0]][cols[0]] * matrix[rows[1]][cols[1]] * matrix[rows[2]][cols[2]] +
						matrix[rows[1]][cols[0]] * matrix[rows[2]][cols[1]] * matrix[rows[0]][cols[2]] +
						matrix[rows[0]][cols[1]] * matrix[rows[1]][cols[2]] * matrix[rows[2]][cols[0]] -

						(matrix[rows[0]][cols[2]] * matrix[rows[1]][cols[1]] * matrix[rows[2]][cols[0]] +
								matrix[rows[1]][cols[0]] * matrix[rows[0]][cols[1]] * matrix[rows[2]][cols[2]] +
								matrix[rows[1]][cols[2]] * matrix[rows[2]][cols[1]] * matrix[rows[0]][cols[0]]);
			}
			default -> {
				return 0;
			}
		}
	}

	private int[] readRow(long mask, int n) {
		int[] row = new int[n];
		int k = 0;
		for (int i = 0; k < n && i < 32; i++) {
			if ((mask & 1L) == 1L) {
				row[k++] = i;
			}
			mask >>= 1;
		}
		return row;
	}
}
