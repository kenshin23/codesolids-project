package codesolids.gui.mapa;

/*
 * 
 * @autor:Hector Prada
 */


//import echopoint.ImageMap;
//import echopoint.model.CircleSection;
//import nextapp.echo.app.ApplicationInstance;


import nextapp.echo.app.Grid;
import codesolids.gui.arena.*;
import codesolids.gui.tienda.*;
import codesolids.gui.academia.*;
//import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Panel;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ContentPane;
//import nextapp.echo.app.Column;
import nextapp.echo.app.Color;
import nextapp.echo.app.Border;
//import nextapp.echo.app.Label;
//import nextapp.echo.app.Font;
//import nextapp.echo.app.Window;
//import nextapp.echo.app.layout.ColumnLayoutData;
import nextapp.echo.app.layout.GridLayoutData;
//import nextapp.echo.app.Alignment;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Button;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

/**
 * @author Hector Prada
 * 
 */

public class MapaDesktop extends ContentPane {

	Panel panel = new Panel();
	public MapaDesktop() {
		super();
		
		constructorComponentes();
	}

	private void constructorComponentes() {
		ResourceImageReference fondo = new ResourceImageReference("/images/Mapa/fondo.jpg");
		ResourceImageReference perfil_des = new ResourceImageReference("/images/Mapa/perfil_sel.png");
		ResourceImageReference perfil_sel = new ResourceImageReference("/images/Mapa/perfil_des.png");
		ResourceImageReference academia_sel = new ResourceImageReference("/images/Mapa/academia_sel.png");
		ResourceImageReference academia_des = new ResourceImageReference("/images/Mapa/academia_des.png");
		ResourceImageReference ranking_sel = new ResourceImageReference("/images/Mapa/ranking_sel.png");
		ResourceImageReference ranking_des = new ResourceImageReference("/images/Mapa/ranking_des.png");
		ResourceImageReference arena_sel = new ResourceImageReference("/images/Mapa/arena_sel.png");
		ResourceImageReference arena_des = new ResourceImageReference("/images/Mapa/arena_des.png");
		ResourceImageReference clanes_sel = new ResourceImageReference("/images/Mapa/clanes_sel.png");
		ResourceImageReference clanes_des = new ResourceImageReference("/images/Mapa/clanes_des.png");
		ResourceImageReference tienda_sel = new ResourceImageReference("/images/Mapa/tienda_sel.png");
		ResourceImageReference tienda_des = new ResourceImageReference("/images/Mapa/tienda_des.png");
		ResourceImageReference chat_sel = new ResourceImageReference("/images/Mapa/chat_sel.png");
		ResourceImageReference chat_des = new ResourceImageReference("/images/Mapa/chat_des.png");
		
		panel.setEnabled(true);
		panel.setVisible(true);
		panel.setHeight(new Extent(700, Extent.PX));
		panel.setWidth(new Extent(886, Extent.PX));
		panel.setBorder(new Border(new Extent(3, Extent.PX), Color.BLACK,Border.STYLE_DOUBLE));
		panel.setBackgroundImage(new FillImage(fondo));
		add(panel);
		ContentPane cpPrincipal = new ContentPane();
		cpPrincipal.setEnabled(true);
		cpPrincipal.setVisible(true);
		panel.add(cpPrincipal);
		
		Grid gridBotones = new Grid();
		gridBotones.setOrientation(Grid.ORIENTATION_HORIZONTAL);
		gridBotones.setSize(3);
		cpPrincipal.add(gridBotones);
		
		Button perfilBtn = new Button();
		perfilBtn.setEnabled(true);
		perfilBtn.setVisible(true);
		perfilBtn.setRolloverEnabled(true);
		perfilBtn.setHeight(new Extent(204, Extent.PX));
		perfilBtn.setWidth(new Extent(204, Extent.PX));
		perfilBtn.setBackgroundImage(new FillImage(perfil_sel));
		perfilBtn.setRolloverBackgroundImage(new FillImage(perfil_des));
			GridLayoutData perfilBtnLD = new GridLayoutData();
			perfilBtnLD.setInsets(new Insets(new Extent(0, Extent.PX),new Extent(20, Extent.PX), new Extent(0, Extent.PX),new Extent(0, Extent.PX)));
			perfilBtn.setLayoutData(perfilBtnLD);
		perfilBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				perfilBtnClicked(e);
			}
		});
		gridBotones.add(perfilBtn);
		
		Button academiaBtn = new Button();
		academiaBtn.setEnabled(true);
		academiaBtn.setVisible(true);
		academiaBtn.setBackgroundImage(new FillImage(academia_des));
		academiaBtn.setRolloverEnabled(true);
		academiaBtn.setRolloverBackgroundImage(new FillImage(academia_sel));
		academiaBtn.setHeight(new Extent(312, Extent.PX));
		academiaBtn.setWidth(new Extent(346, Extent.PX));
			GridLayoutData academiaBtnLD = new GridLayoutData();
			academiaBtnLD.setInsets(new Insets(new Extent(0, Extent.PX),new Extent(70, Extent.PX), new Extent(0, Extent.PX),new Extent(0, Extent.PX)));
			academiaBtnLD.setRowSpan(2);
			academiaBtn.setLayoutData(academiaBtnLD);
			academiaBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					academiaBtnClicked(e);
				}
			});
		gridBotones.add(academiaBtn);
		Button rankingBtn = new Button();
		
		rankingBtn.setEnabled(true);
		rankingBtn.setRolloverBackgroundImage(new FillImage(ranking_sel));
		rankingBtn.setHeight(new Extent(260, Extent.PX));
		rankingBtn.setVisible(true);
		rankingBtn.setBackgroundImage(new FillImage(ranking_des));
		rankingBtn.setRolloverEnabled(true);
		rankingBtn.setWidth(new Extent(149, Extent.PX));
			GridLayoutData rankingBtnLD = new GridLayoutData();
			rankingBtnLD.setInsets(new Insets(new Extent(30, Extent.PX),new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
			rankingBtnLD.setRowSpan(3);
			rankingBtn.setLayoutData(rankingBtnLD);
		rankingBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rankingBtnClicked(e);
				}
			});
		gridBotones.add(rankingBtn);
		
		Button arenaBtn = new Button();
		arenaBtn.setEnabled(true);
		arenaBtn.setRolloverBackgroundImage(new FillImage(arena_sel));
		arenaBtn.setBackgroundImage(new FillImage(arena_des));
		arenaBtn.setRolloverEnabled(true);
		arenaBtn.setWidth(new Extent(196, Extent.PX));
		arenaBtn.setHeight(new Extent(146, Extent.PX));
		arenaBtn.setVisible(true);
			GridLayoutData arenaBtnLD = new GridLayoutData();
			arenaBtnLD.setInsets(new Insets(new Extent(110, Extent.PX),new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
			arenaBtnLD.setRowSpan(2);
			arenaBtn.setLayoutData(arenaBtnLD);
			arenaBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					arenaBtnClicked(e);
				}
			});
		gridBotones.add(arenaBtn);
		
		Button clanesBtn = new Button();
		clanesBtn.setEnabled(true);
		clanesBtn.setRolloverBackgroundImage(new FillImage(clanes_sel));
		clanesBtn.setBackgroundImage(new FillImage(clanes_des));
		clanesBtn.setRolloverEnabled(true);
		clanesBtn.setHeight(new Extent(179, Extent.PX));
		clanesBtn.setVisible(true);
		clanesBtn.setWidth(new Extent(285, Extent.PX));
			GridLayoutData clanesBtnLD = new GridLayoutData();
			clanesBtnLD.setInsets(new Insets(new Extent(80, Extent.PX),new Extent(30, Extent.PX), new Extent(0, Extent.PX),new Extent(0, Extent.PX)));
			clanesBtnLD.setRowSpan(2);
			clanesBtn.setLayoutData(clanesBtnLD);
		clanesBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clanesBtnClicked(e);
				}
			});
		gridBotones.add(clanesBtn);
		
		Button tiendaBtn = new Button();
		tiendaBtn.setEnabled(true);
		tiendaBtn.setRolloverBackgroundImage(new FillImage(tienda_sel));
		tiendaBtn.setBackgroundImage(new FillImage(tienda_des));
		tiendaBtn.setRolloverEnabled(true);
		tiendaBtn.setHeight(new Extent(208, Extent.PX));
		tiendaBtn.setWidth(new Extent(229, Extent.PX));
		tiendaBtn.setVisible(true);
			GridLayoutData tiendaBtnLD = new GridLayoutData();
			tiendaBtnLD.setInsets(new Insets(new Extent(50, Extent.PX),new Extent(20, Extent.PX), new Extent(0, Extent.PX),new Extent(0, Extent.PX)));
			tiendaBtnLD.setRowSpan(3);
			tiendaBtn.setLayoutData(tiendaBtnLD);
			tiendaBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tiendaBtnClicked(e);
				}
			});
		gridBotones.add(tiendaBtn);
		
		Button chatBtn = new Button();
		chatBtn.setEnabled(true);
		chatBtn.setVisible(true);
		chatBtn.setBackgroundImage(new FillImage(chat_des));
		chatBtn.setRolloverEnabled(true);
		chatBtn.setRolloverBackgroundImage(new FillImage(chat_sel));
		chatBtn.setHeight(new Extent(139, Extent.PX));
		chatBtn.setWidth(new Extent(144, Extent.PX));
			GridLayoutData chatBtnLD = new GridLayoutData();
			chatBtnLD.setInsets(new Insets(new Extent(50, Extent.PX),new Extent(160, Extent.PX), new Extent(0, Extent.PX),new Extent(0, Extent.PX)));
			chatBtnLD.setRowSpan(2);
			chatBtn.setLayoutData(chatBtnLD);
			chatBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					chatBtnClicked(e);
				}
			});
		gridBotones.add(chatBtn);
	}


