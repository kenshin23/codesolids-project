package com.tutorial.table;

import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.minotauro.echo.table.base.ETable;
import com.minotauro.echo.table.base.ETableNavigation;
import com.minotauro.echo.table.base.TableColModel;
import com.minotauro.echo.table.base.TableColumn;
import com.minotauro.echo.table.base.TableSelModel;
import com.minotauro.echo.table.renderer.BaseCellRenderer;
import com.minotauro.echo.table.renderer.LabelCellRenderer;
import com.minotauro.echo.table.renderer.NestedCellRenderer;

public class Desktop extends ContentPane {

  private TestTableModel tableDtaModel;

  private TextField txtFrst;
  private TextField txtLast;

  private Button btnCreate;
  private Button btnUpdate;

  private int editingRow;

  // --------------------------------------------------------------------------------

  public Desktop() {
    initGUI();
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {
    setInsets(new Insets(2, 2, 2, 2));

    Grid col = new Grid(1);
    //    col.setCellSpacing(new Extent(4));
    add(col);

    col.add(initTopRow());

    // ----------------------------------------
    // The table models
    // ----------------------------------------

    TableColModel tableColModel = initTableColModel();
    TableSelModel tableSelModel = new TableSelModel();

    tableDtaModel = new TestTableModel();
    tableDtaModel.setEditable(true);
    tableDtaModel.setPageSize(3);

    // ----------------------------------------
    // The table
    // ----------------------------------------

    ETable table = new ETable();
    table.setTableDtaModel(tableDtaModel);
    table.setTableColModel(tableColModel);
    table.setTableSelModel(tableSelModel);

    table.setEasyview(true);

    table.setBorder(new Border(1, Color.BLACK, Border.STYLE_NONE));
    table.setInsets(new Insets(5, 2, 5, 2));
    col.add(table);

    // ----------------------------------------
    // The navigation control
    // ----------------------------------------

    ETableNavigation tableNavigation = new ETableNavigation(tableDtaModel);
    col.add(tableNavigation);
  }

  // --------------------------------------------------------------------------------

  private Row initTopRow() {
    Row row = new Row();
    row.setCellSpacing(new Extent(5));

    row.add(new Label("Frst Name:"));
    txtFrst = new TextField();
    row.add(txtFrst);

    row.add(new Label("Last Name:"));
    txtLast = new TextField();
    row.add(txtLast);

    btnCreate = new Button("Create");
    btnCreate.setStyle(GUIStyles.DEFAULT_STYLE);
    btnCreate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnCreateClicked();
      }
    });
    row.add(btnCreate);

    btnUpdate = new Button("Update");
    btnUpdate.setStyle(GUIStyles.DEFAULT_STYLE);
    btnUpdate.setEnabled(false);
    btnUpdate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnUpdateClicked();
      }
    });
    row.add(btnUpdate);

    return row;
  }

  // --------------------------------------------------------------------------------

  private TableColModel initTableColModel() {
    TableColModel tableColModel = new TableColModel();

    TableColumn tableColumn;
    LabelCellRenderer lcr;

    tableColumn = new TableColumn() {
      @Override
      public Object getValue(ETable table, Object element) {
        PersonBean personaBean = (PersonBean) element;
        return personaBean.getFrstName();
      }
    };
    tableColumn.setWidth(new Extent(150));
    tableColumn.setHeadValue("Frst Name");

    lcr = new LabelCellRenderer();
    lcr.setBackground(Color.BLUE);
    lcr.setForeground(Color.WHITE);
    tableColumn.setHeadCellRenderer(lcr);

    tableColumn.setDataCellRenderer(new LabelCellRenderer());
    tableColModel.getTableColumnList().add(tableColumn);

    tableColumn = new TableColumn() {
      @Override
      public Object getValue(ETable table, Object element) {
        PersonBean personaBean = (PersonBean) element;
        return personaBean.getLastName();
      }
    };
    tableColumn.setWidth(new Extent(150));
    tableColumn.setHeadValue("Last Name");

    lcr = new LabelCellRenderer();
    lcr.setBackground(Color.BLUE);
    lcr.setForeground(Color.WHITE);
    tableColumn.setHeadCellRenderer(lcr);

    tableColumn.setDataCellRenderer(new LabelCellRenderer());
    tableColModel.getTableColumnList().add(tableColumn);

    tableColumn = new TableColumn();
    tableColumn.setWidth(new Extent(50));
    tableColumn.setHeadValue("Actions");

    lcr = new LabelCellRenderer();
    lcr.setBackground(Color.BLUE);
    lcr.setForeground(Color.WHITE);
    tableColumn.setHeadCellRenderer(lcr);

    tableColumn.setDataCellRenderer(initNestedCellRenderer());
    tableColModel.getTableColumnList().add(tableColumn);

    return tableColModel;
  }

  // --------------------------------------------------------------------------------
  // Setup command bar renderer
  // --------------------------------------------------------------------------------

  private NestedCellRenderer initNestedCellRenderer() {
    NestedCellRenderer nestedCellRenderer = new NestedCellRenderer();
    nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
      @Override
      public Component getCellRenderer( //
          final ETable table, final Object value, final int col, final int row) {

        boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

        Button ret = new Button("Edt");
        ret.setStyle(GUIStyles.DEFAULT_STYLE);
        ret.setEnabled(editable);
        ret.setToolTipText("Edt");

        ret.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            btnEdtClicked(row);
          }
        });
        return ret;
      }
    });

    nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
      @Override
      public Component getCellRenderer( //
          final ETable table, final Object value, final int col, final int row) {

        boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

        Button ret = new Button("Del");
        ret.setStyle(GUIStyles.DEFAULT_STYLE);
        ret.setEnabled(editable);
        ret.setToolTipText("Del");
        ret.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            btnDelClicked(row);
          }
        });
        return ret;
      }
    });

    return nestedCellRenderer;
  }

  // --------------------------------------------------------------------------------

  private void btnEdtClicked(int row) {
    PersonBean personaBean = (PersonBean) tableDtaModel.getElementAt(row);

    txtFrst.setText(personaBean.getFrstName());
    txtLast.setText(personaBean.getLastName());

    tableDtaModel.setEditable(false);
    tableDtaModel.currPageChanged();

    btnCreate.setEnabled(false);
    btnUpdate.setEnabled(true);

    editingRow = row;
  }

  // --------------------------------------------------------------------------------

  private void btnDelClicked(int row) {
    tableDtaModel.del(tableDtaModel.getElementAt(row));
  }

  // --------------------------------------------------------------------------------

  private void btnCreateClicked() {
    PersonBean personaBean = new PersonBean();

    personaBean.setFrstName(txtFrst.getText());
    personaBean.setLastName(txtLast.getText());

    tableDtaModel.add(personaBean);
  }

  // --------------------------------------------------------------------------------

  private void btnUpdateClicked() {
    PersonBean personaBean = (PersonBean) tableDtaModel.getElementAt(editingRow);
    personaBean.setFrstName(txtFrst.getText());
    personaBean.setLastName(txtLast.getText());

    tableDtaModel.setEditable(true);

    btnCreate.setEnabled(true);
    btnUpdate.setEnabled(false);

    tableDtaModel.currPageChanged();
  }
}
