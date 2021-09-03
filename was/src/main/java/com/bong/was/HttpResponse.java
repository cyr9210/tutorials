package com.bong.was;

import java.io.IOException;

public interface HttpResponse {
  void writeErrorPage(String error) throws IOException;

  void writeBody(String body) throws IOException;

}
