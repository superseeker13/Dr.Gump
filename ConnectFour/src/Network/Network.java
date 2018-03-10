package Network;
import java.util.stream.*;

/**
 *
 * @author Edward Conn
 */
public class Network {

    String label;
    double[] input;
    double[] hidden;
    double[] output;
    final int numberOfLayers = 3;
    int numberOfNeurons;
    int[] neuronsPerLayer;

    public Network(String label, int[] neuronsPerLayer) {
        this.label = label;
        this.numberOfNeurons = IntStream.of(neuronsPerLayer).sum();
        input = createNewLayer(neuronsPerLayer[0], neuronsPerLayer[1]);
        hidden = createNewLayer(neuronsPerLayer[1], neuronsPerLayer[2]);
        output = createNewLayer(neuronsPerLayer[0], 0);
        
    }

    private double[] createNewLayer(int numberOfNodes, int numberOfConnections) {
        double[] tempLayer = new double[numberOfNodes];
        for (int j = 0; j < numberOfNodes; j++) {              
            tempLayer[j] = Math.random();
        }
        return tempLayer;
    }
    
    private double sigmoid(double a){
        return (1 / (1 + Math.pow(Math.E, -a)));
    }
    
    boolean checkInitialization() {
        return this != null;
    }

    public String getLabel() {
        return label;
    }

    public int getNumberOfLayers() {
        return numberOfLayers;
    }

    public int getNumberOfNeurons() {
        return numberOfNeurons;
    }

    public int[] getNeuronsPerLayer() {
        return neuronsPerLayer;
    }
}
