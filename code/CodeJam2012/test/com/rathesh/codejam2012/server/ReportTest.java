package com.rathesh.codejam2012.server;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ReportTest {

  @Test
  public void test_report() {
    Report report = new Report();
    assertEquals("{\"team\":\"Rathesh.com\",\"destination\":\"notifications@selesse.com\",\"transactions\":[]}", report.toString());
  }
}
