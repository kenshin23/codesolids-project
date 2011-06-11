package codesolids.gui.tienda;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

/**
 * @author Fernando Osuna
 */
@SuppressWarnings("serial")
public class TiendaApp extends ApplicationInstance {
	public Window init() {
		Window ventana = new Window();
		ventana.setTitle("Tienda");
		
		TiendaDesktop tiendaDesktop = new TiendaDesktop();
		ventana.setContent(tiendaDesktop);
		return ventana;
	}

}
