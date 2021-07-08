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

	EventHandler<MouseEvent> click_g = (MouseEvent) ->{
		game_loop.addGameObject(vue.getSatellite().hashCode(),model.addSatellite(vue.getSatellite()));
	};
	EventHandler<MouseEvent> click_d = (MouseEvent) ->{
		//game_loop.removeGameObject(model.removeSatellite(/*Clicked On*.getId()/));
	};
	public Controleur(Model model, Vue vue){
		this.vue=vue;
		this.model=model;
		
		setBooleanListener();
		setCanvasSizeListener();
		setEventHandlerToCanvas();
		setEventHandlerToPutButton();
		
		game_loop.setRenderer(vue).start();
	}
	private void setBooleanListener() {//Boolean listener pour mettre en pause ou play les animations
		vue.getBooleanProperty_fromPlay_Stop().addListener(e->{
			if(vue.getBooleanProperty_fromPlay_Stop().get())game_loop.start();
			else game_loop.stop();
		});
	}
	private void setCanvasSizeListener() {//Size listener pour mettre a jour l'affichage seulement lorsque la taille de la fenetre change
		vue.getCanvaswidthProperty().addListener(e->{
			game_loop.setChanged(true);
		});
		vue.getCanvasheightProperty().addListener(e->{
			game_loop.setChanged(true);
		});
	}
	private void setEventHandlerToCanvas() {//Event permettant lors d'un clique sur Canvas de creer un satellite
		vue.addEventHandlertoCanvas(MouseEvent.MOUSE_RELEASED, click_g);
	}
	private void setEventHandlerToPutButton() {
		vue.addEventHandlerToPutButton(MouseEvent.MOUSE_CLICKED, click_g);
	}
}
