package com.rathesh.codejam2012.server.strategies;

public class LWMAStrategy extends AbstractStrategy {

  public LWMAStrategy(int N, int windowSize, boolean isFast) {
    super(N, windowSize, isFast);
  }

  @Override
  public double update(double price) {

    if (this.prices.size() >= N) {
      this.prices.remove();
    }
    this.prices.add(price);
    int count = 0, i = 1;
    double linearAverage = 0;
    for (double d : this.prices) {
      linearAverage += d * i;
      count += i++;
    }
    addToAverages(linearAverage / count);
    this.setChanged();
    this.notifyObservers(linearAverage / count);
    return this.getAverage();
  }

}
