package com.bong.was;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TimeTest extends BaseTest {

  @Test
  public void getCurrentTime() {
    assertEquals(new Date().toString(), connect("http://localhost:80/Time"));
  }
}