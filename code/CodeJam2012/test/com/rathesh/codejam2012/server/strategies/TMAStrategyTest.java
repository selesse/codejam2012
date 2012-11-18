/**
 * 
 */
package com.rathesh.codejam2012.server.strategies;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.rathesh.codejam2012.server.MSETServlet;

/**
 * @author Alex Bourgeois
 * 
 */
public class TMAStrategyTest extends StrategyTest {

  @Override
  protected List<Double> getExpected() {
    return Lists.newArrayList(61.590, 61.553, 61.518, 61.515, 61.530, 61.570, 61.683, 61.879,
        62.128, 62.417, 62.691, 62.917, 63.040, 63.055, 62.997);
  }

  @Override
  protected void setupStrategy(int N, boolean flag) {
    strat = new TMAStrategy(N, MSETServlet.WINDOW_SIZE, flag);
  }

}
