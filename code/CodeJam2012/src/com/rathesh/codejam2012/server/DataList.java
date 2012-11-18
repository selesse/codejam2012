package com.rathesh.codejam2012.server;

import java.util.List;

import com.google.common.collect.Lists;

public class DataList {
  List<Double> data;

  public DataList() {
    data = Lists.newArrayList();
  }

  public void add(double value) {
    data.add(value);
  }

  public void set(List<Double> collection) {
    data = collection;
  }

  public List<Double> getData() {
    return data;
  }
}
