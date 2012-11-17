package com.rathesh.codejam2012.server.strategies;

public class LWMAStrategy extends AbstractStrategy {

  public LWMAStrategy(int N) {
    super(N);
  }

  @Override
  public double update(double price) {

    if (this.prices.size() > N) {
      this.prices.remove();
    }
    this.prices.add(price);
    int count = 1;
    double linearAverage = 0;
    for (double d : this.prices) {
      linearAverage += d * count;
      count++;
    }
    this.averages.add(linearAverage / count);

    return this.getAverage();
  }

}
