package codesolids.gui.ranking;

import java.util.List;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Font;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Session;
import org.hibernate.criterion.Order;

import codesolids.bd.clases.Clan;
import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.clan.CreateClan;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.style.Styles1;
import codesolids.util.ImageReferenceCache;
import codesolids.util.TestTableModel;

import com.minotauro.echo.table.base.ETable;
import com.minotauro.echo.table.base.ETableNavigation;
import com.minotauro.echo.table.base.TableColModel;
import com.minotauro.echo.table.base.TableColumn;
import com.minotauro.echo.table.base.TableSelModel;
import com.minotauro.echo.table.renderer.LabelCellRenderer;

import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

/**
 * 
 * @author Antonio López
 *
 */

public class RankingClan extends ContentPane {

	private TestTableModel tableDtaModel;
	private List<Clan> listClan;

	public RankingClan() {
		
		constructorComp();
	}

	public void constructorComp() {

		HtmlLayout retHtmlLayout;
		
		try {
			retHtmlLayout = new HtmlLayout(getClass().getResourceAsStream("templateRanking.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		HtmlLayoutData hld;
		hld = new HtmlLayoutData("fondoPantalla");

		ImageReference image = ImageReferenceCache.getInstance().getImageReference("Images/Fondos/fondoRClan.jpg");
		FillImage imagep = new FillImage(image);

		setBackgroundImage(imagep);

		Column col = new Column();
		col.setCellSpacing(new Extent(10));
		Row row;

		Label tituloSuperior = new Label("RANKING CLAN");
		tituloSuperior.setFont(new Font(new Font.Typeface("ARIAL"), //
				Font.BOLD | Font.ITALIC, new Extent(16, Extent.PT)));
		tituloSuperior.setForeground(new Color(0xd4630c));

		row = new Row();
		row.setAlignment(Alignment.ALIGN_CENTER);
		row.add(tituloSuperior);
		col.add(row);

		tableDtaModel = new TestTableModel();
		try {
			setRanking();
		} catch (Exception e) {
			e.printStackTrace();
		}
		col.add(createRankingTable(tableDtaModel, initTableColModel()));

		Button returnButton = new Button();
        returnButton.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/regresar.png")));
        returnButton.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/regresarMouseOver.png"))));
        returnButton.setRolloverEnabled(true);
        returnButton.setHeight(new Extent(27));
        returnButton.setWidth(new Extent(103));
        returnButton.setToolTipText("Regresar al mapa");
		returnButton.setTextAlignment(Alignment.ALIGN_CENTER);
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button1Clicked();
			}
		});

		row = new Row();
		row.setAlignment(Alignment.ALIGN_CENTER);
		row.add(returnButton);
		col.add(row);

		Panel panel = new Panel();
		
		panel.add(col);

		panel.setLayoutData(hld);

		retHtmlLayout.add(panel);

		add(retHtmlLayout);

	}

	private void button1Clicked() {

		removeAll();
		add(new CreateClan());

	}

	private void setRanking() throws Exception {

		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		listClan = session.createCriteria(Clan.class).addOrder(Order.desc("reputacion")).addOrder(Order.desc("cantPersonaje")).list();

		for (int i = 0; i < listClan.size(); i++) {

			Clan clanBean = new Clan();

			clanBean.setId(listClan.get(i).getId());
			clanBean.setDateJoin(listClan.get(i).getDateJoin());
			clanBean.setGold(listClan.get(i).getGold());
			clanBean.setReputacion(listClan.get(i).getReputacion());
			clanBean.setLimite(listClan.get(i).getLimite());
			clanBean.setNameClan(listClan.get(i).getNameClan());
			clanBean.setCantPersonaje(listClan.get(i).getCantPersonaje());
			clanBean.setClanMasterRef(listClan.get(i).getClanMasterRef());
			clanBean.setListPersonaje(listClan.get(i).getListPersonaje());

			tableDtaModel.add(clanBean);

		}

		session.getTransaction().commit();
		session.close();

	}

