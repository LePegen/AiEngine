import java.util.ArrayList;

public class Network {
    /*
    TODO:
     Create multi-layer network
     Create class that will Serialize data
     Implement handwriting recognition
     Implement testing

     Note:
     The code may seem similar to Michael Nielsen's neural network book. That is because the network was not resulting to accurate results.
     I rendered his code in java line by line but it didn't fixed the problem. It turned out that my learning rate was to small and it took me days to figure that out.
     Nonetheless my initial implementation is logically similar to the Michael Nielsen's work.

    */

    Matrix[] biases;
    Matrix[] weights;
    int totalLayers;

    public Network(int[] sizes) {
        this.totalLayers = sizes.length;
        biases = new Matrix[totalLayers - 1];
        weights = new Matrix[totalLayers - 1];

        for (int i = 0; i < totalLayers - 1; i++) {
            biases[i] = (MatrixTools.randomMatrix(sizes[i + 1], 1));
            weights[i] = (MatrixTools.randomMatrix(sizes[i + 1], sizes[i]));
        }
    }


    public Matrix propForward(Matrix input) {
        Matrix activation = new Matrix(input);
        for (int i = 0; i < totalLayers - 1; i++) {
            Matrix tempMat = MatrixTools.add(MatrixTools.multiply(weights[i], activation), biases[i]);
            activation = MathTools.sigmoid(tempMat);
        }
        return activation;
    }

    /*
    TODO:
     Clean updateMiniBatch
     */
    public void train(ArrayList<Matrix> trainingData, ArrayList<Matrix> targetData, ArrayList<Matrix> testData, ArrayList<Matrix> testOutput, int epochs, int minibatchSize, double learningRate) {

        for (int i = 0; i < epochs; i++) {
            for (int j = 0; j < trainingData.size() - minibatchSize; j += minibatchSize) {
                ArrayList<Matrix> miniTrainBatch = new ArrayList<Matrix>(trainingData.subList(j, j + minibatchSize));
                ArrayList<Matrix> miniTargetBatch = new ArrayList<Matrix>(targetData.subList(j, j + minibatchSize));
                updateMiniBatch(miniTrainBatch, miniTargetBatch, learningRate);
            }
            int correct = evaluate(testData, testOutput, .1);
            System.out.println("Epoch " + (i + 1) + ": " + correct + "/" + testData.size());
        }


    }


    public void updateMiniBatch(ArrayList<Matrix> x, ArrayList<Matrix> y, double eta) {
        int size = x.size();
        Matrix[] nablaW = new Matrix[totalLayers-1];
        Matrix[] nablaB = new Matrix[totalLayers-1];
        //initialize values of ArrayLists
        for (int i = 0; i < totalLayers - 1; i++) {
            //map values
            Matrix referenceMatrix = this.weights[i];
            nablaW[i]=(new Matrix(referenceMatrix.getRows(), referenceMatrix.getColumns()));
            referenceMatrix = this.biases[i];
            nablaB[i]=(new Matrix(referenceMatrix.getRows(), referenceMatrix.getColumns()));
        }

        for (int i = 0; i < size; i++) {
            ArrayList<Matrix[]> wandb = propBackward(x.get(i), y.get(i));
            Matrix[] deltaW = wandb.get(0);
            Matrix[] deltaB = wandb.get(1);
            //nw+dnw
            for (int j = 0; j < this.totalLayers - 1; j++) {
                nablaB[j]=( MatrixTools.add(deltaB[(j)], nablaB[(j)]));
                nablaW[j]=( MatrixTools.add(deltaW[(j)], nablaW[(j)]));
            }
        }

        //set self
        //w-(eta/len(mini_batch))*nw
        for (int i = 0; i < totalLayers - 1; i++) {
            this.weights[i] = (MatrixTools.subtract(this.weights[i], MatrixTools.multiplyS(nablaW[(i)], eta / size)));
            this.biases[i] = (MatrixTools.subtract(this.biases[i], MatrixTools.multiplyS(nablaB[(i)], eta / size)));
        }
    }


