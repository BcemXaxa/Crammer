package interfaces;

import solvers.binary.BinarySolutions;
import solvers.extended.ExtendedSolutions;
import solvers.unary.UnarySolutions;
import utils.ArrayUtils;
import utils.JTableUtils;
import utils.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FormMain extends JFrame {
	private JPanel panelMain;
	private JTabbedPane tabbedPane;
	private JFileChooser fileChooserOpen;
	private JFileChooser fileChooserSave;

	private void initPanelMain() {
		this.setTitle("Task");
		this.setContentPane(panelMain);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		fileChooserOpen = new JFileChooser();
		fileChooserSave = new JFileChooser();

		File dir = new File("./Tests");
		fileChooserOpen.setCurrentDirectory(dir);
		fileChooserSave.setCurrentDirectory(dir);

		FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
		fileChooserOpen.addChoosableFileFilter(filter);
		fileChooserSave.addChoosableFileFilter(filter);
		fileChooserSave.setAcceptAllFileFilterUsed(false);
		fileChooserOpen.setAcceptAllFileFilterUsed(false);

		fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
		fileChooserSave.setApproveButtonText("Save");
	}

	private JPanel panelMatrix;
	private JTable tableMatrixInput;
	private JTable tableMatrixOutput;
	private JComboBox<UnarySolutions> comboBoxMatrix;
	private JButton buttonMatrixReadInput;
	private JButton buttonMatrixWriteInput;
	private JButton buttonMatrixWriteOutput;
	private JButton buttonMatrixSolve;

	private void initPanelMatrix() {
		initTable(tableMatrixInput);
		initTable(tableMatrixOutput);
		initButton(buttonMatrixReadInput, tableMatrixInput, true);
		initButton(buttonMatrixWriteInput, tableMatrixInput, false);
		initButton(buttonMatrixWriteOutput, tableMatrixOutput, false);
		comboBoxMatrix.setModel(new DefaultComboBoxModel<>(UnarySolutions.values()));
	}


	private JPanel panelSoLE;
	private JTable tableSoLEInput;
	private JTable tableSoLEOutput;
	private JComboBox<ExtendedSolutions> comboBoxSoLe;
	private JButton buttonSoLEReadInput;
	private JButton buttonSoLEWriteInput;
	private JButton buttonSoLEWriteOutput;
	private JButton buttonSoLESolve;

	private void initPanelSoLE() {
		initTable(tableSoLEInput);
		initTable(tableSoLEOutput);
		initButton(buttonSoLEReadInput, tableSoLEInput, true);
		initButton(buttonSoLEWriteInput, tableSoLEInput, false);
		initButton(buttonSoLEWriteOutput, tableSoLEOutput, false);
		comboBoxSoLe.setModel(new DefaultComboBoxModel<>(ExtendedSolutions.values()));
	}


	private JPanel panelMulti;
	private JTable tableMultiInput1;
	private JTable tableMultiInput2;
	private JTable tableMultiOutput;
	private JComboBox<BinarySolutions> comboBoxMulti;
	private JButton buttonMultiReadInput1;
	private JButton buttonMultiWriteInput1;
	private JButton buttonMultiReadInput2;
	private JButton buttonMultiWriteInput2;
	private JButton buttonMultiWriteOutput;
	private JButton buttonMultiSolve;

	private void initPanelMulti(){
		initTable(tableMultiInput1);
		initTable(tableMultiInput2);
		initTable(tableMultiOutput);
		initButton(buttonMultiReadInput1, tableMultiInput1,true);
		initButton(buttonMultiWriteInput1, tableMultiInput1,false);
		initButton(buttonMultiReadInput2, tableMultiInput2,true);
		initButton(buttonMultiWriteInput2, tableMultiInput2,false);
		initButton(buttonMultiWriteOutput, tableMultiOutput, false);
		comboBoxMulti.setModel(new DefaultComboBoxModel<>(BinarySolutions.values()));
	}

	private void initTable(JTable table) {
		JTableUtils.initJTableForArray(table, 40, true, true, true, true);
		table.setRowHeight(25);
	}

	private void initButton(JButton button, JTable table, boolean read){
		button.addActionListener(event -> {
			try {
				if (read) {
					if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
						File file = fileChooserOpen.getSelectedFile();
						double[][] matrix = ArrayUtils.readDoubleArray2FromFile(file.getPath());
						JTableUtils.writeArrayToJTable(table, matrix);
					}
				} else {
					if (fileChooserSave.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
						File file = fileChooserSave.getSelectedFile();
						double[][] matrix = JTableUtils.readDoubleMatrixFromJTable(table);
						ArrayUtils.writeArrayToFile(file.getPath(), matrix);
					}
				}
			} catch (Exception exception) {
				SwingUtils.showErrorMessageBox(exception);
			}
		});
	}

	public FormMain() {
		initPanelMatrix();
		initPanelSoLE();
		initPanelMulti();
		initPanelMain();

		this.pack();
	}
}
