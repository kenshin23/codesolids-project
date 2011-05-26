package codesolids.gui.academia;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

/*
 * @author: Antonio López
 * 
 */

public class AcademiaServlet extends WebContainerServlet {

	@Override
	public ApplicationInstance newApplicationInstance() {
		return new AcademiaApp();
	}

	
}
