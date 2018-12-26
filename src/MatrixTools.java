public class MatrixTools {


    //add matrices
    static Matrix add(Matrix matrix1,Matrix matrix2){
        int rows=matrix1.getRows();
        int columns=matrix1.getColumns();
        Matrix matrix=new Matrix(rows,columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double value=matrix1.getElement(i,j)+matrix2.getElement(i,j);
                matrix.setElement(i,j,value);
            }
        }

        return matrix;
    }

    static Matrix subtract(Matrix matrix1,Matrix matrix2){
        if(matrix1.getRows()!=matrix2.getRows()){
            System.out.println("WARNING ROWS ARE UNEVEN");
        }
        if(matrix1.getColumns()!=matrix2.getColumns()){
            System.out.println("WARNING ROWS ARE UNEVEN");
        }

        int rows=matrix1.getRows();
        int columns=matrix1.getColumns();
        Matrix matrix=new Matrix(rows,columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double value=matrix1.getElement(i,j)-matrix2.getElement(i,j);
                matrix.setElement(i,j,value);
            }
        }

        return matrix;
    }

    static Matrix subtractPow(Matrix matrix1,Matrix matrix2,int pow){


        int rows=matrix1.getRows();
        int columns=matrix1.getColumns();
        Matrix matrix=new Matrix(rows,columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double value=Math.pow(matrix1.getElement(i,j)-matrix2.getElement(i,j),pow);
                matrix.setElement(i,j,value);
            }
        }

        return matrix;
    }

    static Matrix hadamardProduct(Matrix matrix1,Matrix matrix2){
        int rows=matrix1.getRows();
        int columns=matrix1.getColumns();
        Matrix matrix=new Matrix(rows,columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double value=matrix1.getElement(i,j)*matrix2.getElement(i,j);
                matrix.setElement(i,j,value);
            }
        }

        return matrix;
    }

    static Matrix transpose(Matrix matrix){
        int rows=matrix.getRows();
        int columns=matrix.getColumns();
        Matrix newMatrix=new Matrix(columns,rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <columns ; j++) {
                newMatrix.setElement(j,i,matrix.getElement(i,j));
            }
        }
        return newMatrix;
    }

    static Matrix multiply(Matrix matrix1,Matrix matrix2){
        int rows=matrix1.getRows();
        int columns=matrix2.getColumns();
        Matrix matrix=new Matrix(rows,columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double currentSum=0;
                for (int k = 0; k < matrix1.getColumns(); k++) {
                    currentSum+=matrix1.getElement(i,k)*matrix2.getElement(k,j);
                }
                matrix.setElement(i,j,currentSum);
            }
        }

        return matrix;
    }



    static double magnitude(Matrix matrix){
        double sqTotal=0;
        for (int i = 0; i < matrix.getRows(); i++) {
            sqTotal=sqTotal+Math.pow(matrix.getElement(i,0),2.0);
        }

        return Math.sqrt(sqTotal);
    }

    static Matrix scalarMultiplication(Matrix matrix,double scalar){
        //make consistent
        int rows=matrix.getRows();
        int columns=matrix.getColumns();
        Matrix newMatrix=new Matrix(rows,columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double value=matrix.getElement(i,j)*scalar;
                newMatrix.setElement(i,j,value);
            }
        }


        return newMatrix;
    }

    static double dotProduct(Matrix matrix1,Matrix matrix2){
        //only for vectors
        double value=0;
        int rows=matrix1.getRows();
        int columns=matrix1.getColumns();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                value+=matrix1.getElement(i,j)*matrix2.getElement(i,j);
            }
        }
        return value;
    }

    //default random matrix;
    static Matrix initializeRandom(int rows,int columns){
        Matrix matrix= initializeRandom(rows, columns,1);
        return matrix;
    }




    static Matrix initializeRandom(int rows,int columns,int muliplier){
        Matrix matrix=new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix.setElement(i,j,Math.random()*muliplier);
            }
        }

        return matrix;
    }


    static double matrixSum(Matrix matrix){
        //preferably for vectors
        double sum=0;
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                sum+=matrix.getElement(i,j);
            }
        }
        return sum;
    }




    static double[][] toArray(Matrix matrix){
        int rows=matrix.getRows();
        int columns=matrix.getColumns();
        double[][] array=new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                array[i][j]=matrix.getElement(i,j);
            }
        }

        return array;
    }

    static Matrix randomMatrix(int rows, int columns){
        Matrix matrix=new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix.setElement(i,j,Math.random());
            }
        }
        return matrix;
    }

    static Matrix toMatrix(double[][] array){
       Matrix matrix=new Matrix(array.length,array[0].length);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {

                matrix.setElement(i,j,array[i][j]);

            }
        }
        return matrix;
    }

    static void printMatrix(Matrix matrix){
        int rows=matrix.getRows();
        int columns=matrix.getColumns();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(matrix.getElement(i,j)+" ");
            }
            System.out.println();
        }
    }



}
