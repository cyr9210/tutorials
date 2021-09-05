package com.bong.was.properties;

import java.util.ArrayList;
import java.util.List;

public class Properties {

  private int port;
  private List<HostInfo> hostInfos = new ArrayList<>();

  public int getPort() {
    return port;
  }

  public List<HostInfo> getHostInfos() {
    return hostInfos;
  }

  public HostInfo getHostInfo(String host) {
    return hostInfos.stream()
        .filter(info -> info.getHost().equals(host))
        .findAny().orElse(hostInfos.get(0));
  }

  public static class HostInfo {

    private String host;
    private String name;
    private String packagePath;
    private PageInfo pageInfo;

    public String getHost() {
      return host;
    }

    public String getName() {
      return name;
    }

    public String getPackagePath() {
      return packagePath;
    }

    public PageInfo getPageInfo() {
      return pageInfo;
    }
  }

  public static class PageInfo {
    private String index;
    private String notFound;
    private String forbidden;
    private String serverError;

    public String getIndex() {
      return index;
    }

    public String getNotFound() {
      return notFound;
    }

    public String getForbidden() {
      return forbidden;
    }

    public String getServerError() {
      return serverError;
    }
  }
}
