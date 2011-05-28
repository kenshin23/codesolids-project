/*
 * Created on 19/09/2008
 */
package com.minotauro.echo.table.base;

import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.layout.GridLayoutData;

import com.minotauro.echo.table.event.TableDtaModelEvent;
import com.minotauro.echo.table.event.TableDtaModelListener;
import com.minotauro.echo.table.event.TableSelModelEvent;
import com.minotauro.echo.table.event.TableSelModelListener;

/**
 * @author Demián Gutierrez
 */
public class ETable extends Column implements TableDtaModelListener, TableSelModelListener {

  protected Color backgroundColorMain = new Color(255, 255, 255);
  protected Color backgroundColorEasy = new Color(230, 230, 230);

  protected TableColModel tableColModel;
  protected TableSelModel tableSelModel;
  protected TableDtaModel tableDtaModel;

  protected boolean headVisible = true;

  protected boolean easyview;

  protected Grid grid;

  // --------------------------------------------------------------------------------

  public ETable() {
    add(grid = new Grid());
  }

  // --------------------------------------------------------------------------------

  public Color getBackgroundColorMain() {
    return backgroundColorMain;
  }

  public void setBackgroundColorMain(Color backgroundColorMain) {
    this.backgroundColorMain = backgroundColorMain;
  }

  // --------------------------------------------------------------------------------

  public Color getBackgroundColorEasy() {
    return backgroundColorEasy;
  }

  public void setBackgroundColorEasy(Color backgroundColorEasy) {
    this.backgroundColorEasy = backgroundColorEasy;
  }

  // --------------------------------------------------------------------------------

  public TableColModel getTableColModel() {
    return tableColModel;
  }

  public void setTableColModel(TableColModel tableColModel) {
    this.tableColModel = tableColModel;
    tableColModel.setTable(this);
    render();
  }

  // --------------------------------------------------------------------------------

  public TableSelModel getTableSelModel() {
    return tableSelModel;
  }

  public void setTableSelModel(TableSelModel tableSelModel) {
    switchTableSelModelListener( //
        this.tableSelModel, tableSelModel);

    this.tableSelModel = tableSelModel;
    tableSelModel.setTable(this);
    render();
  }

  // --------------------------------------------------------------------------------

  protected void switchTableSelModelListener( //
      TableSelModel oldTableDtaModel, TableSelModel newTableDtaModel) {

    if (oldTableDtaModel != null) {
      oldTableDtaModel.getTableSelModelEvtProxy(). //
          delTableSelModelListener(this);
    }

    if (newTableDtaModel != null) {
      newTableDtaModel.getTableSelModelEvtProxy(). //
          addTableSelModelListener(this);
    }
  }

  // --------------------------------------------------------------------------------

  public TableDtaModel getTableDtaModel() {
    return tableDtaModel;
  }

  public void setTableDtaModel(TableDtaModel tableDtaModel) {
    switchTableDtaModelListener( //
        this.tableDtaModel, tableDtaModel);

    this.tableDtaModel = tableDtaModel;
    tableDtaModel.setTable(this);
    render();
  }

  // --------------------------------------------------------------------------------

  protected void switchTableDtaModelListener( //
      TableDtaModel oldTableDtaModel, TableDtaModel newTableDtaModel) {

    if (oldTableDtaModel != null) {
      oldTableDtaModel.getTableDtaModelEvtProxy(). //
          delTableDtaModelListener(this);
    }

    if (newTableDtaModel != null) {
      newTableDtaModel.getTableDtaModelEvtProxy(). //
          addTableDtaModelListener(this);
    }
  }

  // --------------------------------------------------------------------------------

  public boolean isEasyview() {
    return easyview;
  }

  public void setEasyview(boolean easyview) {
    this.easyview = easyview;
    render();
  }

  // --------------------------------------------------------------------------------

  public boolean isHeadVisible() {
    return headVisible;
  }

  public void setHeadVisible(boolean headVisible) {
    this.headVisible = headVisible;
    render();
  }

  // --------------------------------------------------------------------------------

  protected Color getRowBackground(int row) {
    if (!easyview) {
      return backgroundColorMain;
    }

    if (row % 2 == 0) {
      return backgroundColorMain;
    } else {
      return backgroundColorEasy;
    }
  }

  // --------------------------------------------------------------------------------

  protected void renderCols() {
    for (int j = 0; j < tableDtaModel.getColCount(); j++) {
      TableColumn tableColumn = //
      tableColModel.getTableColumnList().get(j);

      grid.setColumnWidth(j, tableColumn.getWidth());
    }
  }

  // --------------------------------------------------------------------------------

  protected void renderHead() {
    if (!headVisible) {
      return;
    }

    for (int j = 0; j < tableDtaModel.getColCount(); j++) {
      TableColumn tableColumn = //
      tableColModel.getTableColumnList().get(j);

      CellRenderer cellRenderer = //
      tableColumn.getHeadCellRenderer();

      Component component = cellRenderer.getCellRenderer( //
          this, tableColumn.getHeadValue(), j, -1);

      GridLayoutData gld = cellRenderer.getGridLayoutData();
      
//      gld.setAlignment(Alignment.ALIGN_CENTER);
//      gld.setBackground(Color.GREEN);
//      component.setBackground(Color.RED);

      component.setLayoutData(gld);
      grid.add(component);
    }
  }

  // --------------------------------------------------------------------------------

  protected void renderData() {
    for (int i = 0; i < tableDtaModel.getRowCount(); i++) {
      for (int j = 0; j < tableDtaModel.getColCount(); j++) {
        TableColumn tableColumn = //
        tableColModel.getTableColumnList().get(j);

        CellRenderer cellRenderer = //
        tableColumn.getDataCellRenderer();

        Component component = cellRenderer.getCellRenderer( //
            this, tableDtaModel.getValue(j, i), j, i);

        GridLayoutData gld = cellRenderer.getGridLayoutData();

        if (gld.getBackground() == null) {
          gld.setBackground(getRowBackground(i));
        }

        component.setLayoutData(gld);
        grid.add(component);
      }
    }
  }

  // --------------------------------------------------------------------------------

  protected void render() {
    if (tableDtaModel == null || //
        tableColModel == null || //
        tableSelModel == null) {
      return;
    }

//    grid.setHeight(new Extent(600, Extent.PX));
    
    grid.removeAll();
    grid.setWidth(new Extent(100, Extent.PERCENT));
    grid.setSize(tableDtaModel.getColCount());

    renderCols();
    renderHead();
    renderData();
  }

  // --------------------------------------------------------------------------------
  // Model Listeners
  // --------------------------------------------------------------------------------

  public void tableDtaChanged(TableDtaModelEvent evt) {
    render();
  }

  // --------------------------------------------------------------------------------

  public void tableSelChanged(TableSelModelEvent evt) {
    render();
  }

  public void tableSetChanged(TableSelModelEvent evt) {
    render();
  }
}
