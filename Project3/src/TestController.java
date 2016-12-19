import com.cs210x.Collection210X;
import com.cs210x.MysteryDataStructure;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for TestScreen1.fxml. Really quick and dirty... Beats making the graphs manually!
 *
 * Created by benhylak on 12/5/16.
 */
public class TestController implements Initializable
{
    ResultSummary results;

    @FXML
    ComboBox<TestSummary> tests_ComboBox;

    @FXML
    ComboBox structures_ComboBox;

    @FXML
    private TableView table;

    @FXML
    private ProgressBar progress_Bar;

    @FXML
    private LineChart chart;

    public void strucuture_Selection_Changed(ActionEvent event)
    {
        int selected_structure = structures_ComboBox.getSelectionModel().getSelectedIndex();
        tests_ComboBox.getItems().clear();
        tests_ComboBox.getItems().addAll(results.getSummaryFor(selected_structure));
    }

    public void test_Selection_Changed(ActionEvent event)
    {
        XYChart.Series series = new XYChart.Series();

        TestSummary summary = (TestSummary)tests_ComboBox.getSelectionModel().getSelectedItem();

        if(summary!=null)
        {
            for(DataPoint<Integer, Long> p : summary)
            {
                series.getData().add(new XYChart.Data<Integer, Long>(p.getN(), p.getVal()));
            }

            chart.getData().clear();
            chart.getData().add(series);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        final int cs210XTeamIDForProject3 = 5; // TODO CHANGE THIS TO THE TEAM ID YOU USE TO SUBMIT YOUR PROJECT3 ON INSTRUCT-ASSIST.


        final Collection210X<Integer>[] mysteryDataStructures = (Collection210X<Integer>[]) new Collection210X[5];
        for (int i = 0; i < 5; i++) {
            mysteryDataStructures[i] = MysteryDataStructure.getMysteryDataStructure(cs210XTeamIDForProject3, i, new Integer(0));
        }

        TestExecutive testExec = new TestExecutive(mysteryDataStructures, 100);
        testExec.runTests();

        results= testExec.getResults();

        XYChart.Series series = new XYChart.Series();

        TestSummary summary = results.getSummaryFor(0).get(0);

        for(DataPoint<Integer, Long> p : summary)
        {
            series.getData().add(new XYChart.Data<Integer, Long>(p.getN(), p.getVal()));
        }

        for (int i=0; i<results.numOfStructures(); i++)
        {
            structures_ComboBox.getItems().add(String.format("Data Structure %d", i));
        }

        tests_ComboBox.getItems().addAll(results.getSummaryFor(0));
    }
}
