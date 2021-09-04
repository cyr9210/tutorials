package com.bong.was.http_response;

import com.bong.was.http_request.HttpRequest;
import com.bong.was.properties.Properties.PageInfo;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class HttpResponseImpl implements HttpResponse{

  public static final String LINE_SEPARATOR = "\r\n";
  private final OutputStream stream;
  private final Writer writer;
  private final HttpRequest request;
  private final String root;
  private final PageInfo pageInfo;

  public HttpResponseImpl(OutputStream stream, Writer writer, String root, HttpRequest request,
      PageInfo pageInfo) {
    this.stream = stream;
    this.writer = writer;
    this.root = root;
    this.request = request;
    this.pageInfo = pageInfo;
  }

  private byte[] getFileBytes(String root, String path) throws IOException {
    Path notFoundPath = Paths.get(root, path);
    return Files.readAllBytes(notFoundPath);
  }

  private void sendHeader(Writer writer, String responseCode, String contentType, int length) throws IOException {
    writer.write(responseCode + LINE_SEPARATOR);
    Date now = new Date();
    writer.write("Date: " + now + LINE_SEPARATOR);
    writer.write("Server: JHTTP 2.0" + LINE_SEPARATOR);
    writer.write("Content-length: " + length + LINE_SEPARATOR);
    writer.write("Content-type: " + contentType + LINE_SEPARATOR + LINE_SEPARATOR);
    writer.flush();
  }

  @Override
  public void writeErrorPage(HttpError error) throws IOException {
    byte[] bytes = getFileBytes(root, "/errors/" + error.getErrorPage(pageInfo));
    sendHeader(writer, "HTTP/1.1 " + error.getStatusCode(), "text/html; charset=utf-8", bytes.length);
    stream.write(bytes);
    stream.flush();
  }

  @Override
  public void writeBody(String body) throws IOException {
    String contentType = URLConnection.getFileNameMap().getContentTypeFor(request.getFileName());
    if (contentType == null) {
      contentType = "text/plain";
    }
    sendHeader(writer, "HTTP/1.1 200 OK", contentType, body.length());
    writer.write(body);
    writer.flush();
  }

  @Override
  public void writePage(String fileName) throws IOException {
    Path file = Paths.get(root, fileName.substring(1));
    if (Files.isReadable(file)) {
      String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
      byte[] theData = Files.readAllBytes(file);
      sendHeader(writer, "HTTP/1.0 200 OK", contentType, theData.length);
      stream.write(theData);
    } else {
      writeErrorPage(HttpError.NOT_FOUND);
      return;
    }
    stream.flush();
  }

}
