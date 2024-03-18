package solvers.unary;

public class Transpose {
	private final double[][] result;

	public double[][] getResult() {
		return result;
	}

	public Transpose(double[][] matrix){
		int rows = matrix.length;
		int cols = matrix[0].length;
		result = new double[cols][rows];
		for(int row = 0; row<rows; row++){
			for(int col = 0; col<cols; col++){
				result[col][row] = matrix[row][col];
			}
		}
	}
}
