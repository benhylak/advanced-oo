import com.cs210x.Collection210X;

import java.util.ArrayList;

/**
 * Test exectutive runs the tests on the data structures it is given and creates the ResultSummary data as output
 *
 * Created by benhylak on 12/4/16.
 */
public class TestExecutive
{
    DataTest dataTest;
    Collection210X<Integer>[] structuresToTest;

    static int RUNS_FOR_AVG_DEFAULT = 150;

    ResultSummary results;

    final int[] testIntervals = { 1, 2, 5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 600,
            700, 800, 900, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000 };

    public TestExecutive(Collection210X<Integer>[] inputStructures)
    {
        this(inputStructures, RUNS_FOR_AVG_DEFAULT);
    }

    public TestExecutive(Collection210X<Integer>[] inputStructures, int runsForAvg)
    {
        dataTest = new DataTest(runsForAvg);
        structuresToTest = inputStructures;

        results = new ResultSummary();
    }

    /**
     * Runs all available dataTests on each given structure
     */
    public void runTests()
    {
        for(Collection210X<Integer> c : structuresToTest)
        {
            ArrayList<TestSummary> testSummaries = new ArrayList<TestSummary>();

            dataTest.setDataStructure(c); //set data structure to make tests for

            for (TimedTest t : dataTest) //get tests for current data structure
            {
                testSummaries.add(t.runOnRange(testIntervals));
            }

            results.addStructureResult(testSummaries);
        }
    }

    public ResultSummary getResults()
    {
        return results;
    }
}
