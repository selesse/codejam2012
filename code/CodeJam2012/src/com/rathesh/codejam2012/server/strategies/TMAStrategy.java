package com.rathesh.codejam2012.server.strategies;

import java.util.List;

public class TMAStrategy extends AbstractStrategy {
  protected SMAStrategy smaStrategy;

  public TMAStrategy(int N, int windowSize, boolean isFast) {
    super(N, windowSize, isFast);
    smaStrategy = new SMAStrategy(N, windowSize, isFast);
  }

  @Override
  public double update(double price) {

    smaStrategy.update(price);
    List<Double> smaAverages = smaStrategy.getAverages();
    double triangularAverage = 0;
    if (smaAverages.size() < N) {
      for (int i = 0; i < smaAverages.size(); i++) {
        triangularAverage += smaAverages.get(i);
      }
      triangularAverage = triangularAverage / smaAverages.size();
    }
    else {
      for (int i = smaAverages.size() - N; i < smaAverages.size(); i++) {
        triangularAverage += smaAverages.get(i);
      }
      triangularAverage = triangularAverage / N;
    }

    addToAverages(triangularAverage);
    this.setChanged();
    this.notifyObservers(triangularAverage);
    return this.getAverage();
  }

}
