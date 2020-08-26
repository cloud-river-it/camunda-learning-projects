package com.company.poc.activitylog.model;

import java.util.HashMap;
import java.util.Map;

public class ResponseCorrelation {

  private String messageName;
  private String businessKey;
  Map<String, ResponseProcessVariables> processVariables =  new HashMap<>();


  public ResponseCorrelation(){

  }

  public ResponseCorrelation(String businessKey, String messageName) {
    this.messageName = messageName;
    this.businessKey = businessKey;
  }

  public String getMessageName() {
    return messageName;
  }

  public void setMessageName(String messageName) {
    this.messageName = messageName;
  }

  public String getBusinessKey() {
    return businessKey;
  }

  public void setBusinessKey(String businessKey) {
    this.businessKey = businessKey;
  }

  public Map<String, ResponseProcessVariables> getProcessVariables() {
    return processVariables;
  }

  public void setProcessVariables(
      Map<String, ResponseProcessVariables> processVariables) {
    this.processVariables = processVariables;
  }
}
