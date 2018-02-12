package Network;

import Lists.LList;

/**
 *
 * @author Edward Conn
 * @param <T>
 */
public class Neuron<T> implements NeuronInterface<T> {

    private T data;
    private LList<T> next;
    private LList<T> previous;

    public Neuron(T data) {
        this.data = data;
        this.next = null;
        this.previous = null;
    }

    public Neuron(T data, LList next, LList previous) {
        this.data = data;
        this.next = next;
        this.previous = previous;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public void setNextLayer(LList next) {
        this.next = next;
    }

    @Override
    public LList getNextLayer() {
        return this.next;
    }

    @Override
    public void setPreviousLayer(LList previous) {
        this.previous = previous;
    }

    @Override
    public LList getPreviousLayer() {
        return this.previous;
    }
}