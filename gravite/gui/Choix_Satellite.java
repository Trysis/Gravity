package gui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import ressources.Ressources;

public class Choix_Satellite extends ScrollPane{
	private Selectable_Satellite selected;
	
	private Choix satellite_viewer;
	private ColorPicker color_chooser=new ColorPicker(Color.GRAY.darker());
	private Gui_Satellite gui_satellite=Gui_Satellite.instance(20,15,color_chooser.getValue());
	private Button put_satellite = new Button("Put");
	//Components
	private VBox container = new VBox(5);//Conteneur principal
	
	private Image[] images_for_data = new Image[] {//Images associés à la donnée correspondante
			Ressources.getImage("x"),
			Ressources.getImage("y"),
			Ressources.getImage("m"),
			Ressources.getImage("r")
	};
	private Data[] data_input = new Data[] {//Données avec lequel l'utilisateur pourra intéragir pour paramétrer le Satellite
			new Data(gui_satellite.layoutXProperty(),null),
			new Data(gui_satellite.layoutYProperty(),null),
			new Data(gui_satellite.getMasseProperty(),null),
			new Data(gui_satellite.getRayonProperty(),null)
	};

	private Data_Manager data_to_show = new Data_Manager(images_for_data,data_input);//Lie chaque image à sa donnée et permet de les afficher
	
	{
		selected=gui_satellite;
		satellite_viewer=new Choix(selected);
		//Parametres graphiques
		container.setAlignment(Pos.CENTER);
		color_chooser.setStyle("-fx-color-rect-width: 40px;\r\n"
				+ " -fx-color-label-visible: false ;");//TODO Fichier CSS pour gérer ça
		color_chooser.getStyleClass().add("button");
		//Listener
		color_chooser.valueProperty().addListener(e->{
			gui_satellite.setColor(color_chooser.getValue());
			gui_satellite.render();
			satellite_viewer.setImage(gui_satellite);
			});
		//Properties
		color_chooser.prefWidthProperty().bind(container.widthProperty());//Même longueur que son contenant (container)
		data_to_show.prefWidthProperty().bind(container.widthProperty());//Même longueur que son contenant (container)	
		put_satellite.prefWidthProperty().bind(container.widthProperty());//Même longueur que son contenant (container)	
	}

	public Choix_Satellite() {
		this.setContent(container);
		container.getChildren().addAll(satellite_viewer,color_chooser,data_to_show,put_satellite);
	}
	private class Choix extends Button {
		private ImageView i_view=new ImageView();
		Choix(Selectable_Satellite selectable){
			this.setGraphic(i_view);
			this.setPrefSize(60, 60);
			i_view.setImage(selectable.getImage());
		}
		public void setImage(Selectable_Satellite selectable) {
			i_view.setImage(selectable.getImage());
		}
	}
	private class Data_Manager extends VBox {//Aligne chaque image à sa donnée spécifié
		Data_Manager(Image[] images,Data ... datas){
			HBox[] hbox = new HBox[datas.length];//Contiendras à l'indice i l'image associé à sa Data
			ImageView[] image_view=new ImageView[datas.length];
			for(int i=0;i<image_view.length;i++) {
				hbox[i]=new HBox();
				image_view[i]=new ImageView(images[i]);
				hbox[i].getChildren().addAll(image_view[i],datas[i]);
				if(i==0)continue;
				datas[i].prefWidthProperty().bind(datas[i-1].widthProperty());//Permet d'octroyer la même longueurs aux Datas
				datas[i].translateXProperty().bind(datas[i-1].layoutXProperty().subtract(datas[i].layoutXProperty()));//Permet d'aligner les Datas (input)
			}
			getChildren().addAll(hbox);
		}
	}
	private class Data extends HBox{
		private TextField data=new TextField();
		Data(DoubleProperty property,TextFormatter<Change> format){
			this.setAlignment(Pos.CENTER);
			//Format
			StringConverter<Number> converter=new NumberStringConverter();
			data.textProperty().bindBidirectional(property, converter);
			data.setTextFormatter(format);
			getChildren().addAll(new Label(":"),data);//Ajout en children
		}
	}
	//Getter
	public Selectable_Satellite getSelected_Satellite() {
		return selected;
	}
	public Button getPutButton() {
		return put_satellite;
	}
}
