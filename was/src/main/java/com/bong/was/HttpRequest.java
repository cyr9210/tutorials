package com.bong.was;

public interface HttpRequest {

  String getHeader(String headerName);
  HttpMethod getMethod();
  String getFileName();
  String getExtension();
  String getParameter(String parameterNmae);

}
