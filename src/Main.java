
import interfaces.FormMain;

import java.util.Locale;

public class Main {
	public static void main(String[] args) {
		Locale.setDefault(Locale.ROOT);

		java.awt.EventQueue.invokeLater(() -> new FormMain().setVisible(true));
	}
}