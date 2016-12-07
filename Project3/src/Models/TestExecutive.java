package Models;

import Models.DataTest;
import Models.ResultSummary;
import com.cs210x.Collection210X;

import java.util.ArrayList;

/**
 * Created by benhylak on 12/4/16.
 */
public class TestExecutive
{
    DataTest dataTest;
    Collection210X<Integer>[] structuresToTest;

    static int RUNS_FOR_AVG_DEFAULT = 125;

    ArrayList<String> textResults;

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

    public void runTests()
    {
        textResults = new ArrayList<String>();

        for(Collection210X<Integer> c : structuresToTest)
        {
            ArrayList<TestSummary> testSummaries = new ArrayList<TestSummary>();

            for (Test t : dataTest.getTests(c)) //get tests for next data structure
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
