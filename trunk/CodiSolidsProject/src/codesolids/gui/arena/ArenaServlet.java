package codesolids.gui.arena;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

public class ArenaServlet extends WebContainerServlet  {
	
	public ApplicationInstance newApplicationInstance(){
		
		return new ArenaApp();
		
	}
}
