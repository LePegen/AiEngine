import java.util.ArrayList;

public class Matrix {
    private ArrayList<ArrayList<Double>> matrix;

    public Matrix(int rows,int columns) {
        matrix =new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            ArrayList<Double> currentRow=new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                currentRow.add(0.0);
            }
            matrix.add(currentRow);
        }
    }

    public Matrix(Matrix input) {
        matrix =new ArrayList<>();
        for (int i = 0; i < input.getRows(); i++) {
            ArrayList<Double> currentRow=new ArrayList<>();
            for (int j = 0; j < input.getColumns(); j++) {
                currentRow.add(input.getElement(i,j));
            }
            matrix.add(currentRow);
        }
    }


    public double getElement(int row, int column){
        return  matrix.get(row).get(column);
    }

    public void setElement(int x, int y,double value){
        this.matrix.get(x).set(y,value);
    }

    public int getRows(){
        return matrix.size();
    }

    public int getColumns(){
        return matrix.get(0).size();
    }
}
