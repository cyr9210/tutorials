package com.bong.was.servlet;

import com.bong.was.http_request.HttpRequest;
import com.bong.was.http_response.HttpError;
import com.bong.was.http_response.HttpResponse;
import com.bong.was.properties.Properties.HostInfo;
import java.io.IOException;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleServletImpl implements SimpleServlet {

  private static Logger logger = LoggerFactory.getLogger(SimpleServlet.class);

  private static final String UPPER_PATH = "../";

  private final HostInfo hostInfo;

  public SimpleServletImpl(HostInfo hostInfo) {
    this.hostInfo = hostInfo;
  }

  @Override
  public void service(HttpRequest request, HttpResponse response) throws IOException {
    String path = request.getFileName();
    if (request.getExtension().equals("exe") || path.contains(UPPER_PATH)) {
      response.writeErrorPage(HttpError.FORBIDDEN);
      return;
    }

    if (request.getExtension().equals("html")) {
      response.writePage(path);
      return;
    }

    try {
      String url = path.substring(1);
      Class<?> aClass = Class.forName(hostInfo.getPackagePath() + "." + url);
      Object targetServlet = aClass.newInstance();
      Method service = aClass.getDeclaredMethod("service", HttpRequest.class, HttpResponse.class);
      service.invoke(targetServlet, request, response);
    } catch (Exception e) {
      logger.debug(e.getMessage(), e);
      response.writeErrorPage(HttpError.SERVER_ERROR);
    }
  }

}
