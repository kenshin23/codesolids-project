package com.tutorial.imagemap;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

/**
 * @author Demián Gutierrez
 * <br> Created on Jun 24, 2008
 */
public class ImageMapServlet extends WebContainerServlet {

  public ApplicationInstance newInstance() {
    return new ImageMapApp();
  }

  @Override
  public ApplicationInstance newApplicationInstance() {
    return new ImageMapApp();
  }
}