	private Panel createRankingTable(TestTableModel tableDtaModel, //
			TableColModel initTableColModel) {

		Panel panel = new Panel();
		panel.setInsets(new Insets(200, 10, 200, 10));
		panel.setAlignment(Alignment.ALIGN_CENTER);

		Column col = new Column();
		col.setInsets(new Insets(0, 0, 0, 0));
		col.setCellSpacing(new Extent(10));

		TableColModel tableColModel = initTableColModel;
		TableSelModel tableSelModel = new TableSelModel();
		tableDtaModel.setEditable(true);
		tableDtaModel.setPageSize(10);

		ETable table = new ETable();
		table.setTableDtaModel(tableDtaModel);
		table.setTableColModel(tableColModel);
		table.setTableSelModel(tableSelModel);
		table.setEasyview(false);
		table.setBorder(new Border(1, Color.BLACK, Border.STYLE_NONE));
		col.add(table);

		ETableNavigation tableNavigation = new ETableNavigation(tableDtaModel);
		tableNavigation.setForeground(Color.WHITE);
		col.add(tableNavigation);

		panel.add(col);
		
		return panel;

	}

	private TableColModel initTableColModel(){ 

		TableColModel tableColModel = new TableColModel();
		TableColumn tableColumn;

		LabelCellRenderer headLcr = new LabelCellRenderer();
		headLcr.setBackground(new Color(87, 205, 211));
		headLcr.setForeground(Color.WHITE);
		headLcr.setAlignment(Alignment.ALIGN_CENTER);

		LabelCellRenderer lcr;
		lcr = new LabelCellRenderer();
		lcr.setBackground(new Color(226, 211, 161));
		lcr.setAlignment(Alignment.ALIGN_CENTER);

		tableColumn = new TableColumn() {
			@Override
			public Object getValue(ETable table, Object element) {
				Clan clan = (Clan) element;
				int lugar = 0;
				for(int i = 0; i < listClan.size(); i++){
					if(listClan.get(i).getId() == clan.getId())
						lugar = i +1;
				}
				return lugar;
			}
		};
		tableColumn.setWidth(new Extent(20));
		tableColumn.setHeadValue("Ranking");
		tableColumn.setHeadCellRenderer(headLcr);
		tableColumn.setDataCellRenderer(lcr);
		tableColModel.getTableColumnList().add(tableColumn);

		tableColumn = new TableColumn() {
			@Override
			public Object getValue(ETable table, Object element) {
				Clan clan = (Clan) element;
				return clan.getNameClan();
			}
		};
		tableColumn.setWidth(new Extent(120));
		tableColumn.setHeadValue("Nombre Clan");
		tableColumn.setHeadCellRenderer(headLcr);
		tableColumn.setDataCellRenderer(lcr);
		tableColModel.getTableColumnList().add(tableColumn);

		tableColumn = new TableColumn() {
			@Override
			public Object getValue(ETable table, Object element) {
				Clan clan = (Clan) element;				
				return clan.getClanMasterRef().getUsuarioRef().getLogin();
			}
		};
		tableColumn.setWidth(new Extent(100));
		tableColumn.setHeadValue("Lider Clan");
		tableColumn.setHeadCellRenderer(headLcr);
		tableColumn.setDataCellRenderer(lcr);
		tableColModel.getTableColumnList().add(tableColumn);

		tableColumn = new TableColumn() {
			@Override
			public Object getValue(ETable table, Object element) {
				Clan clan = (Clan) element;
				return clan.getCantPersonaje();
			}
		};
		tableColumn.setWidth(new Extent(50));
		tableColumn.setHeadValue("N° Miembros");
		tableColumn.setHeadCellRenderer(headLcr);
		tableColumn.setDataCellRenderer(lcr);
		tableColModel.getTableColumnList().add(tableColumn);

		tableColumn = new TableColumn() {
			@Override
			public Object getValue(ETable table, Object element) {
				Clan clan = (Clan) element;
				return clan.getReputacion();
			}
		};
		tableColumn.setWidth(new Extent(70));
		tableColumn.setHeadValue("Reputación");
		tableColumn.setHeadCellRenderer(headLcr);
		tableColumn.setDataCellRenderer(lcr);
		tableColModel.getTableColumnList().add(tableColumn);
		
		return tableColModel;

	}

}
