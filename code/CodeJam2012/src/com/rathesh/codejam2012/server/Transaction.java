package com.rathesh.codejam2012.server;

public class Transaction {
  public int time = 0;
  public String type = "";
  public double price = 0; 
  public String manager = "";
  public String strategy = "";
  
  public Transaction(int time,String type,double price,String manager,String strategy){
    this.time = time;
    this.type = type;
    this.price = price;
    this.manager = manager;
    this.strategy = strategy;
  }
}
