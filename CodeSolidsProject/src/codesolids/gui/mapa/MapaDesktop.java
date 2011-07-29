package codesolids.gui.mapa;

/*
 * @autor Hector PRada
 * 
 * */

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Button;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.academia.AcademiaDesktop;
import codesolids.gui.arena.ArenaDesktop;
import codesolids.gui.arena.PreArena;
import codesolids.gui.chat.ChatGui;
import codesolids.gui.editar.EditarDatos;
import codesolids.gui.mision.Mision;
import codesolids.gui.tienda.ImageReferenceCache;
import codesolids.gui.perfil.PerfilDesktop;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.principal.PrincipalDesktop;
import codesolids.gui.ranking.Ranking;
import codesolids.gui.style.Styles1;
import codesolids.gui.tienda.TiendaDesktop;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

@SuppressWarnings("serial")
public class MapaDesktop extends ContentPane {
	
	private Usuario usuario;
	private HtmlLayout htmlLayout;

	public MapaDesktop() {
		super();
		
		PrincipalApp app = (PrincipalApp) ApplicationInstance.getActive();
		usuario = app.getUsuario();
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
				
		ImageReference perfil_des = ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/perfil_sel.png");
		ImageReference perfil_sel = ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/perfil_des.png");
		ImageReference academia_sel = ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/academia_sel.png");
		ImageReference academia_des = ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/academia_des.png");
		ImageReference ranking_sel =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/ranking_sel.png");
		ImageReference ranking_des =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/ranking_des.png");
		ImageReference arena_sel =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/arena_sel.png");
		ImageReference arena_des =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/arena_des.png");
		ImageReference clanes_sel =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/clanes_sel.png");
		ImageReference clanes_des =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/clanes_des.png");
		ImageReference tienda_sel =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/tienda_sel.png");
		ImageReference tienda_des =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/tienda_des.png");
		ImageReference chat_sel =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/chat_sel.png");
		ImageReference chat_des =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/chat_des.png");
		

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
		
		Row row = new Row();
		row.setCellSpacing(new Extent(20));
		
		Button btnEditarDatos = new Button("Editar Datos");
		btnEditarDatos.setStyle(Styles1.DEFAULT_STYLE);
		btnEditarDatos.setHeight(new Extent(15));
		btnEditarDatos.setWidth(new Extent(65));    
		btnEditarDatos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnEditarDatosClicked();

			}
		});
		row.add(btnEditarDatos);

		
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
		row.add(logoutBtn);
		
		row.setLayoutData(hld);
		htmlLayout.add(row);

		return htmlLayout;
	 }

	//funciones para los actionlistener
	 
		private void arenaBtnClicked(ActionEvent e) {
			this.removeAll();
			add(new PreArena());
		}
		
		private void logoutBtnClicked(ActionEvent e) {
			removeAll();
			add(new PrincipalDesktop());
		}
		
        private void btnEditarDatosClicked() {
        	add(new EditarDatos());
        }
		
		private void perfilBtnClicked(ActionEvent e) {
			removeAll();
			add(new PerfilDesktop());
		}
		
		private void rankingBtnClicked(ActionEvent e) {
			removeAll();
			add(new Ranking());
		}
		
		private void clanesBtnClicked(ActionEvent e) {
			removeAll();
			add(new Mision());
		}
		
		private void tiendaBtnClicked(ActionEvent e) {
			removeAll();
			add(new TiendaDesktop());
		}
		
		private void academiaBtnClicked() {
			removeAll();
			add(new AcademiaDesktop());
		}
		
		private void chatBtnClicked(ActionEvent e) {
			
			Session session = null;
			
			session = SessionHibernate.getInstance().getSession();
	        session.beginTransaction();
	        

			PrincipalApp pa = (PrincipalApp) ApplicationInstance.getActive();
			Usuario usuario = pa.getUsuario();
	        
	        
	        Criteria criteria = session.createCriteria(Usuario.class).add(//
	                                               Restrictions.eq("login", usuario.getLogin()));

	        usuario = (Usuario) criteria.uniqueResult();
			
			usuario.setActivo(true);

			session.getTransaction().commit();
			session.close();
			
			
			removeAll();
			add(new ChatGui());
		}
	 
}
