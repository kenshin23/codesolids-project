package codesolids.gui.tienda;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

/**
 * @author Fernando Osuna
 */


public class TiendaServlet extends WebContainerServlet{
	
	@Override
	public ApplicationInstance newApplicationInstance() {
		return new TiendaApp();
	}

}
