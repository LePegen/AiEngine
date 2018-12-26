import sun.rmi.server.Activation;

import java.util.ArrayList;
import java.util.Vector;

public class Network {
    /*
    TODO:
     Create multi-layer network
     Create class that will Serialize data
     Implement handwriting recognition
    */

    ArrayList<Matrix> activations;
    ArrayList<Matrix> biases;
    ArrayList<Matrix> weights;
    int layers;

    public Network(int inputSize, int hiddenLayerSize, int outputLayerSize) {
        this.layers = 3;
        activations = new ArrayList<>();
        biases = new ArrayList<>();
        weights = new ArrayList<>();

        activations.add(new Matrix(hiddenLayerSize, 1));
        activations.add(new Matrix(hiddenLayerSize, 1));
        activations.add(new Matrix(outputLayerSize, 1));

        biases.add(new Matrix(hiddenLayerSize, 1));
        biases.add(MatrixTools.randomMatrix(hiddenLayerSize, 1));
        biases.add(MatrixTools.randomMatrix(outputLayerSize, 1));

        //could be wrong values
        weights.add(new Matrix(hiddenLayerSize, 1));
        weights.add(MatrixTools.randomMatrix(hiddenLayerSize, inputSize));
        weights.add(MatrixTools.randomMatrix(outputLayerSize, hiddenLayerSize));
    }

    public void loadData(Matrix matrix) {
        activations.set(0, matrix);
    }

    public void propForward() {
        for (int i = 1; i < layers; i++) {
            //wa+b
            Matrix weightbyactivation = MatrixTools.multiply(weights.get(i), activations.get(i - 1));
            Matrix newActivation = MatrixTools.add(weightbyactivation, biases.get(i));
            activations.set(i, MathTools.sigmoid(newActivation));
        }
        MatrixTools.printMatrix(activations.get(2));

        System.out.println();
        for (int i = 0; i < activations.size(); i++) {
            //MatrixTools.printMatrix(activations.get(i));
            //System.out.println();
        }


    }

    public void propBackward(Matrix input, Matrix target, double learningRate) {
        //feed forward
        ArrayList<Matrix> tempActivations = new ArrayList<>();
        ArrayList<Matrix> tempZ = new ArrayList<>();
        tempActivations.add(input);
        tempZ.add(new Matrix(1, 1));
        System.out.println("Input");
        MatrixTools.printMatrix(input);
        for (int i = 1; i < layers; i++) {
            //wa+b
            Matrix weightbyactivation = MatrixTools.multiply(weights.get(i), activations.get(i - 1));
            Matrix newActivation = MatrixTools.add(weightbyactivation, biases.get(i));
            tempZ.add(newActivation);
            Matrix zElement = MathTools.sigmoid(newActivation);
            tempActivations.add(zElement);
        }

        //backprop

        //where y is the training matrix
        int layerIndex = tempActivations.size() - 1;

        //for output->hidden layer
        //delta^L=changeC had sigPrime(Z^L)
        //change in C
        Matrix cost = costFunction(tempActivations.get(layerIndex), target);  Matrix sigmoidD = MathTools.sigmoidDerivative(tempActivations.get(layerIndex));
        Matrix delta = MatrixTools.multiply(cost, sigmoidD);

        Matrix deltaB = delta;
        Matrix deltaW = MatrixTools.multiply(delta,MatrixTools.transpose(tempActivations.get(layerIndex - 1)));

        Matrix newWeight =  deltaW;
        Matrix newBias =  deltaB;

        weights.set(layerIndex, MatrixTools.subtract(this.weights.get(layerIndex), MatrixTools.scalarMultiplication(newWeight, learningRate)));
        biases.set(layerIndex, MatrixTools.subtract(this.biases.get(layerIndex), MatrixTools.scalarMultiplication(newBias, learningRate)));


        //next layer
        layerIndex = tempActivations.size() - 2;
        Matrix currentMatrix=tempZ.get(layerIndex);

        sigmoidD = MathTools.sigmoidDerivative(currentMatrix);

        cost=MatrixTools.transpose(this.weights.get(layerIndex+1));
        delta = MatrixTools.multiply(MatrixTools.multiply(cost,delta), sigmoidD);
        deltaB = delta;
        deltaW = MatrixTools.multiply(delta,MatrixTools.transpose(tempActivations.get(layerIndex - 1)));


        newWeight = deltaW;
        newBias =  deltaB;


        this.weights.set(layerIndex, MatrixTools.subtract(this.weights.get(layerIndex), MatrixTools.scalarMultiplication(newWeight, learningRate)));
        this.biases.set(layerIndex, MatrixTools.subtract(this.biases.get(layerIndex), MatrixTools.scalarMultiplication(newBias, learningRate)));
        this.propForward();

        /**
        System.out.println("Cost");
        Matrix costF=costFunction(this.activations.get(2),target);
        System.out.println("Network");
        MatrixTools.printMatrix(this.activations.get(2));
        System.out.println("Target");
        MatrixTools.printMatrix(target);
        System.out.println("Cost");
        MatrixTools.printMatrix(costF);
        System.out.println();
         **/
    }


    public Matrix costFunction(Matrix toAsses, Matrix trueVal) {
        //(1/2n)*||y(x)-a||
        return MatrixTools.subtract(toAsses,trueVal);
    }


    public void train(double learningRate) {
    }

    public void printLayers(){
        System.out.println("Activations");
        for (int i = 0; i <activations.size(); i++) {
            System.out.println(i+1);
            MatrixTools.printMatrix(activations.get(i));
            System.out.println();
        }

        System.out.println("Biases");
        for (int i = 0; i <biases.size(); i++) {
            System.out.println(i+1);
            MatrixTools.printMatrix(biases.get(i));
            System.out.println();

        }

        System.out.println("Weights");
        for (int i = 0; i <weights.size(); i++) {
            System.out.println(i+1);
            MatrixTools.printMatrix(weights.get(i));
            System.out.println();

        }
    }


}
