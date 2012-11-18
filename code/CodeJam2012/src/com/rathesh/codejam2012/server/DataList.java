package com.rathesh.codejam2012.server;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;


public class DataList {
  List<Double> data;
  
  public DataList (){
    data = Lists.newArrayList();
  }
  
  public void add(double value){
    data.add(value);
  }
  
  public void addAll(Collection collection){
    data.addAll(collection);
  }
  
  public List<Double> getData(){
    return data;
  }
}
