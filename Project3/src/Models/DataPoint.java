package Models;

/**
 * Class encapsulates a data point from a TimedTest.
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

    /**
     * N elements value was tested on
     *
     * @return Number of elements in structure when data was collected
     */
    public T getN()
    {
        return xVal;
    }

    /**
     *
     * @return CPU Ticks passed while test was executing
     */

    public V getVal()
    {
        return yVal;
    }
}
