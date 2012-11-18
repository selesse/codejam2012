/**
 * 
 */
package com.rathesh.codejam2012.server.strategies;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.rathesh.codejam2012.server.MSETServlet;

/**
 * @author Alex Bourgeois
 * 
 */
public class SMAStrategyTest extends StrategyTest {

  @Override
  protected List<Double> getExpected() {
    return Lists.newArrayList(61.590, 61.515, 61.450, 61.505, 61.588, 61.792, 62.080, 62.428,
        62.752, 63.032, 63.162, 63.210, 63.046, 62.826, 62.740);
  }
  
  @Override
  protected void setupStrategy(int N, boolean flag) {
    strat = new SMAStrategy(N, MSETServlet.WINDOW_SIZE, flag);
  }

}
