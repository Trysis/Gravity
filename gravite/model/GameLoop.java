package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import gameInterfaces.GameObject;
import gameInterfaces.ObservableGameLoop;
import gameInterfaces.Renderer;
import gui.Gui_Satellite;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;

public class GameLoop extends AnimationTimer implements ObservableGameLoop{
	private Renderer vue;//Utile pour mettre a jour l'affichage
	
	private long TempsTotal=0;//Temps total d'animation
	private long lastFrameTime = System.nanoTime();//Temps d'animation
	private boolean enPause=false;//Specifie si l'animation est en pause ou non
	private boolean changed=true;//Specifie si des donnees ont ete mis a jour et doivent etre actualises visuellement 
	
	private HashMap<Integer,GameObject> gameobject_list = new HashMap<>();
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
	//Renderer
	public GameLoop setRenderer(Renderer vue) {
		this.vue=vue;
		return this;
	}
	public void setChanged(boolean b) {
		changed=b;
	}
	//Interface ObservableGameLoop
	@Override
	public void addGameObject(Integer key,GameObject gameobject) {
		gameobject_list.put(key,gameobject);
		changed=true;
	}
	@Override
	public void removeGameObject(Integer key) {
		gameobject_list.remove(key);
		changed=true;
	}
	public void clearGameObject() {
		gameobject_list.clear();
		changed=true;
	}
	@Override
	public void Notify(long t) {
		for(Map.Entry<Integer, GameObject> tmp: gameobject_list.entrySet())tmp.getValue().update(t);
	}
}
