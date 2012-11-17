package com.rathesh.codejam2012.server.manager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.rathesh.codejam2012.server.MSETServlet;
import com.rathesh.codejam2012.server.strategies.*;

public class Manager implements Observer {
  protected String name;
  protected boolean idol;
  protected List<Double> smaSlow;
  protected List<Double> smaFast;
  protected List<Double> lwmaSlow;
  protected List<Double> lwmaFast;
  protected List<Double> emaSlow;
  protected List<Double> emaFast;
  protected List<Double> tmaSlow;
  protected List<Double> tmaFast;

  public Manager(String name) {
    this.name = name;
    this.idol = true;
    smaSlow = new ArrayList<Double>();
    smaFast = new ArrayList<Double>();
    lwmaSlow = new ArrayList<Double>();
    lwmaFast = new ArrayList<Double>();
    tmaSlow = new ArrayList<Double>();
    tmaFast = new ArrayList<Double>();
    emaSlow = new ArrayList<Double>();
    emaFast = new ArrayList<Double>();
  }
  public void setIdol(boolean status){
    this.idol = status;
  }

  @Override
  public void update(Observable o, Object arg) {
    if(idol){
      return;
    }
    try {
      Method update = getClass().getMethod("update", o.getClass(), Object.class);
      update.invoke(this, o, arg);
    }
    catch (Exception e) {
      // log exception
    }
  }

  public void update(SMAStrategy str, Object arg) {
    if (str.isFast() == true) {
      smaFast.add((double) arg);
    }
    else {
      smaSlow.add((double) arg);
    }

    if (smaFast.size() == smaSlow.size()) {
      if (smaFast.size() == 1) {
        if (smaFast.get(0) > smaSlow.get(0)) {
          MSETServlet.sendBuy(name, "SMA");
        }
      }else if ((smaFast.get(smaFast.size() - 2) > smaSlow.get(smaSlow.size() - 2))
            && (smaFast.get(smaFast.size() - 1) < smaSlow.get(smaSlow.size() - 1))) {
          MSETServlet.sendSell(name, "SMA");;
        }
        else if ((smaFast.get(smaFast.size() - 2) < smaSlow.get(smaSlow.size() - 2))
            && (smaFast.get(smaFast.size() - 1) > smaSlow.get(smaSlow.size() - 1))) {
          MSETServlet.sendBuy(name, "SMA");
        }

    }

  }

  public void update(LWMAStrategy str, Object arg) {
    if (str.isFast() == true) {
      lwmaFast.add((double) arg);
    }
    else {
      lwmaSlow.add((double) arg);
    }
    if (lwmaFast.size() == lwmaSlow.size()) {
      if (lwmaFast.size() == 1) {
        if (lwmaFast.get(0) > lwmaSlow.get(0)) {
          MSETServlet.sendBuy(name, "LWMA");;
        }
      }
        else if ((lwmaFast.get(lwmaFast.size() - 2) > lwmaSlow.get(lwmaSlow.size() - 2))
            && (lwmaFast.get(lwmaFast.size() - 1) < lwmaSlow.get(lwmaSlow.size() - 1))) {
          MSETServlet.sendSell(name, "LWMA");;
        }
        else if ((lwmaFast.get(lwmaFast.size() - 2) < lwmaSlow.get(lwmaSlow.size() - 2))
            && (lwmaFast.get(lwmaFast.size() - 1) > lwmaSlow.get(lwmaSlow.size() - 1))) {
          MSETServlet.sendBuy(name, "LWMA");;
        }
      }

    
  }

  public void update(EMAStrategy str, Object arg) {
    if (str.isFast() == true) {
      emaFast.add((double) arg);
    }
    else {
      emaSlow.add((double) arg);
    }
    if (emaFast.size() == emaSlow.size()) {
      if (emaFast.size() == 1) {
        if (emaFast.get(0) > emaSlow.get(0)) {
          MSETServlet.sendBuy(name, "EMA");;
        }
      }else if ((emaFast.get(emaFast.size() - 2) > emaSlow.get(emaSlow.size() - 2))
            && (emaFast.get(emaFast.size() - 1) < emaSlow.get(emaSlow.size() - 1))) {
          MSETServlet.sendSell(name, "EMA");;
        }
        else if ((emaFast.get(emaFast.size() - 2) < emaSlow.get(emaSlow.size() - 2))
            && (emaFast.get(emaFast.size() - 1) > emaSlow.get(emaSlow.size() - 1))) {
          MSETServlet.sendBuy(name, "EMA");;
        }
      }

    
  }

  public void update(TMAStrategy str, Object arg) {
    if (str.isFast() == true) {
      tmaFast.add((double) arg);
    }
    else {
      tmaSlow.add((double) arg);
    }
    if (tmaFast.size() == tmaSlow.size()) {
      if (tmaFast.size() == 1) {
        if (tmaFast.get(0) > tmaSlow.get(0)) {
          MSETServlet.sendBuy(name, "TMA");;
        }
      } else if ((tmaFast.get(tmaFast.size() - 2) > tmaSlow.get(tmaSlow.size() - 2))
            && (tmaFast.get(tmaFast.size() - 1) < tmaSlow.get(tmaSlow.size() - 1))) {
          MSETServlet.sendSell(name, "TMA");;
        }
        else if ((tmaFast.get(tmaFast.size() - 2) < tmaSlow.get(tmaSlow.size() - 2))
            && (tmaFast.get(tmaFast.size() - 1) > tmaSlow.get(tmaSlow.size() - 1))) {
          MSETServlet.sendBuy(name, "TMA");;
        }
      }

    
  }
  
 

}
