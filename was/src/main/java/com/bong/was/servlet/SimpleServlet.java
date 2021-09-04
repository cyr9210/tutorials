package com.bong.was.servlet;

import com.bong.was.http_request.HttpRequest;
import com.bong.was.http_response.HttpResponse;

public interface SimpleServlet {

   void service(HttpRequest request, HttpResponse response) throws Exception;

}
