package com.tutorial.input2;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

public class InputTextServlet extends WebContainerServlet {

  public ApplicationInstance newApplicationInstance() {
    return new InputTextApp();
  }
}
