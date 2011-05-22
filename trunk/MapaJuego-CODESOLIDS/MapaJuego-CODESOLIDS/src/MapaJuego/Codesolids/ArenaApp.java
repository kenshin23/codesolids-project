package MapaJuego.Codesolids;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Window;

public class ArenaApp extends ApplicationInstance {

    private Window mainWindow;
    private ContentPane contentpane = new ContentPane();

    public Window init() {

        mainWindow = new Window();
        contentpane.add(new Arena()); //MUY IMPORTANTE AJURO HAY QUE CREAR UN CONTENTPANE PARA AGREGARSELO AL WINDOW, NO SE PUEDE AGREGAR UN PANEL DIRECTO AL WINDOW
        mainWindow.setContent(contentpane);
        return mainWindow;
    }
}

