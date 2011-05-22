package MapaJuego.Codesolids;


import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Panel;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Column;
import nextapp.echo.app.Color;
import nextapp.echo.app.Border;
import nextapp.echo.app.Label;
import nextapp.echo.app.Font;
import nextapp.echo.app.Window;
import nextapp.echo.app.layout.ColumnLayoutData;
import nextapp.echo.app.Alignment;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Button;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

public class MapaJuego extends Panel {


	protected MapaJuego() {
		super();
		
		constructorComponentes();
	}

	private void constructorComponentes() {
		//creo el panel exterior inicial, y le coloco el tamaño deseado. comienzo con un panel para darle el tamañi final a la ventana
		
		this.setWidth(new Extent(1280, Extent.PX));
		this.setHeight(new Extent(983, Extent.PX));
	
	
		//este contentpane es el que en realidad me contiene todos los elementos abajo.
		ContentPane cpPrincipal = new ContentPane();
		cpPrincipal.setEnabled(true);
		add(cpPrincipal);
		
		//creo la columna principal con bordes
		Column colPrincipal = new Column();
		colPrincipal.setBorder(new Border(new Extent(5, Extent.PX), new Color(0xd4630c), Border.STYLE_DOUBLE));
		colPrincipal.setBackground(Color.BLACK);
		colPrincipal.setEnabled(true);
		cpPrincipal.add(colPrincipal);
		
		//este label simplemente esta ubicado en el centro de la columna y es el titulo de la pantalla
		Label tituloPantalla = new Label();
		tituloPantalla.setFont(new Font(new Font.Typeface("ARIAL"), Font.ITALIC | Font.BOLD, new Extent(16, Extent.PT)));
		tituloPantalla.setForeground(new Color(0xd4630c));
		tituloPantalla.setText("MAPA PRINCIPAL");

		ColumnLayoutData tituloPantallaLD = new ColumnLayoutData();
		tituloPantallaLD.setAlignment(new Alignment(Alignment.CENTER,Alignment.DEFAULT));
		tituloPantalla.setLayoutData(tituloPantallaLD);
		colPrincipal.add(tituloPantalla);
		
		//este panel solo agrega una linea de color de altura 1 pixel(igual que las demas ventanas) para separar el titulo del background
		Panel panelLinea = new Panel();
		ColumnLayoutData panelLineaLD = new ColumnLayoutData();
		panelLineaLD.setBackground(new Color(0xd4630c));
		panelLineaLD.setHeight(new Extent(1, Extent.PX));
		panelLinea.setLayoutData(panelLineaLD);
		panelLinea.setEnabled(true);
		colPrincipal.add(panelLinea);
		
		//este panel contiene la foto background de la pantalla inicial y le acomode el la altura acorde a la imagen
		Panel panelImagenBG = new Panel();
		ColumnLayoutData panelImagenBGLD = new ColumnLayoutData();
		ResourceImageReference ir = new ResourceImageReference("/MapaJuego/Codesolids/pantalla_inicial.jpg");
		panelImagenBGLD.setHeight(new Extent(947, Extent.PX));
		panelImagenBG.setEnabled(true);
		panelImagenBGLD.setBackgroundImage(new FillImage(ir));
		panelImagenBG.setLayoutData(panelImagenBGLD);
		colPrincipal.add(panelImagenBG);
	
		//este cuarto panel contiene la columna con los botones, hecha especificamente para ubicarlos usando el inset property en la parte inferior de la pantalla
		//me parecio mas sencillo que usar la columna solamente y ubicar cada boton, aqui solo muevo el panel y no cada boton dentro de la columna
		Panel panelColBotones = new Panel();
		panelColBotones.setInsets(new Insets(new Extent(580, Extent.PX), new Extent(700,Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		panelImagenBG.add(panelColBotones);
		Column colBotones = new Column();
		colBotones.setCellSpacing(new Extent(10, Extent.PX));
		colBotones.setEnabled(true);
		panelColBotones.add(colBotones);
	
		// de aqui para abajo son los botones usados en la pantalla inicial usando el stylesheet en ButtonStyle Y SU RESPECTIVO ACTIONLISTENER
		Button button1 = new Button();
		button1.setText("ARENA");
		button1.setStyle(ButtonStyle.BOTONERA_PANTALLA_PRINCIPAL);
		//button1.setRolloverFont(new Font(new Font.Typeface("ARIAL"), Font.BOLD	| Font.ITALIC, new Extent(16, Extent.PT)));
		//button1.setFont(new Font(new Font.Typeface("arial"), Font.BOLD| Font.ITALIC, new Extent(13, Extent.PT)));
		//button1.setRolloverForeground(new Color(0xb81833));
		//button1.setForeground(Color.BLACK);
		//button1.setRolloverEnabled(true); 
		//LO DEJO COMO REFERENCIA!!!
		
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button1Clicked(e);
			}
		});
		
		colBotones.add(button1);
		
		
		Button button2 = new Button();
		button2.setText("ACADEMIA");
		button2.setStyle(ButtonStyle.BOTONERA_PANTALLA_PRINCIPAL);
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button2Clicked(e);
				}
		});
		colBotones.add(button2);
		
		
		Button button3 = new Button();
		button3.setText("TIENDA");
		button3.setStyle(ButtonStyle.BOTONERA_PANTALLA_PRINCIPAL);
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button3Clicked(e);
					}
		});
		colBotones.add(button3);
		
		
		Button button4 = new Button();
		button4.setText("CLANES");
		button4.setStyle(ButtonStyle.BOTONERA_PANTALLA_PRINCIPAL);
		
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button4Clicked(e);	
			}
		});
		colBotones.add(button4);
		
		
		Button button5 = new Button();
		button5.setText("PERFIL");
		button5.setStyle(ButtonStyle.BOTONERA_PANTALLA_PRINCIPAL);
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button5Clicked(e);
			}
		});
		colBotones.add(button5);
		
		
		Button button6 = new Button();
		button6.setText("RANKING");
		button6.setStyle(ButtonStyle.BOTONERA_PANTALLA_PRINCIPAL);
		
		button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button6Clicked(e);
			}
		});
		colBotones.add(button6);
		
		
		Button button7 = new Button();
		button7.setText("CHAT");
		button7.setStyle(ButtonStyle.BOTONERA_PANTALLA_PRINCIPAL);
		button7.setRolloverEnabled(true);
		
		button7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button7Clicked(e);
			}
		});
		colBotones.add(button7);
		
		
	}
	
	//funciones para los actionlistener
	private void button1Clicked(ActionEvent e) {
		
		removeAll();
		add(new Arena());
	}
	private void button5Clicked(ActionEvent e) {
		
		removeAll();
		add(new Perfil());
	}
	private void button6Clicked(ActionEvent e) {
		
		removeAll();
		add(new Ranking());
	}
	private void button4Clicked(ActionEvent e) {
	
		removeAll();
		add(new Clanes());
	}
	private void button3Clicked(ActionEvent e) {
	
		removeAll();
		add(new Tienda());
	}
	private void button2Clicked(ActionEvent e) {
	
		removeAll();
		add(new Academia());
	
	}
	private void button7Clicked(ActionEvent e) {
		
		removeAll();
		add(new Chat());
	}
		
	
}
