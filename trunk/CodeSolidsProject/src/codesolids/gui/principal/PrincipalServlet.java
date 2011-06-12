package codesolids.gui.principal;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

public class PrincipalServlet extends WebContainerServlet  {
	
	public ApplicationInstance newApplicationInstance(){
		
		return new PrincipalApp();
		
	}
}
