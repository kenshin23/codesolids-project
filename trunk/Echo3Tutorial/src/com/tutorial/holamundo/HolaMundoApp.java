package com.tutorial.holamundo;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Label;
import nextapp.echo.app.Window;

public class HolaMundoApp extends ApplicationInstance {

  public Window init() {

    // ----------------------------------------
    // Construye una ventana (del navegador)
    // ----------------------------------------

    Window window = new Window();
    window.setTitle("Hola Mundo");

    // ----------------------------------------
    // Construye un panel raiz y se lo asigna
    // a la ventana
    // ----------------------------------------

    ContentPane contentPane = new ContentPane();
    window.setContent(contentPane);

    // ----------------------------------------
    // Construye la GUI y la añade al panel
    // raiz recien construido
    // ----------------------------------------

    Label label = new Label("¡¡¡Hola Mundo!!!");
    contentPane.add(label);

    //    ProgressBar pb = new ProgressBar();
    //    pb.setBackground(Color.RED);
    //    pb.setForeground(Color.BLUE);
    //    pb.setBarBackground(Color.CYAN);
    //    pb.setPercentage(50);
    //    contentPane.add(pb);

    return window;
  }
}
