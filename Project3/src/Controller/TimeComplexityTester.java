package Controller; /**
 * Created by benhylak on 12/5/16.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TimeComplexityTester extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("Views/TestScreen1.fxml"));

        primaryStage.setTitle("Time Complexity Tester");
        primaryStage.setScene(new Scene(root, 660, 650));
        primaryStage.show();
    }
}
