package codesolids.gui.academia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.informagen.echo.app.CapacityBar;

import codesolids.gui.style.Styles1;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.Window;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

/**
 * @author: Antonio López
 * 
 */

public class AcademiaApp extends ApplicationInstance {

	public Window init() {
		Window ventana = new Window();
		ventana.setTitle("Academia");
		
		Desktop desktop = new Desktop();
	    ventana.setContent(desktop);
		return ventana;
	}		
}
