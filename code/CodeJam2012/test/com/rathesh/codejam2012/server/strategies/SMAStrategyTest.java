/**
 * 
 */
package com.rathesh.codejam2012.server.strategies;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * @author Alex Bourgeois
 * 
 */
public class SMAStrategyTest {


  protected Strategy strat;
  protected double tol=0.000001;

  protected void setupStrategy(int N) {
    strat = new SMAStrategy(N);
  }
  
  @Test
  public void update() {
    setupStrategy(2);
    // Average of [1]
    double avg = strat.update(1);
    assertEquals(1.0, avg, tol);
    // Average of [1,3]
    avg = strat.update(3);
    assertEquals(2.0, avg, tol);
    // Average of [3,3] (N = 2)
    avg = strat.update(3);
    assertEquals(3.0, avg, tol);
    // Average of [3,4] (N = 2)
    avg = strat.update(4);
    assertEquals(3.5, avg, tol);
  }

  @Test
  public void averageWithConstantPrice() {
    setupStrategy(5);
    strat.update(1);
    strat.update(1);
    strat.update(1);
    strat.update(1);
    strat.update(1);
    strat.update(1);
    strat.update(1);
    double avg = strat.getAverage();
    assertEquals(1.0, avg, tol);
  }

  @Test
  public void average() {
    setupStrategy(5);
    strat.update(2); // avg 2
    strat.update(10); // avg 6
    double avg = strat.getAverage();
    // Smaller than window
    assertEquals(6.0, avg, tol);
    
    strat.update(6); // avg 6
    strat.update(2); // avg 5
    strat.update(30); // avg 10
    strat.update(12); // avg 12
    avg = strat.getAverage();
    // Larger than window
    assertEquals(12.0, avg, tol);
  }
  
  @Test
  public void averages() {
    setupStrategy(5);
    List<Double> expected = Lists.newArrayList();
    strat.update(2); // avg 2
    expected.add(2.0);
    strat.update(10); // avg 6
    expected.add(6.0);
    List<Double> actual = strat.getAverages();
    // Smaller than window
    assertEquals(expected.size(), actual.size());
    for (int i=0; i < actual.size(); i++) {
      assertEquals(expected.get(i).doubleValue(), actual.get(i).doubleValue(), tol);
    }
    strat.update(6); // avg 6
    expected.add(6.0);
    strat.update(2); // avg 5
    expected.add(5.0);
    strat.update(30); // avg 10
    expected.add(10.0);
    strat.update(12); // avg 12
    expected.add(12.0);
    // Larger than window
    assertEquals(expected.size(), actual.size());
    for (int i=0; i < actual.size(); i++) {
      assertEquals(expected.get(i).doubleValue(), actual.get(i).doubleValue(), tol);
    }
  }

}