//funciones para los actionlistener
	private void arenaBtnClicked(ActionEvent e) {
		
		removeAll();
		add(new ArenaDesktop());
	}
	private void perfilBtnClicked(ActionEvent e) {
		
		removeAll();
		add(new Perfil());
	}
	private void rankingBtnClicked(ActionEvent e) {
		
		removeAll();
		add(new Ranking());
	}
	private void clanesBtnClicked(ActionEvent e) {
	
		removeAll();
		add(new Clanes());
	}
	private void tiendaBtnClicked(ActionEvent e) {
	
		removeAll();
		add(new TiendaDesktop());
	}
	private void academiaBtnClicked(ActionEvent e) {
	
		removeAll();
		add(new AcademiaDesktop());
	
	}
	private void chatBtnClicked(ActionEvent e) {
		
		removeAll();
		add(new Chat());
	}
}


	
//		//creo el panel exterior inicial, y le coloco el tamaño deseado. comienzo con un panel para darle el tamañi final a la ventana
//
//		this.setWidth(new Extent(1280, Extent.PX));
//		this.setHeight(new Extent(983, Extent.PX));
//	
//	
//		//este contentpane es el que en realidad me contiene todos los elementos abajo.
//		ContentPane cpPrincipal = new ContentPane();
//		cpPrincipal.setEnabled(true);
//		add(cpPrincipal);
//		
//		//creo la columna principal con bordes
//		Column colPrincipal = new Column();
//		colPrincipal.setBorder(new Border(new Extent(5, Extent.PX), new Color(0xd4630c), Border.STYLE_DOUBLE));
//		colPrincipal.setBackground(Color.BLACK);
//		colPrincipal.setEnabled(true);
//		cpPrincipal.add(colPrincipal);
//		
//		//este label simplemente esta ubicado en el centro de la columna y es el titulo de la pantalla
//		Label tituloPantalla = new Label();
//		tituloPantalla.setFont(new Font(new Font.Typeface("ARIAL"), Font.ITALIC | Font.BOLD, new Extent(16, Extent.PT)));
//		tituloPantalla.setForeground(new Color(0xd4630c));
//		tituloPantalla.setText("MAPA PRINCIPAL");
//
//		ColumnLayoutData tituloPantallaLD = new ColumnLayoutData();
//		tituloPantallaLD.setAlignment(new Alignment(Alignment.CENTER,Alignment.DEFAULT));
//		tituloPantalla.setLayoutData(tituloPantallaLD);
//		colPrincipal.add(tituloPantalla);
//		
//		//este panel solo agrega una linea de color de altura 1 pixel(igual que las demas ventanas) para separar el titulo del background
//		Panel panelLinea = new Panel();
//		ColumnLayoutData panelLineaLD = new ColumnLayoutData();
//		panelLineaLD.setBackground(new Color(0xd4630c));
//		panelLineaLD.setHeight(new Extent(1, Extent.PX));
//		panelLinea.setLayoutData(panelLineaLD);
//		panelLinea.setEnabled(true);
//		colPrincipal.add(panelLinea);
//		
//		//este panel contiene la foto background de la pantalla inicial y le acomode el la altura acorde a la imagen
//		Panel panelImagenBG = new Panel();
//		ColumnLayoutData panelImagenBGLD = new ColumnLayoutData();
//		ImageReference ir = new ResourceImageReference("/codesolids.gui.mapa/pantalla_inicial.jpg");
//		ImageMap imageMap = new ImageMap(ir);
//		imageMap.addSection(new CircleSection(500, 500, 150, "circle"));
//		panelImagenBGLD.setHeight(new Extent(947, Extent.PX));
//		panelImagenBG.setEnabled(true);
//		panelImagenBGLD.setBackgroundImage(new FillImage(ir));
//		panelImagenBG.setLayoutData(panelImagenBGLD);
//		panelImagenBG.add(imageMap);
//	    imageMap.addActionListener(new ActionListener() {
//	        @Override
//	        public void actionPerformed(ActionEvent evt) {
//	          lb  lSelected.setText(evt.getActionCommand());
//	        }
//	      });
//		colPrincipal.add(panelImagenBG);

