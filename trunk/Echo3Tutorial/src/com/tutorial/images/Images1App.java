package com.tutorial.images;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Window;

/**
 * @author Demián Gutierrez
 * <br> Created on Jun 24, 2008
 */
public class Images1App extends ApplicationInstance {

  public Window init() {
    Window window = new Window();

    ContentPane contentPane = new ContentPane();
    contentPane.setInsets(new Insets(2, 2, 2, 2));
    window.setContent(contentPane);

    ImageReference ir = new ResourceImageReference( //
        "com/tutorial/images/icono_msn2.gif");

    Label label = new Label("Hello, world!", ir);
    contentPane.add(label);

    return window;
  }
}
