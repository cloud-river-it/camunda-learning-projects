package com.company.poc.activitylog.config;
import connectjar.org.apache.http.impl.client.CloseableHttpClient;
import connectjar.org.apache.http.impl.client.HttpClients;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.impl.AbstractHttpConnector;
import org.camunda.connect.spi.ConnectorConfigurator;

public class HttpConnectorConfigurator implements ConnectorConfigurator<HttpConnector> {

  public Class<HttpConnector> getConnectorClass() {
    return HttpConnector.class;
  }

  public void configure(HttpConnector connector) {
    CloseableHttpClient client = HttpClients.custom()
        .setMaxConnPerRoute(10)
        .setMaxConnTotal(200)
        .build();
    ((AbstractHttpConnector) connector).setHttpClient(client);
  }
}
