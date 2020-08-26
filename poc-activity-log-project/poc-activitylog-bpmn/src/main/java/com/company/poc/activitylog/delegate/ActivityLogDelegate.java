package com.company.poc.activitylog.delegate;

import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.springframework.stereotype.Component;

@Component
public class ActivityLogDelegate implements JavaDelegate {


  private static final Logger LOGGER = Logger.getLogger(ActivityLogDelegate.class.getName());


  private static String URI = "http://localhost:8083/activity";

  @Override
  public void execute(DelegateExecution delegateExecution) {
    String taskId = delegateExecution.getVariable("CALLER_ID").toString();
    String businessKey =  delegateExecution.getBusinessKey();

    LOGGER.info("businessKey : " +  businessKey + "taskId : " +  taskId);

    this.postRequest(this.getPayload(businessKey, taskId));
    delegateExecution.setVariable(taskId, true);
    delegateExecution.setVariable("businessKey", businessKey);
  }


  private String postRequest(final String request) {
    LOGGER.info("postRequest : " +  request);
    HttpConnector http = Connectors.getConnector(HttpConnector.ID);
    Object response = http.createRequest()
        .post()
        .url(URI)
        .contentType("application/json")
        .payload(request)
        .execute().getResponse();

    return response.toString();
  }

  private String getPayload(final String businessKey, final String value) {
    LOGGER.info("{\"businessKey\" : \"" + businessKey + "\", \"value\" : \""  + value + "\", \"bpmFlag\" : \"" + true + "\"}");
    return "{\"businessKey\" : \"" + businessKey + "\", \"value\" : \""  + value + "\", \"bpmFlag\" : \"" + true + "\"}";
  }
}
