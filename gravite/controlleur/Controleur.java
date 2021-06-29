package controlleur;

import java.util.LinkedList;

import gui.Vue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.GameLoop;
import model.Satellite;

public class Controleur {
	private GameLoop game_loop;
	private Vue vue;
	private LinkedList<Satellite> satellite_list=new LinkedList<>();//Liste des satellites

	EventHandler<MouseEvent> click = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent arg0) {//2n+2 operations
			Satellite tmp=new Satellite(vue.getSatellite());//Satellite partie model
			tmp.addSatellite(satellite_list);//Ajout des satellites pour le calculs de sa trajectoire
			for(Satellite bis: satellite_list)bis.addSatellite(tmp);
			satellite_list.add(tmp);
			game_loop.addGameObject(tmp);
		}
	};
	public Controleur(GameLoop game_loop, Vue vue){
		this.game_loop=game_loop;
		this.vue=vue;
		
		game_loop.setVue(vue).start();
		
		setBooleanListener();
		setCanvasSizeListener();
		setEventHandlerToCanvas();
		setEventHandlerToPutButton();
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

		vue.addEventHandlertoCanvas(MouseEvent.MOUSE_RELEASED, click);
	}
	private void setEventHandlerToPutButton() {
		vue.addEventHandlerToPutButton(MouseEvent.MOUSE_CLICKED, click);
	}
}
