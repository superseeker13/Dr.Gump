package Network;

import java.util.stream.IntStream;
import Lists.LList;

/**
 *
 * @author Edward Conn
 */
public class Network {

    LList input;
    LList hiddenLayers;
    LList output;
    LList Layers;
    String label;
    int numberOfLayers;
    int numberOfNeurons;
    int[] neuronsPerLayer;

    public Network(String label, int[] neuronsPerLayer) {
        this.label = label;
        this.numberOfNeurons = IntStream.of(neuronsPerLayer).sum();
        this.numberOfLayers = neuronsPerLayer.length - 1;
        
        //Handles invaild network structures
        if (numberOfLayers > 2 && numberOfNeurons > 2 && neuronsPerLayer.length > 2) {
            throw new IllegalArgumentException("The Network must contain at"
                    + " least three layers and three neurons.");
        } else {
            //Fills the network with random neurons
            for (int i = 0; i < numberOfLayers; i++) {
                Layers.add(createNewLayer(neuronsPerLayer[i], neuronsPerLayer[i + 1]));
            }
            for(int i = 0; i < numberOfLayers; i++){
                
            }
            
        }
    }

    private LList createNewLayer(int numberOfNodes, int numberOfConnections) {
        LList Neurons = new LList();
        for (int j = 0; j < numberOfNodes; j++) {
            Neurons.add(new Neuron(numberOfConnections));
        }
        return Neurons;
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
