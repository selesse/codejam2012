/**
 * 
 */
package com.rathesh.codejam2012.server.strategies;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * @author Alex Bourgeois
 * 
 */
public abstract class StrategyTest {

  protected Strategy strat;
  protected double tol = 0.000001;
  protected List<Double> prices = Lists.newArrayList(61.590, 61.440, 61.320, 61.670, 61.920,
      62.610, 62.880, 63.060, 63.290, 63.320, 63.260, 63.120, 62.240, 62.190, 62.890);
  protected List<Double> expected = getExpected();

  protected List<Double> getExpected() {
    return null;
  }

  protected void setupStrategy(int N) {
  }

  @Test
  public void update() {
    setupStrategy(5);
    double avg;
    for (int i = 0; i < prices.size(); i++) {
      avg = strat.update(prices.get(i));
      assertEquals(expected.get(i), avg, tol);
    }
  }

  @Test
  public void averageWithConstantPrice() {
    setupStrategy(4);
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
    int i = 0;
    for (; i < 4; i++) {
      strat.update(prices.get(i));
    }
    double avg = strat.getAverage();
    // Smaller than window
    assertEquals(expected.get(i - 1), avg, tol);

    for (; i < prices.size(); i++) {
      strat.update(prices.get(i));
    }
    avg = strat.getAverage();
    // Larger than window
    assertEquals(expected.get(i - 1), avg, tol);
  }

  @Test
  public void averages() {
    setupStrategy(5);
    int i = 0;
    for (; i < 4; i++) {
      strat.update(prices.get(i));
    }
    List<Double> actual = strat.getAverages();
    // Smaller than window
    assertEquals(4, actual.size());
    for (int j = 0; j < 4; j++) {
      assertEquals(expected.get(j).doubleValue(), actual.get(j).doubleValue(), tol);
    }

    for (; i < prices.size(); i++) {
      strat.update(prices.get(i));
    }
    actual = strat.getAverages();
    // Larger than window
    assertEquals(expected.size(), actual.size());
    for (int j = 0; j < actual.size(); j++) {
      assertEquals(expected.get(j).doubleValue(), actual.get(j).doubleValue(), tol);
    }
  }

}
