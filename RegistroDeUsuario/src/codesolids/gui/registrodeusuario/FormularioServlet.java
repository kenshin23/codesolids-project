package codesolids.gui.registrodeusuario;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;


/**
 * @author Karla Moreno
 */


public class FormularioServlet extends WebContainerServlet{
	
	@Override
	public ApplicationInstance newApplicationInstance() {
		return new FormularioApp();
	}

}