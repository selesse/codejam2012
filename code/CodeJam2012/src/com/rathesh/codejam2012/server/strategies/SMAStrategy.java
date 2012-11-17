/**
 * 
 */
package com.rathesh.codejam2012.server.strategies;

/**
 * @author Alex Bourgeois
 *
 */
public class SMAStrategy extends AbstractStrategy {

  public SMAStrategy(int N) {
    super(N);
  }
  
  @Override
  public double update(double price) {
    // Update prices queue
    double ptMinusN = this.prices.poll();
    this.prices.add(price);
    
    // Update averages list
    double SMAt = 0;
    int t = this.prices.size();
    if (t > N) {
      SMAt = getAverage() - (ptMinusN/N) + (price/N);
    } else {
      for (double p : this.prices) {
        SMAt += p/t;
      }
    }
    this.averages.add(SMAt);    
    return SMAt;
  }
}
