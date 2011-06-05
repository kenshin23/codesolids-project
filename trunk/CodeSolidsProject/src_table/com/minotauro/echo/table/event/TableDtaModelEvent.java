/*
 * Created on 22/09/2008
 */
package com.minotauro.echo.table.event;

import java.util.EventObject;

import com.minotauro.echo.table.base.TableDtaModel;

/**
 * @author Demián Gutierrez
 */
public class TableDtaModelEvent extends EventObject {

  public TableDtaModelEvent(TableDtaModel source) {
    super(source);
  }
}
