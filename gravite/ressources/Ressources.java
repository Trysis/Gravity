package ressources;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public final class Ressources {
	public final static String PATH = "src/ressources/";
	public final static String PATH_toItems = PATH+"Items/";
	public final static String PATH_toSatellite = PATH+"Satellites/";
	public final static File Fichier_toFile(String Path,String File_Name,String extension) {
		return new File(Path+File_Name+"."+extension);
	}
	public final static String Fichier_toStringPath(String Path,String File_Name,String extension) {
		try {
			return Fichier_toFile(Path,File_Name,extension).toURI().toURL().toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return "";
	}
	public final static Image getImage(String text) {
		SnapshotParameters parametres = new SnapshotParameters();
		parametres.setFill(Color.TRANSPARENT);
		Text t=new Text(text);
		t.setFont(new Font(15));
		return t.snapshot(parametres, null);
	}
}
