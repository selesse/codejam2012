package com.rathesh.codejam2012.server;

import java.util.List;

import com.google.gson.Gson;
import com.google.gwt.thirdparty.guava.common.collect.Lists;

public class DataDump {
  List<double[]> price;
  double[][] smaSlow;
  double[][] smaFast;

  double[][] lwmaSlow;
  double[][] lwmaFast;

  double[][] emaSlow;
  double[][] emaFast;

  double[][] tmaSlow;
  double[][] tmaFast;
  
  public DataDump() {
    double[] samplePoints = {0, 1};
    double[] samplePoints2 = {0, 0.25};
    price = Lists.newArrayList(samplePoints, samplePoints2);
    smaSlow = new double[0][0];
    smaFast = new double[0][0];
    lwmaSlow = new double[0][0];
    lwmaFast = new double[0][0];
    emaSlow = new double[0][0];
    emaFast = new double[0][0];
    tmaSlow = new double[0][0];
    tmaFast = new double[0][0];
  }
  
  public List<double[]> getPrice() {
    return price;
  }
  
  public void setPrice(List<Double> prices) {
    double[] point = new double[2];
    for (int i = 0; i < prices.size(); i++) {
      point[0] = i + 1;
      point[1] = prices.get(i);
    }
    
    price.add(point);
  }

  public double[][] getSmaSlow() {
    return smaSlow;
  }

  public void setSmaSlow(List<Double> smaSlows) {
    smaSlow = new double[2][smaSlows.size()];
    for (int i = 0; i < smaSlows.size(); i++) {
      smaSlow[0][i] = i + 1;
      smaSlow[1][i] = smaSlows.get(i);
    }
  }

  public double[][] getSmaFast() {
    return smaFast;
  }

  public void setSmaFast(List<Double> smaFasts) {
    smaFast = new double[2][smaFasts.size()];
    for (int i = 0; i < smaFasts.size(); i++) {
      smaFast[0][i] = i + 1;
      smaFast[1][i] = smaFasts.get(i);
    }
  }

  public double[][] getLwmaSlow() {
    return lwmaSlow;
  }

  public void setLwmaSlow(List<Double> lwmaSlows) {
    lwmaSlow = new double[2][lwmaSlows.size()];
    for (int i = 0; i < lwmaSlows.size(); i++) {
      lwmaSlow[0][i] = i + 1;
      lwmaSlow[1][i] = lwmaSlows.get(i);
    }
  }

  public double[][] getLwmaFast() {
    return lwmaFast;
  }

  public void setLwmaFast(List<Double> lwmaFasts) {
    lwmaFast = new double[2][lwmaFasts.size()];
    for (int i = 0; i < lwmaFasts.size(); i++) {
      lwmaFast[0][i] = i + 1;
      lwmaFast[1][i] = lwmaFasts.get(i);
    }
  }

  public double[][] getEmaSlow() {
    return emaSlow;
  }

  public void setEmaSlow(List<Double> emaSlows) {
    emaSlow = new double[2][emaSlows.size()];
    for (int i = 0; i < emaSlows.size(); i++) {
      emaSlow[0][i] = i + 1;
      emaSlow[1][i] = emaSlows.get(i);
    }
  }

  public double[][] getEmaFast() {
    return emaFast;
  }

  public void setEmaFast(List<Double> emaFasts) {
    emaFast = new double[2][emaFasts.size()];
    for (int i = 0; i < emaFasts.size(); i++) {
      emaFast[0][i] = i + 1;
      emaFast[1][i] = emaFasts.get(i);
    }
  }

  public double[][] getTmaSlow() {
    return tmaSlow;
  }

  public void setTmaSlow(List<Double> tmaSlows) {
    tmaSlow = new double[2][tmaSlows.size()];
    for (int i = 0; i < tmaSlows.size(); i++) {
      tmaSlow[0][i] = i + 1;
      tmaSlow[1][i] = tmaSlows.get(i);
    }
  }

  public double[][] getTmaFast() {
    return tmaFast;
  }

  public void setTmaFast(List<Double> tmaFasts) {
    tmaFast = new double[2][tmaFasts.size()];
    for (int i = 0; i < tmaFasts.size(); i++) {
      tmaFast[0][i] = i + 1;
      tmaFast[1][i] = tmaFasts.get(i);
    }
  }

  @Override
  public String toString() {
    Gson gson = new Gson();
    String json = gson.toJson(this);
    
    return json;
  }
}
