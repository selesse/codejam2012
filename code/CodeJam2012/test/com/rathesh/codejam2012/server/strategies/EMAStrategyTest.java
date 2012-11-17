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
public class EMAStrategyTest extends StrategyTest {

  @Override
  protected List<Double> getExpected() {
    return Lists.newArrayList(61.590, 61.540, 61.467, 61.534, 61.663, 61.979, 62.279, 62.539,
        62.790, 62.966, 63.064, 63.083, 62.802, 62.598, 62.695);
  }

  protected void setupStrategy(int N) {
    strat = new EMAStrategy(N);
  }

}
