package codesolids.gui.mapa;

/*
 * @autor Hector PRada
 * 
 * */

import nextapp.echo.app.Button;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import codesolids.bd.clases.Usuario;
import codesolids.gui.academia.AcademiaDesktop;
import codesolids.gui.arena.PreArena;
import codesolids.gui.chat.ChatGui;
import codesolids.gui.perfil.PerfilDesktop;
import codesolids.gui.principal.PrincipalDesktop;
import codesolids.gui.style.Styles1;
import codesolids.gui.tienda.TiendaDesktop;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;
import nextapp.echo.app.Column;

@SuppressWarnings("serial")
public class MapaDesktop extends ContentPane {
	
	private Usuario usuario;
	private HtmlLayout htmlLayout;

	public MapaDesktop(Usuario usuario) {
		super();
		
		this.usuario = usuario;
		initGUI();
	}

	 private void initGUI() {
	    add(initMapa());
	 }

	 private Component initMapa(){
		
		try {
			htmlLayout = new HtmlLayout(getClass().getResourceAsStream("templateMapa.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
			}
		HtmlLayoutData hld;
		
		
		ResourceImageReference perfil_des = new ResourceImageReference("/Images/Mapa/perfil_sel.png");
		ResourceImageReference perfil_sel = new ResourceImageReference("/Images/Mapa/perfil_des.png");
		ResourceImageReference academia_sel = new ResourceImageReference("/Images/Mapa/academia_sel.png");
		ResourceImageReference academia_des = new ResourceImageReference("/Images/Mapa/academia_des.png");
		ResourceImageReference ranking_sel = new ResourceImageReference("/Images/Mapa/ranking_sel.png");
		ResourceImageReference ranking_des = new ResourceImageReference("/Images/Mapa/ranking_des.png");
		ResourceImageReference arena_sel = new ResourceImageReference("/Images/Mapa/arena_sel.png");
		ResourceImageReference arena_des = new ResourceImageReference("/Images/Mapa/arena_des.png");
		ResourceImageReference clanes_sel = new ResourceImageReference("/Images/Mapa/clanes_sel.png");
		ResourceImageReference clanes_des = new ResourceImageReference("/Images/Mapa/clanes_des.png");
		ResourceImageReference tienda_sel = new ResourceImageReference("/Images/Mapa/tienda_sel.png");
		ResourceImageReference tienda_des = new ResourceImageReference("/Images/Mapa/tienda_des.png");
		ResourceImageReference chat_sel = new ResourceImageReference("/Images/Mapa/chat_sel.png");
		ResourceImageReference chat_des = new ResourceImageReference("/Images/Mapa/chat_des.png");
		

		hld = new HtmlLayoutData("botonPerfil");
		Button perfilBtn = new Button();
		perfilBtn.setEnabled(true);
		perfilBtn.setVisible(true);
		perfilBtn.setRolloverEnabled(true);
		perfilBtn.setHeight(new Extent(204, Extent.PX));
		perfilBtn.setWidth(new Extent(204, Extent.PX));
		perfilBtn.setBackgroundImage(new FillImage(perfil_sel));
		perfilBtn.setRolloverBackgroundImage(new FillImage(perfil_des));
		perfilBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				perfilBtnClicked(e);
			}
		});
		perfilBtn.setLayoutData(hld);
		htmlLayout.add(perfilBtn);
		
		
		hld = new HtmlLayoutData("botonAcademia");
		Button academiaBtn = new Button();
		academiaBtn.setEnabled(true);
		academiaBtn.setVisible(true);
		academiaBtn.setBackgroundImage(new FillImage(academia_des));
		academiaBtn.setRolloverEnabled(true);
		academiaBtn.setRolloverBackgroundImage(new FillImage(academia_sel));
		academiaBtn.setHeight(new Extent(312, Extent.PX));
		academiaBtn.setWidth(new Extent(346, Extent.PX));
		academiaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				academiaBtnClicked();
				}
			});
		academiaBtn.setLayoutData(hld);
		htmlLayout.add(academiaBtn);
		
		hld = new HtmlLayoutData("botonRanking");
		Button rankingBtn = new Button();
		rankingBtn.setEnabled(true);
		rankingBtn.setRolloverBackgroundImage(new FillImage(ranking_sel));
		rankingBtn.setHeight(new Extent(260, Extent.PX));
		rankingBtn.setVisible(true);
		rankingBtn.setBackgroundImage(new FillImage(ranking_des));
		rankingBtn.setRolloverEnabled(true);
		rankingBtn.setWidth(new Extent(149, Extent.PX));
		rankingBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rankingBtnClicked(e);
				}
			});
		rankingBtn.setLayoutData(hld);
		htmlLayout.add(rankingBtn);
		
		hld = new HtmlLayoutData("botonArena");
		Button arenaBtn = new Button();
		arenaBtn.setEnabled(true);
		arenaBtn.setRolloverBackgroundImage(new FillImage(arena_sel));
		arenaBtn.setBackgroundImage(new FillImage(arena_des));
		arenaBtn.setRolloverEnabled(true);
		arenaBtn.setWidth(new Extent(196, Extent.PX));
		arenaBtn.setHeight(new Extent(146, Extent.PX));
		arenaBtn.setVisible(true);
		arenaBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					arenaBtnClicked(e);
				}
			});
		arenaBtn.setLayoutData(hld);
		htmlLayout.add(arenaBtn);
		
		hld = new HtmlLayoutData("botonClanes");
		Button clanesBtn = new Button();
		clanesBtn.setEnabled(true);
		clanesBtn.setRolloverBackgroundImage(new FillImage(clanes_sel));
		clanesBtn.setBackgroundImage(new FillImage(clanes_des));
		clanesBtn.setRolloverEnabled(true);
		clanesBtn.setHeight(new Extent(179, Extent.PX));
		clanesBtn.setVisible(true);
		clanesBtn.setWidth(new Extent(285, Extent.PX));
		clanesBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clanesBtnClicked(e);
				}
			});
		clanesBtn.setLayoutData(hld);
		htmlLayout.add(clanesBtn);
		
		hld = new HtmlLayoutData("botonTienda");
		Button tiendaBtn = new Button();
		tiendaBtn.setEnabled(true);
		tiendaBtn.setRolloverBackgroundImage(new FillImage(tienda_sel));
		tiendaBtn.setBackgroundImage(new FillImage(tienda_des));
		tiendaBtn.setRolloverEnabled(true);
		tiendaBtn.setHeight(new Extent(208, Extent.PX));
		tiendaBtn.setWidth(new Extent(229, Extent.PX));
		tiendaBtn.setVisible(true);
		tiendaBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tiendaBtnClicked(e);
				}
			});
		tiendaBtn.setLayoutData(hld);
		htmlLayout.add(tiendaBtn);
		
		hld = new HtmlLayoutData("botonChat");
		Button chatBtn = new Button();
		chatBtn.setEnabled(true);
		chatBtn.setVisible(true);
		chatBtn.setBackgroundImage(new FillImage(chat_des));
		chatBtn.setRolloverEnabled(true);
		chatBtn.setRolloverBackgroundImage(new FillImage(chat_sel));
		chatBtn.setHeight(new Extent(139, Extent.PX));
		chatBtn.setWidth(new Extent(144, Extent.PX));
		chatBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					chatBtnClicked(e);
				}
			});
		chatBtn.setLayoutData(hld);	
		htmlLayout.add(chatBtn);
		
		hld = new HtmlLayoutData("botonLogout");
		Button logoutBtn = new Button("Cerrar Sesion");
		logoutBtn.setEnabled(true);
		logoutBtn.setVisible(true);
		logoutBtn.setStyle(Styles1.DEFAULT_STYLE);
		logoutBtn.setHeight(new Extent(15, Extent.PX));
		logoutBtn.setWidth(new Extent(100, Extent.PX));
		logoutBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					logoutBtnClicked(e);
				}
			});
		logoutBtn.setLayoutData(hld);
		htmlLayout.add(logoutBtn);

		return htmlLayout;
	 }

	//funciones para los actionlistener
	 
		private void arenaBtnClicked(ActionEvent e) {
			this.removeAll();
			add(new PreArena(usuario));
		}
		private void logoutBtnClicked(ActionEvent e) {
			removeAll();
			add(new PrincipalDesktop());
		}
		private void perfilBtnClicked(ActionEvent e) {
			removeAll();
			add(new PerfilDesktop(usuario));
		}
		private void rankingBtnClicked(ActionEvent e) {
			removeAll();
			add(new Ranking(usuario));
		}
		private void clanesBtnClicked(ActionEvent e) {
			removeAll();
			add(new Clanes(usuario));
		}
		private void tiendaBtnClicked(ActionEvent e) {
			removeAll();
			add(new TiendaDesktop(usuario));
		}
		
		private void academiaBtnClicked() {
			removeAll();
			add(new AcademiaDesktop());
		}
		
		private void chatBtnClicked(ActionEvent e) {
			add(new ChatGui());
		}
	 
}