    // weight=[0], bias=[1]
    public ArrayList<Matrix[]> propBackward(Matrix x, Matrix y) {
        Matrix[] nablaW = new Matrix[totalLayers-1];
        Matrix[] nablaB = new Matrix[totalLayers-1];
        for (int i = 0; i < totalLayers - 1; i++) {
            nablaW[i]=(new Matrix(weights[(i)].getRows(), weights[(i)].getColumns()));
            nablaB[i]=(new Matrix(biases[(i)].getRows(), biases[(i)].getColumns()));
        }
        //feed forward
        Matrix activation = new Matrix(x);
        ArrayList<Matrix> activations = new ArrayList<>();
        activations.add(activation);
        ArrayList<Matrix> zs = new ArrayList<>();


        for (int i = 0; i < totalLayers - 1; i++) {
            //wa+b
            Matrix z = MatrixTools.add(MatrixTools.multiply(this.weights[(i)], activation), this.biases[(i)]);
            zs.add(z);
            activation = MathTools.sigmoid(z);
            activations.add(activation);
        }

        //backprop

        //where y is the training matrix
        //for output->hidden layer
        //delta^L=changeC had sigPrime(Z^L)
        //change in C
        Matrix cost = costDerivative(y,activations.get(activations.size() - 1));
        MatrixTools.printMatrix(cost);
        Matrix sp = MathTools.sigmoidPrime(zs.get(zs.size() - 1));
        Matrix delta = MatrixTools.hadamardProduct(cost, sp);
        nablaB[nablaB.length - 1]= delta;
        nablaW[nablaW.length - 1] =MatrixTools.multiply(delta, MatrixTools.transpose(activations.get(activations.size() - 2)));

        //next layer
        for (int i = nablaB.length- 2; i >= 0; i--) {
            Matrix z = zs.get(i);
            sp = MathTools.sigmoidPrime(z);
            // delta = np.dot(self.weights[-l+1].transpose(), delta) * sp
            delta = MatrixTools.hadamardProduct(MatrixTools.multiply(MatrixTools.transpose(this.weights[(i + 1)]), delta), sp);
            nablaB[i]=delta;
            nablaW[i]= MatrixTools.multiply(delta, MatrixTools.transpose(activations.get(i)));
        }

        ArrayList<Matrix[]> wandb = new ArrayList<>();
        wandb.add(nablaW);
        wandb.add(nablaB);
        return wandb;
    }

    public int evaluate(ArrayList<Matrix> input, ArrayList<Matrix> target, double alpha) {
        int correct = 0;
        for(int i = 0; i < input.size(); i++) {
            Matrix output = propForward(input.get(i));
            int maxResultRow = 0;
            int maxOutputRow = 0;
            for (int j = 0; j < target.get(i).getRows(); j++) {
                if (target.get(i).getElement(j, 0) > target.get(i).getElement(maxResultRow, 0))
                    maxResultRow = j;
                if (output.getElement(j, 0) > output.getElement(maxOutputRow, 0))
                    maxOutputRow = j;
            }
            if (maxResultRow == maxOutputRow)
                correct += 1;
        }
        return correct;
    }


    public Matrix costDerivative(Matrix yPrime, Matrix y) {
        //
        return MatrixTools.subtract(yPrime, y);
    }


    public void printLayers() {


        System.out.println("Biases");
        for (int i = 0; i < biases.length; i++) {
            System.out.println(i + 1);
            MatrixTools.printMatrix(biases[(i)]);
            System.out.println();

        }

        System.out.println("Weights");
        for (int i = 0; i < weights.length; i++) {
            System.out.println(i + 1);
            MatrixTools.printMatrix(weights[(i)]);
            System.out.println();
        }


    }


}
