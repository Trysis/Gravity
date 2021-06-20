package model;

import java.util.LinkedList;

import gameInterfaces.GameObject;
import gameInterfaces.ObservableGameLoop;
import gameInterfaces.Renderer;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;

public class GameLoop extends AnimationTimer implements ObservableGameLoop{
	private Renderer vue;
	private LinkedList<GameObject> handler=new LinkedList<>();
	private long TempsTotal=0;//Temps total d'animation
	private long lastFrameTime = System.nanoTime();//Temps d'animation
	private boolean enPause=false;//Specifie si l'animation est en pause ou non
	private boolean changed=true;//Specifie si des donnees ont ete mis a jour et doivent etre actualises visuellement 
	//Start - Handle - Stop
	@Override
	public void start() {
		enPause=false;
		changed=true;
		super.start();
	}
	@Override
	public void handle(long now) {
		if(!enPause)Notify(call(now));
		else lastFrameTime=call(now);
		if(changed) {
			vue.render();
			if(enPause)changed=false;
		}
	}
	@Override
	public void stop() {
		enPause=true;
		changed=false;
		//super.stop();
	}
	//Temps calcule (entre maintenant et la fin de l'animation)
	private long call(long now) {
		long secondsSinceLastFrame = (now-lastFrameTime);
		lastFrameTime = now;
		TempsTotal+=secondsSinceLastFrame;
		return secondsSinceLastFrame;
	}
	//Vue
	public GameLoop setVue(Renderer vue) {
		this.vue=vue;
		return this;
	}
	public void setChanged(boolean b) {
		changed=b;
	}
	//Interface ObservableGameLoop
	@Override
	public void addGameObject(GameObject gameobject) {
		handler.add(gameobject);
		changed=true;
	}
	@Override
	public void removeGameObject(GameObject gameobject) {
		handler.remove(gameobject);
		changed=true;
	}
	public void clearGameObject() {
		handler.clear();
		changed=true;
	}
	@Override
	public void Notify(long t) {
		for(GameObject tmp : handler)tmp.update(t);
	}
}
