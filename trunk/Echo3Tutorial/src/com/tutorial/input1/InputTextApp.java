package com.tutorial.input1;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.MutableStyle;
import nextapp.echo.app.Style;
import nextapp.echo.app.TextField;
import nextapp.echo.app.Window;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

public class InputTextApp extends ApplicationInstance {

  // ----------------------------------------
  // Un estilo predefinido
  // ----------------------------------------

  public static final Style DEFAULT_STYLE;

  static {
	    MutableStyle style = new MutableStyle();

	    style.set(Button.PROPERTY_LINE_WRAP, false);

	    style.set(Button.PROPERTY_BACKGROUND, new Color(0xD2, 0x12, 0xF2));
	    style.set(Button.PROPERTY_BORDER, new Border(1, new Color(0xD6, 0xF3, 0xDE), Border.STYLE_SOLID));

	    style.set(Button.PROPERTY_ROLLOVER_ENABLED, true);
	    style.set(Button.PROPERTY_ROLLOVER_BACKGROUND, new Color(0xDE, 0xF3, 0xFF));
	    style.set(Button.PROPERTY_ROLLOVER_BORDER, new Border(1, new Color(0x82, 0x99, 0xC9), Border.STYLE_SOLID));

	    style.set(Button.PROPERTY_INSETS, new Insets(2));
	    //style.set(Button.PROPERTY_OUTSETS, new Insets(1));

	    style.set(Button.PROPERTY_TEXT_ALIGNMENT, new Alignment(Alignment.TRAILING, Alignment.DEFAULT));
	    style.set(Button.PROPERTY_ICON_TEXT_MARGIN, new Extent(3));
	    //style.set(Button.PROPERTY_MOUSE_CURSOR, CURSOR_POINTER);

	    style.set(Button.PROPERTY_DISABLED_FOREGROUND, Color.YELLOW);

    DEFAULT_STYLE = style;
  }

  // ----------------------------------------

  private String input;
  private Label welcomeLabel;
  private TextField nameField;
  private WindowPane windowPane;

  private static final Extent PX_800 = new Extent(800, Extent.PX);
  private static final Extent PX_400 = new Extent(400, Extent.PX);

  public Window init() {
    Window window = new Window();
    window.setTitle("Hello World");

    ContentPane contentPane = new ContentPane();
    Label label = new Label("Enter name:");

    windowPane = new WindowPane(WindowPane.PROPERTY_MINIMIZE_ICON, PX_400, PX_200);
    windowPane.setTitle("Hello World!");
    windowPane.setInsets(new Insets(20, 20, 20, 20));

    Grid grid = new Grid(4);
    grid.setBackground(Color.YELLOW);

    Button ok = new Button("OK");
    // Es una buena práctica reutilizar los estilos para los botones los componentes, etc
    // Una buena estrategia es declararlos como se pone aquí en una sola clase y reusarlos
    // Echo2 y Echo3 soportan definir los estilos en XML
    //ok.setBorder(new Border(new Extent(2), Color.BLACK, Border.STYLE_SOLID));
    ok.setStyle(DEFAULT_STYLE);

    ok.setActionCommand("submit name");
    ok.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Esto usualmente es una mala práctica
        // Normalmente esto debería ir a una funcion
        // delegada (Ver mas abajo)
        input = nameField.getText();
        welcomeLabel.setText("Hello " + input);
        windowPane.userClose();
      }
    });

    nameField = new TextField();

    Button clear = new Button("clear");
    clear.setStyle(DEFAULT_STYLE);

    clear.setActionCommand("clear name");
    clear.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnClearClicked(e);
      }
    });

    windowPane.add(grid);
    grid.add(label);
    grid.add(nameField);
    grid.add(ok);
    grid.add(clear);
    welcomeLabel = new Label();
    contentPane.add(welcomeLabel);
    contentPane.add(windowPane);
    window.setContent(contentPane);
    return window;
  }

  // Funcion delegada (o como sea que se llame ;-)
  private void btnClearClicked(ActionEvent e) {
    nameField.setText("");
  }
}
