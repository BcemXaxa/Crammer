import Solvers.Crammer;
import Solvers.Gauss;
import Solvers.Solver;
import Units.R;
import Units.Unit;
import Units.Z;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class UserInterface {
	private enum InputMode {
		FILE,
		CONSOLE,
		NONE
	}

	private enum InputType {
		REAL,
		COMPLEX
	}

	static private InputMode mode = InputMode.NONE;
	public static Scanner scanner = new Scanner(System.in);


	static public void handleException(Exception exception) {
		System.out.printf("%sError:%s %s%n%n", RED, RESET, exception.getMessage());
		scanner = new Scanner(System.in);
	}

	static public void checkExit() throws Exception {
		if (scanner.hasNext("exit")) {
			throw new Exception("should exit");
		}
	}

	static public int getInt() throws Exception {
		checkExit();
		if (scanner.hasNextInt()) {
			return scanner.nextInt();
		} else {
			throw new Exception("not an integer");
		}
	}

	static public int getInt(Scanner scanner) throws Exception {
		if (scanner.hasNextInt()) {
			return scanner.nextInt();
		} else {
			throw new Exception("not an integer");
		}
	}

	static public double getDouble() throws Exception {
		checkExit();
		if (scanner.hasNextDouble()) {
			return scanner.nextDouble();
		} else {
			throw new Exception("not a double");
		}
	}

	static public double getDouble(Scanner scanner) throws Exception {
		if (scanner.hasNextDouble()) {
			return scanner.nextDouble();
		} else {
			throw new Exception("not a double");
		}
	}

	static public char getChar() throws Exception {
		checkExit();
		String input = scanner.next();
		if (input.length() == 1) {
			return input.charAt(0);
		} else {
			throw new Exception("incorrect input");
		}
	}

	static public String askForFile() {
		System.out.printf("Enter the name of your file: ");
		return scanner.next();
	}

	static public void askForInputMethod() throws Exception {
		System.out.printf("%sChoose your input method:%s%n", CYAN, RESET);
		System.out.printf("[F]ile\t\t[C]onsole%n");
		char input = getChar();
		switch (input) {
			case 'F', 'f' -> mode = InputMode.FILE;
			case 'C', 'c' -> mode = InputMode.CONSOLE;
			default -> {
				mode = InputMode.NONE;
				throw new Exception("mode [" + input + "] doesn't exist");
			}
		}
		System.out.println();
	}

	static private InputType askForInputType() throws Exception {
		System.out.printf("%sChoose your input type:%s%n", CYAN, RESET);
		System.out.printf("[R]eal\t\t[C]omplex%n");
		char input = getChar();
		InputType type;
		switch (input) {
			case 'R', 'r' -> type = InputType.REAL;
			case 'C', 'c' -> type = InputType.COMPLEX;
			default -> throw new Exception("mode [" + input + "] doesn't exist");
		}
		System.out.println();
		return type;
	}

	static public void greetingMessage() {
		System.out.printf("%nHello, user!%n");
		System.out.printf("This is a program to solve your systems of linear equations!%n");
	}

	static public void guide() {
		System.out.printf("Enter coefficients and independent number for each line.%n%n" +
								  "%sExample for complex:%s%n" +
								  "re11 im11   re12 im12   re13 im13  ...  re1 im1%n" +
								  "re21 im21   re22 im22   re23 im23  ...  re2 im2%n" +
								  "re31 im31   re32 im32   re33 im33  ...  re3 im3%n" +
								  "\t\t...%n%n", YELLOW, RESET);
	}

	static public void sayGoodbye() {
		System.out.printf("Thanks for using this program!%n");
	}

	static public Solver getInputs() throws Exception {
		InputType type;
		int n;
		Scanner src;
		Unit[][] coefficients;
		Unit[] independent;

		if (mode == InputMode.CONSOLE) {
			type = askForInputType();

			System.out.printf("Dimensions of \"matrix\": ");
			n = getInt();

			System.out.printf("Your \"matrix\":%n");

			src = new Scanner(System.in);

		} else if (mode == InputMode.FILE) {
			String file = askForFile();
			Path path = Path.of("src/Tests", file).toAbsolutePath();
			System.out.printf("Chosen path: %s%n", path);

			if (!Files.exists(path)) {
				throw new Exception("file \"" + file + "\" doesn't exist");
			}
			System.out.println();

			InputStream is = Files.newInputStream(path);
			src = new Scanner(is);

			switch (src.nextLine().toUpperCase()) {
				case "#REAL" -> type = InputType.REAL;
				case "#COMPLEX" -> type = InputType.COMPLEX;
				default -> throw new Exception("invalid type specification");
			}

			n = getInt(src);

		} else {
			throw new Exception("input mode was not chosen");
		}

		switch (type) {
			case REAL -> {
				coefficients = new R[n][n];
				independent = new R[n];

				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						coefficients[i][j] = new R(getDouble(src));
					}
					independent[i] = new R(getDouble(src));
				}
			}
			case COMPLEX -> {
				coefficients = new Z[n][n];
				independent = new Z[n];

				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						coefficients[i][j] = new Z(getDouble(src), getDouble(src));
					}
					independent[i] = new Z(getDouble(src), getDouble(src));
				}
			}
			default -> throw new Exception("impossible");
		}

		return new Crammer(coefficients, independent);
	}

	static public void printSolutions(Unit[] solutions) {
		System.out.printf("%sSolutions:%s%n", GREEN, RESET);
		if (solutions[0] instanceof Z) {
			for (int i = 0; i < solutions.length; i++) {
				Z sol = (Z) solutions[i];
				System.out.printf("X%d: %.3f + %.3fi%n", i + 1, sol.re, sol.im);
			}
		} else if (solutions[0] instanceof R) {
			for (int i = 0; i < solutions.length; i++) {
				R sol = (R) solutions[i];
				System.out.printf("X%d: %f%n", i + 1, sol.r);
			}
		} else {
			System.out.println("#ERROR_TYPE");
		}
		System.out.println();
	}

	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String CYAN = "\u001B[36m";
	public static final String BLACK = "\u001B[30m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";
	public static final String WHITE = "\u001B[37m";
}
