package com.tutorial.input2;

import nextapp.echo.app.Button;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

public class Desktop extends ContentPane {

  private Label lblName;

  // --------------------------------------------------------------------------------

  public Desktop() {
    initGUI();
    setInsets(new Insets(5, 5, 5, 5));
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {

    // ----------------------------------------
    // Layout stuff
    // ----------------------------------------

    Row row = new Row();
    row.setCellSpacing(new Extent(5, Extent.PX));
    add(row);

    // ----------------------------------------

    Button btnAskForName = new Button("Preguntar Nombre");
    btnAskForName.setStyle(GUIStyles.DEFAULT_STYLE);

    // Clase / Interfaz Anonima
    btnAskForName.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnAskForNameClicked(evt);
      }
    });
    row.add(btnAskForName);

    // ----------------------------------------

    lblName = new Label("Sin nombre...");
    row.add(lblName);
  }

  // --------------------------------------------------------------------------------

  private void btnAskForNameClicked(ActionEvent evt) {
    InputWindow inputWindow = new InputWindow();
    add(inputWindow);

    inputWindow.getActionListenerProxy().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        inputWindowClicked(evt);
      }
    });
  }

  // --------------------------------------------------------------------------------

  private void inputWindowClicked(ActionEvent evt) {
    InputWindow inputWindow = (InputWindow) evt.getSource();
    lblName.setText(inputWindow.getName());
  }
}
