/**
 * 
 */
package com.rathesh.codejam2012.server.strategies;

/**
 * @author Alex Bourgeois
 * 
 */
public class SMAStrategy extends AbstractStrategy {

  public SMAStrategy(int N, int windowSize, boolean isFast) {
    super(N, windowSize, isFast);
  }

  @Override
  public double update(double price) {
    int t = this.prices.size();
    double SMAt = 0;
    if (t >= N) {
      // Update prices
      double ptMinusN = this.prices.poll();
      this.prices.add(price);

      // Calculate simple moving average
      SMAt = getAverage() - (ptMinusN / N) + (price / N);
    }
    else {
      // Update prices
      this.prices.add(price);
      t++;

      // Calculate simple moving average
      for (double p : this.prices) {
        SMAt += p / t;
      }
    }

    // Update averages
    addToAverages(SMAt);
    this.notifyObservers(SMAt);
    return SMAt;
  }
}
