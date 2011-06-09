package codesolids.gui.registrodeusuario;

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
	 * @author Karla Moreno
	 */

	public class FormularioApp extends ApplicationInstance {
		public Window init() {
		
			Window ventana = new Window();
			ventana.setTitle("Registro de Usuario");
			
			Desktop desktop = new Desktop();
			ventana.setContent(desktop);
			return ventana;
		}

	}

