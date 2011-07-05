package codesolids.gui.mapa;
/**
 * @autor:Hector Prada
 * 
 * modified on 04-07-2011 by Jeisson Bastidas
 * 
 */

import java.util.List;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Font;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Session;
import org.hibernate.criterion.Order;

import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.style.Styles1;
import codesolids.util.TestTableModel;

import com.minotauro.echo.table.base.ETable;
import com.minotauro.echo.table.base.TableColModel;
import com.minotauro.echo.table.base.TableColumn;
import com.minotauro.echo.table.base.TableSelModel;
import com.minotauro.echo.table.renderer.LabelCellRenderer;

@SuppressWarnings("serial")
public class Ranking extends ContentPane {

	private Usuario usuario;

	private TestTableModel tableDtaModel;
	private List<Personaje> personajes;

	Panel panel = new Panel();

	protected Ranking(Usuario usuario) {

		super(); // SIN ESTE SUPER FUE UN DOLOR PARA QUE FUNCIONARA, este salva vidas
		this.usuario = usuario;
		constructorComp();

	}

	private void constructorComp() {

		setBackground(Color.BLACK);

		//al panel inicial le coloco las medidas exactas a usar
		panel.setHeight(new Extent(983, Extent.PX));
		panel.setWidth(new Extent (1280, Extent.PX));
		panel.setAlignment(Alignment.ALIGN_CENTER);
		panel.setBorder(new Border(new Extent(5, Extent.PX), //
				new Color(0xd4630c), Border.STYLE_DOUBLE));
		panel.setBackground(Color.BLACK);
		panel.setBackgroundImage(new FillImage( //
				new ResourceImageReference("/Images/Mapa/mago_fuego4.jpg"), //
				new Extent(50, Extent.PERCENT), new Extent(50, Extent.PERCENT), //
				FillImage.NO_REPEAT));

		Column col = new Column();
		col.setCellSpacing(new Extent(10));
		Row row;

		//agrego titulo superior y características

		Label tituloSuperior = new Label("RANKING");
		tituloSuperior.setFont(new Font(new Font.Typeface("ARIAL"), //
				Font.BOLD | Font.ITALIC, new Extent(16, Extent.PT)));
		tituloSuperior.setForeground(new Color(0xd4630c));

		row = new Row();
		row.setAlignment(Alignment.ALIGN_CENTER);
		row.add(tituloSuperior);
		col.add(row);

		//Crea la tabla que muestra el ranking de jugadores ordenada por nivel
		tableDtaModel = new TestTableModel();
		try {
			setRanking();
		} catch (Exception e) {
			e.printStackTrace();
		}
		col.add(createRankingTable(tableDtaModel, initTableColModel()));

		//el botón de regresar es creado con propiedades roll-over y foreground,
		//para el futuro deseo agregarle mas características, si es posible  

		Button returnButton = new Button();
		returnButton.setText("Regresar");
		returnButton.setWidth(new Extent(100));
		returnButton.setStyle(Styles1.DEFAULT_STYLE);
		returnButton.setTextAlignment(Alignment.ALIGN_CENTER);
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button1Clicked(e);
			}
		});

		row = new Row();
		row.setAlignment(Alignment.ALIGN_CENTER);
		row.add(returnButton);
		col.add(row);

		panel.add(col);
		add(panel);

	}

	private void button1Clicked(ActionEvent e) {

		removeAll();
		add(new MapaDesktop(usuario));
		//muchachos esta parte fue un dolor de cabeza,
		//después de muchos intentos por fin pude
		//utilizar el action event para crear un nuevo panel

	}

	@SuppressWarnings("unchecked")
	private void setRanking() throws Exception {

		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		personajes = session.createCriteria( //
				Personaje.class).addOrder(Order.desc("level")).list();

		for (int i = 0; i < 10; i++) {

			Personaje per = new Personaje();

			per.setId(personajes.get(i).getId());
			per.setDirImage(personajes.get(i).getDirImage());
			per.setFechaFin(personajes.get(i).getFechaFin());
			per.setFechaInicio(personajes.get(i).getFechaInicio());
			per.setGold(personajes.get(i).getGold());
			per.setHp(personajes.get(i).getHp());
			per.setLearning(personajes.get(i).getLearning());
			per.setLevel(personajes.get(i).getLevel());
			per.setMp(personajes.get(i).getMp());
			per.setTipo(personajes.get(i).getTipo());
			per.setXp(personajes.get(i).getXp());

			tableDtaModel.add(per);

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

		// ----------------------------------------
		// The table models
		// ----------------------------------------

		TableColModel tableColModel = initTableColModel;
		TableSelModel tableSelModel = new TableSelModel();
		tableDtaModel.setEditable(true);
		tableDtaModel.setPageSize(10);

		// ----------------------------------------
		// The table
		// ----------------------------------------

		ETable table = new ETable();
		table.setTableDtaModel(tableDtaModel);
		table.setTableColModel(tableColModel);
		table.setTableSelModel(tableSelModel);
		table.setEasyview(false);
		table.setBorder(new Border(1, Color.BLACK, Border.STYLE_NONE));
		col.add(table);

		// ----------------------------------------
		// The navigation control
		// ----------------------------------------

		//		Row row = new Row();
		//		row.setAlignment(Alignment.ALIGN_CENTER);
		//
		//		ETableNavigation tableNavigation = new ETableNavigation(tableDtaModel);
		//		tableNavigation.setForeground(Color.WHITE);
		//		row.add(tableNavigation);
		//
		//		col.add(row);
		panel.add(col);
		return panel;

	}

	private TableColModel initTableColModel() {

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
				Personaje personaje = (Personaje) element;
				return personajes.indexOf(personaje) + 1;
			}
		};
		tableColumn.setWidth(new Extent(30));
		tableColumn.setHeadValue("Ranking");
		tableColumn.setHeadCellRenderer(headLcr);
		tableColumn.setDataCellRenderer(lcr);
		tableColModel.getTableColumnList().add(tableColumn);

		tableColumn = new TableColumn() {
			@Override
			public Object getValue(ETable table, Object element) {
				Personaje personaje = (Personaje) element; 
				return personaje.getId();
			}
		};
		tableColumn.setWidth(new Extent(100));
		tableColumn.setHeadValue("Id");
		tableColumn.setHeadCellRenderer(headLcr);
		tableColumn.setDataCellRenderer(lcr);
		tableColModel.getTableColumnList().add(tableColumn);

		tableColumn = new TableColumn() {
			@Override
			public Object getValue(ETable table, Object element) {
				Personaje personaje = (Personaje) element;
				return personaje.getLevel();
			}
		};
		tableColumn.setWidth(new Extent(30));
		tableColumn.setHeadValue("Nivel");
		tableColumn.setHeadCellRenderer(headLcr);
		tableColumn.setDataCellRenderer(lcr);
		tableColModel.getTableColumnList().add(tableColumn);

		tableColumn = new TableColumn() {
			@Override
			public Object getValue(ETable table, Object element) {
				Personaje personaje = (Personaje) element;
				return personaje.getTipo();
			}
		};
		tableColumn.setWidth(new Extent(50));
		tableColumn.setHeadValue("Tipo");
		tableColumn.setHeadCellRenderer(headLcr);
		tableColumn.setDataCellRenderer(lcr);
		tableColModel.getTableColumnList().add(tableColumn);

		return tableColModel;

	}

}
