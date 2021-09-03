package com.bong.was;

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
  private static final int BUFFER_SIZE = 1_024 * 1_024;
  private final OutputStream stream;
  private final Writer writer;
  private final HttpRequest request;
  private final String root;

  public HttpResponseImpl(OutputStream stream, Writer writer, String root, HttpRequest request ) {
    this.stream = stream;
    this.writer = writer;
    this.root = root;
    this.request = request;
  }

//  @Override
//  public void writePage(String path) throws IOException {
//    Path file = Paths.get(root, path.substring(1));
//    if (Files.isReadable(file)) {
//      String contentType = URLConnection.getFileNameMap().getContentTypeFor(path);
//      byte[] theData = Files.readAllBytes(file);
//      sendHeader(writer, "HTTP/1.0 200 OK", contentType, theData.length);
//      stream.write(theData);
//    } else {
//      byte[] bytes = getFileBytes(root, "/errors/404.html");
//      sendHeader(writer, "HTTP/1.0 404 File Not Found", "text/html; charset=utf-8", bytes.length);
//      stream.write(bytes);
//    }
//    stream.flush();
//  }

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
  public void writeErrorPage(String error) throws IOException {
    byte[] bytes = getFileBytes(root, "/errors/" + error + ".html");
    sendHeader(writer, "HTTP/1.1 403 forbidden", "text/html; charset=utf-8", bytes.length);
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

}
