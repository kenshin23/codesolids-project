package com.tutorial.filetransfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import javax.servlet.ServletContext;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Row;
import nextapp.echo.app.Window;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.GridLayoutData;
import nextapp.echo.filetransfer.app.DownloadCommand;
import nextapp.echo.filetransfer.app.DownloadProvider;
import nextapp.echo.filetransfer.app.UploadSelect;
import nextapp.echo.filetransfer.app.event.UploadEvent;
import nextapp.echo.filetransfer.app.event.UploadListener;

import com.tutorial.images.ImageReferenceCache;

/**
 * @author Demián Gutierrez
 * <br> Created on Jun 24, 2008
 */
public class FileTransferApp extends ApplicationInstance {

  private static final String BASE_UPLOAD_PATH = "uploads";
  private Grid grdFiles;
  private UploadSelect uploadSelect;
  private Row row;

  public Window init() {
    Window window = new Window();

    ContentPane contentPane = new ContentPane();
    contentPane.setInsets(new Insets(2, 2, 2, 2));
    window.setContent(contentPane);

    Column col = new Column();
    contentPane.add(col);

    try {
      uploadSelect = initUploadSelect();
      row = new Row();
      row.add(uploadSelect);
      col.add(row);
    } catch (Exception e) {
      e.printStackTrace();
    }

    //    CheckBox chkShowSend = new CheckBox("Mostrar Botón Send");
    //    chkShowSend.setSelected(true);
    //    chkShowSend.addActionListener(new ActionListener() {
    //      public void actionPerformed(ActionEvent evt) {
    //        CheckBox source = (CheckBox) evt.getSource();
    //        //uploadSelect.setSendButtonDisplayed(source.isSelected());
    //      }
    //    });
    //    col.add(chkShowSend);

    grdFiles = new Grid(4);
    col.add(grdFiles);

    updateFileList();

    return window;
  }

  private UploadSelect initUploadSelect() throws TooManyListenersException {
    UploadSelect uploadSelect = new UploadSelect();

    uploadSelect.addUploadListener(new UploadListener() {
      @Override
      public void uploadComplete(UploadEvent evt) {
        try {
          System.err.println("uploadComplete");
          upload(evt);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    return uploadSelect;
  }

  private void upload(UploadEvent uploadEvent) throws IOException {

    // ----------------------------------------
    // Verifica si la ruta de uploads existe
    // y si no, la crea.
    // ----------------------------------------

    ServletContext context = FileTransferServlet.getActiveConnection().getServlet().getServletContext();
    String basePath = context.getRealPath(BASE_UPLOAD_PATH);

    File baseFile = new File(basePath);

    if (!baseFile.exists()) {
      baseFile.mkdir();
    }

    // ----------------------------------------
    // Obtiene la información del upload y
    // escribe el archivo.
    // ----------------------------------------

    byte[] b = new byte[(int) uploadEvent.getUpload().getSize()];
    uploadEvent.getUpload().getInputStream().read(b, 0, (int) uploadEvent.getUpload().getSize());

    String uploadFileName = basePath + System.getProperty("file.separator") + uploadEvent.getUpload().getFileName();

    FileOutputStream fos = new FileOutputStream(uploadFileName);
    fos.write(b);
    fos.close();

    updateFileList();

    row.removeAll();
    try {
      uploadSelect = initUploadSelect();
      row.add(uploadSelect);
    } catch (TooManyListenersException e) {
      e.printStackTrace();
    }
  }

  private void updateFileList() {
    grdFiles.removeAll();

    // ----------------------------------------
    // Verifica si la ruta de uploads existe
    // y si no, la crea.
    // ----------------------------------------

    ServletContext context = FileTransferServlet.getActiveConnection().getServlet().getServletContext();
    String basePath = context.getRealPath(BASE_UPLOAD_PATH);

    File baseFile = new File(basePath);
    if (!baseFile.exists()) {
      baseFile.mkdir();
    }

    // ----------------------------------------
    // Lista los archivos y crea los botones
    // ----------------------------------------

    File[] files = baseFile.listFiles();

    for (final File file : files) {
      if (file.isDirectory()) {
        continue;
      }

      Row row = new Row();
      row.setCellSpacing(new Extent(5));
      GridLayoutData gld = new GridLayoutData();
      gld.setInsets(new Insets(5, 5, 5, 5));
      row.setLayoutData(gld);
      grdFiles.add(row);

      // ----------------------------------------
      // El botón con el nombre
      // ----------------------------------------

      Button btnFile = new Button(file.getName());

      btnFile.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          try {
            downloadFile(file);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
      btnFile.setForeground(Color.BLUE);
      row.add(btnFile);

      // ----------------------------------------
      // El botón para eliminar
      // ----------------------------------------

      Button btnDelete = new Button(ImageReferenceCache.getInstance().getImageReference(
          "com/tutorial/filetransfer/del_e.gif"));
      btnDelete.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          deleteFile(file);
        }
      });
      row.add(btnDelete);
    }
  }

  private void deleteFile(File file) {
    file.delete();
    updateFileList();
  }

  private void downloadFile(File file) throws Exception {
    byte[] b = new byte[(int) file.length()];

    FileInputStream fis = new FileInputStream(file);
    fis.read(b);
    fis.close();
    doDownload(b, file.getName(), "");
  }

  // ----------------------------------------
  // Utilitario para descargar un archivo getInputStream()
  // ----------------------------------------

  private void doDownload(final byte[] content, final String fileName, final String contentType) {
    DownloadCommand download = new DownloadCommand();
    download.setProvider(new DownloadProvider() {
      public String getContentType() {
        return contentType;
      }

      public String getFileName() {
        return fileName;
      }

      public long getSize() {
        return content.length;
      }

      public void writeFile(OutputStream os) throws IOException {
        os.write(content);
      }
    });
    //    download.setActive(true);
    ApplicationInstance.getActive().enqueueCommand(download);
  }
}
