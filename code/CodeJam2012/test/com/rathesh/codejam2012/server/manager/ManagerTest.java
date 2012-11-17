package com.rathesh.codejam2012.server.manager;

import static org.junit.Assert.*;

import java.util.Observable;

import org.junit.Test;

import com.rathesh.codejam2012.server.strategies.*;

public class ManagerTest {

  @Test
  public void test() {
    Manager manager = new Manager("selesse");
    manager.setIdol(false);
    Observable o = new SMAStrategy(5, false);
    manager.update(o, (double) 5.2);
    assert (manager.smaSlow.get(0) == 5.2);
    o = new SMAStrategy(5, true);
    manager.update(o, (double) 2.4);
    assert (manager.smaFast.get(0) == 2.4);

    o = new LWMAStrategy(5, false);
    manager.update(o, (double) 5.2);
    assert (manager.lwmaSlow.get(0) == 5.2);
    o = new LWMAStrategy(5, true);
    manager.update(o, (double) 2.4);
    assert (manager.lwmaFast.get(0) == 2.4);

    o = new TMAStrategy(5, false);
    manager.update(o, (double) 5.2);
    assert (manager.tmaSlow.get(0) == 5.2);
    o = new TMAStrategy(5, true);
    manager.update(o, (double) 2.4);
    assert (manager.tmaFast.get(0) == 2.4);

    o = new EMAStrategy(5, false);
    manager.update(o, (double) 5.2);
    assert (manager.emaSlow.get(0) == 5.2);
    o = new EMAStrategy(5, true);
    manager.update(o, (double) 2.4);
    assert (manager.emaFast.get(0) == 2.4);

  }

  @Test
  public void smaLogicTest() {
    Manager manager = new Manager("selesse");
    manager.setIdol(false);
    Observable low = new SMAStrategy(5, false);
    Observable high = new SMAStrategy(7, true);
    manager.update(low, (double) 2);
    manager.update(high, (double) 1);
    manager.update(low, (double) 1);
    manager.update(high, (double) 2);
    manager.update(low, (double) 2);
    manager.update(high, (double) 3);
    manager.update(low, (double) 4);
    manager.update(high, (double) 3);
    manager.update(low, (double) 2);
    manager.update(high, (double) 1);
    manager.update(low, (double) 7);
    manager.update(high, (double) 9);
    manager.update(low, (double) 1);
    manager.update(high, (double) 2);
    manager.update(low, (double) 7);
    manager.update(high, (double) 1);
  }

}
