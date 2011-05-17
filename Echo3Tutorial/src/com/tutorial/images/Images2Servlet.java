package com.tutorial.images;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

/**
 * @author Demián Gutierrez
 * <br> Created on Jun 24, 2008
 */
public class Images2Servlet extends WebContainerServlet {

  public ApplicationInstance newInstance() {
    return new Images2App();
  }

  @Override
  public ApplicationInstance newApplicationInstance() {
    return new Images2App();
  }
}
