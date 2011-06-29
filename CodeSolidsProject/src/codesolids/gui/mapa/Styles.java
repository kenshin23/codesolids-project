package codesolids.gui.mapa;
/*
 * 
 * @autor:Hector Prada
 */


import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Font;
import nextapp.echo.app.Font.Typeface;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.MutableStyle;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Style;

public class Styles {

  public static final Style BOTONERA_PANTALLA_PRINCIPAL;
  static {
    MutableStyle style = new MutableStyle();

    style.set(Button.PROPERTY_FONT, new Font(new Font.Typeface("ARIAL") , Font.BOLD | Font.ITALIC, new Extent(13, Extent.PT)));
    style.set(Button.PROPERTY_FOREGROUND, Color.BLACK);
    style.set(Button.PROPERTY_ROLLOVER_ENABLED, true);
    style.set(Button.PROPERTY_ROLLOVER_FOREGROUND, new Color(0xb81833));
    style.set(Button.PROPERTY_ROLLOVER_FONT, new Font(new Font.Typeface("ARIAL") , Font.BOLD | Font.ITALIC, new Extent(16, Extent.PT)));

    BOTONERA_PANTALLA_PRINCIPAL = style;
  }
  
  public static final Style BOTON_REGRESAR;
  static {
	  MutableStyle estilo2 = new MutableStyle();

	    estilo2.set(Button.PROPERTY_FONT, new Font(new Font.Typeface("ARIAL") , Font.BOLD | Font.ITALIC, new Extent(15, Extent.PT)));
	    estilo2.set(Button.PROPERTY_FOREGROUND, Color.MAGENTA);
	    estilo2.set(Button.PROPERTY_ROLLOVER_ENABLED, true);
	    estilo2.set(Button.PROPERTY_ROLLOVER_FOREGROUND, Color.CYAN);
	    estilo2.set(Button.PROPERTY_ROLLOVER_FONT, new Font(new Font.Typeface("ARIAL") , Font.BOLD | Font.ITALIC, new Extent(20, Extent.PT)));
	  
	  
	  BOTON_REGRESAR = estilo2;
  }
  
  public static final Style BOTONES_LOGIN_SCREEN;
  static{
	ResourceImageReference espada2 = new ResourceImageReference("/Images/Mapa/Killarity_Sword.png");
	MutableStyle estilo3 = new MutableStyle();
	
	estilo3.set(Button.PROPERTY_ROLLOVER_ENABLED, true);
	estilo3.set(Button.PROPERTY_ROLLOVER_FONT, new Font(null, Font.PLAIN, new Extent(11, Extent.PT)));
	estilo3.set(Button.PROPERTY_ROLLOVER_BACKGROUND_IMAGE, new FillImage(espada2));
	estilo3.set(Button.PROPERTY_ROLLOVER_BORDER, new Border(new Extent(5, Extent.PX),new Color(0x0033ff), Border.STYLE_RIDGE));
	estilo3.set(Button.PROPERTY_ROLLOVER_BACKGROUND, new Color(0x4cbfe9));
	estilo3.set(Button.PROPERTY_FONT, new Font(null, Font.PLAIN,new Extent(10, Extent.PT)));
	estilo3.set(Button.PROPERTY_BACKGROUND, new Color(0x4cbfe9));
	estilo3.set(Button.PROPERTY_BACKGROUND_IMAGE,new FillImage(espada2));

	estilo3.set(Button.PROPERTY_BORDER, new Border(new Extent(2, Extent.PX), new Color(0x0033ff), Border.STYLE_RIDGE));
	
	BOTONES_LOGIN_SCREEN = estilo3;
  }
//  
//  public static final Style TXT_PASSW_FIELD;
//  static{
//	ResourceImageReference espada1 = new ResourceImageReference("/map/128px-AoL_Sword.png");
//	MutableStyle estilo4 = new MutableStyle();
//  
//	estilo4.set(PasswordField.Property
//	contrasena.setEnabled(true);
//	contrasena.setVisible(true);
//	contrasena.setFont(new Font(null, Font.PLAIN, new Extent(12, Extent.PT)));
//	contrasena.setBackground(new Color(0xe1eefa));
//	contrasena.setBackgroundImage(new FillImage(espada1));
//	contrasena.setBorder(new Border(new Extent(3, Extent.PX), new Color(0x002aff), Border.STYLE_RIDGE));
//	
//	
//	
//	
//	
//	
//  }

}


// style.set(Button.PROPERTY_BORDER, new Border(1, new Color(0xD6, 0xD3, 0xCE), Border.STYLE_SOLID));
//style.set(Button.PROPERTY_ROLLOVER_FONT, new Font.Typeface("ARIAL"));
//style.set(Button.PROPERTY_OUTSETS, new Insets(1));
//style.set(Button.PROPERTY_MOUSE_CURSOR, CURSOR_POINTER);
//style.set(Button.PROPERTY_INSETS, new Insets(2));
// style.set(Button.PROPERTY_TEXT_ALIGNMENT, new Alignment(Alignment.TRAILING, Alignment.DEFAULT));
// style.set(Button.PROPERTY_ICON_TEXT_MARGIN, new Extent(3));
//style.set(Button.PROPERTY_DISABLED_FOREGROUND, Color.LIGHTGRAY);
//style.set(Button.PROPERTY_LINE_WRAP, false);