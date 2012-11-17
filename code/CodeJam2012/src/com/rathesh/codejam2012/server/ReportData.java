package com.rathesh.codejam2012.server;

public class ReportData {
  public int time;
  public String type;
  public double price; 
  public String manager;
  public String strategy;
  public ReportData(int time,String type,double price,String manager,String strategy){
    this.time = time;
    this.type = type;
    this.price = price;
    this.manager = manager;
    this.strategy = strategy;
  }
}
