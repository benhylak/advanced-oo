import com.cs210x.*;

/**
  * Class to deduce the identity of mystery data structures.
  */
public class ExperimentRunner {
	private static final int NUM_DATA_STRUCTURES_TO_DEDUCE = 5;

	public static void main (String[] args) {
		final int cs210XTeamIDForProject3 = 5; // TODO CHANGE THIS TO THE TEAM ID YOU USE TO SUBMIT YOUR PROJECT3 ON INSTRUCT-ASSIST.
		final int runs_for_avg = 500; //number of runs to take the average of

		// Fetch the collections whose type you must deduce.
		// Note -- you are free to change the type parameter from Integer to whatever you want. In this
		// case, make sure to replace (over the next 4 lines of code) Integer with whatever class you prefer.
		// In addition, you'll need to pass the method getMysteryDataStructure a "sample" (an instance) of 
		// the class you want the collection to store.
		@SuppressWarnings("unchecked")
		final Collection210X<Integer>[] mysteryDataStructures = (Collection210X<Integer>[]) new Collection210X[NUM_DATA_STRUCTURES_TO_DEDUCE];
		for (int i = 0; i < NUM_DATA_STRUCTURES_TO_DEDUCE; i++) {
			mysteryDataStructures[i] = MysteryDataStructure.getMysteryDataStructure(cs210XTeamIDForProject3, i, new Integer(0));
		}

		TestExecutive testExec = new TestExecutive(mysteryDataStructures, runs_for_avg);

		testExec.runTests();

		ResultSummary testResults = testExec.getResults();

        testResults.printData();
	}
}
