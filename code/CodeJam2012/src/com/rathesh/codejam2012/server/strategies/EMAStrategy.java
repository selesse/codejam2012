/**
 * 
 */
package com.rathesh.codejam2012.server.strategies;

/**
 * @author Alex Bourgeois
 * 
 */
public class EMAStrategy extends AbstractStrategy {
  double alpha;

  public EMAStrategy(int N, boolean isFast) {
    super(N, isFast);
    alpha = (2 / ((double) N + 1));
  }

  @Override
  public double update(double price) {
    double average;
    if (this.getAverage() == -1) {
      average = price;
      this.averages.add(price);
    }
    else {
      double prevAvg = this.getAverage();
      average = prevAvg + alpha * (price - prevAvg);
      this.averages.add(average);
    }
    this.notifyObservers(average);
    return average;
  }

}
