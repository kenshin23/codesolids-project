package codesolids.gui.academia;


import codesolids.bd.clases.Usuario;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

/**
 * @author: Antonio López
 * 
 */

public class AcademiaApp extends ApplicationInstance {

	public Window init() {
		Window ventana = new Window();
		ventana.setTitle("Academia");
		
		AcademiaDesktop desktop = new AcademiaDesktop();
	    ventana.setContent(desktop);
		return ventana;
	}		
}
