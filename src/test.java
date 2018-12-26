import java.util.Random;

public class test {
    public static void main(String[] args) {
        Network network = new Network(2, 2, 1);

        double[][][] xorBits = {
                {{0}, {0}},
                {{1}, {1}},
                {{0}, {1}},
                {{1}, {0}}
        };
        double[][][] trueVal={
                {{0}},
                {{0}},
                {{1}},
                {{1}}
        };


        int trainingSize = 10000;
        Random random=new Random();
        for (int i = 0; i < trainingSize; i++) {
            int index=random.nextInt(4);
            double[][] trainData = xorBits[index];
            double[][] trainVal = trueVal[index];
            Matrix inputMatrix =MatrixTools.toMatrix(trainData);
            Matrix outputMatrix =MatrixTools.toMatrix(trainVal);
            network.propBackward(inputMatrix,outputMatrix,20);

        }

        double[][] unknown={
                {1,1}
        };
        network.loadData(MatrixTools.transpose(MatrixTools.toMatrix(unknown)));
        network.propForward();

        network.printLayers();

    }
}
