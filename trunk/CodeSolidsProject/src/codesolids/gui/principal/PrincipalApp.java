package codesolids.gui.principal;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

public class PrincipalApp extends ApplicationInstance {
	
	public Window init(){
		Window window = new Window();
		window.setTitle("CodeSolids Project");
		
	    PrincipalDesktop principalDesktop = new PrincipalDesktop();
	    window.setContent(principalDesktop);
	    
	    return window;
	}

}
