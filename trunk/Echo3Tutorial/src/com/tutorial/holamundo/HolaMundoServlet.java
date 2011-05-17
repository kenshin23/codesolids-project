package com.tutorial.holamundo;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

// El servlet de echo siempre debe heredar de WebContainerServlet
public class HolaMundoServlet extends WebContainerServlet {

  // En este método retorna la ApplicationInstance que
  // representa toda la aplicación
  
  public ApplicationInstance newApplicationInstance() {
    return new HolaMundoApp();
  }
}
