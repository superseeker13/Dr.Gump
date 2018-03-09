package Network;

import Lists.LList;

/**
 *
 * @author Edward Conn
 * @param <T>
 */
public class Neuron<T extends Comparable<? super T>> {

    private LList previous;
    private LList next;
    private LList weight;
    private boolean fired;
    private T data;

    public Neuron(int connections) {
        for (int i = 0; i < connections; i++) {
            this.weight.add((float) Math.random());
        }
        fired = false;
    }
    
    public Neuron(int connections, T data) {
        for (int i = 0; i < connections; i++) {
            this.weight.add((float) Math.random());
        }
        fired = false;
        setData(data);
    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }

    public LList getWeight() {
        return this.weight;
    }

    public void setWeight(LList weight) {
        this.weight = weight;
    }

    public void setWeightAtIndex(int index, float weight) {
        this.weight.set(index, weight);
    }

    public T getData() {
        return data;
    }

    private void setData(T data) {
        this.data = data;
    }

    public boolean hasNext() {
        return next != null;
    }

    public boolean hasPrevious() {
        return previous != null;
    }

    public LList next() {
        return next;
    }

    void setNext(LList next) {
        this.next = next;
    }

    public LList previous() {
        return previous;
    }

    void setPrevious(LList previous) {
        this.previous = previous;
    }
    
}
