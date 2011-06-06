package codesolids.gui.mapa;

/*
 * 
 * @autor:Hector Prada
 */

 
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Window;

public class MapaApp extends ApplicationInstance {


	   // private ContentPane contentpane = new ContentPane();

	    public Window init() {
	    	
	    	Window mainWindow = new Window();
	        MapaDesktop mapadesktop = new MapaDesktop();
	        //contentpane.add(new MapaJuego()); //MUY IMPORTANTE AJURO HAY QUE CREAR UN CONTENTPANE PARA AGREGARSELO AL WINDOW, NO SE PUEDE AGREGAR UN PANEL DIRECTO AL WINDOW
	        mainWindow.setContent(mapadesktop);
	        return mainWindow;
	    }
	}

