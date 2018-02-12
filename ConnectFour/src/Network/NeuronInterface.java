package Network;

import Lists.LList;

/**
 *
 * @author Edward Conn
 */
interface NeuronInterface<T> {
    public void setData(T data);

    public T getData();

    public void setNextLayer(LList next);

    public LList getNextLayer();

    public void setPreviousLayer(LList previous);

    public LList getPreviousLayer();

}