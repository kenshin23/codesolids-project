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
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
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
public class Images3App extends ApplicationInstance {

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

    Label lblTitle = new Label("Images 3");
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

    for (int i = 0; i < labelCount; i++) {
      // XXX El cache usando un singleton resuelve el problema de mantener la traza de todas las imágenes
      labelArray[i] = new Label( //
          ImageReferenceCache.getInstance().getImageReference(imagePath[currentImageIndex]));
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
    for (int i = 0; i < labelArray.length; i++) {

      // XXX El cache usando un singleton resuelve el problema de mantener la traza de todas las imágenes
      labelArray[i].setIcon(ImageReferenceCache.getInstance().getImageReference(imagePath[currentImageIndex]));
    }

    lblData.setText(memoryReport());
  }

  public String memoryReport() {
    return "Free: " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + //
        " / Max: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + //
        " / Total: " + Runtime.getRuntime().totalMemory() / 1024 / 1024;
  }
}
