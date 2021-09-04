package com.bong.was.http_response;

import com.bong.was.properties.Properties.PageInfo;

public enum HttpError {
  NOT_FOUND("404 not found"),
  FORBIDDEN("403 forbidden"),
  SERVER_ERROR("500 serverError");

  private String statusCode;

  HttpError(String statusCode) {
    this.statusCode = statusCode;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public String getErrorPage(PageInfo pageInfo) {
    if (NOT_FOUND.equals(this)) {
      return pageInfo.getNotFound();
    }

    if (FORBIDDEN.equals(this)) {
      return pageInfo.getForbidden();
    }

    return pageInfo.getServerError();
  }
}
