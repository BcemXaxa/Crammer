package solvers.extended;

import units.Unit;

public class Gauss implements Solver {
	private final Unit[][] coefficients;
	private final Unit[] independent;
	private final Unit[] solutions;

	public Gauss(Unit[][] coefficients, Unit[] independent) throws Exception {
		this.coefficients = coefficients;
		this.independent = independent;
		this.solutions = solutions();
	}

	@Override
	public Unit[] getSolutions() {
		return solutions;
	}

	private Unit[] solutions() throws Exception {
		int size = coefficients.length;

		for (int col = size - 1; col >= 0; col--) {
			arrangeColumn(col);
			for (int row = 0; row < col; row++) {
				nullify(row, col);
			}
		}

		for (int col = 0; col < size; col++) {
			divideRowBy(col, coefficients[col][col]);
			for (int row = col + 1; row < size; row++) {
				subtractRow(row, col, coefficients[row][col]);
			}
		}

		return independent.clone();
	}

	private void divideRowBy(int row, Unit coefficient) throws Exception {
		for (int i = 0; i < coefficients.length; i++) {
			coefficients[row][i] = coefficients[row][i].divide(coefficient);
		}
		independent[row] = independent[row].divide(coefficient);
	}

	private void multiplyRowBy(int row, Unit coefficient) throws Exception {
		for (int i = 0; i < coefficients.length; i++) {
			coefficients[row][i] = coefficients[row][i].times(coefficient);
		}
		independent[row] = independent[row].times(coefficient);
	}

	private void subtractRow(int to, int from, Unit c) throws Exception {
		for (int i = 0; i < coefficients.length; i++) {
			coefficients[to][i] = coefficients[to][i].minus(coefficients[from][i].times(c));
		}
		independent[to] = independent[to].minus(independent[from].times(c));
	}

	private void swapRows(int r1, int r2) {
		if (r1 == r2) {
			return;
		}

		Unit[] temp = coefficients[r1];
		coefficients[r1] = coefficients[r2];
		coefficients[r2] = temp;

		Unit tempVal = independent[r1];
		independent[r1] = independent[r2];
		independent[r2] = tempVal;
	}

	private void arrangeColumn(int col) {
		for (int i = col; i >= 0; i--) {
			if (!coefficients[i][col].equals(coefficients[0][0].zeroInstance())) {
				swapRows(col, i);
				return;
			}
		}
	}

	private void nullify(int row, int col) throws Exception {
		Unit thisCoefficient = coefficients[row][col];
		Unit otherCoefficient = coefficients[col][col];

		subtractRow(row, col, thisCoefficient.divide(otherCoefficient));
	}
}
