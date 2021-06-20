package application;
	
import controlleur.Controleur;
import gui.Vue;
import javafx.application.Application;
import javafx.stage.Stage;
import model.GameLoop;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Vue root = new Vue();
			Scene scene = new Scene(root,400,400);
			new Controleur(new GameLoop(),root);
			
			root.bind(scene.widthProperty(), scene.heightProperty());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setMinWidth(scene.getWidth());
			primaryStage.setMinHeight(scene.getHeight());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
