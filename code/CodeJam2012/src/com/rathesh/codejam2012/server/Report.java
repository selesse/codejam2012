package com.rathesh.codejam2012.server;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

public class Report {
  @SuppressWarnings("unused")
  private final String team = "Rathesh.com";
  @SuppressWarnings("unused")
  private final String destination = "mcgillcodejam2012@gmail.com";
  private List<Transaction> transactions = Lists.newArrayList();

  public void add(Transaction transaction) {
    transactions.add(transaction);
  }

  @Override
  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
