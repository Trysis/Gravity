package gui;

import gameInterfaces.Renderer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Gui_Satellite extends Canvas implements Renderer,Selectable_Satellite {
	public final static Color BASE_FILL_COLOR=Color.WHITE;
	private Color fillColor=BASE_FILL_COLOR;
	
	private GraphicsContext context=this.getGraphicsContext2D();
	
	private DoubleProperty rayon = new SimpleDoubleProperty(0);//Rayon
	private DoubleProperty masse = new SimpleDoubleProperty(0);//Masse
	
	private DoubleProperty end_x= new SimpleDoubleProperty(0);//Direction en x du vecteur
	private DoubleProperty end_y= new SimpleDoubleProperty(0);//Direction en y du vecteur
	
	{
		this.widthProperty().bind(rayon.multiply(2));
		this.heightProperty().bind(rayon.multiply(2));
	}
	private Gui_Satellite(double masse,double rayon, Color color) {
		this(rayon,color);
		this.setMasse(masse);
	}
	private Gui_Satellite(double rayon,Color color) {
		setRayon(rayon);
		setColor(color);
		init();
	}
	private void init() {
		if(fillColor==null)fillColor=BASE_FILL_COLOR;
		context.fillOval(0, 0, getWidth(), getHeight());
	}
	//Setter
	@Override
	public void setCenter(double x,double y) {
		setCenterX(x);
		setCenterY(y);
	}
	@Override
	public void setCenterX(double x) {
		this.setLayoutX(x-getRayon());
	}
	@Override
	public void setCenterY(double y) {
		this.setLayoutY(y-getRayon());
	}
	@Override
	public void setMasse(double masse) {
		this.masse.set(masse);
	}
	@Override
	public void setRayon(double rayon) {
		this.rayon.set(rayon);
	}
	@Override
	public void setColor(Color color) {
		fillColor=color;
		context.setFill(fillColor);
	}
	//Getter
	@Override
	public double getCenterX() {
		return this.getLayoutX()+getRayon();
	}
	@Override
	public double getCenterY() {
		return this.getLayoutY()+getRayon();
	}
	@Override
	public double getMasse() {
		return masse.get();
	}
	@Override
	public double getRayon() {
		return rayon.get();
	}
	@Override
	public Color getColor() {
		return fillColor;
	}
	@Override
	public Image getImage() {
		SnapshotParameters parametres = new SnapshotParameters();
		parametres.setFill(Color.TRANSPARENT);
		return this.snapshot(parametres, null);
	}
	public DoubleProperty getMasseProperty() {
		return masse;
	}
	public DoubleProperty getRayonProperty() {
		return rayon;
	}
	public DoubleProperty getendXProperty() {
		return end_x;
	}
	public DoubleProperty getendYProperty() {
		return end_y;
	}
	@Override
	public void render() {
		context.save();
		context.clearRect(0, 0, getWidth(), getHeight());
		context.setFill(fillColor);
		context.fillOval(0, 0, getWidth(), getHeight());
		context.strokeLine(getRayon(), getRayon(), getRayon()+end_x.get(), getRayon()+end_y.get());
		context.restore();
	}
	//Static
	public static Gui_Satellite instance(Selectable_Satellite gui_satellite) {
		Gui_Satellite t=new Gui_Satellite(gui_satellite.getMasse(),gui_satellite.getRayon(),gui_satellite.getColor());
		t.setCenter(gui_satellite.getCenterX(), gui_satellite.getCenterY());
		return t;
	}
	public static Gui_Satellite instance(double rayon) {
		return new Gui_Satellite(rayon,BASE_FILL_COLOR);
	}
	public static Gui_Satellite instance(double rayon,Color color) {
		return new Gui_Satellite(rayon,color);
	}
	public static Gui_Satellite instance(double masse,double rayon,Color color) {
		return new Gui_Satellite(masse,rayon,color);
	}
	//
	@Override
	public String toString() {
		return "[x="+getCenterX()+"];[y="+getCenterY()+"]";
	}

}
