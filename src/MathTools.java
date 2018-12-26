public class MathTools {

    public static double sigmoid(double number){
        return 1.0/(1.0+Math.pow(Math.E,-1.0*number));
    }

    static Matrix sigmoid(Matrix matrix){
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                matrix.setElement(i,j,MathTools.sigmoid(matrix.getElement(i,j)));
            }
        }

        return matrix;

    }

    public static double sigmoidDerivative(double number)   {
        return sigmoid(number)*(1.0-sigmoid(number));
    }

    public static Matrix sigmoidDerivative(Matrix matrix){
        Matrix matrix1=new Matrix(matrix.getRows(),matrix.getColumns());
        for (int i = 0; i < matrix1.getRows(); i++) {
            for (int j = 0; j < matrix1.getColumns(); j++) {
                matrix1.setElement(i,j,1);
            }
        }
        Matrix value=MatrixTools.multiply(sigmoid(matrix),MatrixTools.subtract(matrix1,sigmoid(matrix)) );

        return value;
    }

}
