package codesolids.gui.validacion;

/*
 * 
 * @autor:Hector Prada
 */


import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

/**
 * @author hector prada
 * 
 */
public class validacionAppServlet extends WebContainerServlet {
	
  @Override
  public ApplicationInstance newApplicationInstance() {
    return new validacionApp();
  }
}
