package interfaces;

import solvers.binary.BinarySolutions;
import solvers.binary.Multiply;
import solvers.extended.Cramer;
import solvers.extended.ExtendedSolutions;
import solvers.unary.Determinant;
import solvers.unary.Inverse;
import solvers.unary.Transpose;
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
		this.setTitle("Matrix calculator");
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
		buttonMatrixSolve.addActionListener(event -> {
			UnarySolutions solution = (UnarySolutions) comboBoxMatrix.getSelectedItem();
			try {
				double[][] matrix = JTableUtils.readDoubleMatrixFromJTable(tableMatrixInput);
				assert matrix != null : "Matrix must be non-null";
				switch (solution) {
					case DETERMINANT -> {
						Determinant det = new Determinant(matrix);
						double result = det.getResult();
						SwingUtils.showInfoMessageBox("Det: " + result);
					}
					case TRANSPOSE -> {
						Transpose transpose = new Transpose(matrix);
						double[][] result = transpose.getResult();
						JTableUtils.writeArrayToJTable(tableMatrixOutput, result);
					}
					case INVERSE -> {
						Inverse inverse = new Inverse(matrix);
						double[][] result = inverse.getResult();
						JTableUtils.writeArrayToJTable(tableMatrixOutput, result);
					}
					default -> {
						SwingUtils.showInfoMessageBox("This feature is not supported");
					}
				}
			} catch (Exception exception) {
				SwingUtils.showInfoMessageBox(exception.getMessage());
			}
		});
	}


	private JPanel panelSoLE;
	private JTable tableSoLEInput;
	private JTable tableSoLEOutput;
	private JComboBox<ExtendedSolutions> comboBoxSoLE;
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
		comboBoxSoLE.setModel(new DefaultComboBoxModel<>(ExtendedSolutions.values()));
		buttonSoLESolve.addActionListener(event -> {
			ExtendedSolutions solution = (ExtendedSolutions) comboBoxSoLE.getSelectedItem();
			try {
				double[][] matrixExtended = JTableUtils.readDoubleMatrixFromJTable(tableSoLEInput);
				assert matrixExtended != null : "Matrix must be non-null";
				switch (solution) {
					case CRAMER -> {
						Cramer cramer = new Cramer(matrixExtended);
						double[][] result = new double[1][];
						result[0] = cramer.getResult();
						JTableUtils.writeArrayToJTable(tableMultiOutput, result);
					}
					default -> {
						SwingUtils.showInfoMessageBox("This feature is not supported");
					}
				}
			} catch (Exception exception) {
				SwingUtils.showInfoMessageBox(exception.getMessage());
			}
		});
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

	private void initPanelMulti() {
		initTable(tableMultiInput1);
		initTable(tableMultiInput2);
		initTable(tableMultiOutput);
		initButton(buttonMultiReadInput1, tableMultiInput1, true);
		initButton(buttonMultiWriteInput1, tableMultiInput1, false);
		initButton(buttonMultiReadInput2, tableMultiInput2, true);
		initButton(buttonMultiWriteInput2, tableMultiInput2, false);
		initButton(buttonMultiWriteOutput, tableMultiOutput, false);
		comboBoxMulti.setModel(new DefaultComboBoxModel<>(BinarySolutions.values()));
		buttonMultiSolve.addActionListener(event -> {
			BinarySolutions solution = (BinarySolutions) comboBoxMulti.getSelectedItem();
			try {
				double[][] matrix1 = JTableUtils.readDoubleMatrixFromJTable(tableMultiInput1);
				double[][] matrix2 = JTableUtils.readDoubleMatrixFromJTable(tableMultiInput2);
				assert matrix1 != null : "Matrix must be non-null";
				assert matrix2 != null : "Matrix must be non-null";
				switch (solution) {
					case MULTIPLY -> {
						Multiply multiply = new Multiply(matrix1, matrix2);
						double[][] result = multiply.getResult();
						JTableUtils.writeArrayToJTable(tableMultiOutput, result);
					}
					default -> {
						SwingUtils.showInfoMessageBox("This feature is not supported");
					}
				}
			} catch (Exception exception) {
				SwingUtils.showInfoMessageBox(exception.getMessage());
			}
		});
	}

	private void initTable(JTable table) {
		JTableUtils.initJTableForArray(table, 40, true, true, true, true);
		table.setRowHeight(25);
	}

	private void initButton(JButton button, JTable table, boolean read) {
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
