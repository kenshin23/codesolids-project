package codesolids.gui.seleccion;

import codesolids.bd.clases.Usuario;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

/**
 * 
 * @author Antonio López
 *
 */

public class SeleccionApp extends ApplicationInstance {

	public Window init() {
		
		Window ventana = new Window();
		ventana.setTitle("Seleccion Personaje");
		
		DesktopSelect desktop = new DesktopSelect();
		ventana.setContent(desktop);
		
		return ventana;
	}

}
