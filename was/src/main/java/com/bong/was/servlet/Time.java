package com.bong.was.servlet;

import com.bong.was.http_request.HttpRequest;
import com.bong.was.http_response.HttpResponse;
import java.util.Date;

public class Time implements SimpleServlet {

  private final String root;

  public Time(String root) {
    this.root = root;
  }

  @Override
  public void service(HttpRequest request, HttpResponse response) throws Exception {
    Date date = new Date();
    response.writeBody(date.toString());
  }
}
