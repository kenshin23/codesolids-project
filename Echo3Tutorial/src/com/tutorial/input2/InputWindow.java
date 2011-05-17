package com.tutorial.input2;

import nextapp.echo.app.Button;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.EventListenerList;

public class InputWindow extends WindowPane {

  private EventListenerList eventListenerList = new EventListenerList();

  private ActionListenerProxy actionListenerProxy = //
  new ActionListenerProxy(eventListenerList);

  private TextField txtName;

  // --------------------------------------------------------------------------------

  public InputWindow() {
    setTitle("Hello World!");
    setInsets(new Insets(5, 5, 5, 5));

    initGUI();
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {

    // ----------------------------------------
    // Layout stuff
    // ----------------------------------------

    Grid grid = new Grid(2);
    add(grid);

    Label label = new Label("Enter Name:");
    grid.add(label);

    Row rowField = new Row();
    grid.add(rowField);

    txtName = new TextField();
    rowField.add(txtName);

    Button btnClear = new Button("Clear");
    btnClear.setStyle(GUIStyles.DEFAULT_STYLE);
    btnClear.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnClearClicked(evt);
      }
    });
    rowField.add(btnClear);

    Row rowCommand = new Row();
    rowCommand.setCellSpacing(new Extent(5, Extent.PX));
    rowCommand.setInsets(new Insets(0, 5, 0, 0));
    grid.add(rowCommand);

    Button btnOK = new Button("OK");
    btnOK.setStyle(GUIStyles.DEFAULT_STYLE);
    btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnOKClicked(evt);
      }
    });
    rowCommand.add(btnOK);

    Button btnCancel = new Button("Cancelar");
    btnCancel.setStyle(GUIStyles.DEFAULT_STYLE);
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnCancelClicked(evt);
      }
    });
    rowCommand.add(btnCancel);
  }

  protected void btnOKClicked(ActionEvent evt) {
    actionListenerProxy.fireActionEvent(new ActionEvent(this, null));
    userClose();
  }

  protected void btnCancelClicked(ActionEvent evt) {
    userClose();
  }

  private void btnClearClicked(ActionEvent e) {
    txtName.setText("");
  }

  // --------------------------------------------------------------------------------

  public ActionListenerProxy getActionListenerProxy() {
    return actionListenerProxy;
  }

  // --------------------------------------------------------------------------------

  public String getName() {
    return txtName.getText();
  }
}
