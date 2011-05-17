package com.tutorial.imageservlet;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

/**
 * @author Demián Gutierrez
 * <br> Created on Jun 24, 2008
 */
public class ImagesAppServlet extends WebContainerServlet {

  public ApplicationInstance newInstance() {
    return new ImagesApp();
  }

  @Override
  public ApplicationInstance newApplicationInstance() {
    return new ImagesApp();
  }
}
