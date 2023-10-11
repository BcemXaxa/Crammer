public class Solver {
	public final Z[][] coefficients;
	public final Z[] independent;
	public final Z[] solutions;
	public Solver(Z[][] coefficients, Z[] independent)
	{
		this.coefficients = coefficients;
		this.independent = independent;
		this.solutions = solutions();
	}
	private Z[] solutions() {
		int length = independent.length;
		Z[] solutions = new Z[length];

		Z primaryDeterminant = determinant(coefficients);
		Z secondaryDeterminant;

		for (int i = 0; i < length; i++) {
			secondaryDeterminant = determinant(rewriteColumn(coefficients, independent, i));
			solutions[i] = secondaryDeterminant.divide(primaryDeterminant);
		}

		return solutions;
	}

	private Z[][] rewriteColumn(Z[][] initial, Z[] insert, int column) {
		int length = initial.length;
		Z[][] result = new Z[length][length];
		for (int row = 0; row < length; row++) {
			for (int col = 0; col < length; col++) {
				if (col != column) {
					result[row][col] = initial[row][col];
				} else {
					result[row][col] = insert[row];
				}
			}
		}
		return result;
	}

	private Z determinant(Z[][] coefficients) {
		int length = coefficients.length;
		Z result = new Z(0, 0);
		if (length > 2) {
			for (int i = 0; i < length; i++) {
				Z[][] temporary = buildTemporary(coefficients, 0, i);
				result = result.plus(coefficients[0][i].times(determinant(temporary)).times(Math.pow(-1, 1 + i)));
			}
		} else {
			result = coefficients[0][0].times(coefficients[1][1]).minus(coefficients[0][1].times(coefficients[1][0]));
		}
		return result;
	}

	public Z[][] buildTemporary(Z[][] initial, int banRow, int banCol) {
		int length = initial.length;
		Z[][] temporary = new Z[length - 1][length - 1];

		int subRow = 0;
		for (int row = 0; row < length; row++) {
			if (row != banRow) {
				int subCol = 0;
				for (int col = 0; col < length; col++) {
					if (col != banCol) {
						temporary[subRow][subCol] = initial[row][col];
						subCol++;
					}
				}
				subRow++;
			}
		}
		return temporary;
	}
}
