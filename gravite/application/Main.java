package application;
	
import controlleur.Controleur;
import gui.Vue;
import javafx.application.Application;
import javafx.stage.Stage;
import model.GameLoop;
import model.Model;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Vue root_v = new Vue();
			Model root_m=new Model();
			Scene scene = new Scene(root_v,400,400);
			new Controleur(root_m,root_v);
			
			root_v.bind(scene.widthProperty(), scene.heightProperty());
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
