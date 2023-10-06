import java.util.Scanner;

public class Main {
	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.printf("%nHello, user!%n");
		System.out.printf("This is a program to solve your systems of linear equations!%n");

		System.out.printf("Enter coefficients and independent number for each line.%n%n" +
								   "Example:%n" +
								   "a11 a12 a13 ...  b1%n" +
								   "a21 a22 a23 ...  b2%n" +
								   "a31 a32 a33 ...  b3%n" +
								   "\t...%n%n");

		System.out.printf("Dimensions of \"matrix\": ");
		int n = scanner.nextInt();

		Z[][] coefficients = new Z[n][n];
		Z[] independent = new Z[n];

		System.out.printf("Your \"matrix\":%n");

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				coefficients[i][j] = new Z(scanner.nextDouble(), scanner.nextDouble());
			}

			independent[i] = new Z(scanner.nextDouble(), scanner.nextDouble());
		}

		Z[] solutions = solutions(coefficients, independent);

		System.out.printf("Solutions:%n");
		for (int i = 0; i < solutions.length; i++) {
			System.out.printf("X%s: %.3f + %.3fi; ",(i + 1), solutions[i].re, solutions[i].im);
		}
	}

	public static Z[] solutions(Z[][] coefficients, Z[] independent) {
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

	public static Z[][] rewriteColumn(Z[][] initial, Z[] insert, int column) {
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

	public static Z determinant(Z[][] coefficients) {
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

	public static Z[][] buildTemporary(Z[][] initial, int banRow, int banCol) {
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