README 

Submitted/Created by: Benjamin Hylak

Attached Classes: 

ExperimentRunner.java (Provided)

TestExecutive.java
	-Test Executive
	-Runs tests on all of the mystery data structures provided and 		generates a “ResultSummary” (See Below)

DataTest.java 
	-Has functions for testing the various properties of 210X data 		structures. 
	-Is iterable. Iterating over a DataTest object allows you to go 	through the battery of tests. You should set the data structure under 	test before iterating. 

TimedTest.java
	-A Timed Test
	-Abstract class
	-Has one abstract function
		- long test(int N)
		-should return time it takes for function to complete when
		 testing N elements
	-Has various functions like averaging and running test on a range of
	N’s

TestSummary.java
	-Results for a specific type of test on a specific data structure
	ran over a range of N’s
	-Also iterable, will supply DataPoints of (N (elements), Value 		(Time)) 

ResultSummary.java
	-Total results of all tests ran on all data structures
	-allows for excel export (commented out for now) 
	-allows for printing to console

DataPoint.java
	-A data point for a timed test
	-N (number of elements) => Value (CPU Ticks)

FUN STUFF
	-UI Files
		-Yes I made a UI for this. Made verifying my program easier 		to see a graph I could actually adjust. Half baked though.
	