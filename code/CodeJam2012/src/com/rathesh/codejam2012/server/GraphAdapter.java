package com.rathesh.codejam2012.server;

import java.io.IOException;
import java.util.List;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class GraphAdapter extends TypeAdapter<DataList> {

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
    String s = "[";
    for (int i = 0; i < value.size(); i++) {
      s = s + "[" + (i + 1) + ".0," + value.get(i) + "],";
    }
    s = s + "]";
    out.value(s);
  }
  
}
