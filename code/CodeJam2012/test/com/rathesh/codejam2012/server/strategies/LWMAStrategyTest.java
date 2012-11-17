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
public class LWMAStrategyTest extends StrategyTest {

  @Override
  protected List<Double> getExpected() {
    return Lists.newArrayList(61.590, 61.490, 61.405, 61.511, 61.647, 61.988, 62.351, 62.677,
        62.965, 63.154, 63.230, 63.216, 62.893, 62.607, 62.629);
  }

  protected void setupStrategy(int N) {
    strat = new LWMAStrategy(N);
  }

}
