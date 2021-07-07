package gui;

import javafx.scene.paint.Color;

public interface Selectable_Satellite extends Selectable {
	public void setCenter(double x,double y);
	public void setCenterX(double x);
	public void setCenterY(double y);
	public void setMasse(double masse);
	public void setRayon(double rayon);
	public void setColor(Color color);
	public double getCenterX();
	public double getCenterY();
	public double getMasse();
	public double getRayon();
	public Color getColor();
}
