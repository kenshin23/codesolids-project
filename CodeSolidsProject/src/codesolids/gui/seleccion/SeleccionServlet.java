package codesolids.gui.seleccion;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

public class SeleccionServlet extends WebContainerServlet{
	
	public ApplicationInstance newApplicationInstance() {
	
		return new SeleccionApp();
	
	}

	
}
