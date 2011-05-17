/*
 * Created on 13/04/2007
 */
package com.tutorial.images;

import java.util.HashMap;
import java.util.Map;

import nextapp.echo.app.ImageReference;
import nextapp.echo.app.ResourceImageReference;

/**
 * @author Demián Gutierrez
 */
public class ImageReferenceCache {

  private static ImageReferenceCache instance;

  private Map<String, ImageReference> imageReferenceMap = new HashMap<String, ImageReference>();

  // ----------------------------------------

  private ImageReferenceCache() {
    // Empty
  }

  // ----------------------------------------

  public synchronized static ImageReferenceCache getInstance() {
    if (instance == null) {
      instance = new ImageReferenceCache();
    }

    return instance;
  }

  // ----------------------------------------

  public ImageReference getImageReference(String name) {
    String key = ImageReference.class.getName() + ":" + name;

    ImageReference ret = imageReferenceMap.get(key);

    if (ret == null) {
      ret = new ResourceImageReference(name);
      imageReferenceMap.put(key, ret);
    }

    return ret;
  }
}
