package com.tutorial.table.copy;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

public class TableServlet extends WebContainerServlet {

  @Override
  public ApplicationInstance newApplicationInstance() {
    return new TableApp();
  }
}
