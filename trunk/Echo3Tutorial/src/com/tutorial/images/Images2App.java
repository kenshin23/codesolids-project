package com.tutorial.images;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Grid;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.Window;
import nextapp.echo.app.Font.Typeface;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.GridLayoutData;
import nextapp.echo.app.layout.RowLayoutData;

/**
 * @author Demián Gutierrez
 * <br> Created on Jun 24, 2008
 */
public class Images2App extends ApplicationInstance {

  private String[] imagePath = new String[]{ //
  /**/"com/tutorial/images/acercademinotauroh1.jpg", //
      "com/tutorial/images/acercademinotauroh2.jpg", //
      "com/tutorial/images/acercademinotauroh3.jpg"};

  private int currentImageIndex = 0;

  private int labelCount = 640;

  private Label[] labelArray = new Label[labelCount];
  private Label lblData;

  public Window init() {
    Window window = new Window();

    ContentPane contentPane = new ContentPane();
    contentPane.setInsets(new Insets(2, 2, 2, 2));
    window.setContent(contentPane);

    Column col = new Column();
    col.setCellSpacing(new Extent(4));
    contentPane.add(col);

    // ----------------------------------------
    // El título, el boton y el label de memoria
    // ----------------------------------------

    Label lblTitle = new Label("Images 2");
    lblTitle.setFont(new Font(new Typeface("SansSerif"), Font.BOLD, new Extent(20, Extent.PT)));
    col.add(lblTitle);

    Row row = new Row();
    col.add(row);

    Button btnSiguiente = new Button("Siguiente ->");
    btnSiguiente.setBorder(new Border(new Extent(1), Color.BLACK, Border.STYLE_SOLID));
    btnSiguiente.setInsets(new Insets(3, 3, 3, 3));
    btnSiguiente.setBackground(Color.LIGHTGRAY);
    btnSiguiente.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnSiguienteClicked();
      }
    });
    row.add(btnSiguiente);

    lblData = new Label(memoryReport());
    RowLayoutData rld = new RowLayoutData();
    rld.setInsets(new Insets(3, 3, 3, 3));
    lblData.setLayoutData(rld);
    row.add(lblData);

    // ----------------------------------------
    // El grid con los labels
    // ----------------------------------------

    Grid g = new Grid(4);

    // XXX: Esta es la alternativa correcta, una misma instancia de
    // ImageReference para todos los labels con el mismo ícono.
    //ImageReference ir = new ResourceImageReference(imagePath[currentImageIndex]);

    for (int i = 0; i < labelCount; i++) {

      // XXX: Aquí el consumo de memoria es mucho mayor, y el navegador
      // tiene que cargar múltiples imágenes a si sean la misma. Esto
      // se puede ver con un debugger (como firebug) y por la diferencia
      // el tiempo de carga entre esta alternativa y con el ImageReference
      // fuera del lazo
      ImageReference ir = new ResourceImageReference(imagePath[currentImageIndex]);

      labelArray[i] = new Label(ir);
      GridLayoutData gld = new GridLayoutData();
      gld.setInsets(new Insets(3, 3, 3, 3));
      labelArray[i].setLayoutData(gld);
      g.add(labelArray[i]);
    }

    col.add(g);

    Column buttonColumn = new Column();
    Button button1 = new Button("First Button");
    buttonColumn.add(button1);
    Button button2 = new Button("Second Button");
    button2.setBackground(Color.GREEN);
    buttonColumn.add(button2);

    return window;
  }

  protected void btnSiguienteClicked() {
    currentImageIndex = (currentImageIndex + 1) % imagePath.length;

    updateImages();
  }

  private void updateImages() {

    // XXX: Esta es la alternativa correcta, una misma instancia de
    // ImageReference para todos los labels con el mismo ícono.
    //ImageReference ir = new ResourceImageReference(imagePath[currentImageIndex]);

    for (int i = 0; i < labelArray.length; i++) {

      // XXX: Aquí el consumo de memoria es mucho mayor, y el navegador
      // tiene que cargar múltiples imágenes a si sean la misma. Esto
      // se puede ver con un debugger (como firebug) y por la diferencia
      // el tiempo de carga entre esta alternativa y con el ImageReference
      // fuera del lazo
      ImageReference ir = new ResourceImageReference(imagePath[currentImageIndex]);
      labelArray[i].setIcon(ir);
    }

    lblData.setText(memoryReport());
  }

  public String memoryReport() {
    return "Free: " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + //
        " / Max: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + //
        " / Total: " + Runtime.getRuntime().totalMemory() / 1024 / 1024;
  }
}
