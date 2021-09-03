package com.bong.was;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BaseTest {

  protected String connect(String requestUrl) {
    String result = null;
    try {
      URL url = new URL(requestUrl);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      if(conn != null){
        conn.setConnectTimeout(10000); // 10초 동안 기다린 후 응답이 없으면 종료
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        int resCode = conn.getResponseCode();
        if(resCode == HttpURLConnection.HTTP_OK){
          BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
          StringBuilder builder = new StringBuilder();
          while(true){
            String line = reader.readLine();
            if(line == null) {
              break;
            }
            builder.append(line);
          }
          result = builder.toString();
          reader.close();
        }
        conn.disconnect();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return result;
  }

}
