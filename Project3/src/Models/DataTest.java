package Models;

import com.cs210x.CPUClock;
import com.cs210x.Collection210X;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by benhylak on 12/4/16.
 */
public class DataTest
{
    Collection210X<Integer> toTest;
    int timesToRun;
    //int N;

    long start = 0;

    public DataTest(int timesToRun)
    {
       // this.N = N;
        this.timesToRun = timesToRun;
    }

    public void setData(Collection210X<Integer> newToTest)
    {
       toTest = newToTest;
    }

    public Test[] getTests(Collection210X<Integer> nextToTest)
    {
        setData(nextToTest);

        return new Test[]{
                new Test(timesToRun)
                {
                    public long test(int N)
                    {
                        return testAdd(N);
                    }
                    public String getName()
                    {
                        return "Add Method";
                    }
                },

                new Test(timesToRun)
                {
                    public long test(int N)
                    {
                        return testContains(N);
                    }
                    public String getName()
                    {
                        return "Contains Method";
                    }
                },

                new Test(timesToRun)
                {
                    public long test(int N)
                    {
                        return testRemove(N);
                    }
                    public String getName()
                    {
                        return "Remove Method";
                    }
                }
        };
    }

    /**
     *Tests duration of add method on N elements
     *
     * @param N Models.Test method on N elements
     * @return Avg cpu time to run
     */
    public long testAdd(int N)
    {
        ///TEST SETUP///
        toTest.clear(); //clear elements
        long[] addDurs = new long[N]; //duration for each add
        ///////////////

        for (int i = 0; i < N; i++) {  // populate the mystery data structure
            startClock();

            toTest.add(new Integer(i));

            addDurs[i] = elapsedTime();
        }

        return Test.average(addDurs);
    }

    /**
     * Tests how long it takes for the contains function to run. Clears data structure, populates with N elements,
     * Then tests how long remove function takes.
      * @param N number of elements to be in structure when test is ran
     * @return duration of contains function
     */
    public long testContains(int N)
    {
        toTest.clear();

        for (int i = 0; i < N; i++)
        {  // populate the mystery data structure
            toTest.add(new Integer(i));
        }

        ///TEST SETUP///
        final Random random = new Random(System.currentTimeMillis());  // instantiate a random number generator
        int toFind = random.nextInt(N);
        ////////////////

        startClock(); //Start Models.Test Timer

        toTest.contains(toFind); //Models.Test

        return elapsedTime(); //End Models.Test Timer
    }

    /**
     * Tests how long it takes for the remove function to run. Clears data structure, populates with N elements,
     * Then tests how long the remove function takes
     *
     * @param N number of elements to be in structure when test is ran
     * @return duration of remove function run time
     */
    public long testRemove(int N)
    {
        toTest.clear();

        for (int i = 0; i < N; i++)
        {  // populate the mystery data structure
            toTest.add(new Integer(i));
        }

        ///TEST SETUP///
        final Random random = new Random();  // instantiate a random number generator
        int toRemove = random.nextInt(N);
        ////////////////

        startClock(); //start Models.Test Timer

        toTest.remove(toRemove); //Models.Test

        long time = elapsedTime(); //end Models.Test timer

        toTest.add(toRemove);

        return time;
    }

    public void startClock()
    {
        start = CPUClock.getNumTicks();
    }

    public long elapsedTime()
    {
        return (CPUClock.getNumTicks() - start);
    }
}
