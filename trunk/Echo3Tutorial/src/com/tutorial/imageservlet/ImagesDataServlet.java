package com.tutorial.imageservlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Demián Gutierrez
 * <br> Created on Jun 24, 2008
 */
public class ImagesDataServlet extends HttpServlet {

  // In real applications data might come from database BLOB column
  private byte[] loadImage(String name) throws IOException {
    File file = new File(getClass().getResource(name).getPath());

    byte[] data = new byte[(int) file.length()];
    InputStream is = new FileInputStream(file);
    is.read(data);

    return data;
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res) //
      throws ServletException, IOException {

    String imageIdStr = req.getParameter("image_id");

    int imageIdInt = -1;

    try {
      imageIdInt = Integer.parseInt(imageIdStr);
    } catch (NumberFormatException e) {
      // ----------------------------------------
      // Do nothing, imageIdInt will remain in -1
      // ----------------------------------------
    }

    byte[] data;

    switch (imageIdInt) {
      case 1 :
        data = loadImage("acercademinotauroh1.jpg");
        break;
      case 2 :
        data = loadImage("acercademinotauroh2.jpg");
        break;
      case 3 :
        data = loadImage("acercademinotauroh3.jpg");
        break;
      default :
        data = loadImage("error.jpg");
        break;
    }

    res.setContentType("image/jpg");
    res.getOutputStream().write(data);
  }
}
