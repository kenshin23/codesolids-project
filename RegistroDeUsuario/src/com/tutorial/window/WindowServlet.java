package com.tutorial.window;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

public class WindowServlet extends WebContainerServlet {

  public ApplicationInstance newApplicationInstance() {
    return new WindowApp();
  }
}
