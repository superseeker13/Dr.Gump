package Network;

import Lists.LList;

/**
 *
 * @author Edward Conn
 */
public class Network {
    LList input;
    LList hidddenLayers;
    LList output;
    final int DEFAULT_INPUT_SIZE = 42;
    final int DEFAULT_OUTPUT_SIZE = 7;
    
    
    public Network(int inputSize, int outputSize, int hiddenLayers, int[] listSize){
       input = new LList<>();
       output = new LList<>();
    }
    
    void addNeuron(Neuron n) {
        input.add(n);
    }
}