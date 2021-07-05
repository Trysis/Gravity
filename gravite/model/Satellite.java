package model;

import java.util.LinkedList;

import gameInterfaces.GameObject;
import gui.Gui_Satellite;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Satellite implements GameObject{
	public static double G=6.673*Math.pow(10, -11);//Constante gravitationnelle en N.m².kg-²
	protected double masse=15.972*Math.pow(10, Math.random()*24);//Masse en kg (ici de la Terre)
	
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
		System.out.println(masse);
		x.set(gui_satellite.getLayoutX());
		y.set(gui_satellite.getLayoutY());
		//Properties of layout
		gui_satellite.layoutXProperty().bind(x);//Lie la valeur en x du layout au x du Satellite
		gui_satellite.layoutYProperty().bind(y);//Lie la valeur en y du layout en y du Satellite
		//Properties of vector
		gui_satellite.getendXProperty().bind(vitesse.getXproperty().multiply(gui_satellite.getRayon()));
		gui_satellite.getendYProperty().bind(vitesse.getYproperty().multiply(gui_satellite.getRayon()));
	}
	public double getMasse() {
		return this.masse;
	}
	public double getDistance(Satellite b) {
		return Math.sqrt(Math.pow((b.getX()-this.getX()), 2)+Math.pow(b.getY()-this.getY(), 2));
	}
	public double getForce(Satellite b) {//Expression de la loi de Newton
		return G*(this.getMasse()*b.getMasse())/Math.pow(getDistance(b),2);
	}
	public Vecteur getVecteurVers(Satellite b) {//Crée un vecteur amenant this.Satellite au Satellite b
		return new Vecteur(b.getX()-this.getX(),b.getY()-this.getY());
	}
	public void applyForce(Satellite b) {
		//F = m*a
		Vecteur force = getVecteurVers(b).normaliser().multiplyVecteur(getForce(b)/getMasse());
		acceleration.addVecteur(force);
	}
	@Override
	public void updateData(long n) {//Permet de mettre a jours la valeurs du vecteur (trajectoire)
		for(Satellite tmp: satellite_list)applyForce(tmp);
		vitesse.addVecteur(acceleration);
		
		x.set(x.get()+vitesse.getNewMultiplyVecteur(1).getX_Magnitude());
		y.set(y.get()+vitesse.getNewMultiplyVecteur(1).getY_Magnitude());
		
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
