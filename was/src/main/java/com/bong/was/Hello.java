package com.bong.was;

import java.io.IOException;

public class Hello implements SimpleServlet {

  private final String root;

  public Hello(String root) {
    this.root = root;
  }

  @Override
  public void service(HttpRequest request, HttpResponse response) throws IOException {
    StringBuilder builder = new StringBuilder();
    builder.append("Hello, ");
    builder.append(request.getParameter("name"));
    String body = builder.toString();
    response.writeBody(body);
  }
}
