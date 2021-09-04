package com.bong.was.http_request;

public interface HttpRequest {

  String getHeader(String headerName);
  HttpMethod getMethod();
  String getFileName();
  String getExtension();
  String getParameter(String parameterNmae);
  String getHostHeader();

}
