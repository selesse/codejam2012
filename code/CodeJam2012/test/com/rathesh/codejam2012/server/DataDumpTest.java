package com.rathesh.codejam2012.server;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class DataDumpTest {
  protected List<Double> prices = Lists.newArrayList(61.590, 61.440, 61.320, 61.670, 61.920,
      62.610, 62.880, 63.060, 63.290, 63.320, 63.260, 63.120, 62.240, 62.190, 62.890);

  protected List<Double> smaAverages = Lists.newArrayList(61.590, 61.515, 61.450, 61.505, 61.588,
      61.792, 62.080, 62.428, 62.752, 63.032, 63.162, 63.210, 63.046, 62.826, 62.740);

  protected List<Double> lwmaAverages = Lists.newArrayList(61.590, 61.490, 61.405, 61.511, 61.647,
      61.988, 62.351, 62.677, 62.965, 63.154, 63.230, 63.216, 62.893, 62.607, 62.629);

  protected List<Double> emaAverages = Lists.newArrayList(61.590, 61.540, 61.467, 61.534, 61.663,
      61.979, 62.279, 62.539, 62.790, 62.966, 63.064, 63.083, 62.802, 62.598, 62.695);

  protected List<Double> tmaAverages = Lists.newArrayList(61.590, 61.553, 61.518, 61.515, 61.530,
      61.570, 61.683, 61.879, 62.128, 62.417, 62.691, 62.917, 63.040, 63.055, 62.997);

  @Test
  public void dumpTest() {
    DataDump dataDump = new DataDump();
    dataDump.price.data = prices;
    
    dataDump.smaSlow.data = smaAverages;
    dataDump.smaFast.data = smaAverages;
    
    dataDump.lwmaSlow.data = lwmaAverages;
    dataDump.lwmaFast.data = lwmaAverages;
    
    dataDump.emaSlow.data = emaAverages;
    dataDump.emaFast.data = emaAverages;
    
    dataDump.tmaSlow.data = tmaAverages;
    dataDump.tmaFast.data = tmaAverages;
    dataDump.toString();
    
  }

}
