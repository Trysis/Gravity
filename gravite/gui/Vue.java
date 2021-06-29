package gui;

import java.util.LinkedList;

import gameInterfaces.Renderer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Vue extends Group implements Renderer{
	private Canvas canvas;
	private GraphicsContext context;
	private Play_Stop gui_play_stop;
	private Choix_Satellite gui_satellite_choice;

	private LinkedList<Gui_Satellite> gui_satellite_list;
	{
		//Initialisation
		canvas=new Canvas();//Canvas
		context=canvas.getGraphicsContext2D();//GraphicsContext du Canvas
		gui_play_stop=new Play_Stop();//Bouton play/pause
		gui_satellite_choice=new Choix_Satellite();//Choix_Satellite liste des gui_satellites posables
		gui_satellite_list=new LinkedList<>();//Nombre de GUI satellite
		//Ajouts children
		getChildren().add(canvas);
		getChildren().add(gui_satellite_choice);
		getChildren().add(gui_play_stop);
		//Properties
		gui_satellite_choice.layoutXProperty().bind(this.canvas.widthProperty().subtract(gui_satellite_choice.widthProperty().multiply(1.1)));
		gui_satellite_choice.layoutYProperty().bind(this.canvas.heightProperty().subtract(gui_satellite_choice.heightProperty()).multiply(0.5));
		
		gui_play_stop.layoutXProperty().bind(gui_satellite_choice.layoutXProperty());
		gui_play_stop.layoutYProperty().bind(gui_satellite_choice.layoutYProperty().add(gui_satellite_choice.heightProperty().multiply(1.1)));
	}
	public Vue() {
		setEventFilterToCanvas();
		setEventFilterToPutButton();
	}
	//Getter
	public final Gui_Satellite getSatellite() {
		return gui_satellite_list.getLast();
	}
	public final Selectable_Satellite getSelected_Satellite() {
		return gui_satellite_choice.getSelected_Satellite();
	}
	public BooleanProperty getBooleanProperty_fromPlay_Stop() {//Property value play_stop state
		return gui_play_stop.getBooleanProperty();
	}
	public final ObservableValue<? extends Number>  getCanvaswidthProperty() {//Property value width
		return canvas.widthProperty();
	}
	public final ObservableValue<? extends Number>  getCanvasheightProperty() {//Property value height
		return canvas.heightProperty();
	}
	//Setter
	//Canvas principal
	private final <T extends Event> void addEventFiltertoCanvas(EventType<T> eventType, EventHandler<? super T> eventFilter) {
		canvas.addEventFilter(eventType, eventFilter);
	}
	public final <T extends Event> void addEventHandlertoCanvas(EventType<T> eventType, EventHandler<? super T> eventHandler) {
		canvas.addEventHandler(eventType, eventHandler);
	}
	//Put Button
	public final <T extends Event> void addEventFilterToPutButton(EventType<T> eventType, EventHandler<? super T> eventFilter) {
		gui_satellite_choice.getPutButton().addEventFilter(eventType, eventFilter);
	}
	public final <T extends Event> void addEventHandlerToPutButton(EventType<T> eventType, EventHandler<? super T> eventHandler) {
		gui_satellite_choice.getPutButton().addEventHandler(eventType, eventHandler);
	}
	private void setEventFilterToCanvas() {
		EventHandler<MouseEvent> click = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Gui_Satellite tmp = Gui_Satellite.instance(getSelected_Satellite().getRayon(),getSelected_Satellite().getColor());//A changer
				
				tmp.setCenterX(event.getSceneX());
				tmp.setCenterY(event.getSceneY());
				gui_satellite_list.add(tmp);
			}
		};
		addEventFiltertoCanvas(MouseEvent.MOUSE_RELEASED, click);
	}
	public final void setEventFilterToPutButton() {
		EventHandler<MouseEvent> click = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Gui_Satellite tmp = Gui_Satellite.instance(getSelected_Satellite().getRayon(),getSelected_Satellite().getColor());//A changer
				
				tmp.setCenterX(getSelected_Satellite().getCenterX()-tmp.getRayon());
				tmp.setCenterY(getSelected_Satellite().getCenterY()-tmp.getRayon());
				gui_satellite_list.add(tmp);
			}
		};
		addEventFilterToPutButton(MouseEvent.MOUSE_CLICKED, click);
	}
	public final void bind(ObservableValue<? extends Number> readOnlyDoubleProperty,ObservableValue<? extends Number> readOnlyDoubleProperty2) {
		canvas.widthProperty().bind(readOnlyDoubleProperty);
		canvas.heightProperty().bind(readOnlyDoubleProperty2);
	}
	//Graphics
	@Override
	public void render() {
		context.save();
		context.setFill(Color.BLANCHEDALMOND.brighter());
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Gui_Satellite tmp:gui_satellite_list)context.drawImage(tmp.getImage(), tmp.getLayoutX(), tmp.getLayoutY());
		context.restore();
	}

}
