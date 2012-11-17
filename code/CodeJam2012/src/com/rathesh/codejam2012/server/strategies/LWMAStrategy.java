package com.rathesh.codejam2012.server.strategies;

public class LWMAStrategy extends AbstractStrategy {

  public LWMAStrategy(int N, boolean isFast) {
    super(N, isFast);
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
      count+= i++;
    }
    this.averages.add(linearAverage / count);

    return this.getAverage();
  }

}
