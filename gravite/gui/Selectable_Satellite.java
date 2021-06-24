package gui;

import javafx.scene.paint.Color;

public interface Selectable_Satellite extends Selectable {
	public void setCenterX(double x);
	public void setCenterY(double y);
	public void setRayon(double rayon);
	public void setColor(Color color);
	public double getCenterX();
	public double getCenterY();
	public double getRayon();
	public Color getColor();
}
