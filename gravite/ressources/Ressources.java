package ressources;

import java.io.File;
import java.net.MalformedURLException;

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
}
