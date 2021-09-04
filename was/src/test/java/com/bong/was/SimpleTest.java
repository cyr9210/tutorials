package com.bong.was;

import com.bong.was.properties.Properties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;

public class SimpleTest {

  @Test
  public void jsonParse() throws IOException {
    List<String> strings = Files.readAllLines(Paths.get("/Users/yrchoi/Bong/tutorials/was/src/main/resources/properties.json"));
    String jsonString = String.join("", strings);
    ObjectMapper objectMapper = new ObjectMapper();
    Properties properties = objectMapper.readValue(jsonString, Properties.class);
  }
}
