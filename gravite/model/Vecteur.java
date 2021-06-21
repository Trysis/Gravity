package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Vecteur {
	private DoubleProperty x = new SimpleDoubleProperty(0);//abscisse du vecteur
	private DoubleProperty y = new SimpleDoubleProperty(0);//ordonnee du vecteur
	private double scaling=1;
	//Constructeur
	public Vecteur() {
		this(0,0);
	}
	public Vecteur(double scaling) {
		this();
		this.scaling=scaling;
	}
	public Vecteur(double x,double y) {
		this.x.set(x);
		this.y.set(y);
	}
	public Vecteur(double x,double y,double scaling) {
		this(x,y);
		this.scaling=scaling;
	}
	public Vecteur normaliser() {//Ramène le vecteur sur des normes (rayon 1 max)
		double longueur=longueurVecteur();
		if(longueur!=0) {
			x.set(x.get()/longueur);
			y.set(y.get()/longueur);
		}
		return this;
	}
	//Getter
	public double getX() {
		return this.x.get();
	}
	public double getY() {
		return this.y.get();
	}
	public DoubleProperty getXproperty() {
		return x;
	}
	public DoubleProperty getYproperty() {
		return y;
	}
	//Setter
	public void setX(double x) {
		this.x.set(x);
	}
	public void setY(double y) {
		this.y.set(y);
	}
	public void set(double x,double y) {
		setX(x);
		setY(y);
	}
	public void setVelocite(double scaling) {
		this.scaling=scaling;
	}
	//Calculs numériques sur vecteur
	public Vecteur addVecteur(Vecteur vecteur) {
		x.set(x.get()+vecteur.getX());
		y.set(y.get()+vecteur.getY());
		return this;
	}
	public Vecteur multiplyVecteur(Vecteur vecteur) {//Inutile puisqu'on normalise
		x.set(x.get()*vecteur.getX());
		y.set(y.get()*vecteur.getY());
		return this;
	}
	public Vecteur subVecteur(Vecteur vecteur) {
		x.set(x.get()-vecteur.getX());
		y.set(y.get()-vecteur.getY());
		opposateVecteur();
		return this;
	}
	public Vecteur subVecteur(double x,double y) {
		return subVecteur(new Vecteur(x,y));
	}
	//Produit scalaire
	public void addScalaire(double valeur) {//Produit scalaire
		x.set(x.get()*valeur);
		y.set(y.get()*valeur);
	}
	public double getScalaire(Vecteur vecteur) {//get
		return getX()*vecteur.getX()+getY()*vecteur.getY();
	}
	//Image / Inverse d'un vecteur
	public void opposateXVecteur() {
		x.set(-x.get());
	}
	public void opposateYVecteur() {
		y.set(-y.get());
	}
	public void opposateVecteur() {
		opposateXVecteur();
		opposateYVecteur();
	}
	//Getter
	public double getVelociteX() {
		return getX()*scaling;
	}
	public double getVelociteY() {
		return getY()*scaling;
	}
	//Longueur du vecteur
	public double longueurVecteur() {
		return Math.pow(getX()*getX()+getY()*getY(), 0.5);
	}
	public String toString() {
		return "Direction = "+x+";"+y+" speed ="+scaling;
	}
}
