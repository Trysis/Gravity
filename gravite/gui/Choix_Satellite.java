package gui;

import java.util.LinkedList;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Choix_Satellite extends ScrollPane{
	private Selectable_Satellite selected=Gui_Satellite.instance(5,Color.DARKGOLDENROD);
	private Selectable_Satellite[] selectable_list=new Selectable_Satellite[3];
	{
		selectable_list[0]=selected;
		selectable_list[1]=Gui_Satellite.instance(10,Color.YELLOWGREEN);
		selectable_list[2]=Gui_Satellite.instance(15,Color.INDIANRED);
	}
	public Choix_Satellite() {
		VBox vbox=new VBox();
		vbox.getChildren().addAll(getChoix());
		this.setContent(vbox);
	}
	private class Choix extends Button {
		Choix(Selectable_Satellite selectable){
			this.setGraphic(new ImageView(selectable.getImage()));
			this.setPrefSize(60, 60);
			this.setOnMouseClicked(e->{
				selected=selectable;
			});
		}
	}
	private final LinkedList<Choix> getChoix(){
		LinkedList<Choix> choice_list=new LinkedList<>();
		for(int i=0;i<selectable_list.length;i++)choice_list.add(new Choix(selectable_list[i]));
		return choice_list;
	}
	//Getter
	public Selectable_Satellite getSelected_Satellite() {
		return selected;
	}
}
