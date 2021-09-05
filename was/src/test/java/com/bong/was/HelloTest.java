package com.bong.was;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class HelloTest extends BaseTest {

  @Test
  public void getHelloName() {
    assertEquals("Hello, choi", connect("http://localhost:80/Hello?name=choi"));
  }

}