package codesolids.gui.perfil;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

/**
 * @author Fernando Osuna
 */
@SuppressWarnings("serial")

public class PerfilApp extends ApplicationInstance {
	public Window init() {
		Window ventana = new Window();
		ventana.setTitle("Perfil");
		
		PerfilDesktop perfilDesktop = new PerfilDesktop();
		ventana.setContent(perfilDesktop);
		return ventana;
	}

}