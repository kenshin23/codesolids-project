package codesolids.gui.validacion;

/*
 * 
 * @autor:Hector Prada
 */

 
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

public class validacionApp extends ApplicationInstance {


	   // private ContentPane contentpane = new ContentPane();

	    public Window init() {
	    	
	    	Window mainWindow = new Window();
	        Validacion validacion = new Validacion();
	        //contentpane.add(new MapaJuego()); //MUY IMPORTANTE AJURO HAY QUE CREAR UN CONTENTPANE PARA AGREGARSELO AL WINDOW, NO SE PUEDE AGREGAR UN PANEL DIRECTO AL WINDOW
	        mainWindow.setContent(validacion);
	        return mainWindow;
	    }
	}

