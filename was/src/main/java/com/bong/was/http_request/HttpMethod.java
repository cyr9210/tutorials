package com.bong.was.http_request;

import java.util.Arrays;

public enum HttpMethod {
  GET, POST, DELETE, PATCH, PUT;

  public static HttpMethod findMethod(String methodName) {
    return Arrays.stream(HttpMethod.values()).filter(method -> method.name().equals(methodName))
        .findAny().orElseThrow(() -> new IllegalArgumentException());
  }

  public boolean isGet() {
    return HttpMethod.GET.equals(this);
  }
}
