package com.bong.was.util;

import com.bong.was.properties.Properties;
import com.bong.was.properties.Properties.HostInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PropertiesUtil {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final Properties PROPERTIES;

  static {
    InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("properties.json");
    Properties properties = new Properties();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    try {
      StringBuffer buffer = new StringBuffer();
      int read;
      char[] bf = new char[1024];
      while ((read = bufferedReader.read(bf)) > 0) {
        buffer.append(bf, 0, read);
      }
      String jsonString = buffer.toString();
      inputStream.close();
      bufferedReader.close();
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
