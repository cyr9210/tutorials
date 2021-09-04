package com.bong.was.http_request;

import java.util.Map;

public class HttpRequestImpl implements HttpRequest {

  public static final String INDEX_PATH = "index.html";
  public static final String HOST_KEY = "Host";
  public static final String DEFAULT_HOST = "a.com";
  private final Map<String, String> headers;
  private final Map<String, String> parameters;
  private final HttpMethod httpMethod;
  private final String fileName;

  public HttpRequestImpl(Map<String, String> headers, Map<String, String> parameters, String methodName, String fileName) {
    this.headers = headers;
    this.parameters = parameters;
    this.httpMethod = HttpMethod.findMethod(methodName);
    this.fileName = fileName;
  }

  @Override
  public String getHeader(String headerName) {
    return headers.get(headerName);
  }

  @Override
  public HttpMethod getMethod() {
    return this.httpMethod;
  }

  public String getFileName() {
    if (fileName.endsWith("/")) {
      return fileName + INDEX_PATH;
    }
    return fileName;
  }

  @Override
  public String getExtension() {
    String fileName = getFileName();
    int extensionSeparator = fileName.lastIndexOf(".");
    if (extensionSeparator < 0) {
      return "";
    }
    int extensionStartIndex = extensionSeparator + 1;
    return fileName.substring(extensionStartIndex);
  }

  @Override
  public String getParameter(String parameterName) {
    return parameters.get(parameterName);
  }

  @Override
  public String getHostHeader() {
    if (!headers.containsKey(HOST_KEY)) {
      return DEFAULT_HOST;
    }
    return getHeader(HOST_KEY);
  }
}
