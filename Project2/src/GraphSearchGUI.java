import java.util.stream.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;

public class GraphSearchGUI extends Application {

	static String actorsPath = "/Users/benhylak/Downloads/IMDB/actors.list";
	static String actressesPath = "/Users/benhylak/Downloads/IMDB/actresses.list";

	public GraphSearchGUI () {
	}

	protected static class StringComparator implements Comparator<String> {
		public int compare (String obj1, String obj2) {
			if (obj1 == obj2) {
				return 0;
			} else if (obj1 == null) {
				return -1;
			} else if (obj2 == null) {
				return 1;
			} else {
				return obj1.compareTo(obj2);
			}
		}
	}

	protected static List<String> getNames (Collection<? extends Node> nodes) {
		return nodes.stream().map(node -> node.getName()).collect(Collectors.toList());
	}

	protected static ObservableList<String> getSortedObservableList (Collection<? extends Node> nodes) {
		return FXCollections.observableList(getNames(nodes)).sorted(new StringComparator());
	}

	@Override
	public void start (Stage primaryStage) {
		primaryStage.setTitle("Graph Search");

		final Graph graph;

		try {
			graph = new IMDBActorsGraph(actorsPath, actressesPath);
			//graph = new CiteSeerGraph();
		} catch (IOException ioe) {
			System.out.println("Couldn't load data");
			return;
		}

		ListView<String> actorsList1 = new ListView<String>(getSortedObservableList(graph.getNodes()));
		//actorsList1.setPrefSize(200, 250);
		ListView<String> actorsList2 = new ListView<String>(getSortedObservableList(graph.getNodes()));
		//actorsList2.setPrefSize(200, 250);
		final ListView<String> resultsList = new ListView<String>();
		//resultsList.setPrefSize(200, 250);

		VBox rootPanel = new VBox();
			HBox comboPanel = new HBox();
			comboPanel.setPadding(new Insets(15, 12, 15, 12));
			comboPanel.setSpacing(10);
				VBox actorsPanel1 = new VBox();
				actorsPanel1.getChildren().addAll(new Label("Actor 1"), actorsList1);
				VBox actorsPanel2 = new VBox();
				actorsPanel2.getChildren().addAll(new Label("Actor 2"), actorsList2);
			comboPanel.getChildren().addAll(actorsPanel1, actorsPanel2);

			VBox buttonPanel = new VBox();
			buttonPanel.setPadding(new Insets(15, 12, 15, 12));
			buttonPanel.setSpacing(10);
			final Label resultsLabel = new Label();
			Button searchButton = new Button("Search") {
				public void fire () {
					final GraphSearchEngine searchEngine = new GraphSearchEngineSpeedy();

					if (! actorsList1.getSelectionModel().isEmpty() && ! actorsList2.getSelectionModel().isEmpty()) {
						String actor1 = actorsList1.getSelectionModel().getSelectedItem();
						String actor2 = actorsList2.getSelectionModel().getSelectedItem();
						List<Node> shortestPath = searchEngine.findShortestPath(graph.getNodeByName(actor1),
																				graph.getNodeByName(actor2));
						if (shortestPath == null) {
							List<String> nopath = new ArrayList<String>();
							nopath.add("No path");
							resultsList.getItems().setAll(FXCollections.observableList(nopath));
							resultsLabel.setText("");
						} else {
							resultsList.getItems().setAll(getNames(shortestPath));
							resultsLabel.setText((shortestPath.size()-1) + " hops");
						}
					}
				}
			};

		buttonPanel.getChildren().addAll(searchButton, resultsList, resultsLabel);
		rootPanel.getChildren().addAll(comboPanel, buttonPanel);

		primaryStage.setScene(new Scene(rootPanel, 400, 300));
		primaryStage.show();
	}
}
