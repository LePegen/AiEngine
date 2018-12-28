public class MathTools {

    public static double sigmoid(double number){
        return 1/(1+Math.pow(Math.E,-number));
    }

    static Matrix sigmoid(Matrix matrix){
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                matrix.setElement(i,j,MathTools.sigmoid(matrix.getElement(i,j)));
            }
        }
        return matrix;
    }

    public static double sigmoidPrime(double number)   {
        return sigmoid(number)*(1.0-sigmoid(number));
    }

    public static Matrix sigmoidPrime(Matrix z){
        Matrix x=sigmoid(z);

        for (int i = 0; i < z.getRows(); i++) {
            for (int j = 0; j < z.getColumns(); j++) {
                x.setElement(i,j, (1-x.getElement(i,j)));
            }
        }
        return MatrixTools.hadamardProduct(z,x);
    }

}
