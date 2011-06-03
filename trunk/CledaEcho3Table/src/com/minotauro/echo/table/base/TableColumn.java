/*
 * Created on 19/09/2008
 */
package com.minotauro.echo.table.base;

import nextapp.echo.app.Extent;

/**
 * @author Demián Gutierrez
 */
public class TableColumn {

  protected CellRenderer headCellRenderer;
  protected CellRenderer dataCellRenderer;

  protected boolean eventOnSetValue;

  protected String headValue;

  protected Extent width;

  // --------------------------------------------------------------------------------

  public TableColumn() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public CellRenderer getHeadCellRenderer() {
    return headCellRenderer;
  }

  public void setHeadCellRenderer(CellRenderer headCellRenderer) {
    this.headCellRenderer = headCellRenderer;
  }

  // --------------------------------------------------------------------------------

  public CellRenderer getDataCellRenderer() {
    return dataCellRenderer;
  }

  public void setDataCellRenderer(CellRenderer dataCellRenderer) {
    this.dataCellRenderer = dataCellRenderer;
  }

  // --------------------------------------------------------------------------------

  public boolean isEventOnSetValue() {
    return eventOnSetValue;
  }

  public void setEventOnSetValue(boolean eventOnSetValue) {
    this.eventOnSetValue = eventOnSetValue;
  }

  // --------------------------------------------------------------------------------

  public String getHeadValue() {
    return headValue;
  }

  public void setHeadValue(String headerValue) {
    this.headValue = headerValue;
  }

  // --------------------------------------------------------------------------------

  public Extent getWidth() {
    return width;
  }

  public void setWidth(Extent width) {
    this.width = width;
  }

  // --------------------------------------------------------------------------------

  public Object/**/getValue(ETable table, Object element) {
    return null;
  }

  public void/*  */setValue(ETable table, Object element, //
      Object value) {
    // Empty
  }
}
