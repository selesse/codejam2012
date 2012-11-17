package com.rathesh.codejam2012.server.strategies;

import java.util.List;
import java.util.Queue;
import com.google.common.collect.*;



public abstract class AbstractStrategy implements Strategy {

  int N;
  Queue<Double> prices;
  List<Double> averages;
  
  public AbstractStrategy(int N) {
    this.N = N;
    prices = Queues.newArrayBlockingQueue(N);
    averages = Lists.newArrayList();
  }
  
  @Override
  public double update(double price) {
    // TODO Auto-generated method stub
    return getAverage();
  }

  @Override
  public double getAverage() {
    return this.averages.get(averages.size() - 1);
  }

  @Override
  public List<Double> getAverages() {
    return this.averages;
  }
}
