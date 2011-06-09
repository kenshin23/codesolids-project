package com.tutorial.events;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

public class EventsServlet extends WebContainerServlet {

  public ApplicationInstance newApplicationInstance() {
    return new EventsApp();
  }
}
