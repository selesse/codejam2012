package com.rathesh.codejam2012.server;

import java.util.List;

import com.google.common.collect.Lists;

public class DataList {
  protected List<Double> data;
  protected int time;

  public DataList() {
    data = Lists.newArrayList();
  }

  public DataList(int time) {
    data = Lists.newArrayList();
    this.time = time;
  }

  public void setTime(int time) {
    this.time = time;
  }
  public int getTime(){
    return this.time;
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
