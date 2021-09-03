package com.bong.was;

import java.util.Map;
import lombok.Getter;

@Getter
public class HttpRequestImpl implements HttpRequest {

  private final Map<String, String> headers;
  private final HttpMethod httpMethod;
  private final String path;

  public HttpRequestImpl(Map<String, String> headers, String methodName, String path) {
    this.headers = headers;
    this.httpMethod = HttpMethod.findMethod(methodName);
    this.path = path;
  }

  @Override
  public String getHeader(String headerName) {
    return headers.get(headerName);
  }

  @Override
  public HttpMethod getMethod() {
    return this.httpMethod;
  }

  @Override
  public String getPath() {
    return path;
  }

  @Override
  public String getExtension() {
    int extensionSeparator = path.lastIndexOf(".");
    if (extensionSeparator < 0) {
      if (httpMethod.isGet()) {
        return "java";
      }
      return "html";
    }
    int extensionStartIndex = extensionSeparator + 1;
    return path.substring(extensionStartIndex);
  }
}
