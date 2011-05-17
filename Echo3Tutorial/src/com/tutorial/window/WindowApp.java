package com.tutorial.window;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Label;
import nextapp.echo.app.Window;
import nextapp.echo.app.WindowPane;

public class WindowApp extends ApplicationInstance {

  private WindowPane windowPane;

  public Window init() {
    Window window = new Window();
    window.setTitle("Window1");

    ContentPane contentPane = new ContentPane();
    window.setContent(contentPane);

    windowPane = new WindowPane();
    windowPane.setTitle("Window");
    windowPane.add(new Label("New Window"));
    contentPane.add(windowPane);

    return window;
  }
}
