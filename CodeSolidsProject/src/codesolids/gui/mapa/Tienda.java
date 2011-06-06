package codesolids.gui.mapa;


import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Panel;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Column;
import nextapp.echo.app.Color;
import nextapp.echo.app.Border;
import nextapp.echo.app.Label;
import nextapp.echo.app.Font;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.ColumnLayoutData;
import nextapp.echo.app.Alignment;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Button;

public class Tienda extends ContentPane {
	Panel panel = new Panel();
	protected Tienda() {
		super(); // SIN ESTE SUPER FUE UN DOLOR PARA QUE FUNCIONARA, este salva vidas

		constructorComp();
	}
	
	private void constructorComp() {
		//al panel inicial le coloco las medidas exactas a usar
		panel.setHeight(new Extent(983, Extent.PX));
		panel.setWidth(new Extent (1280, Extent.PX));
		add(panel);
		ContentPane cpExterno = new ContentPane();
		cpExterno.setEnabled(true);
		panel.add(cpExterno);
		
		//agrego la columna que contiene los paneles y sus caracteristicas
		
		Column columnaExterna = new Column();
		columnaExterna.setBorder(new Border(new Extent(5, Extent.PX), new Color(0xd4630c), Border.STYLE_DOUBLE));
		columnaExterna.setEnabled(true);
		columnaExterna.setBackground(Color.BLACK);
		cpExterno.add(columnaExterna);
		
		//agrego titulo superior y caracteristicas
		
		Label tituloSuperior = new Label();
		tituloSuperior.setFont(new Font(new Font.Typeface("ARIAL"), Font.BOLD | Font.ITALIC, new Extent(16, Extent.PT)));
		tituloSuperior.setForeground(new Color(0xd4630c));					
		tituloSuperior.setText("TIENDA");

		//creo el columnLayoutdata para centrar el titulo superior en la columna
		
		ColumnLayoutData tituloSuperiorLD = new ColumnLayoutData();
		tituloSuperiorLD.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		tituloSuperior.setLayoutData(tituloSuperiorLD);
		columnaExterna.add(tituloSuperior);
		
		//se podia usar un splitpane como el ejemplo de de Echo3Tutorial/panelchange, usando el separador estatico
		//creo mas sencillo usar el panel de altura 1pixel y agregarle color
		
		Panel lineaSeparadora = new Panel();
		lineaSeparadora.setEnabled(true);
		ColumnLayoutData lineaSeparadoraLD = new ColumnLayoutData();
		lineaSeparadoraLD.setBackground(new Color(0xd4630c));
		lineaSeparadoraLD.setHeight(new Extent(1, Extent.PX));
		lineaSeparadora.setLayoutData(lineaSeparadoraLD);
		columnaExterna.add(lineaSeparadora);
		
		//este panel es el que efectivamente muestra el background que se ve en cada pantalla, le modifico el HEIGHT para 947, si no se lo
		//agregaba no me muestra la imagen completa, me costo que me funcionara la funcion de Fillimage usando el No_Repeat, ya que la estaba usando 
		//en minusculas
		
		Panel panelBackground = new Panel();
		panelBackground.setEnabled(true);
		ColumnLayoutData panelBakcgroundLD = new ColumnLayoutData();
		panelBakcgroundLD.setHeight(new Extent(947, Extent.PX));
		ResourceImageReference ir = new ResourceImageReference("/images/Mapa/mago_hielo2.jpg");
	    panelBakcgroundLD.setBackgroundImage(new FillImage(ir,new Extent(50, Extent.PERCENT), new Extent(50, Extent.PERCENT),FillImage.NO_REPEAT));
		panelBackground.setLayoutData(panelBakcgroundLD);
		columnaExterna.add(panelBackground);
		
		//este panel contiene la columna que finalmente contiene el boton de regresar a la pantalla inicial
		//en realidad es overkill usar el panel para una columna con un solo boton, ya que se puede agregar el boton dentro de la columna y se le acomodan 
		//los insets adecuadamente, pero lo dejo asi por si acaso le agregamos mas botones a cada pantalla, es bastante util
		
		Panel panelContenedorCol = new Panel();
		panelContenedorCol.setInsets(new Insets(new Extent(580, Extent.PX), new Extent(700,Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		panelBackground.add(panelContenedorCol);
		Column columnaBoton = new Column();
		columnaBoton.setEnabled(true);
		columnaBoton.setCellSpacing(new Extent(10, Extent.PX));
		panelContenedorCol.add(columnaBoton);
		
		//el boton de regresar es creado con propiedades rollover y foreground, para el futuro deseo agregarle mas caracteristicas, si es posible  
		
		Button returnButton = new Button();
		returnButton.setText("REGRESAR");
		returnButton.setStyle(ButtonStyle.BOTON_REGRESAR);
		returnButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			button1Clicked(e);
				
			}
		});
		columnaBoton.add(returnButton);
	}
		private void button1Clicked(ActionEvent e) {
		
		removeAll();
		add(new MapaDesktop());//muchahos esta parte fue un dolor de cabeza, despues de muchos intentos porfin pude utilizar el actionevent para crear un nuevo panel
	}
}

//NOTA: VERAN QUE A CADA ELEMENTO TUVE QUE AGREGARLE LA PROPIEDAD SETENABLED(TRUE), YA QUE PARA USAR LA PROPIEDAD ROLLOVER DEL BOTON NO ME QUERIA 
//HACER EL EFECTO SIN ESE SETENABLED(TRUE). A SER SINCERO NO ESTOY SEGURO POR QUE ES ASI, PÈRO FUNCIONA :)