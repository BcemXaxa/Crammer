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

	static public InputMode mode = InputMode.NONE;
	public static Scanner scanner = new Scanner(System.in);


	static public void handleException(Exception exception) {
		System.out.printf("%sError:%s %s%n", RED, RESET, exception.getMessage());
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

	static public String askForFile()
	{
		System.out.printf("Enter the name of your file: ");
		return scanner.next();
	}

	static public void askForInputMethod() throws Exception {
		System.out.printf("%sChoose your input method:%s%n", CYAN, RESET);
		System.out.printf("[F]ile\t\t[C]onsole%n");
		char input = getChar();
		switch (input) {
			case 'F', 'f' -> {
				mode = InputMode.FILE;
			}
			case 'C', 'c' -> {
				mode = InputMode.CONSOLE;
			}
			default -> {
				mode = InputMode.NONE;
				throw new Exception("mode [" + input + "] doesn't exist");
			}
		}
	}

	static public void greetingMessage() {
		System.out.printf("%nHello, user!%n");
		System.out.printf("This is a program to solve your systems of linear equations!%n");
	}

	static public void guide() {
		System.out.printf("Enter coefficients and independent number for each line.%n%n" +
								  "Example:%n" +
								  "re11 im11   re12 im12   re13 im13  ...  re1 im1%n" +
								  "re21 im21   re22 im22   re23 im23  ...  re2 im2%n" +
								  "re31 im31   re32 im32   re33 im33  ...  re3 im3%n" +
								  "\t\t...%n%n");
	}

	static public void sayGoodbye() {
		System.out.printf("Thanks for using this program!%n");
	}

	static public Solver getInputs() throws Exception {
		int n;
		Z[][] coefficients;
		Z[] independent;

		if (mode == InputMode.CONSOLE) {
			System.out.printf("Dimensions of \"matrix\": ");
			n = getInt();

			coefficients = new Z[n][n];
			independent = new Z[n];

			System.out.printf("Your \"matrix\":%n");

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					coefficients[i][j] = new Z(getDouble(), getDouble());
				}
				independent[i] = new Z(getDouble(), getDouble());
			}
		} else if (mode == InputMode.FILE) {
			String file = askForFile();
			Path path = Path.of("src", file).toAbsolutePath();
			System.out.printf("Chosen path: %s%n", path);

			if (!Files.exists(path)) {
				throw new Exception("file \"" + file + "\" doesn't exist");
			}

			InputStream is = Files.newInputStream(path);
			Scanner fileScanner = new Scanner(is);

			n = getInt(fileScanner);

			coefficients = new Z[n][n];
			independent = new Z[n];

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					coefficients[i][j] = new Z(getDouble(fileScanner), getDouble(fileScanner));
				}
				independent[i] = new Z(getDouble(fileScanner), getDouble(fileScanner));
			}
		} else {
			throw new Exception("input mode hadn't been chosen");
		}

		return new Solver(coefficients, independent);
	}

	static public void printSolutions(Z[] solutions) {
		System.out.printf("Solutions:%n");
		for (int i = 0; i < solutions.length; i++) {
			System.out.printf("X%s: %.3f + %.3fi%n", (i + 1), solutions[i].re, solutions[i].im);
		}
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
