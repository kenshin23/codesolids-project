package com.tutorial.events;

import java.util.EventObject;

public class MyCustomEvent extends EventObject {

  private String data;

  public MyCustomEvent(Object source, String data) {
    super(source);
    this.data = data;
  }

  public String getData() {
    return data;
  }
}
