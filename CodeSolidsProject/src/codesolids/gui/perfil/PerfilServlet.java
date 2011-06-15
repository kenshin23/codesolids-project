package codesolids.gui.perfil;
import codesolids.gui.perfil.PerfilApp;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

/**
 * @author Fernando Osuna
 */

@SuppressWarnings("serial")

public class PerfilServlet extends WebContainerServlet{

	@Override
	public ApplicationInstance newApplicationInstance() {
		return new PerfilApp();
	}

}
