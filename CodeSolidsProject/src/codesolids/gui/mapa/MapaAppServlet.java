package codesolids.gui.mapa;

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
public class MapaAppServlet extends WebContainerServlet {
	
  @Override
  public ApplicationInstance newApplicationInstance() {
    return new MapaApp();
  }
}