//	
//		//este cuarto panel contiene la columna con los botones, hecha especificamente para ubicarlos usando el inset property en la parte inferior de la pantalla
//		//me parecio mas sencillo que usar la columna solamente y ubicar cada boton, aqui solo muevo el panel y no cada boton dentro de la columna
//		Panel panelColBotones = new Panel();
//		panelColBotones.setInsets(new Insets(new Extent(580, Extent.PX), new Extent(700,Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
//		panelImagenBG.add(panelColBotones);
//		Column colBotones = new Column();
//		colBotones.setCellSpacing(new Extent(10, Extent.PX));
//		colBotones.setEnabled(true);
//		panelColBotones.add(colBotones);
//	
//		// de aqui para abajo son los botones usados en la pantalla inicial usando el stylesheet en ButtonStyle Y SU RESPECTIVO ACTIONLISTENER
//		Button button1 = new Button();
//		button1.setText("ARENA");
//		button1.setStyle(ButtonStyle.BOTONERA_PANTALLA_PRINCIPAL);
//		//button1.setRolloverFont(new Font(new Font.Typeface("ARIAL"), Font.BOLD	| Font.ITALIC, new Extent(16, Extent.PT)));
//		//button1.setFont(new Font(new Font.Typeface("arial"), Font.BOLD| Font.ITALIC, new Extent(13, Extent.PT)));
//		//button1.setRolloverForeground(new Color(0xb81833));
//		//button1.setForeground(Color.BLACK);
//		//button1.setRolloverEnabled(true); 
//		//LO DEJO COMO REFERENCIA!!!
//		
//		button1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				button1Clicked(e);
//			}
//		});
//		
//		colBotones.add(button1);
//		
//		
//		Button button2 = new Button();
//		button2.setText("ACADEMIA");
//		button2.setStyle(ButtonStyle.BOTONERA_PANTALLA_PRINCIPAL);
//		button2.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				button2Clicked(e);
//				}
//		});
//		colBotones.add(button2);
//		
//		
//		Button button3 = new Button();
//		button3.setText("TIENDA");
//		button3.setStyle(ButtonStyle.BOTONERA_PANTALLA_PRINCIPAL);
//		button3.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				button3Clicked(e);
//					}
//		});
//		colBotones.add(button3);
//		
//		
//		Button button4 = new Button();
//		button4.setText("CLANES");
//		button4.setStyle(ButtonStyle.BOTONERA_PANTALLA_PRINCIPAL);
//		
//		button4.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				button4Clicked(e);	
//			}
//		});
//		colBotones.add(button4);
//		
//		
//		Button button5 = new Button();
//		button5.setText("PERFIL");
//		button5.setStyle(ButtonStyle.BOTONERA_PANTALLA_PRINCIPAL);
//		button5.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				button5Clicked(e);
//			}
//		});
//		colBotones.add(button5);
//		
//		
//		Button button6 = new Button();
//		button6.setText("RANKING");
//		button6.setStyle(ButtonStyle.BOTONERA_PANTALLA_PRINCIPAL);
//		
//		button6.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				button6Clicked(e);
//			}
//		});
//		colBotones.add(button6);
//		
//		
//		Button button7 = new Button();
//		button7.setText("CHAT");
//		button7.setStyle(ButtonStyle.BOTONERA_PANTALLA_PRINCIPAL);
//		button7.setRolloverEnabled(true);
//		
//		button7.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				button7Clicked(e);
//			}
//		});
//		colBotones.add(button7);
//		
//		
//	}
//	
//	//funciones para los actionlistener
//	private void button1Clicked(ActionEvent e) {
//		
//		removeAll();
//		add(new Arena());
//	}
//	private void button5Clicked(ActionEvent e) {
//		
//		removeAll();
//		add(new Perfil());
//	}
//	private void button6Clicked(ActionEvent e) {
//		
//		removeAll();
//		add(new Ranking());
//	}
//	private void button4Clicked(ActionEvent e) {
//	
//		removeAll();
//		add(new Clanes());
//	}
//	private void button3Clicked(ActionEvent e) {
//	
//		removeAll();
//		add(new Tienda());
//	}
//	private void button2Clicked(ActionEvent e) {
//	
//		removeAll();
//		add(new Academia());
//	
//	}
//	private void button7Clicked(ActionEvent e) {
//		
//		removeAll();
//		add(new Chat());
//	}
//		
	
