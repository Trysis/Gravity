package model;

import java.util.LinkedList;

import gameInterfaces.GameObject;
import gui.Gui_Satellite;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Satellite implements GameObject{
	public static double CONSTANTE_GRAVITATIONNEL=6.673*Math.pow(10, -11);//en N.m².kg-²
	protected double masse=0;//Masse en kg

	private LinkedList<Satellite> satellite_list=new LinkedList<>();//Liste des autres satellites
	protected DoubleProperty x = new SimpleDoubleProperty(0);//Centre en x
	protected DoubleProperty y = new SimpleDoubleProperty(0);//Centre en y
	
	protected Vecteur vecteur=new Vecteur(0,0);
	
	protected Gui_Satellite gui_satellite;
	public Satellite(Gui_Satellite gui_satellite) {
		this.gui_satellite=gui_satellite;
		x.set(gui_satellite.getLayoutX());
		y.set(gui_satellite.getLayoutY());
		gui_satellite.layoutXProperty().bind(x);//Lie la valeur en x du layout au x du Satellite
		gui_satellite.layoutYProperty().bind(y);//Lie la valeur en y du layout en y du Satellite
		//
		gui_satellite.getendXProperty().bind(vecteur.getXproperty().multiply(gui_satellite.getRayon()));
		gui_satellite.getendYProperty().bind(vecteur.getYproperty().multiply(gui_satellite.getRayon()));
	}
	@Override
	public void updateData(long n) {//n-1+4 operations Permet de mettre a jours la valeurs du vecteur (trajectoire)
		double tmp_x=0;
		double tmp_y=0;
		for(Satellite tmp: satellite_list) {
			tmp_x+=tmp.getX()-getX();
			tmp_y+=tmp.getY()-getY();
		}
		vecteur.subVecteur(tmp_x,tmp_y);
		
		x.set(x.get()+vecteur.getVelociteX());
		y.set(y.get()+vecteur.getVelociteY());
		gui_satellite.render();
	}
	//Ajouts - remove
	public void addSatellite(Satellite satellite) {
		satellite_list.add(satellite);
	}
	public void addSatellite(LinkedList<Satellite> satellite) {
		satellite_list.addAll(satellite);
	}
	public void removeSatellite(Satellite satellite) {
		satellite_list.remove(satellite);
	}
	public void clearSatellite() {
		satellite_list.clear();
	}
	//Getter
	public double getX() {
		return x.get();
	}
	public double getY() {
		return y.get();
	}
	public Vecteur getVecteur() {
		return vecteur;
	}
}
