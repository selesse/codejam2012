package com.rathesh.codejam2012.server;

import java.io.IOException;
import java.util.List;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class GraphAdapter extends TypeAdapter<DataList> {

  private final int window;

  public GraphAdapter(int window) {
    super();
    this.window = window;
  }

  @Override
  public DataList read(JsonReader in) throws IOException {
    // TODO Auto-generated method stub
    // if (in.peek() == JsonToken.NULL) {
    // in.nextNull();
    // return null;
    // }
    // String collection = reader.nextString();
    return null;
  }

  @Override
  public void write(JsonWriter out, DataList data) throws IOException {
    List<Double> value = data.getData();
    int time = data.getTime();
    int startTime = time - this.window;
    if (startTime < 0) {
      startTime = 0;
    }

    String s = "[";
    int i = value.size() - this.window;
    if (i < 0) {
      i = 0;
    }
    for (; i < value.size(); i++, startTime++) {
      s = s + "[" + (startTime) + ".0," + value.get(i) + "]";
      if (i != value.size() - 1) {
        s = s + ",";
      }
    }
    s = s + "]";
    out.value(s);
  }

}
