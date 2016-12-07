package Models;

/**
 * Created by benhylak on 12/4/16.
 */
public abstract class Test
{
    abstract long test(int N);
    abstract String getName();

    int runsForAvg;

    public Test(int runs)
    {
        this.runsForAvg = runs;
    }

    /**
     * Runs the test a preset amount of times and returns an average of the result.
     */
    public long avgTest(int N)
    {
        return avgTest(N, runsForAvg);
    }

    public TestSummary runOnRange(int[] N_Vals)
    {
        TestSummary summary = new TestSummary(this.getName());

        for (int N : N_Vals)
        {
            long result = this.avgTest(N); //get the average result from running the test on N elements
            summary.addDataForN(N, result);
        }

        return summary;
    }

    /**
     *
     * @param N number of elements to test
     * @param runs number of times to run the test (if custom number not needed, use function un-overloaded)
     * @return average test result after running test (runs) times on (N) elements
     */
    public long avgTest(int N, int runs)
    {
        long[] runResults = new long[runs];

        for(int i=0; i<runs; i++)
        {
            runResults[i] = test(N);
        }

        return average(runResults);
    }

    public static long average(long[] nums)
    {
        long sum = 0;

        for(long l : nums)
        {
            sum+=l;
        }

        return sum/nums.length;
    }
}
