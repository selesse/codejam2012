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

  public EMAStrategy(int N, int windowSize, boolean isFast) {
    super(N, windowSize, isFast);
    alpha = (2 / ((double) N + 1));
  }

  @Override
  public double update(double price) {
    double average;
    if (this.getAverage() == -1) {
      average = price;
      addToAverages(price);
    }
    else {
      double prevAvg = this.getAverage();
      average = prevAvg + alpha * (price - prevAvg);
      addToAverages(average);
    }
    this.notifyObservers(average);
    return average;
  }

}
