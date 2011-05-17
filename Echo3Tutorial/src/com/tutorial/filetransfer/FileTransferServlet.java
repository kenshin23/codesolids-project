package com.tutorial.filetransfer;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

/**
 * @author Demián Gutierrez
 * <br> Created on Jun 24, 2008
 */
public class FileTransferServlet extends WebContainerServlet {

  public ApplicationInstance newInstance() {
    return new FileTransferApp();
  }

  @Override
  public ApplicationInstance newApplicationInstance() {
    return new FileTransferApp();
  }
}
