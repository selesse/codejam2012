package com.rathesh.codejam2012.server.strategies;

import java.util.List;
import java.util.Observable;
import java.util.Queue;
import com.google.common.collect.*;

public abstract class AbstractStrategy extends Observable implements Strategy   {

  protected int N;
  protected Queue<Double> prices;
  protected List<Double> averages;
  protected boolean flag;

  public AbstractStrategy(int N, boolean isFast) {
    this.N = N;
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
  
  @Override
  public boolean isFast() {
    return flag;
  }
}
