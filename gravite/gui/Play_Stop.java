package gui;

import java.util.LinkedList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ressources.Ressources;

public class Play_Stop extends ScrollPane {
	private BooleanProperty bool;//True - Play / False - Stop
	private Selectable_Item[] selectable_list=new Selectable_Item[1];
	{
		bool=new SimpleBooleanProperty(true);
		selectable_list[0]= new Play();
	}
	public Play_Stop() {
		VBox vbox=new VBox();
		vbox.getChildren().addAll(getChoix());
		this.setContent(vbox);
	}
	private class Choix extends Button {
		private ImageView image=new ImageView();
		{
			image.fitWidthProperty().bind(this.prefWidthProperty().multiply(0.7));
			image.fitHeightProperty().bind(this.prefHeightProperty().multiply(0.7));
		}
		Choix(Selectable_Item selectable){
			image.setImage(selectable.getImage());
			this.setPrefSize(60, 60);
			this.setGraphic(image);
			this.setOnMouseClicked(e->{
				selectable.apply();
				image.setImage(selectable.getImage());
			});
		}
	}
	private class Play implements Selectable_Item {
		@Override
		public Image getImage() {
			if(bool.get())return new Image(Ressources.Fichier_toStringPath(Ressources.PATH_toItems,"Stop","png"));
			return new Image(Ressources.Fichier_toStringPath(Ressources.PATH_toItems,"Play","png"));
		}
		@Override
		public void apply() {
			bool.set(!bool.get());
		}	
	}
	//Getter
	private final LinkedList<Choix> getChoix(){
		LinkedList<Choix> choice_list=new LinkedList<>();
		for(int i=0;i<selectable_list.length;i++)choice_list.add(new Choix(selectable_list[i]));
		return choice_list;
	}
	public BooleanProperty getBooleanProperty() {
		return bool;
	}

}
