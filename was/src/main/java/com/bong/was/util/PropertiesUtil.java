package com.bong.was.util;

import com.bong.was.HttpServer;
import com.bong.was.properties.Properties;
import com.bong.was.properties.Properties.HostInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PropertiesUtil {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final Properties PROPERTIES;

  static {
    String resource = HttpServer.class.getResource("/properties.json").getFile();
    Properties properties = new Properties();
    try {
      List<String> strings = Files.readAllLines(Paths.get(resource));
      String jsonString = String.join("", strings);
      properties = OBJECT_MAPPER.readValue(jsonString, Properties.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    PROPERTIES = properties;
  }

  public static int getPort() {
    return PROPERTIES.getPort();
  }

  public static HostInfo getHostInfo(String host) {
    return PROPERTIES.getHostInfo(host);
  }

}
