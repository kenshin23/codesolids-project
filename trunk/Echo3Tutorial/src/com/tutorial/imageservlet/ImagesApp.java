package com.tutorial.imageservlet;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.HttpImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.Window;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

/**
 * @author Demián Gutierrez
 * <br> Created on Jun 24, 2008
 */
public class ImagesApp extends ApplicationInstance {

  private Label lblImage;
  private TextField txtId;

  // --------------------------------------------------------------------------------

  public Window init() {
    Window window = new Window();

    ContentPane contentPane = new ContentPane();
    contentPane.setInsets(new Insets(2, 2, 2, 2));
    window.setContent(contentPane);

    Column col = new Column();
    col.setCellSpacing(new Extent(5));
    contentPane.add(col);

    // HttpImageReference ir = new HttpImageReference(
    //   "http://www.google.com/intl/en_ALL/images/srpr/logo1w.png");

    HttpImageReference ir = new HttpImageReference("imagesdata?image_id=-1");

    lblImage = new Label("Hello, world!", ir);
    col.add(lblImage);

    Row row = new Row();
    row.setCellSpacing(new Extent(5));
    col.add(row);

    txtId = new TextField();
    row.add(txtId);

    Button btnGo = new Button("Go...");
    btnGo.setToolTipText("Este es el tooltip");
    btnGo.setStyle(GUIStyles.DEFAULT_STYLE);
    btnGo.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        btnGoClicked();
      }
    });
    row.add(btnGo);

    return window;
  }

  // --------------------------------------------------------------------------------

  private void btnGoClicked() {
    HttpImageReference ir = new HttpImageReference( //
        "imagesdata?image_id=" + txtId.getText());

    lblImage.setIcon(ir);
  }
}
