package Models;

/**
 * Created by benhylak on 12/6/16.
 */
public class DataPoint<T, V>
{
    T xVal;
    V yVal;

    public DataPoint(T xVal, V yVal)
    {
        this.xVal = xVal;
        this.yVal  =yVal;
    }

    public T getN()
    {
        return xVal;
    }

    public V getVal()
    {
        return yVal;
    }
}
