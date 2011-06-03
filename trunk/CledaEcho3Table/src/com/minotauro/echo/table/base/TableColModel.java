/*
 * Created on 19/09/2008
 */
package com.minotauro.echo.table.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Demián Gutierrez
 */
public class TableColModel {

  protected List<TableColumn> tableColumnList = //
  new ArrayList<TableColumn>();

  protected ETable table;

  // --------------------------------------------------------------------------------

  public TableColModel() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public ETable getTable() {
    return table;
  }

  public void setTable(ETable table) {
    if (table.getTableColModel() != this) {
      throw new IllegalStateException();
    }

    this.table = table;
  }

  // --------------------------------------------------------------------------------

  public List<TableColumn> getTableColumnList() {
    return tableColumnList;
  }

  public void setTableColumnList(List<TableColumn> tableColumnList) {
    this.tableColumnList = tableColumnList;
  }
}
