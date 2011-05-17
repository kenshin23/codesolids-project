package com.tutorial.htmllayout;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;
import nextapp.echo.webcontainer.WebContainerServlet;

public class HtmlLayoutServlet extends WebContainerServlet {

  public ApplicationInstance newApplicationInstance() {
    ApplicationInstance applicationInstance = new ApplicationInstance() {

      public Window init() {
        Window window = new Window();
        window.setTitle("Test");
        window.setContent(new HtmlLayoutApp(HtmlLayoutServlet.this));

        return window;
      }
    };

    return applicationInstance;
  }
}