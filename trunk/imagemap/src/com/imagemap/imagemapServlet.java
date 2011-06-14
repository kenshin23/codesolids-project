package com.imagemap;


import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

/**
 * @author Demián Gutierrez
 * <br> Created on Jun 24, 2008
 */
public class imagemapServlet extends WebContainerServlet {

//  public ApplicationInstance newInstance() {
   // return new imagemapApp();
 // }

  @Override
  public ApplicationInstance newApplicationInstance() {
    return new imagemapApp();
  }
}
