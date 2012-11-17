package com.rathesh.codejam2012.server.strategies;

import java.util.List;

public interface Strategy {
  public double update(double price);
  public double getAverage();
  public List<Double> getAverages();
  public boolean isFast();
}


