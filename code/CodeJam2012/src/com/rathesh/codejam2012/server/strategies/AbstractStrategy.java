package com.rathesh.codejam2012.server.strategies;

import java.util.List;
import java.util.Observable;
import java.util.Queue;
import com.google.common.collect.*;

public abstract class AbstractStrategy extends Observable implements Strategy {

  protected final int N, windowSize;
  protected Queue<Double> prices;
  protected List<Double> averages;
  protected boolean flag;

  public AbstractStrategy(int N, int windowSize, boolean isFast) {
    this.N = N;
    this.windowSize = windowSize;
    this.flag = isFast;
    prices = Queues.newArrayBlockingQueue(N);
    averages = Lists.newArrayList();
  }

  @Override
  public double getAverage() {
    if (averages.size() < 1) {
      return -1;
    }
    return this.averages.get(averages.size() - 1);
  }

  @Override
  public List<Double> getAverages() {
    return this.averages;
  }

  public void addToAverages(double d) {
    if (this.averages.size() >= windowSize) {
      this.averages.remove(0);
    }
    this.averages.add(d);
  }

  @Override
  public boolean isFast() {
    return flag;
  }
}
