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
	
	protected Vecteur vitesse=new Vecteur(0,0);
	protected Vecteur acceleration=new Vecteur(0,0);
	
	protected double taille=0;//Nombre de satellites
	protected double somme_x=0;//Somme des positions en x des satellites
	protected double somme_y=0;//Somme des positions en y des satellites
	protected Gui_Satellite gui_satellite;
	public Satellite(Gui_Satellite gui_satellite) {
		this.gui_satellite=gui_satellite;
		x.set(gui_satellite.getLayoutX());
		y.set(gui_satellite.getLayoutY());
		//Properties of layout
		gui_satellite.layoutXProperty().bind(x);//Lie la valeur en x du layout au x du Satellite
		gui_satellite.layoutYProperty().bind(y);//Lie la valeur en y du layout en y du Satellite
		//Properties of vector
		gui_satellite.getendXProperty().bind(vitesse.getXproperty().multiply(gui_satellite.getRayon()));
		gui_satellite.getendYProperty().bind(vitesse.getYproperty().multiply(gui_satellite.getRayon()));
	}
	@Override
	public void updateData(long n) {//Permet de mettre a jours la valeurs du vecteur (trajectoire)
		for(Satellite tmp: satellite_list) {
			tmp.somme_x-=this.getX();
			tmp.somme_y-=this.getY();
		}
		acceleration.setVecteur(somme_x-taille*this.getX(),somme_y-taille*this.getY()).setMagnitude(1).normaliser();
		
		vitesse.addVecteur(acceleration);
		
		x.set(x.get()+vitesse.getMultiplyVecteur(1).getX_Magnitude());
		y.set(y.get()+vitesse.getMultiplyVecteur(1).getY_Magnitude());
		
		for(Satellite tmp: satellite_list) {
			tmp.somme_x+=this.getX();
			tmp.somme_y+=this.getY();
		}
		gui_satellite.render();
	}
	//Ajouts - remove
	public void addSatellite(Satellite satellite) {
		taille++;
		somme_x+=satellite.getX();
		somme_y+=satellite.getY();
		satellite_list.add(satellite);
	}
	public void addSatellite(LinkedList<Satellite> satellite) {
		for(Satellite tmp: satellite)addSatellite(tmp);
	}
	public void removeSatellite(Satellite satellite) {
		taille--;
		somme_x-=satellite.getX();
		somme_y-=satellite.getY();
		satellite_list.remove(satellite);
	}
	public void clearSatellite() {
		for(Satellite tmp: satellite_list)removeSatellite(tmp);
	}
	
	//Getter
	public double getX() {
		return x.get();
	}
	public double getY() {
		return y.get();
	}
	public Vecteur getVecteur() {
		return vitesse;
	}
}
