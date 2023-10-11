public class Main {


	public static void main(String[] args) {

		UserInterface.greetingMessage();

		while (true) {
			try {
			UserInterface.askForInputMethod();
			Solver solver = UserInterface.getInputs();
			UserInterface.printSolutions(solver.solutions);

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