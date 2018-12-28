import java.util.ArrayList;
import java.util.Random;

public class test {
    public static void main(String[] args) {
        Network network = new Network(new int[]{2, 2, 1});

        double[][][] xorBits = {
                {{0}, {0}},
                {{1}, {1}},
                {{0}, {1}},
                {{1}, {0}}
        };
        double[][][] trueVal = {
                {{0}},
                {{0}},
                {{1}},
                {{1}}
        };


        ArrayList<Matrix> trainingData = new ArrayList<>();
        ArrayList<Matrix> targetData = new ArrayList<>();
        ArrayList<Matrix> evaluationInputData = new ArrayList<>();
        ArrayList<Matrix> evaluationTargetData = new ArrayList<>();

        int size = 1000;
        int evalutaionSize=100;
        double learningRate=.001;
        int epoch=10;
        int batchSize=100;
        for (int i = 0; i < size; i++) {
            int index = (int) (Math.random() * xorBits.length);
            Matrix data = MatrixTools.toMatrix(xorBits[index]);
            Matrix target = MatrixTools.toMatrix(trueVal[index]);
            trainingData.add(data);
            targetData.add(target);
        }

        for (int i = 0; i < evalutaionSize; i++) {
            int index = (int) (Math.random() * xorBits.length);
            Matrix data = MatrixTools.toMatrix(xorBits[index]);
            Matrix target = MatrixTools.toMatrix(trueVal[index]);
            evaluationInputData.add(data);
            evaluationTargetData.add(target);
        }

        int index = (int) (Math.random() * xorBits.length);
        Matrix random = MatrixTools.toMatrix(xorBits[index]);

        network.printLayers();
        System.out.println("Random");
        MatrixTools.printMatrix(network.propForward(random));

        network.train(trainingData,targetData,evaluationInputData,evaluationTargetData,epoch,batchSize,learningRate);

    }
}
