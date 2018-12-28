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

    ArrayList<Matrix> biases;
    ArrayList<Matrix> weights;
    int totalLayers;

    public Network(int[] sizes) {
        this.totalLayers = sizes.length;
        biases = new ArrayList<>();
        weights = new ArrayList<>();

        for (int i = 0; i < totalLayers -1; i++) {
            biases.add(MatrixTools.randomMatrix(sizes[i+1], 1));
            weights.add(MatrixTools.randomMatrix(sizes[i+1], sizes[i]));
        }
    }


    public Matrix propForward(Matrix input) {
        Matrix activation=new Matrix(input);
        for (int i = 0; i < totalLayers -1; i++) {
            Matrix tempMat=MatrixTools.add(MatrixTools.multiply(weights.get(i),activation),biases.get(i));
            activation=MathTools.sigmoid(tempMat);
        }
        return activation;
    }
    /*
    TODO:
     Clean updateMiniBatch
     */
    public void train(ArrayList<Matrix> trainingData,ArrayList<Matrix> targetData,ArrayList<Matrix> testData,ArrayList<Matrix> testOutput,int epochs,int minibatchSize,double learningRate){

        for (int i = 0; i < epochs; i++) {
            for (int j = 0; j < trainingData.size()-minibatchSize; j+=minibatchSize) {
                ArrayList<Matrix> miniTrainBatch= new ArrayList<Matrix>(trainingData.subList(j,j+minibatchSize));
                ArrayList<Matrix> miniTargetBatch= new ArrayList<Matrix>(targetData.subList(j,j+minibatchSize));
                updateMiniBatch(miniTrainBatch,miniTargetBatch,learningRate);
            }
            int correct = evaluate(testData,testOutput,5);
            System.out.println("Epoch " + (i + 1) + ": " + correct + "/" + testData.size());
        }


    }


    public void updateMiniBatch(ArrayList<Matrix> x,ArrayList<Matrix> y,double eta){
        int size=x.size();
        ArrayList<Matrix> tempWeights=new ArrayList<>(this.weights.size());
        ArrayList<Matrix> tempBiases=new ArrayList<>(this.biases.size());
        //initialize values of ArrayLists
        for (int i = 0; i < totalLayers -1; i++) {
            //map values
            Matrix referenceMatrix=this.weights.get(i);
            tempWeights.add(new Matrix(referenceMatrix.getRows(),referenceMatrix.getColumns()));
            referenceMatrix=this.biases.get(i);
            tempBiases.add(new Matrix(referenceMatrix.getRows(),referenceMatrix.getColumns()));
        }

        for (int i = 0; i < size; i++) {
            ArrayList<Matrix>[] wandb=propBackward(x.get(i),y.get(i));
            ArrayList<Matrix> deltaW=wandb[0];
            ArrayList<Matrix> deltaB=wandb[1];
            //nw+dnw
            for (int j = 0; j <this.totalLayers -1; j++) {
                Matrix newMatrix=MatrixTools.add(deltaB.get(j),tempBiases.get(j));
                tempBiases.set(j,newMatrix);
                newMatrix=MatrixTools.add(deltaW.get(j),tempWeights.get(j));
                tempWeights.set(j,newMatrix);
            }
        }

        //set self
        //w-(eta/len(mini_batch))*nw
        for (int i = 0; i < totalLayers -1; i++) {
            Matrix newMatrix=MatrixTools.subtract(this.weights.get(i),MatrixTools.multiplyS(tempWeights.get(i),eta/size));
            this.weights.set(i,newMatrix);
            newMatrix=MatrixTools.subtract(this.biases.get(i),MatrixTools.multiplyS(tempBiases.get(i),eta/size));
            this.biases.set(i,newMatrix);
        }
    }



    // weight=[0], bias=[1]
    public ArrayList<Matrix>[] propBackward(Matrix x, Matrix y) {
        ArrayList<Matrix> nablaW=new ArrayList<>();
        ArrayList<Matrix> nablaB =new ArrayList<>();
        for (int i = 0; i < totalLayers-1; i++) {
            nablaW.add(new Matrix(weights.get(i).getRows(),weights.get(i).getColumns()));
            nablaB.add(new Matrix(biases.get(i).getRows(),biases.get(i).getColumns()));
        }
        //feed forward
        Matrix activation=new Matrix(x);
        ArrayList<Matrix> activations = new ArrayList<>();
        activations.add(activation);
        ArrayList<Matrix> zs = new ArrayList<>();


        for (int i = 0; i < totalLayers -1; i++) {
            //wa+b
            Matrix z = MatrixTools.add(MatrixTools.multiply(this.weights.get(i), activation), this.biases.get(i));
            zs.add(z);
            activation= MathTools.sigmoid(z);
            activations.add(activation);
        }

        //backprop

        //where y is the training matrix
        //for output->hidden layer
        //delta^L=changeC had sigPrime(Z^L)
        //change in C
        Matrix cost = costDerivative(activations.get(activations.size()-1), y);  Matrix sp = MathTools.sigmoidPrime(zs.get(zs.size()-1));
        Matrix delta = MatrixTools.hadamardProduct(cost, sp);
        nablaB.set(nablaB.size()-1, delta);
        nablaW.set(nablaW.size()-1, MatrixTools.multiply(delta,MatrixTools.transpose(activations.get(activations.size()-2))));

        //next layer
        for (int i = nablaB.size()-2; i>=0; i--){
            Matrix z=zs.get(i);
            sp = MathTools.sigmoidPrime(z);
            // delta = np.dot(self.weights[-l+1].transpose(), delta) * sp
            delta = MatrixTools.hadamardProduct(MatrixTools.multiply(MatrixTools.transpose(this.weights.get(i+1)),delta),sp);
            nablaB.set(i, delta);
            nablaW.set(i, MatrixTools.multiply(delta,MatrixTools.transpose(activations.get(i))));
        }

        ArrayList[] wandb=new ArrayList[2];
        wandb[0]=nablaW;
        wandb[1]= nablaB;
        return wandb;
    }

    public int evaluate(ArrayList<Matrix> input,ArrayList<Matrix> target,double alpha){
        int correct=0;
        for (int i = 0; i < input.size(); i++) {
            Matrix out= propForward(input.get(i));
            Matrix currentData=target.get(i);
            Matrix difference=MatrixTools.subtract(out,currentData);

            for (int j = 0; j < difference.getRows(); j++) {
                for (int k = 0; k < difference.getColumns(); k++) {
                    if(Math.abs(difference.getElement(j,k))<alpha){
                        correct++;
                    }
                }
            }

        }
        return correct;
    }


    public Matrix costDerivative(Matrix toAsses, Matrix trueVal) {
        //
        return MatrixTools.subtract(toAsses,trueVal);
    }


    public void printLayers(){


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
