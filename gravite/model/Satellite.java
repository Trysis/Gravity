package model;

import java.util.LinkedList;

import gameInterfaces.GameObject;
import gui.Gui_Satellite;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Satellite implements GameObject{
	//public static double G=6.673*Math.pow(10, -11);//Constante gravitationnelle en N.m².kg-²
	//protected double masse=5*Math.pow(10, 14);//Masse en kg (ici de la Terre *10^-10)
	double G=1;
	private LinkedList<Satellite> satellite_list=new LinkedList<>();//Liste des autres satellites
	
	protected DoubleProperty x = new SimpleDoubleProperty(0);//Centre en x
	protected DoubleProperty y = new SimpleDoubleProperty(0);//Centre en y
	protected DoubleProperty masse = new SimpleDoubleProperty(0);//Masse
	protected DoubleProperty rayon = new SimpleDoubleProperty(1);//Rayon
	
	protected Vecteur vitesse=new Vecteur(0,0);
	protected Vecteur acceleration=new Vecteur(0,0);
	
	protected double taille=0;//Nombre de satellites
	protected double somme_x=0;//Somme des positions en x des satellites
	protected double somme_y=0;//Somme des positions en y des satellites
	protected Gui_Satellite gui_satellite;
	public Satellite(Gui_Satellite gui_satellite) {
		this.gui_satellite=gui_satellite;
		
		rayon.set(gui_satellite.getRayon());
		masse.set(gui_satellite.getMasse());
		x.set(gui_satellite.getLayoutX()+rayon.get());
		y.set(gui_satellite.getLayoutY()+rayon.get());
		//Properties of layout
		gui_satellite.getRayonProperty().bind(rayon);//Lie la valeur du rayon du gui_Satellite au rayon du Satellite
		gui_satellite.getMasseProperty().bind(masse);//Lie la valeur de la masse du Gui_Sallelite au Satellite correspondant
		gui_satellite.layoutXProperty().bind(x.subtract(rayon.get()));//Lie la valeur en x du layout au x du Satellite
		gui_satellite.layoutYProperty().bind(y.subtract(rayon.get()));//Lie la valeur en y du layout en y du Satellite
		//Properties of vector
		gui_satellite.getendXProperty().bind(vitesse.getXproperty().multiply(gui_satellite.getRayon()));
		gui_satellite.getendYProperty().bind(vitesse.getYproperty().multiply(gui_satellite.getRayon()));
	}
	public double getMasse() {
		return this.masse.get();
	}
	public double getRayon() {
		return this.rayon.get();
	}
	public boolean collide(Satellite b) {//Provisoire, car ne prend pas en charge des polygons de forme non circulaire
		return getDistance(b)<=this.gui_satellite.getRayon()+b.gui_satellite.getRayon();
	}
	public void onCollision(Satellite b) {//Collision "response" //TODO applications vectorielles d'entre-choc à faire
		double rayons = this.getRayon()+b.getRayon();
		Vecteur vect = getVecteurVers(b).normaliser().multiplyVecteur(rayons+1);
		b.x.set(this.getX()+vect.getX_Magnitude());
		b.y.set(this.getY()+vect.getY_Magnitude());
	}
	public double getDistance(Satellite b) {
		return Math.sqrt(Math.pow((b.getX()-this.getX()), 2)+Math.pow(b.getY()-this.getY(), 2));
	}
	private double constraint(double x,double x1,double x2) {
		if(x>x2)return x2;
		return x<x1 ? x1:x;
	}
	public double getForce(Satellite b) {//Expression de la loi de Newton
		double distance = constraint(getDistance(b),this.gui_satellite.getRayon()+b.gui_satellite.getRayon(),500);
		return (G*this.getMasse()*b.getMasse())/(distance*distance);
	}
	public Vecteur getVecteurVers(Satellite b) {//Crée un vecteur amenant this.Satellite au Satellite b
		return new Vecteur(b.getX()-this.getX(),b.getY()-this.getY());
	}
	public void applyForce(Satellite b) {
		//F = m*a
		Vecteur force = getVecteurVers(b).normaliser().multiplyVecteur(getMasse());
		if(getMasse()!=0)force.multiplyVecteur(getForce(b)/(getMasse()*getMasse()));
		acceleration.addVecteur(force);
	}
	@Override
	public void updateData(long n) {//Permet de mettre a jours la valeurs du vecteur (trajectoire)
		for(Satellite tmp: satellite_list) {
			applyForce(tmp);
		}
		vitesse.addVecteur(acceleration);
		acceleration.setVecteur(0, 0);
		
		x.set(x.get()+vitesse.getNewMultiplyVecteur(1).getX_Magnitude());
		y.set(y.get()+vitesse.getNewMultiplyVecteur(1).getY_Magnitude());
		for(Satellite tmp: satellite_list)if(tmp.collide(this))tmp.onCollision(this);
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
