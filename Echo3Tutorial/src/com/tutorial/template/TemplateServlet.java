package com.tutorial.template;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;
import nextapp.echo.webcontainer.WebContainerServlet;

public class TemplateServlet extends WebContainerServlet {

  private Window window;

  public ApplicationInstance newApplicationInstance() {
    ApplicationInstance applicationInstance = new ApplicationInstance() {

      public Window init() {
        window = new Window();
        window.setTitle("Test");
        window.setContent(new TemplateApp(TemplateServlet.this));

        return window;
      }
    };

    return applicationInstance;
  }
}