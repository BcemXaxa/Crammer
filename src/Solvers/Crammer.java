package Solvers;

import Units.R;
import Units.Unit;

public class Crammer implements Solver {
	private final Unit[][] coefficients;
	private final Unit[] independent;
	private final Unit[] solutions;

	public Crammer(Unit[][] coefficients, Unit[] independent) throws Exception {
		this.coefficients = coefficients;
		this.independent = independent;
		this.solutions = solutions();
	}

	@Override
	public Unit[] getSolutions() {
		return solutions;
	}

	private Unit[] solutions() throws Exception {
		int length = independent.length;
		Unit[] solutions = new Unit[length];

		Unit primaryDeterminant = determinant(coefficients);
		System.out.println(((R)primaryDeterminant).r);
		Unit secondaryDeterminant;

		for (int i = 0; i < length; i++) {
			secondaryDeterminant = determinant(rewriteColumn(coefficients, independent, i));
			System.out.println(((R)secondaryDeterminant).r);
			solutions[i] = secondaryDeterminant.divide(primaryDeterminant);
		}

		return solutions;
	}

	private Unit[][] rewriteColumn(Unit[][] initial, Unit[] insert, int column) {
		int length = initial.length;
		Unit[][] result = new Unit[length][length];
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

	private Unit determinant(Unit[][] coefficients) throws Exception {
		int length = coefficients.length;
		Unit result = coefficients[0][0].zeroInstance();
		if (length > 2) {
			for (int i = 0; i < length; i++) {
				Unit[][] temporary = buildTemporary(coefficients, 0, i);
				result = result.plus(coefficients[0][i].times(determinant(temporary)).times(Math.pow(-1, i)));
			}
		} else {
			result = coefficients[0][0].times(coefficients[1][1]).minus(coefficients[0][1].times(coefficients[1][0]));
		}
		return result;
	}

	private Unit[][] buildTemporary(Unit[][] initial, int banRow, int banCol) {
		int length = initial.length;
		Unit[][] temporary = new Unit[length - 1][length - 1];

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
