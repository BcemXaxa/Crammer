import interfaces.UserInterface;
import solvers.extended.Solver;

import java.util.Locale;

public class Main {
	public static void main(String[] args) {
		Locale.setDefault(Locale.ROOT);

		UserInterface.greetingMessage();
		UserInterface.guide();

		while (true) {
			try {
			UserInterface.askForInputMethod();
			Solver solver = UserInterface.getInputs();
			UserInterface.printSolutions(solver.getSolutions());

			} catch (Exception exception) {
				if (exception.getMessage().equals("should exit")) {
					break;
				} else {
					UserInterface.handleException(exception);
				}
			}
		}

		UserInterface.sayGoodbye();
	}
}