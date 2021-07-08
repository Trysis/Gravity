package model;

import java.util.LinkedList;

import gameInterfaces.GameObject;
import gui.Gui_Satellite;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Satellite implements GameObject{
	//public static double G=6.673*Math.pow(10, -11);//Constante gravitationnelle en N.m².kg-²
	//protected double masse=5*Math.pow(10, 14);//Masse en kg (ici de la Terre *10^-10)
	double G=3;
	private LinkedList<Satellite> satellite_list=new LinkedList<>();//Liste des autres satellites
	
	protected DoubleProperty x = new SimpleDoubleProperty();//Centre en x
	protected DoubleProperty y = new SimpleDoubleProperty();//Centre en y
	protected DoubleProperty masse = new SimpleDoubleProperty();//Masse
	protected DoubleProperty rayon = new SimpleDoubleProperty();//Rayon
	
	protected Vecteur vitesse=new Vecteur();
	protected Vecteur acceleration=new Vecteur();
	
	protected double taille=0;//Nombre de satellites
	protected double somme_x=0;//Somme des positions en x des satellites
	protected double somme_y=0;//Somme des positions en y des satellites
	protected Gui_Satellite gui_satellite;
	protected double rendering_speed=1;
	
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
	public double getCarreDistance(Satellite b) {
		return (b.getX()-this.getX())*(b.getX()-this.getX())+(b.getY()-this.getY())*(b.getY()-this.getY());
	}
	public double getDistance(Satellite b) {
		return Math.sqrt(getCarreDistance(b));
	}
	public double getForce(Satellite b) {//Expression de la loi de Newton
		double distance = constraint(getDistance(b),0,1000);
		return (G*this.getMasse()*b.getMasse())/(distance*distance);
	}
	public Vecteur getVecteurVers(Satellite b) {//Crée un vecteur amenant this.Satellite au Satellite b
		return new Vecteur(b.getX()-this.getX(),b.getY()-this.getY());
	}
	public boolean collide(Satellite b) {//Provisoire, car ne prend pas en charge des polygons de forme non circulaire
		return getCarreDistance(b)<=(b.getRayon()+this.getRayon())*(b.getRayon()+this.getRayon());
	}
	private double constraint(double x,double x1,double x2) {//x compris entre [x1;x2]
		if(x>x2)return x2;
		return x<x1 ? x1:x;
	}
	public void applyForce(Satellite b) {//Applique la force d'attraction gravitationnelle sur le Satellite b
		//F = m*a
		Vecteur force = b.getVecteurVers(this).normaliser().multiplyVecteur(b.getMasse());
		if(b.getMasse()!=0)force.multiplyVecteur(b.getForce(this)/(b.getMasse()*b.getMasse()));
		b.getVecteurAcceleration().addVecteur(force);
	}
	public void onCollision(Satellite b) {//Collision "response" //TODO applications vectorielles d'entre-choc à faire
		//https://ericleong.me/research/circle-circle/ Formula
		double dist = getDistance(b);
		double midpointx = (this.getX() + b.getX()) / 2; 
		double midpointy = (this.getY() + b.getY()) / 2;
		
		double c1_x = midpointx + this.getRayon() * (this.getX() - b.getX()) / dist; 
		double c1_y = midpointy + this.getRayon() * (this.getY() - b.getY()) / dist; 
		double c2_x = midpointx + b.getRayon() * (b.getX() - this.getX()) / dist; 
		double c2_y = midpointy + b.getRayon() * (b.getY() - this.getY()) / dist;
		
		this.x.set(c1_x);
		this.y.set(c1_y);
		b.x.set(c2_x);
		b.y.set(c2_y);
		
		dist = getDistance(b);
		
		double cx2=b.getX();
		double cx1=this.getX();
		double cy2=b.getY();
		double cy1=this.getY();
		double circle1_vx=this.getVecteurVitesse().getX_Magnitude();
		double circle1_vy=this.getVecteurVitesse().getY_Magnitude();
		double circle1_masse=this.getMasse();
		
		double circle2_vx=b.getVecteurVitesse().getX_Magnitude();
		double circle2_vy=b.getVecteurVitesse().getY_Magnitude();
		double circle2_masse=b.getMasse();
		
		double nx = (cx2 - cx1) / dist; 
		double ny = (cy2 - cy1) / dist; 
		double p = 2 * (circle1_vx * nx + circle1_vy * ny - circle2_vx * nx - circle2_vy * ny) / 
		        (circle1_masse + circle2_masse); 
		double vx1 = circle1_vx - p * circle1_masse * nx; 
		double vy1 = circle1_vy - p * circle1_masse * ny; 
		double vx2 = circle2_vx + p * circle2_masse * nx; 
		double vy2 = circle2_vy + p * circle2_masse * ny;
		
		this.getVecteurVitesse().setVecteur(vx1, vy1);
		b.getVecteurVitesse().setVecteur(vx2, vy2);
	}

	@Override
	public void updateData(long n) {//Permet de mettre a jours la valeurs du vecteur (trajectoire)
		for(Satellite tmp: satellite_list)tmp.applyForce(this);
		
		getVecteurVitesse().addVecteur(getVecteurAcceleration());
		getVecteurAcceleration().setVecteur(0, 0);
		
		x.set(x.get()+vitesse.getX_Magnitude()*rendering_speed);
		y.set(y.get()+vitesse.getY_Magnitude()*rendering_speed);
		
		for(Satellite tmp: satellite_list)if(this.collide(tmp))this.onCollision(tmp);
		//gui_satellite.render();
	}
	//Ajouts - remove
	public void addSatellite(Satellite satellite) {
		taille++;
		somme_x+=satellite.getX();
		somme_y+=satellite.getY();
		if(satellite.collide(this))satellite.onCollision(this);//Résolution partielle, 
		//ne prend pas en compte une résolution d'une collision qui en entraîne une autre
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
	public Vecteur getVecteurVitesse() {
		return vitesse;
	}
	public Vecteur getVecteurAcceleration() {
		return acceleration;
	}
	public String toString() {
		return "Position ["+this.getX()+";"+this.getY()+"]\n"+"Vecteur vitesse = "+getVecteurVitesse();
	}
}
