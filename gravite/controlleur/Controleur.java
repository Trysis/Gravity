package controlleur;

import gui.Vue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.GameLoop;
import model.Model;

public class Controleur {
	private GameLoop game_loop=new GameLoop();
	private Model model;
	private Vue vue;

	EventHandler<MouseEvent> click_g = (MouseEvent) ->{//Ajout d'un Satellite
		game_loop.addGameObject(vue.getSatellite().hashCode(),model.addSatellite(vue.getSatellite()));
	};
	EventHandler<MouseEvent> click_d = (MouseEvent) ->{//Suppression d'un Satellite
		//game_loop.removeGameObject(model.removeSatellite(/*Clicked On*.getId()/));
	};
	public Controleur(Model model, Vue vue){
		this.vue=vue;
		this.model=model;
		
		setBooleanListener();
		setCanvasSizeListener();
		setEventHandlerToCanvas();
		setEventHandlerToPutButton();
		setEventFilterToCanvas();
		setEventFilterToPutButton();
		
		game_loop.setRenderer(vue).start();
	}
	private void setBooleanListener() {//Boolean listener, permet de mettre en pause/play l'affichage
		vue.getBooleanProperty_fromPlay_Stop().addListener(e->{
			if(vue.getBooleanProperty_fromPlay_Stop().get())game_loop.start();
			else game_loop.stop();
		});
	}
	private void setCanvasSizeListener() {//Size listener permet à la Game_loop d'actualiser l'affichage après une redimension
		vue.getCanvaswidthProperty().addListener(e->{
			game_loop.setChanged(true);
		});
		vue.getCanvasheightProperty().addListener(e->{
			game_loop.setChanged(true);
		});
	}
	private void setEventHandlerToCanvas() {//Event qui se produit après l'EventFilter 
		vue.addEventHandlertoCanvas(MouseEvent.MOUSE_CLICKED, click_g);//Permet de créer un Satellite lors d'un clique sur le Canvas
	}
	private void setEventHandlerToPutButton() {//Set l'EventHandler , Event qui se produit après l'EventFilter 
		vue.addEventHandlerToPutButton(MouseEvent.MOUSE_CLICKED, click_g);//Permet de créer un Satellite lors d'un clique sur le Bouton
	}
	private void setEventFilterToCanvas() {//Set l'EventFilter , Event qui se produit avant l'EventHandler
		vue.setEventFilterToCanvas(MouseEvent.MOUSE_CLICKED);//Permet de créer un Gui_Satellite lors d'un clique sur le Canvas
	}
	private void setEventFilterToPutButton() {//Set l'EventFilter , Event qui se produit avant l'EventHandler
		vue.setEventFilterToPutButton(MouseEvent.MOUSE_CLICKED);//Permet de créer un Gui_Satellite lors d'un clique sur le bouton
	}
}
