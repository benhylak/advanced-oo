package Models;

import Models.DataPoint;

import java.util.*;

/**
 * Created by benhylak on 12/5/16.
 */
public class TestSummary implements Iterable<DataPoint<Integer, Long>>
{
    private String TestName;
    private LinkedHashMap<Integer, Long> data;

    public TestSummary(String testName)
    {
        this.TestName = testName;
        data = new LinkedHashMap<Integer, Long>();
    }

    public void addDataForN(int N, Long result)
    {
        data.put(N, result);
    }


    public Set<Integer> getIntervals()
    {
        return data.keySet();
    }

    public Collection<Long> getResults()
    {
        return data.values();
    }

    public String getName()
    {
        return TestName;
    }

    @Override
    public Iterator<DataPoint<Integer, Long>> iterator()
    {
        Iterator<DataPoint<Integer, Long>> it = new Iterator<DataPoint<Integer,Long>>() {

            Iterator<Integer> keyIterator = getIntervals().iterator();

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < getIntervals().size() && (keyIterator.hasNext());
            }

            @Override
            public DataPoint<Integer, Long> next()
            {
                int interval = keyIterator.next();

                DataPoint p = new DataPoint(interval, data.get(interval));

                return p;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    @Override
    public String toString()
    {
        return getName();
    }
}
