package com.bong.was;

public interface HttpRequest {

  String getHeader(String headerName);
  HttpMethod getMethod();
  String getPath();
  String getExtension();

}
