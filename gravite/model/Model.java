package model;

import java.util.HashMap;
import java.util.Map;

import gameInterfaces.GameObject;
import gui.Gui_Satellite;

public class Model {
	private HashMap<Integer,Satellite> satellite_list = new HashMap<>();
	
	public GameObject addNewSatellite(Gui_Satellite gui_satellite) {
		Satellite satellite = new Satellite(gui_satellite);
		for(Map.Entry<Integer, Satellite> tmp: satellite_list.entrySet()) {
			satellite.addSatellite(tmp.getValue());
			tmp.getValue().addSatellite(satellite);
		}
		satellite_list.put(gui_satellite.hashCode(), satellite);
		return satellite;
	}
	public GameObject removeSatellite(Integer key) {
		return satellite_list.remove(key);
	}
	public HashMap<Integer,GameObject> clearSatellite() {
		HashMap<Integer,GameObject> tmp=new HashMap<>();
		for(Map.Entry<Integer, Satellite> t: satellite_list.entrySet()) {
			tmp.put(t.getKey(), t.getValue());
			removeSatellite(t.getKey());
		}
		return tmp;
	}
}
