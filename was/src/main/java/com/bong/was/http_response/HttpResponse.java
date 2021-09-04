package com.bong.was.http_response;

import java.io.IOException;

public interface HttpResponse {
  void writeErrorPage(HttpError error) throws IOException;

  void writeBody(String body) throws IOException;

  void writePage(String fileName) throws IOException;

}
