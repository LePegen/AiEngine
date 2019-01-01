import java.util.ArrayList;
import java.util.Random;

public class test {
    /**
     * Implementation of the XOR problem using the network
     */
    public static void main(String[] args) {
        Network network = new Network(new int[]{2,1, 1});

        double[][][] bits = {
                {{1.0}, {1.0}},
                {{1.0}, {0.0}},
                {{0}, {1.0}},
                {{0}, {0}}
        };
        double[][][] trueVal = {
                {{0}},
                {{1.0}},
                {{1.0}},
                {{0}}
        };


        ArrayList<Matrix> trainingData = new ArrayList<>();
        ArrayList<Matrix> targetData = new ArrayList<>();
        ArrayList<Matrix> evaluationInputData = new ArrayList<>();
        ArrayList<Matrix> evaluationTargetData = new ArrayList<>();

        int size = 1000;
        int evaluationSize=100;

        for (int i = 0; i < 1000; i++) {
            int index= (int) (Math.random()*4);
            Matrix trainValue=new Matrix(2,1);
            Matrix trueValMat=new Matrix(1,1);
            trainValue.setElement(0,0,bits[index][0][0]);
            trainValue.setElement(1,0,bits[index][1][0]);
            trueValMat.setElement(0,0,trueVal[index][0][0]);
            trainingData.add(trainValue);
            targetData.add(trueValMat);
        }

        for (int i = 0; i < 100; i++) {
            int index= (int) (Math.random()*4);
            Matrix trainValue=new Matrix(2,1);
            Matrix trueValMat=new Matrix(1,1);
            trainValue.setElement(0,0,bits[index][0][0]);
            trainValue.setElement(1,0,bits[index][1][0]);
            trueValMat.setElement(0,0,trueVal[index][0][0]);
            evaluationInputData.add(trainValue);
            evaluationTargetData.add(trueValMat);
        }



        double learningRate=.1;
        int epoch=100;
        int batchSize=100;

        network.train(trainingData,targetData,evaluationInputData,evaluationTargetData,epoch,batchSize,.1);

        int index;
        Matrix random;

        index = (int) (Math.random() * bits.length);
        random = MatrixTools.toMatrix(bits[index]);
        System.out.println("Random");
        MatrixTools.printMatrix(random);
        System.out.println("Random");
        MatrixTools.printMatrix(network.propForward(random));
        System.out.println();
        index = (int) (Math.random() * bits.length);
        random = MatrixTools.toMatrix(bits[index]);
        System.out.println("Random");
        MatrixTools.printMatrix(random);
        System.out.println("Random");
        MatrixTools.printMatrix(network.propForward(random));
        System.out.println();
        index = (int) (Math.random() * bits.length);
        random = MatrixTools.toMatrix(bits[index]);
        System.out.println("Random");
        MatrixTools.printMatrix(random);
        System.out.println("Random");
        MatrixTools.printMatrix(network.propForward(random));
        System.out.println();
        index = (int) (Math.random() * bits.length);
        random = MatrixTools.toMatrix(bits[index]);
        System.out.println("Random");
        MatrixTools.printMatrix(random);
        System.out.println("Random");
        MatrixTools.printMatrix(network.propForward(random));

    }
}
